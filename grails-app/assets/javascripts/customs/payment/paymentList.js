/**
 * Created by osbie on 4/9/2017.
 */

/**
 * Created by osbie on 4/9/2017.
 */

var paymentList=function(){

    return{
        accountTypeHeadUrl:"/express/ar/account/accountTypeahead",
        addAccountAutoComplete:function(){

            $('#autoAccount').typeahead({
                source: function (query, process) {
                    var $url = paymentList.accountTypeHeadUrl;
                    var $items = new Array;
                    $items = [""];
                    $.ajax({
                        url: $url,
                        data: {query: query},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            data = data.aaData;
                            $.map(data, function (data) {
                                var group;
                                group = {
                                    id: data.accountId,
                                    name: data.accountName,
                                    replace: function (string) {
                                        var value = '';
                                        value += this.name;
                                        if (typeof(this.level) != 'undefined') {
                                            value += ' <span class="pull-right muted">';
                                            value += this.level;
                                            value += '</span>';
                                        }
                                        return String.prototype.replace.apply('<div style="padding: 10px; font-size: 1.5em;">' + value + '</div>', arguments);
                                    }
                                };
                                $items.push(group);


                            });

                            process($items);
                        },

                    });
                },
                updater: function (item) {
                    var id = item.id;
                    $("#accountId").val(id);
                    paymentList.refreshTable();
                    return item.name;
                }
            });



        },
        addPaymentList:function(){

            paymentListTable= $("#paymentListTable").DataTable({
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
                "columns": [
                    {"width": "1%", "data": "rowNum", "name": "rowNum"},
                    {"width": "1%", "data": "paymentId", "name": "paymentId", "visible": true},
                    //{"width": "10%", "data": "account", "name": "account"},
                    {"width": "10%", "data": "paymentDate", "name": "paymentDate"},
                    {"width": "5%", "data": "totalAmount", "name": "totalAmount"},
                    {"width": "5%", "data": "status", "name": "status"},

                    {"width": "69%", "data": "actions",
                        render: function (data, type, full, meta) {

                            return ' <div style="padding-top:6px;">' +
                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="paymentList.transActionMovementInfo(' + meta.row + ');" aria-hidden="true"></a>' ;

                            '<div> ';

                        }
                    },



                ]


            });


        },
        transActionMovementInfo:function(index){

            var row = paymentListTable.row(index);

            $(".modal-body").html("");
            $('#modal-body').load(accountBalanceList.movementDetailsUrl, {record: JSON.stringify(row.data())}, function (result) {
                $('#myModal').modal({show: true});

            });


        },
        addFormToDateDatePicker:function(){
            $('#fromDate input').datepicker({
                    format: "yyyy-mm-dd",
                    weekStart: 6,
                    todayBtn: "linked",
                    language: 'en',
                    orientation: "bottom left",
                    daysOfWeekHighlighted: "5",
                    calendarWeeks: true,
                    autoClose: true,
                    todayHighlight: true

                })
                .on('changeDate', function (e) {
                    paymentList.refreshTable();

                });

            $('#toDate input').datepicker({
                    format: "yyyy-mm-dd",
                    weekStart: 6,
                    todayBtn: "linked",
                    language: 'en',//common.currentLang.toString(),
                    orientation: "bottom left",
                    daysOfWeekHighlighted: "5",
                    calendarWeeks: true,
                    autoclose: true,
                    todayHighlight: true

                })
                .on('changeDate', function (e) {
                    paymentList.refreshTable();

                });



        },
        setDefaultData: function () {

            $("#accountId").val(1);
            $("#autoAccount").val("فوزي الشلش");

            var date = new Date();
            $('#fromDate input').datepicker('update',moment().add(-1, 'days').toDate() );
            $('#toDate input').datepicker('update', date.getMonth() + 1 + '-' + date.getFullYear());

            //   $('#dailyMovmentDatePicker input').datepicker().trigger('changeDate');
        },
        refreshTable: function () {
            var table = $("#paymentListTable").DataTable();
            var tableId = $("#paymentListTable");
            var urlData = common.projectPath + "/payment/getPaymentList";
            var data ={fromDate: $('#fromDate input').val(),toDate: $('#toDate input').val(),accountId:$("#accountId").val()}

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);
                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                paymentListTable.page(paymentListTable.page()).draw(false);
            });


        },
        init:function(){
            paymentList.addPaymentList();
            paymentList.refreshTable();
            paymentList.addAccountAutoComplete();
            paymentList.addFormToDateDatePicker();
            paymentList.setDefaultData();

        }

    }


}();

