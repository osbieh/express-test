/**
 * Created by osbie on 3/22/2017.
 */

var orderDetails = function () {

    return {

        addOrderDetailsTable: function () {
            orderDetailsTable = $("#orderDetailsTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },
                "lengthMenu": [[3, 10, 50, -1], [3, 10, 50, "All"]],
                "paging": true,
                "ordering": true,
                "info": true,
                stateSave: true,
                sPaginationType: "full_numbers",
                bJQueryUI: true,
                "dom": 'lf<"toolbar">rt<"footer"><"bottom"ip><"clear">',
                "aaSorting": [[1, 'asc']],
                initComplete: function () {
                    $("div.toolbar")
                        .html(' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="orderDetails.addOrderDetailsItem();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>');

                },
                "fnDrawCallback": function (oSettings) {
                    /* Need to redo the counters if filtered or sorted */
                    if (oSettings.bSorted || oSettings.bFiltered) {
                        for (var i = 0, iLen = oSettings.aiDisplay.length; i < iLen; i++) {
                            $('td:eq(0)', oSettings.aoData[oSettings.aiDisplay[i]].nTr).html(i + 1);
                        }
                    }
                },

                fnFooterCallback: function (nRow, aaData, iStart, iEnd, aiDisplay) {
                    var api = this.api(), data;

                    // Remove the formatting to get integer data for summation
                    var intVal = function (i) {
                        return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '') * 1 :
                            typeof i === 'number' ?
                                i : 0;
                    };

                    // Total over all pages
                    total = api
                        .column(orderDetails.getColumnIndex("total"))
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    // Total over this page
                    pageTotal = api
                        .column(orderDetails.getColumnIndex("total"), {page: 'current'})
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    var footer = $("tfoot")[0];
                    var columnIndex = orderDetails.getColumnIndex("total");
                    var totalCell = footer.children[0].children[columnIndex];
                    var discountCell = footer.children[1].children[columnIndex];
                    var totalAmountCell = footer.children[2].children[columnIndex];


                    var pages = orderDetailsTable.page.info().pages;
                    if (pages > 1) {
                        // $($("tfoot")[0].children[0].children[orderDetails.getColumnIndex("total")]).html(pageTotal + '( من ' + total + ')<i class="fa fa-ils"></i> ');
                        $(totalCell).html(pageTotal + '  <i class="fa fa-ils"></i> ');
                        $(totalAmountCell).html(total + '  <i class="fa fa-ils"></i> ');
                    } else {
                        $(totalCell).html(total + '  <i class="fa fa-ils"></i> ');
                        $(totalAmountCell).html(total + '  <i class="fa fa-ils"></i> ');
                    }


                },

                "aoColumnDefs": [
                    {"bSortable": false, "aTargets": [0]}
                ],
                "columns": [
                    {"width": "1%", "data": "rowNum", "name": "rowNum"},
                    {"width": "20%", "data": "productName", "name": "productName"},
                    {"width": "10%", "data": "productUnit", "name": "productUnit"},
                    {"width": "5%", "data": "quantity", "name": "quantity"},
                    {"width": "5%", "data": "unitPrice", "name": "unitPrice"},
                    {"width": "5%", "data": "discount", "name": "discount"},
                    {"width": "15%", "data": "total", "name": "total"},
                    {
                        "width": "29%", "data": "actions", "name": "actions",
                        render: function (data, type, full, meta) {

                            return ' <div style="padding-top:6px;">' +

                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="orderDetails.getInfo();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Details" style="margin-left:10px;" class="glyphicon glyphicon-tasks" onclick="dailyMovement.editDetails();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Save" style="margin-left:10px;" class="glyphicon glyphicon-save" onclick="dailyMovement.saveRecord(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Delete" style="margin-left:10px;" class="glyphicon glyphicon-trash" onclick="orderDetails.deleteOrderDetailsItem(' + meta.row + ');" aria-hidden="true"></a>' +

                                '<div> ';

                        }
                    },
                    {"width": "1%", "data": "orderDetailsId", "name": "orderDetailsId", "visible": false}

                ]
            });


            orderDetailsTable.on('order.dt search.dt', function () {
                orderDetailsTable.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();


        },
        addOrderDetailsItem: function () {
            var row;
            try {
                row = orderDetailsTable.row.add({
                    "rowNum": '',
                    "productName": '',
                    "productUnit": '',
                    "quantity": '',
                    "unitPrice": '',
                    "total": '',
                    "discount": '',
                    "actions": '',
                    "orderDetailsId": '',
                });
                orderDetailsTable.page('last').order([[1, 'desc']]).draw(false);

                var rowsCount = orderDetailsTable.rows().count();

                var pagesLength = orderDetailsTable.page.len();

                if ((rowsCount % pagesLength) == 1)
                    orderDetailsTable.page('last').order([[1, 'desc']]).draw(false);

                orderDetails.makeOrderDetailsInEditMode(row);
                orderDetails.addRowStatus(row, "new");

            } catch (e) {
                console.log("exception", e);
            }

        },

        addRowStatus: function (row, status) {

            var record = row.node();
            var currentStatus = $(record).attr("status");
            if ((currentStatus == undefined) || (currentStatus == "")) {
                $(record).attr("status", status);
                return;
            }
            var currentStatusArr = currentStatus.split(",");
            currentStatusArr.push(status);
            var uniqueNames = [];
            $.each(currentStatusArr, function (i, el) {
                if ($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
            });
            $(record).attr("status", uniqueNames.join(","));
        },

        setRowMode: function (row, mode) {
            $(row.node()).attr("mode", mode);  //set Row To be In Edit Mode
        },
        isRowInEditMode: function (index) {
            var row = orderDetailsTable.row(index);
            return $(row.node()).attr("mode") == "edit";
        },

        getRowStatus: function (row) {

            return $(row.node()).attr("status");
        },
        getRowMode: function (row) {
            return $(row.node()).attr("mode");
        },

        deleteOrderDetailsItem: function (index) {

            var data = orderDetailsTable.row(index).data();

            if (data.orderDetailsId) {

            } else {
                orderDetailsTable.rows(index).remove().draw();
            }


            console.log(index, '  --- ', orderDetailsTable.rows().data());

        },
        orderDetailsEvents: function () {

            orderDetails.orderDetailsTableDbClick();

        },
        orderDetailsTableDbClick: function () {
            $('#orderDetailsTable').find('tbody').on('dblclick', 'tr', function () {
                var row = orderDetailsTable.row(this);
                var index = orderDetailsTable.row(this).index();
                var editMode = row.node().getAttribute('control');
                if (!orderDetails.isRowInEditMode(index)) {
                    var fieldsId = orderDetails.makeOrderDetailsInEditMode(row);
                    orderDetails.setRowValuesInEditMode(index, fieldsId);
                    orderDetails.addRowStatus(row, "update");
                }

            });

        },
        getColumnIndex: function (colName) {
            return orderDetailsTable.column(colName + ':name').index();
        },
        makeOrderDetailsInEditMode: function (row) {
            var date = new Date().getMilliseconds();
            orderDetails.setRowMode(row, 'edit');

            row = row.node();
            var rowNum = row.children[orderDetails.getColumnIndex("rowNum")];
            //   var orderDetailsId = row.children[orderDetails.getColumnIndex("orderDetailsId")];
            var product = row.children[orderDetails.getColumnIndex("productName")];
            var productUnit = row.children[orderDetails.getColumnIndex("productUnit")];
            var quantity = row.children[orderDetails.getColumnIndex("quantity")];
            var unitPrice = row.children[orderDetails.getColumnIndex("unitPrice")];
            var discount = row.children[orderDetails.getColumnIndex("discount")];
            var total = row.children[orderDetails.getColumnIndex("total")];
            var transaction = row.children[orderDetails.getColumnIndex("actions")];


            var productVal = product.innerHTML;

            var productName = "productName-" + date
            var productId = "productId-" + date
            var hidden = "hiddenProductId-" + date
            var productUtoCompleteField = $.ajax({
                url: common.projectPath + "/product/productAutoComplete",
                data: {id: productId, name: productName, hidden: hidden},
                success: function (response) {
                    $(product).html(response);

                },
            });
            product.innerHTML = productUtoCompleteField;

            productUnit.innerHTML = orderDetailVars.productUnits;
            var unitId = productUnit.children[0].id = "unit-" + date;
            var unitName = productUnit.children[0].name = "unit-" + date;
            var unitStyle = productUnit.children[0].style = "padding:unset;";


            quantity.innerHTML = "<input  class='form-control' type='text' value='1'>";
            var quantityId = quantity.children[0].id = "quantity-" + date;
            var quantityName = quantity.children[0].name = "quantity-" + date;


            unitPrice.innerHTML = "<input  class='form-control' type='text'>";
            var unitPriceId = unitPrice.children[0].id = "unitPrice-" + date;
            var unitPriceName = unitPrice.children[0].name = "unitPrice-" + date;

            discount.innerHTML = "<div class='form-inline'>" +
                "<input  style='border-radius: 40px !important; border-left: medium none; width: 90px;' class='form-control' type='text' value='0'>" +
                "<span style='border-radius: 40px !important; border-right: medium none; margin-right: -39px;  font-weight: bold; line-height: 22px; font-size: 30px; color: rgb(223, 112, 215);' class='form-control'>%</span>"+
                "</div>";


            var discountId = discount.children[0].children[0].id = "discount-" + date;
            var discountName = discount.children[0].children[0].name = "discount-" + date;

            var discountIconId = discount.children[0].children[1].id = "discountIcon-" + date;
            var discountIconName = discount.children[0].children[1].name = "discountIcon-" + date;

            $('#'+discountIconId).on('click',function(){
                if(this.innerHTML!='%'){
                    this.innerHTML='%';

                }else{
                    this.innerHTML='-';
                }

            });


            $("#" + quantityId + ",#" + unitPriceId).on('keydown', function (e) {
                -1 !== $.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) || /65|67|86|88/.test(e.keyCode) && (!0 === e.ctrlKey || !0 === e.metaKey) || 35 <= e.keyCode && 40 >= e.keyCode || (e.shiftKey || 48 > e.keyCode || 57 < e.keyCode) && (96 > e.keyCode || 105 < e.keyCode) && e.preventDefault()
            });

            var footer = $("tfoot")[0];
            var columnIndex = orderDetails.getColumnIndex("total");
            var discountCell = footer.children[1].children[columnIndex];

            discountCell.innerHTML= "<div class='form-inline'>" +
                "<input  style='border-radius: 40px !important; border-left: medium none; width: 90px;' class='form-control' type='text' value='0'>" +
                "<span style='border-radius: 40px !important; border-right: medium none; margin-right: -39px;  font-weight: bold; line-height: 22px; font-size: 30px; color: rgb(223, 112, 215);' class='form-control'>%</span>"+
                "</div>";



            $("#" + unitPriceId + ",#" + quantityId+",#"+discountId).on("keyup change", function () {
                if($("#"+discountIconId).text()=="%"){
                    if($("#"+discountId).val()>0){
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                        total=((100-$("#"+discountId).val())/100)*total;
                    }else{
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                    }
                }else {
                    if($("#"+discountId).val()>0){
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                        total=total-($("#"+discountId).val());
                    }else{
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                    }


                }
                var cell = orderDetailsTable.cell(row, orderDetails.getColumnIndex("total"));
                cell.node().style = "font-size: 20px;vertical-align: middle;";
                cell.data(total);
                setTimeout(function () {
                    orderDetailsTable.page(orderDetailsTable.page()).draw(false);
                }, 2000);

            });

            $("#"+discountIconId).on('click',function(){
                if($("#"+discountIconId).text()=="%"){
                    if($("#"+discountId).val()>0){
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                        total=((100-$("#"+discountId).val())/100)*total;
                    }else{
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                    }
                }else {
                    if($("#"+discountId).val()>0){
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                        total=total-($("#"+discountId).val());
                    }else{
                        var total = $("#"+unitPriceId).val() * $("#"+quantityId).val();
                    }

                }
                var cell = orderDetailsTable.cell(row, orderDetails.getColumnIndex("total"));
                cell.node().style = "font-size: 20px;vertical-align: middle;";
                cell.data(total);
                setTimeout(function () {
                    orderDetailsTable.page(orderDetailsTable.page()).draw(false);
                }, 2000);

            });

            return date;
        },

        setRowValuesInEditMode: function (rowIndex, fieldId) {
            var data = orderDetailsTable.row(rowIndex).data();
            $('#unit-' + fieldId).val(data.productUnit2);
            $('#quantity-' + fieldId).val(data.quantity);
            $('#unitPrice-' + fieldId).val(data.unitPrice);
            var discount=data.discount.split(' ');

           if(discount.length>1){
               $('#discountIcon-'+fieldId).text(discount[0]);
               $('#discount-'+fieldId).val(discount[1]);
           }else{
               $('#discountIcon-'+fieldId).text('%');
               $('#discount-'+fieldId).val(0);
           }

            setTimeout(function () {
                $('#productId-' + fieldId).val(data.productName);
                $('#hiddenProductId-' + fieldId).val(data.productId);

            }, 200);
        },
        getOrderDetailsRecord: function (index) {

            var row = orderDetailsTable.row(index);
            var record = row.data();
            var node = row.node();
            record.rowStatus = orderDetails.getRowStatus(row);
            record.rowMode = orderDetails.getRowMode(row);

            if (orderDetails.isRowInEditMode(index)) {
                record.rowNum = $(node.children[orderDetails.getColumnIndex("rowNum")]).text();
                record.orderDetailsId = record.orderDetailsId;//$(node.children[orderDetails.getColumnIndex("orderDetailsId")]).text();
                record.productName = $(node.children[orderDetails.getColumnIndex("productName")]).find("[type=text]").val();
                record.productId = $(node.children[orderDetails.getColumnIndex("productName")]).find("[type=hidden]").val();
                record.productUnit = $(node.children[orderDetails.getColumnIndex("productUnit")]).find("option:selected").val();
                record.quantity = $(node.children[orderDetails.getColumnIndex("quantity")]).find("[type=text]").val();
                record.unitPrice = $(node.children[orderDetails.getColumnIndex("unitPrice")]).find("[type=text]").val();
                var discount=$(node.children[orderDetails.getColumnIndex("discount")]);

                var disVal=discount.find("[type=text]").val();
                if(disVal!=0){
                    var disSign=discount.find("span").text();
                    record.discount=disSign+' '+disVal;
                }

                record.total = $(node.children[orderDetails.getColumnIndex("total")]).text();

            }


            return JSON.stringify(record);
        },
        getAllEditedOrderDetailsRecord: function () {
            var records = Array();
            for (i = 0; i < orderDetailsTable.rows().count(); i++) {
                record = orderDetails.getOrderDetailsRecord(i);
                var row = orderDetailsTable.row(i);
                console.log("record::: ", JSON.parse(record));
                if (orderDetails.isRowInEditMode(i)) {
                    records.push(JSON.parse(record));
                }

            }
            return records;


        },
        manageOrder: function () {
            $.ajax({
                url: common.projectPath + "/orderP/manageOrder",
                data: {
                    order: orderForm.getOrderFormInfo,
                    details: JSON.stringify(orderDetails.getAllEditedOrderDetailsRecord())
                },
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {
                    toastr.success("Order are modified", "Success");
                    orderDetails.modalClose();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                      toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                    // $('#myModal').modal('hide');

                }
            });

        },
        modalClose: function () {
            $('#dailyMovementModal').modal('hide');

        },
        refreshOrderDetailsTable: function () {
            var table = $("#orderDetailsTable").DataTable();
            var tableId = $("#orderDetailsTable");
            var urlData = common.projectPath + "/orderP/loadOrderDetails";
            var data = {orderId: $("#orderId").val()};

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);

                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                orderDetailsTable.page(orderDetailsTable.page()).draw(false);
            });


        },

        init: function () {
            orderDetails.addOrderDetailsTable();
            orderDetails.refreshOrderDetailsTable();
            orderDetails.orderDetailsEvents();
            setTimeout(function () {
                if ($("#orderStatus").val() == "COMPLETED") {
                    $("#modal-body a.btn.btn-circle.btn-icon-only.btn-default").addClass("hide");
                    $(".modal-footer .btn.btn-primary").addClass("hide");
                }
            }, 500);
        }
    }

}();
