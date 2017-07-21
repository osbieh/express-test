/**
 * Created by osbie on 4/9/2017.
 */

var cheque = function () {
    return {
        addChequeTable: function () {
            chequeTable = $("#chequeTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },
                "lengthMenu": [[2, 10, 50, -1], [2, 10, 50, "All"]],
                "paging": true,
                "ordering": true,
                "info": true,
                stateSave: true,
                sPaginationType: "full_numbers",
                bJQueryUI: true,
                "dom": 'lf<"toolbar">rt<"footer"><"bottom"ip><"clear">',
                "aaSorting": [[1, 'asc']],
                bAutoWidth: false,
                "columns": [
                    {"width": "2%",  "data": "rowNum", "name": "rowNum"},
                    {"width": "15%", "data": "chequeNo", "name": "chequeNo"},
                    {"width": "25%", "data": "bankName", "name": "bankName"},
                    {"width": "15%", "data": "amount", "name": "amount"},
                    {"width": "30%", "data": "dueDate", "name": "dueDate"},
                    {"width": "10%", "data": "actions", "name": "actions",
                        render: function (data, type, full, meta) {

                            return '<div style="padding-top:6px;">' +
                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="orderDetails.getInfo();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Details" style="margin-left:10px;" class="glyphicon glyphicon-tasks" onclick="dailyMovement.editDetails();" aria-hidden="true"></a>' +
                              //  '<a data-toggle="tooltip" title="Save" style="margin-left:10px;" class="glyphicon glyphicon-save" onclick="cheque.saveRecord(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Delete" style="margin-left:10px;" class="glyphicon glyphicon-trash" onclick="orderDetails.deleteOrderDetailsItem(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<div>';

                        }
                    },
                    {"data": "paymentDetailsId", "name": "paymentDetailsId", "visible": false},
                    {"data": "total2", "name": "total2", "visible": false}

                ],
                initComplete: function () {
                    $("div.toolbar")
                        .html(' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="cheque.addNewCheque();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>');

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
                    total = api.column(cheque.getColumnIndex("total2")).data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);


                    // Total over this page
                    pageTotal = api
                        .column(cheque.getColumnIndex("total2"), {page: 'current'}).data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    var pages = chequeTable.page.info().pages;
                    if (pages > 1)
                        $($("tfoot")[0].children[0].children[cheque.getColumnIndex("amount")]).html(pageTotal + '( من ' + total + ')<i class="fa fa-ils"></i> ');
                    else
                        $($("tfoot")[0].children[0].children[cheque.getColumnIndex("amount")]).html(total + '  <i class="fa fa-ils"></i> ');
                    $("#cheque").val(total);
                    $("#totalPayment").val( parseFloat( $("#cheque").val()) + parseFloat( $("#cashAmount").val()));
                },


            });
            chequeTable.column(cheque.getColumnIndex("paymentDetailsId")).visible(false);
            chequeTable.column(cheque.getColumnIndex("total2")).visible(false);

        },
        getColumnIndex: function (colName) {
            return chequeTable.column(colName + ':name').index();
        },

        addNewCheque: function () {
            var row;
            try {
                row = chequeTable.row.add({
                    "rowNum": '',
                    "chequeNo": '',
                    "bankName": '',
                    "amount": '',
                    "dueDate": '',
                    "actions": '',
                    "paymentDetailsId": '',
                    "chequeId":'',
                    "total2": ''
                });
                chequeTable.page('last').order([[1, 'desc']]).draw(false);
                var rowsCount = chequeTable.rows().count();
                var pagesLength = chequeTable.page.len();
                if ((rowsCount % pagesLength) == 1)
                    chequeTable.page('last').order([[1, 'desc']]).draw(false);
                cheque.makeChequeRowInEditMode(row);
                //
                //paymentDetails.setRowMode(row, 'edit');
                //paymentDetails.addRowStatus(row, "new");


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
            var row= chequeTable.row(index);
            return $(row.node()).attr("mode") == "edit";
        },

        getRowStatus: function (row) {

            return $(row.node()).attr("status");
        },
        getRowMode: function (row) {
            return $(row.node()).attr("mode");
        },

        makeChequeRowInEditMode: function (row) {

            var date = new Date().getMilliseconds();
            cheque.setRowMode(row, 'edit');
            row = row.node();

            var rowNum = row.children[cheque.getColumnIndex("rowNum")];
            var chequeNo = row.children[cheque.getColumnIndex("chequeNo")];
            var bank = row.children[cheque.getColumnIndex("bankName")];
            var amount = row.children[cheque.getColumnIndex("amount")];
            var dueDate = row.children[cheque.getColumnIndex("dueDate")];
            var total2 = row.children[cheque.getColumnIndex("total2")];

            var chequeNoField = '<input type="text" class="form-control" />';
            chequeNo.innerHTML = chequeNoField;
            chequeNo.children[0].id = "chequeNo-" + date;
            chequeNo.children[0].name = "chequeNo-" + date;


            var bankVal = bank.innerHTML;
            var bankName = "bankName-" + date
            var bankId = "bankId-" + date
            var hidden = "hiddenBankId-" + date
            var bankUtoCompleteField = $.ajax({
                url: common.projectPath + "/category/renderBanksAutoComplete",
                data: {id: bankId, name: bankName, hidden: hidden},
                success: function (response) {
                    $(bank).html(response);

                },
            });
            bank.innerHTML = bankUtoCompleteField;

            var amountVal = amount.innerHTML;
            var amountField = '<input type="text" class="form-control" />';
            amount.innerHTML = amountField;
            amount.children[0].id = "amount-" + date;
            amount.children[0].name = "amount-" + date;

            $("#amount-" + date).on('keydown', function (e) {
                -1 !== $.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) || /65|67|86|88/.test(e.keyCode) && (!0 === e.ctrlKey || !0 === e.metaKey) || 35 <= e.keyCode && 40 >= e.keyCode || (e.shiftKey || 48 > e.keyCode || 57 < e.keyCode) && (96 > e.keyCode || 105 < e.keyCode) && e.preventDefault()
            });

            $("#amount-" + date).on("keyup change", function () {
                var totalFieldVal = $("#amount-" + date).val();
                var cell = chequeTable.cell(row, cheque.getColumnIndex("total2"));
                cell.data(totalFieldVal);
                setTimeout(function () {
                    chequeTable.page(chequeTable.page()).draw(false);
                }, 2000);
            });



            var dueDateVal = dueDate.innerHTML;
            var dueDateField ='<div class="input-group date dt" data-provide="datepicker">'+
                '<input type="text" class="form-control dt">'+
                '<div class="input-group-addon">'+
                '<span class="glyphicon glyphicon-th"></span>'+
                '</div>'+
                '</div>';

            $(".datepicker").datepicker("destroy");

            dueDate.innerHTML = dueDateField;
            dueDate.children[0].id = "dueDate-" + date;
            dueDate.children[0].name = "dueDate-" + date;
            $('#dueDate-'+date).datepicker({
                format: "yyyy-mm-dd",
                weekStart: 6,
                isInline:true,
                todayBtn: "linked",
                language: 'en',//common.currentLang.toString(),
                orientation: "bottom left",
                daysOfWeekHighlighted: "5",
                calendarWeeks: true,
                autoclose: true,
                todayHighlight: true,
            })  .on('show', function(e) {
                    var popup =$(this).offset();
                console.log('popup ',popup.top);
                    var style='display:block;left:'+parseInt(popup.left)+'px!important;z-index: 10061;top:'+parseInt(popup.top+30)+'px!important';
                   setTimeout(function() {
                       $(".datepicker").attr('style', style);
                   },0);
            });
            return date;
        },

        setPaymentAmount:function(){
            $("#cashAmount").on("keyup change", function () {
            $("#totalPayment").val( parseFloat( $("#cheque").val()) + parseFloat( $(this).val()));
            });

        },

        validateRow:function(){},
        getChequeRecord:function(index){
            var row = chequeTable.row(index);
            var record = row.data();
            var node = row.node();
            record.rowStatus = cheque.getRowStatus(row);
            record.rowMode = cheque.getRowMode(row);
            if (cheque.isRowInEditMode(index)) {
                record.rowNum = $(node.children[cheque.getColumnIndex("rowNum")]).text();
                record.chequeNo = $(node.children[cheque.getColumnIndex("chequeNo")]).find("[type=text]").val();
                record.bankName = $(node.children[cheque.getColumnIndex("bankName")]).find("[type=text]").val();
                record.bankId = $(node.children[cheque.getColumnIndex("bankName")]).find("[type=hidden]").val();
                record.amount = $(node.children[cheque.getColumnIndex("amount")]).find("[type=text]").val();
                record.dueDate = $(node.children[cheque.getColumnIndex("dueDate")]).find("[type=text]").val();
             //   record.actions = $(node.children[cheque.getColumnIndex("")]).text();
             //   record.paymentDetailsId = $(node.children[cheque.getColumnIndex("")]).text();
             //   record.total = $(node.children[cheque.getColumnIndex("")]).text();
            }

            return JSON.stringify(record);
        },
           getAllEditedChequeRecords: function() {

               var records = Array();
               for (i = 0; i < chequeTable.rows().count(); i++) {
                   record = cheque.getChequeRecord(i);
                   var row = chequeTable.row(i);
                   console.log("record::: ", JSON.parse(record));
                   if(cheque.isRowInEditMode(i)){
                       records.push(JSON.parse(record));
                   }

               }
               return records;

            },

        managePaymentDetails:function(){
            $.ajax({
                url: common.projectPath + "/payment/manageWholePayment",
                data: {
                    payment: paymentForm.getPaymentFormInfo,
                    cheques: JSON.stringify(cheque.getAllEditedChequeRecords()),
                    cash:JSON.stringify({cashAmount:parseFloat($("#cashAmount").val()),cashDetailsId:$("#cashDetailsId").val()}),
                    chequesAmounts:parseFloat($("#cheque").val()),
                    paymentTotalAmounts:parseFloat($("#totalPayment").val())
                },
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {
                    toastr.success("Order are modified", "Success");
                    cheque.modalClose();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                    //    toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                    // $('#myModal').modal('hide');

                }
            });


        },

        refreshChequeTable: function () {
            var table = $("#chequeTable").DataTable();
            var tableId = $("#chequeTable");
            var urlData = common.projectPath + "/payment/loadPaymentDetails2";
            var data = {paymentId: $("#paymentId").val()};

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);
                for (var i = 0; i < json.aaData.length; i++) {
                        table.oApi._fnAddData(oSettings, json.aaData[i]);
                }

                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                chequeTable.page(chequeTable.page()).draw(false);
                    if(json.cashData.length>0){
                        $("#cashAmount").val( json.cashData[0].cashAmount);
                        $("#cashDetailsId").val(json.cashData[0].cashDetailsId);
                    }




            });


        },

        chequeEvents: function () {

            cheque. chequeTableDbClick();

        },
        chequeTableDbClick: function () {
            $('#chequeTable').find('tbody').on('dblclick', 'tr', function () {
                var row = chequeTable.row(this);
                var index = chequeTable.row(this).index();
                var editMode = row.node().getAttribute('control');
                if (!cheque.isRowInEditMode(index)) {
                    var fieldsId = cheque.makeChequeRowInEditMode(row);
                    cheque.setRowValuesInEditMode(index, fieldsId);
                    cheque.addRowStatus(row, "update");
                }

            });

        },

        setRowValuesInEditMode: function (rowIndex, fieldId) {
            var data = chequeTable.row(rowIndex).data();
            $('#chequeNo-' + fieldId).val(data.chequeNo);
            $('#amount-' + fieldId).val(data.amount);
            $('#dueDate-' + fieldId +' input').val(data.dueDate);
            setTimeout(function () {
                $('#bankId-' + fieldId).val(data.bankName);
                $('#hiddenBankId-' + fieldId).val(data.bankId);

            }, 200);
        },
        modalClose: function () {
            $('#dailyMovementModal').modal('hide');

        },
        init: function () {

            cheque.addChequeTable();
            setTimeout(function () {
                chequeTable.order([0, 'desc']).draw();
            }, 500);
            cheque.setPaymentAmount();
            cheque.refreshChequeTable();


            setTimeout(function(){
                if($("#paymentStatus").val()=="COMPLETED") {
                    $("#modal-body a.btn.btn-circle.btn-icon-only.btn-default").addClass("hide");
                    $("#paymentStatus").attr("disabled", "disabled");
                    //   paymentDetailsTable.columns(paymentDetails.getColumnIndex("actions")).visible(false);
                    $(".btn.btn-primary").hide();
                }else{
                    cheque.chequeEvents();

                }
            },500);



        }




    }


}();