/**
 * Created by osbie on 5/21/2017.
 */


var accountBalanceList = function () {

    return {
        accountTypeHeadUrl:"/express/ar/account/accountTypeahead",
        accountBalancesListUrl:"/express/ar/account/getAccountBalances",
        movementDetailsUrl:"/express/ar/accountBalance/movementDetails",
        addBalanceListTable: function () {
            balanceTable = $("#balanceTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                "paging": true,
                "ordering": true,
                "info": true,
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },
                "columns": [
                    {"width": "3%",  "data": "rowNum"},
                    {"width": "20%", "data": "accountName"},
                    {"width": "10%", "data": "movementType"},
                    {"width": "7%", "data": "movementDay"},
                    {"width": "20%", "data": "movementValue"},
                    {"width": "20%", "data": "totalAmount"},
                    { "render": function (data, type, full, meta) {

                        var actions= ' <div style="padding-top:6px;">' +
                            '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="accountBalanceList.transActionMovementInfo(' + meta.row + ');" aria-hidden="true"></a>' ;
                    return actions;
                    },

                        "width": "20%", "data": "transactions"},

                ],
            });
        },

        transActionMovementInfo:function(index){

            var row = balanceTable.row(index);

            $(".modal-body").html("");
            $('#modal-body').load(accountBalanceList.movementDetailsUrl, {record: JSON.stringify(row.data())}, function (result) {
                $('#myModal').modal({show: true});

            });


        },
        addAccountAutoComplete:function(){

            $('#autoAccount').typeahead({
                source: function (query, process) {
                    var $url = accountBalanceList.accountTypeHeadUrl;
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
                    accountBalanceList.refreshTable();
                    return item.name;
                }
            });



        },

        addFormToDateDatePicker:function(){
            $('#fromDate input').datepicker({
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
                    accountBalanceList.refreshTable();

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
                    accountBalanceList.refreshTable();

                });



        },
        setDefaultData: function () {

            $("#accountId").val(1);
            $("#autoAccount").val("فوزي الشلش");

            var date = new Date();
            $('#fromDate input').datepicker('update',moment().add(-150, 'days').toDate() );
            $('#toDate input').datepicker('update', date.getMonth() + 1 + '-' + date.getFullYear());

            //   $('#dailyMovmentDatePicker input').datepicker().trigger('changeDate');
        },

        refreshTable: function () {

            var tableId = $('#balanceTable');
            var urlData = "/express/ar/account/getAccountBalances";
            var data = {fromDate: $('#fromDate input').val(),toDate: $('#toDate input').val(),accountId:$("#accountId").val()};

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);
                for (var i = 0; i < json.aaData.length; i++) {
                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                table.fnDraw();


            });

        },

        printReport:function(){
            $("#printBtn").on("click",function(e){
                var url="/express/ar/reports/getAccountBalanceReport?";
               url+= "fromDate="+ $('#fromDate input').val()+"&toDate="+ $('#toDate input').val()+"&accountId="+$("#accountId").val()
                window.open(url,'_blank','fullscreen=yes');
            });
        },

        init: function () {
            accountBalanceList.addAccountAutoComplete();
            accountBalanceList.addBalanceListTable();
            accountBalanceList.addFormToDateDatePicker();
            accountBalanceList.setDefaultData();
            accountBalanceList.refreshTable();
            accountBalanceList.printReport();
        }
    }

}();
