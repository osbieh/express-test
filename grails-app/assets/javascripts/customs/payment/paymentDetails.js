/**
 * Created by osbie on 4/9/2017.
 */
var paymentDetails = function () {
    return {

        addPaymentDetailsTable: function () {

            paymentDetailsTable = $("#paymentDetailsTable").DataTable({
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
                        .html(' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="paymentDetails.addPaymentDetailsItem();">' +
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
                    total = api.column(paymentDetails.getColumnIndex("total2")).data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);


                    // Total over this page
                    pageTotal = api
                        .column(paymentDetails.getColumnIndex("total2"), {page: 'current'}).data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    var pages = paymentDetailsTable.page.info().pages;
                    if (pages > 1)
                        $($("tfoot")[0].children[0].children[paymentDetails.getColumnIndex("total")]).html(pageTotal + '( من ' + total + ')<i class="fa fa-ils"></i> ');
                    else
                        $($("tfoot")[0].children[0].children[paymentDetails.getColumnIndex("total")]).html(total + '  <i class="fa fa-ils"></i> ');

                },

                "columns": [
                    {"width": "1%",  "data": "rowNum", "name": "rowNum"},
                    {"width": "10%", "data": "paymentType", "name": "paymentType"},
                    {"width": "20%", "data": "total", "name": "total"},
                    {"width": "5%",  "data": "currency", "name": "currency", "visible":false},
                    {"width": "29%", "data": "actions", "name": "actions",
                        render: function (data, type, full, meta) {

                            return '<div style="padding-top:6px;">' +
                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="orderDetails.getInfo();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Details" style="margin-left:10px;" class="glyphicon glyphicon-tasks" onclick="dailyMovement.editDetails();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Save" style="margin-left:10px;" class="glyphicon glyphicon-save" onclick="dailyMovement.saveRecord(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Delete" style="margin-left:10px;" class="glyphicon glyphicon-trash" onclick="orderDetails.deleteOrderDetailsItem(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<div>';

                        }
                    },
                    {"width": "1%", "data": "paymentDetailsId", "name": "paymentDetailsId", "visible":false},
                    {"width": "1%", "data": "total2","name": "total2","visible":false}

                ]

            });

            paymentDetailsTable.on('order.dt search.dt', function () {
                paymentDetailsTable.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                    cell.innerHTML = i + 1;
                });
            }).draw();

            paymentDetailsTable.column(paymentDetails.getColumnIndex("total2")).visible(false);

        },
        getColumnIndex: function (colName) {
            return paymentDetailsTable.column(colName + ':name').index();
        },

        addPaymentDetailsItem: function () {

            var row;
            try {
                row = paymentDetailsTable.row.add({
                    "rowNum": '',
                    "paymentType": '',
                    "total": '',
                    "currency": '',
                    "actions": '',
                    "paymentDetailsId": '',
                    "total2": ''
                });
                paymentDetailsTable.page('last').order([[1, 'desc']]).draw(false);
                var rowsCount = paymentDetailsTable.rows().count();
                var pagesLength = paymentDetailsTable.page.len();
                if ((rowsCount % pagesLength) == 1)
                    paymentDetailsTable.page('last').order([[1, 'desc']]).draw(false);

                paymentDetails.makePaymentDetailsInEditMode(row);

                paymentDetails.setRowMode(row, 'edit');
                paymentDetails.addRowStatus(row, "new");


            } catch (e) {
                console.log("exception", e);
            }


        },
        makePaymentDetailsInEditMode: function (row) {
            console.log(row);
            var date = new Date().getMilliseconds();
            row = row.node();
            var rowNum = row.children[paymentDetails.getColumnIndex("rowNum")];
            var paymentType = row.children[paymentDetails.getColumnIndex("paymentType")];
            var total = row.children[paymentDetails.getColumnIndex("total")];
            var currency = row.children[paymentDetails.getColumnIndex("currency")];
            var actions = row.children[paymentDetails.getColumnIndex("actions")];
            var paymentDetailsId = row.children[paymentDetails.getColumnIndex("paymentDetailsId")];
            var total2 = row.children[paymentDetails.getColumnIndex("total2")];

            var paymentTypeVal=paymentType.innerHTML;
            paymentType.innerHTML=paymentDetailVars.paymentType;
            paymentTypeId= paymentType.children[0].id ="paymentType-"+date;
            paymentTypeName=paymentType.children[0].name ="paymentType-"+date;
            //var currencyVal = currency.innerHTML;
            //currency.innerHTML =paymentDetailVars.currencyType;

            var totalVal=total.innerHTML;
            total.innerHTML="<input type='text' class='form-control'>";
            var totalId = total.children[0].id = "total-" + date;
            var totalName = total.children[0].name = "total-" + date;

            $("#" + totalId).on('keydown', function (e) {
                -1 !== $.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) || /65|67|86|88/.test(e.keyCode) && (!0 === e.ctrlKey || !0 === e.metaKey) || 35 <= e.keyCode && 40 >= e.keyCode || (e.shiftKey || 48 > e.keyCode || 57 < e.keyCode) && (96 > e.keyCode || 105 < e.keyCode) && e.preventDefault()
            });

            $("#" + totalId).on("keyup change", function () {
                var totalFieldVal = $("#"+totalId).val();
                var cell = paymentDetailsTable.cell(row, paymentDetails.getColumnIndex("total2"));
                cell.data(totalFieldVal);
                setTimeout(function () {
                    paymentDetailsTable.page(paymentDetailsTable.page()).draw(false);
                }, 2000);
            });

            return date;

        },

        setRowValuesInEditMode: function (rowIndex, fieldId) {
            var data = paymentDetailsTable.row(rowIndex).data();
            $('#total-'+fieldId).val(data.total2);
             $('#paymentType-'+fieldId).val(data.paymentTypeId);
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
        isRowInEditMode: function (index) {
            var row= paymentDetailsTable.row(index);
            return $(row.node()).attr("mode") == "edit";
        },
        getRowStatus: function (row) {

            return $(row.node()).attr("status");
        },
        getRowMode: function (row) {
            return $(row.node()).attr("mode");
        },
        setRowMode: function (row, mode) {
            $(row.node()).attr("mode", mode);  //set Row To be In Edit Mode
        },

        getAllPaymentDetailsRecord:function(){
            var records = Array();
            for (i = 0; i < paymentDetailsTable.rows().count(); i++) {
                record = paymentDetails.getPaymentDetailsRecord(i);
                var row = paymentDetailsTable.row(i);
                console.log("record::: ", JSON.parse(record));
                if(paymentDetails.isRowInEditMode(i)){
                    records.push(JSON.parse(record));
                }

            }
            return records;

        },

        getPaymentDetailsRecord:function(index){
            var row = paymentDetailsTable.row(index);
            var record = row.data();
            var node = row.node();
            record.rowStatus = orderDetails.getRowStatus(row);
            record.rowMode = orderDetails.getRowMode(row);

            if (paymentDetails.isRowInEditMode(index)) {
                record.rowNum = $(node.children[paymentDetails.getColumnIndex("rowNum")]).text();
                record.paymentType=$(node.children[paymentDetails.getColumnIndex("paymentType")]).find("option:selected").val();
                record.paymentDetailsId = record.paymentDetailsId;//$(node.children[orderDetails.getColumnIndex("orderDetailsId")]).text();
                record.total = $(node.children[paymentDetails.getColumnIndex("total")]).text();

            }


            return JSON.stringify(record);
        },


        managePayment: function () {
            $.ajax({
                url: common.projectPath + "/payment/manageWholePayment",
                data: {
                    payment: paymentForm.getPaymentFormInfo,
                    details: JSON.stringify(paymentDetails.getAllPaymentDetailsRecord())
                },
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {

                    toastr.success("payment are Created", "Success");
                    dailyMovement.refreshTable();
                    paymentDetails.modalClose();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                    //    toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                    // $('#myModal').modal('hide');

                }
            });

        },

        refreshPaymentDetailsTable: function () {
            var table = $("#paymentDetailsTable").DataTable();
            var tableId = $("#paymentDetailsTable");
            var urlData = common.projectPath + "/payment/loadPaymentDetails";
            var data = {paymentId: $("#paymentId").val()};

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);

                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                paymentDetailsTable.page(paymentDetailsTable.page()).draw(false);
            });


        },

        paymentDetailsEvents: function () {

            paymentDetails.paymentDetailsTableDbClick();

        },
        paymentDetailsTableDbClick: function () {
            $('#paymentDetailsTable').find('tbody').on('dblclick', 'tr', function () {
                var row =paymentDetailsTable.row(this);
                var index = paymentDetailsTable.row(this).index();
                var editMode = row.node().getAttribute('control');
                if (!paymentDetails.isRowInEditMode(index)) {
                    var fieldsId = paymentDetails.makePaymentDetailsInEditMode(row);
                    paymentDetails.setRowValuesInEditMode(index, fieldsId);
                    paymentDetails.addRowStatus(row, "update");
                    paymentDetails.setRowMode(row, 'edit');

                }

            });

        },

        modalClose:function(){

            $('#dailyMovementModal').modal('hide');
        },
        init: function () {

            paymentDetails.addPaymentDetailsTable();
            paymentDetails.refreshPaymentDetailsTable();
            paymentDetails.paymentDetailsEvents();
            setTimeout(function(){
                paymentDetailsTable.order([0, 'desc']).draw();
                if($("#paymentStatus").val()=="COMPLETED") {
                    $("#modal-body a.btn.btn-circle.btn-icon-only.btn-default").addClass("hide");
                    $("#paymentStatus").attr("disabled", "disabled");
                    $(".btn.btn-primary").hide();
                    //   paymentDetailsTable.columns(paymentDetails.getColumnIndex("actions")).visible(false);
                }
            },1000);
        }
    }


}();