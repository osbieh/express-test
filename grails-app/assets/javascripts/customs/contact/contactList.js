/**
 * Created by osbieh on 4/13/2016.
 */

var contactList = function () {

    return {

        contactDetailsLink:null,
        addContactListTable: function () {
            contactTable = $("#contactTable").DataTable({
                    "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                    "paging": true,
                    "ordering": true,
                    "info": true,
                    "ajax": contactLinks.contactListData,
                    "columns": [
                        {"width": "5%", "data": "contactNo"},
                        {"width": "25%", "data": "contactName"},
                        {"width": "25%", "data": "PhoneNo"},
                        {"width": "25%", "data": "address"},
                        {"visible": false, "data": "email"},
                        //{"visible":false, "data": 5},
                        //  {"visible": false, "data": "countOfGuards"}
                    ],
                    "language": {
                        "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                    },
                    dom: '<"internalToolbar">rtip',
                    //  dom: '<"internalToolbar">',
                    initComplete: function () {
                        $("div.internalToolbar").html('<div class="tools">' +
                            ' <div  class="actions">' +
                            ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="contactList.createContact();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>' +
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                                //    '<div class="col-md-6">'+
                                //    '    <ul class="pager">'+
                                //    '        <div class="col-md-2">'+
                                //    '            <li class="previous">'+
                                //    '                <a class="btn btn-default" id="previous" href="javascript:;">&laquo;</a>'+
                                //    '            </li>'+
                                //    '        </div>'+
                                //'        <div style="margin: 0 -40px -40px;" class="col-md-8">'+
                                //'            <div class="form-group">'+
                                //'                <div id="employeeField">'+
                                //'                    <input id="autoContact" type="text" data-provide="typeahead" class="typeahead form-control">'+
                                //'                    <input id="contactId" type="hidden">'+
                                //'                    <span class="input-group-btn"></span>'+
                                //'                </div>'+
                                //'            </div>'+
                                //'        </div>'+
                                //'        <div class="col-md-2">'+
                                //'            <li class="next">'+
                                //'                <a class="btn btn-default" id="next" href="javascript:;">&raquo;</a>'+
                                //'            </li>'+
                                //'        </div>'+
                                //'    </ul>'+
                                //'</div>'+
                            '</div>'
                        );
                    },


                }
            );
        },

        createContact:function(){

            $('#contactModal-body').load(contactList.contactDetailsLink, {}, function (result) {
                $('#contactModal').modal({show: true});
            });

        },

        accountCreateContact:function(){

            $('#contact-modal-body').load(contactList.contactDetailsLink, {}, function (result) {

                $('#newContactModal').modal({keyboard: false, show: true});

            });

        },



        refreshTable: function () {

            var table = $("#contactTable").DataTable();
            var tableId = $("#contactTable");
            var urlData = contactLinks.contactListData;
            var data = {contactId: $("#contactId").val()};

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


            //table.rows().remove().draw();
            //contactTable.ajax.reload();

        },
        onTableClick: function (th) {


            if ($(th).hasClass('selected')) {
                $(th).removeClass('selected');

                if ($(th).hasClass('specialRow')) {
                    $(th).removeClass('SelectSpecialRow');
                }

                if ($(th).hasClass('holidayRow')) {
                    $(th).removeClass('SelectSpecialRow');
                }
                if ($(th).hasClass('vacationRow')) {
                    $(th).removeClass('SelectSpecialRow');
                }
            }
            else {
                //   console.log(th);
                contactTable.$('tr.selected').removeClass('selected');
                contactTable.$('tr.SelectSpecialRow').removeClass('SelectSpecialRow');
//                attendanceSheetTable.$('tr.missingCheck').removeClass('missingCheck');

                if ($(th).hasClass('specialRow')) {
                    $(th).addClass('SelectSpecialRow');
                }

                if ($(th).hasClass('holidayRow')) {
                    $(th).addClass('SelectSpecialRow');
                }
                if ($(th).hasClass('vacationRow')) {
                    $(th).addClass('SelectSpecialRow');
                }

//                if ($(th).hasClass('missingCheck')) {
//                    $(th).addClass('SelectSpecialRow');
//                }

                $(th).addClass('selected');


            }


        },
        onTableDbClick: function (th) {
            var row = contactTable.row(th);
            var contactId=row.data().contactNo

            $('#contactModal-body').load(contactList.contactDetailsLink, {contactId:contactId}, function (result) {
                $('#contactModal').modal({show: true});
            });

        },

        addContactListTableEvents: function () {

            //manage Style
            $('#contactTable tbody').on('click', 'tr', function () {
                contactList.onTableClick(this);
            });


            //manage Row details
            $('#contactTable tbody').on('dblclick', 'tr', function () {
                contactList.onTableDbClick(this);
            });


        },

        addTypeHead: function () {

            $('#autoContact').typeahead({
                source: function (query, process) {
                    var $url = contactLinks.typeHeadUrl;
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
                                    id: data.contactId,
                                    name: data.contactName,
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
                    $("#contactId").val(id);
                    debugger;
                    contactList.refreshTable();
                    return item.name;
                }
            });


        },


        init: function () {
            contactList.addContactListTable();
            contactList.addContactListTableEvents();
            contactList.addTypeHead();
        }

    }


}();