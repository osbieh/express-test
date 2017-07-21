/**
 * Created by osbie on 6/10/2017.
 */

var chequeList = function () {

    return {
        chequeListUrl:"/express/ar/cheque/getChequeList",
        addChequeListTable:function(){
            chequeTable = $("#chequeTable").DataTable({
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
                "columns": [
                    {"width": "1%", "data": "rowNum", "name": "rowNum"},
                    {"width": "5%", "data": "chequeNo", "name": "chequeNo"},
                    {"width": "20%", "data": "bankName", "name": "bankName", "visible": true},
                    {"width": "10%", "data": "dueDate", "name": "dueDate"},
                    {"width": "10%", "data": "amount", "name": "amount"},
                    {"width": "15%", "data": "insertDate", "name": "insertDate"},

                    {"width": "10%", "data": "actions",
                        render: function (data, type, full, meta) {

                            return ' <div style="padding-top:6px;">' +

                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="orderDetails.getInfo();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Details" style="margin-left:10px;" class="glyphicon glyphicon-tasks" onclick="dailyMovement.editDetails();" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Save" style="margin-left:10px;" class="glyphicon glyphicon-save" onclick="dailyMovement.saveRecord(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Delete" style="margin-left:10px;" class="glyphicon glyphicon-trash" onclick="orderDetails.deleteOrderDetailsItem(' + meta.row + ');" aria-hidden="true"></a>' +

                                '<div> ';

                        }
                    },


                ]
            });

        },
        refreshTable: function () {
            var table = $("#chequeTable").DataTable();
            var tableId = $("#chequeTable");
            var urlData = chequeList.chequeListUrl;
            var data ={fromDate: $('#fromDate input').val(),toDate: $('#toDate input').val(),accountId:$("#accountId").val()}

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);

                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                chequeTable.page(chequeTable.page()).draw(false);
            });


        },
        init:function(){
            chequeList.addChequeListTable();
            chequeList.refreshTable();
        }

    }
}();
