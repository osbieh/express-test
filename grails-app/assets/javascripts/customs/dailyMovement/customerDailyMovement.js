/**
 * Created by osbie on 6/11/2017.
 */

var customerDailyMovement = function () {

    return {
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
                    customerDailyMovement.refreshTable();
                    return item.name;
                }
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
                    customerDailyMovement.refreshTable();

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
                    customerDailyMovement.refreshTable();

                });



        },
        addDailyMovementTable: function () {

            dailyMovementTable = $("#dailyMovementTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "lengthMenu": [[10, 20, 50, -1], [10, 20, 50, "All"]],
                "paging": true,
                "ordering": true,
                "info": true,
                stateSave: true,
                sPaginationType: "full_numbers",
                bJQueryUI: true,
                "dom": 'lf<"toolbar">rt<"footer"><"bottom"ip><"clear">',
                "aaSorting": [[1, 'asc']],
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },

                "columns": [
                    {"width": "1%", "data": "rowNum"},
                    {"width": "1%", "data": "dailyMovementId"},
                    {"width": "15%", "data": "movementType"},
                    {"width": "10%", "data": "movementDay"},
                    {"width": "10%", "data": "receptNo"},
                    {"width": "10%", "data": "total"},
                    {"width": "10%", "data": "status"},
                    {
                        "render": function (data, type, full, meta) {
                            if (full.referenceId != null)
                                return "<input class='form-control' type='checkbox' disabled checked>";
                            return '<input class="form-control" type="checkbox" onclick="customerDailyMovement.dailyMovementDetails(' + meta.row + ')" >';

                        }, "width": "5%", "data": "details"
                    },
                    {
                        "render": function (data, type, full, meta) {

                            var actions= ' <div style="padding-top:6px;">' +

                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="customerDailyMovement.dailyMovementDetails(' + meta.row + ');" aria-hidden="true"></a>' +
                                         '<div> ';

                            return actions;


                        }, "width": "35%", "data": "actions"
                    },
                    {"width": "8%", "visible": false, "data": "movementType2"},
                ],


                //initComplete: function () {
                //    $("div.internalToolbar").html('<div class="tools">' +
                //        ' <div  class="actions">' +
                //        ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="dailyMovement.createMovement();">' +
                //        ' <i class="glyphicon glyphicon-plus"></i>' +
                //        ' </a>' +
                //        '</div>'
                //    );
                //},
            });


        },
        dailyMovementDetails: function (index) {

            if (dailyMovement.validateRecord(index)) {
                var recordValues = customerDailyMovement.getRecordValues(index);

                $(".modal-body").html("");
                $('#modal-body').load(dailyMovement.dailyMovementDetailsLink, {record: recordValues}, function (result) {
                    $('#dailyMovementModal').modal({show: true});

                });
                var movementType = JSON.parse(recordValues).movementType2;
                setTimeout(function () {
                    if (movementType == "ORDER")
                        $("#myModalLabel").text("ADD NEW ORDER");
                    if (movementType == "PAYMENT")
                        $("#myModalLabel").text("ADD NEW PAYMENT");
                }, 200);
            }
        },
        getRecordValues: function (index) {


                dailyMovementTable.row(index).data().movementType = dailyMovementTable.row(index).data().movementType2;
            dailyMovementTable.row(index).data().accountName=$("#autoAccount").val();
            return JSON.stringify(dailyMovementTable.row(index).data());



        },
        refreshTable: function () {

            var tableId = $('#dailyMovementTable');
            var urlData = "/express/ar/dailyMovement/getCustomerMovementsByRange";
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
        setDefaultData: function () {

            $("#accountId").val(1);
            $("#autoAccount").val("فوزي الشلش");

            var date = new Date();
            $('#fromDate input').datepicker('update',moment().add(-10, 'days').toDate() );
            $('#toDate input').datepicker('update', date.getMonth() + 1 + '-' + date.getFullYear());

            //   $('#dailyMovmentDatePicker input').datepicker().trigger('changeDate');
        },
        init:function(){
            customerDailyMovement.addDailyMovementTable();
            customerDailyMovement.addAccountAutoComplete();
            customerDailyMovement.addFormToDateDatePicker();
            customerDailyMovement.setDefaultData();
        }

    }
}();
