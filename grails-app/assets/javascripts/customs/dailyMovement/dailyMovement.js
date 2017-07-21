/**
 * Created by osbie on 3/5/2017.
 */

var dailyMovement = function () {

    return {
        saveDailyMovementRecordLink: "/express/ar/dailyMovement/saveDailyMovementRecord",
        dailyMovementDetailsLink: "/express/ar/dailyMovement/dailyMovementDetails",
        addDailyMovementCalendar: function () {

            $('#dailyMovmentDatePicker input').datepicker({
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
                    dailyMovement.refreshTable();

                });

        },
        setDefaultDate: function () {
            var date = new Date();
            $('#dailyMovmentDatePicker input').datepicker('update', date.getMonth() + 1 + '-' + date.getFullYear());

            //   $('#dailyMovmentDatePicker input').datepicker().trigger('changeDate');
        },
        addDailyMovementTable: function () {

            dailyMovementTable = $("#dailyMovementTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "paging": true,
                "ordering": true,
                "info": true,
                "fnRowCallback": function (nRow, aData, iDisplayIndex) {
                    $("td:first", nRow).html(iDisplayIndex + 1);
                    return nRow;
                },
                "footerCallback": function (row, data, start, end, display) {
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
                        .column(4)
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    // Total over this page
                    pageTotal = api
                        .column(4, {page: 'current'})
                        .data()
                        .reduce(function (a, b) {
                            return intVal(a) + intVal(b);
                        }, 0);

                    // Update footer
                    $(api.column(4).footer()).html(
                        '$' + pageTotal + ' ( $' + total + ' total)'
                    );
                },
                "columns": [
                    {"width": "1%", "data": "rowNum"},
                    {"width": "1%", "data": "dailyMovementId"},
                    {"width": "8%", "data": "movementType"},
                    {"width": "20%", "data": "accountName"},
                    {"width": "10%", "data": "receptNo"},
                    {"width": "10%", "data": "total"},
                    {"width": "10%", "data": "status"},
                    {
                        "render": function (data, type, full, meta) {
                            if (full.referenceId != null)
                                return "<input class='form-control' type='checkbox' disabled checked>";
                            return '<input class="form-control" type="checkbox" onclick="dailyMovement.dailyMovementDetails(' + meta.row + ')" >';

                        }, "width": "5%", "data": "details"
                    },
                    {
                        "render": function (data, type, full, meta) {

                           var actions= ' <div style="padding-top:6px;">' +

                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="dailyMovement.dailyMovementDetails(' + meta.row + ');" aria-hidden="true"></a>' +
                                '<a data-toggle="tooltip" title="Details" style="margin-left:10px;" class="glyphicon glyphicon-tasks" onclick="dailyMovement.editDetails();" aria-hidden="true"></a>' ;
                            if(full.dailyMovementId=="" ||full.dailyMovementId==null){
                                actions+=   '<a data-toggle="tooltip" title="Save" style="margin-left:10px;" class="glyphicon glyphicon-save" onclick="dailyMovement.saveRecord(' + meta.row + ');" aria-hidden="true"></a>';

                            }

                            actions+='<a data-toggle="tooltip" title="Delete" style="margin-left:10px;" class="glyphicon glyphicon-trash" onclick="dailyMovement.deleteRecord();" aria-hidden="true"></a>' +
                                '<div> ';

                            return actions;


                        }, "width": "35%", "data": "actions"
                    },
                    {"width": "8%", "visible": false, "data": "movementType2"},
                ],

                dom: '<"internalToolbar">rtip',
                initComplete: function () {
                    $("div.internalToolbar").html('<div class="tools">' +
                        ' <div  class="actions">' +
                        ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="dailyMovement.createMovement();">' +
                        ' <i class="glyphicon glyphicon-plus"></i>' +
                        ' </a>' +
                        '</div>'
                    );
                },
            });


        },

        createMovement: function () {
            var row;
            try {

                row = dailyMovementTable.row.add({
                    "rowNum": '',
                    "dailyMovementId": '',
                    "movementType": '',
                    "accountName": '',
                    "receptNo": '',
                    "total": '',
                    "status": '',
                    "actions": '',
                    "movementType2": '',
                }).draw(false);
                dailyMovementTable.order([1, 'asc']).draw();
                dailyMovementTable.page('last').draw(false);
                dailyMovement.makeMovementInEditMode(row, row.index());
                dailyMovement.addRowStatus(row, "new");
            } catch (e) {
                console.log("exception", e);
            }

        },

        dailyMovementDetails: function (index) {

           if (dailyMovement.validateRecord(index)) {
                var recordValues = dailyMovement.getRecordValues(index);

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



        editDetails: function () {
            alert(2);
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
            var row= dailyMovementTable.row(index);
            return $(row.node()).attr("mode") == "edit";
        },

        getRowStatus: function (row) {
            return $(row.node()).attr("status");
        },
        getRowMode: function (row) {
            return $(row.node()).attr("mode");
        },



        saveRecord: function (index) {
            if (dailyMovement.validateRecord(index)) {
                $.ajax({
                    url: dailyMovement.saveDailyMovementRecordLink,
                    data: {record: dailyMovement.getRecordValues(index)},
                    dataType: "json",
                    type: "POST",
                    beforeSend: function (xhr) {
                        $('#ajax_loader').show();
                    },
                    success: function (result) {
                        //contactList.refreshTable();
                        toastr.success("DailyMovement Created", "Success");

                        dailyMovementTable.row(index).invalidate().draw();
                        $(dailyMovementTable.row(index).node()).removeAttr("status", "edit");
                        $(dailyMovementTable.row(index).node()).find(".glyphicon-save").hide();
                        dailyMovement.refreshTable();
                    },
                    error: function (result) {
                        console.log(result, ' FAIL MESSAGE ', result.message);
                        //    toastr.error(result.responseJSON.error,"Fail");
                        $('#ajax_loader').hide();
                        // $('#myModal').modal('hide');

                    }
                });


            }
        },
        deleteRecord: function (index) {


        },
        validateRecord: function (index) {
            if(dailyMovement.isRowInEditMode(index)){
                var td = (dailyMovementTable.row(index).node().children[3]);
                var hidden = $(td).find("[type=hidden]");
                var accountField = $(td).find("[type=text]");
                var hiddenVal = hidden.val();
                if ((hiddenVal == null || hiddenVal == "") || ( accountField.val() == null || accountField.val() == "" )) {
                    accountField.addClass("red");
                    return false;
                }
                return true;
            }else{
                return true;
            }

        },

        getRecordValues: function (index) {
            if (dailyMovement.isRowInEditMode(index)) {

                var row = dailyMovementTable.row(index);
                var record = row.data();
                var node = row.node();
                record.rowNum = $(node.children[0]).text();
                record.dailyMovementId = $(node.children[1]).text();
                record.movementType = $(node.children[2]).find("option:selected").val();
                record.movementType2 = $(node.children[2]).find("option:selected").val();

                record.accountId = $(node.children[3]).find("[type=hidden]").val();
                record.accountName = $(node.children[3]).find("[type=text]").val();

                record.receptNo = $(node.children[4]).find("[type=text]").val();
                record.totalAmount = $(node.children[5]).find("[type=text]").val();

                record.status = $(node.children[6]).find("option:selected").val();
                record.movementDay = $('#dailyMovmentDatePicker input').val();
                return JSON.stringify(record);

            } else {
                dailyMovementTable.row(index).data().movementDay = $('#dailyMovmentDatePicker input').val();
                dailyMovementTable.row(index).data().movementType = dailyMovementTable.row(index).data().movementType2;
                return JSON.stringify(dailyMovementTable.row(index).data());

            }

        },

        makeMovementInEditMode: function (row, index) {
            var date = new Date().getMilliseconds();
            dailyMovement.setRowMode(row, 'edit');
            row=row.node();

            var td0 = row.children[0];
            var td1 = row.children[1];
            var td2 = row.children[2];
            var td3 = row.children[3];
            var td4 = row.children[4];
            var td5 = row.children[5];
            var td6 = row.children[6];
            var td7 = row.children[7];
            var td8 = row.children[8];


            var movementType = dailyMovementsVars.select;
            td2.innerHTML = movementType;

            var name = "accountName-" + date
            var id = "accountId-" + date
            var hidden = "hiddenAccountId-" + date
            var accUtoCom = $.ajax({
                url: common.projectPath + "/account/renderAccountAutoComplete",
                data: {id: id, name: name, hidden: hidden},
                success: function (response) {
                    $(td3).html(response);
                },
            });

            td3.innerHTML = accUtoCom;


            var td4Val = td3.innerHTML;
            var recept = '<input type="text" class="form-control" />';
            td4.innerHTML = recept;
            td4.children[0].id = "recept-" + date;
            td4.children[0].name = "recept-" + date;

            var td5Val = td5.innerHTML;
            var total = '<input type="text" class="form-control" />';
            td5.innerHTML = total;
            td5.children[0].id = "total-" + date;
            td5.children[0].name = "total-" + date;

            var td6Val = td6.innerHTML;
            var phoneType = dailyMovementsVars.orderStatus;
            td6.innerHTML = phoneType;
            var phoneTypeId = td6.children[0].id = "phoneType-" + date;
            td6.children[0].name = "phoneType-" + date;
            return date
        },


        refreshTable: function () {

            var tableId = $('#dailyMovementTable');
            var urlData = "/express/ar/dailyMovement/getMovements";
            var data = {movementDay: $('#dailyMovmentDatePicker input').val()};

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

        init: function () {
            dailyMovement.addDailyMovementCalendar();
            dailyMovement.addDailyMovementTable();
            dailyMovement.setDefaultDate();
            dailyMovement.refreshTable();
        }

    }
}();
