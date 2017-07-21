/**
 * Created by osbie on 4/9/2017.
 */

var orderList = function () {

    return {
        accountTypeHeadUrl:"/express/ar/account/accountTypeahead",
        movementDetailsUrl:"/express/ar/orderP/movementDetails",
        addAccountAutoComplete:function(){

            $('#autoAccount').typeahead({
                source: function (query, process) {
                    var $url = orderList.accountTypeHeadUrl;
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
                    orderList.refreshTable();
                    return item.name;
                }
            });



        },
        addOrderList: function () {

            orderListTable = $("#orderListTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },
                fnFooterCallback: function (nRow, aaData, iStart, iEnd, aiDisplay) {
                    var api = this.api(), data;
                    var footer = $("tfoot")[0];
                    var columnIndex = orderList.getColumnIndex("totalAmount");
                    var totalAmountCell = footer.children[0].children[columnIndex];
                    var pages = orderListTable.page.info().pages;
                    // Remove the formatting to get integer data for summation
                    var intVal = function (i) {
                        return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '') * 1 :
                            typeof i === 'number' ?
                                i : 0;
                    };

                    // Total over all pages
                    total = api
                        .column(columnIndex)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    // Total over this page
                    pageTotal = api
                        .column(columnIndex, {page: 'current'})
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);


                    if (pages > 1)
                        $($("tfoot")[0].children[0].children[columnIndex]).html(pageTotal + '( من ' + total + ')<i class="fa fa-ils"></i> ');
                    else
                        $($("tfoot")[0].children[0].children[columnIndex]).html(total + '  <i class="fa fa-ils"></i> ');


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
                "columns": [
                    {"width": "1%", "data": "rowNum", "name": "rowNum"},
                    {"width": "1%", "data": "orderId", "name": "orderId", "visible": true},
                    {"width": "10%", "data": "account", "name": "account"},
                    {"width": "10%", "data": "orderDate", "name": "orderDate"},
                    {"width": "5%", "data": "totalAmount", "name": "totalAmount"},
                    {"width": "5%", "data": "status", "name": "status"},
                    {"width": "69%", "data": "actions",
                        render: function (data, type, full, meta) {

                            return ' <div style="padding-top:6px;">' +
                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="orderList.transActionMovementInfo(' + meta.row + ');" aria-hidden="true"></a>' ;

                                '<div> ';

                        }
                    },


                ]

            });
        },

        getColumnIndex: function (colName) {
            return orderListTable.column(colName + ':name').index();
        },
        transActionMovementInfo:function(index){
            console.log('transActionMovementInfo index ',index);
            var row = orderListTable.row(index);
            $(".modal-body").html("");
            $('#modal-body').load(orderList.movementDetailsUrl, {record: JSON.stringify(row.data())}, function (result) {
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
                    orderList.refreshTable();

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
                    orderList.refreshTable();

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
            var table = $("#orderListTable").DataTable();
            var tableId = $("#orderListTable");
            var urlData = common.projectPath + "/orderP/getOrderList";
            var data ={fromDate: $('#fromDate input').val(),toDate: $('#toDate input').val(),accountId:$("#accountId").val()}

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);

                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                orderListTable.page(orderListTable.page()).draw(false);
            });


        },
        init: function () {
            orderList.addOrderList();
            orderList.refreshTable();
            orderList.addAccountAutoComplete();
            orderList.addFormToDateDatePicker();
            orderList.setDefaultData();
        }

    }


}();
