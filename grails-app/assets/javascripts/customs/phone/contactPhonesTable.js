/**
 * Created by osbie on 2/16/2017.
 */
/**
 * Created by osbie on 2/8/2017.
 */
var contactPhoneTable = function () {
    return {
        phonesLink:null,
        masterTableId:null,
        phonesList: function () {

            contactPhonesTable = $("#contactPhonesTable").DataTable({
                "paging": false,
                "ordering": false,
                "info": false,
                "ajax": {
                    url: contactPhoneTable.phonesLink,// contactLinks.contactPhones,
                    "data": {masterTableId:contactPhoneTable.masterTableId }
                },
                dom: '<"toolbar">rtp',
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "columns": [
                    {
                        "render": function (data, type, full, meta) {

                            if (data == true)
                                return '<input type="radio" name="isPrimary"  checked>';

                            return '<input type="radio" name="isPrimary" >';

                        }, targets: 0, "width": "5%", "data": "isPrimary"
                    },  // here's a radio button, modify to use data to populate it

                    {
                        "render": function (data, type, full, meta) {
                            if (data == true)
                                return '<input type="checkbox" checked>';

                            return '<input type="checkbox">';

                        }, "width": "5%", "data": "isActive"
                    },
                    {"width": "10%", "data": "phoneNo"},
                    {"width": "15%", "data": "phoneType"},
                    {"visible": false, "width": "5%", "data": "contactId"},

                ],
                initComplete: function () {
                    $("div.toolbar")
                        .html(' <div class="row">' +
                            '<div class="tools">' +
                            ' <div class="col-md-6"> <label class="control-label bold">Phones</label></div>' +
                            ' <div class="col-md-6"  class="actions">' +
                            ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="contactPhoneTable.addNewPhoneListRecord();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>' +
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                            '</div> </div>');
                },


            });


        },

        addNewPhoneListRecord: function () {
            contactPhoneTable.setColumnVisible(4, true);
            var row;
            try {

                row = contactPhonesTable.row.add({
                    isPrimary: '',
                    isActive: '',
                    phoneNo: '',
                    phoneType: '',
                    contactId: ''
                }).draw();
                var length = ($("#contactPhonesTable tr").length - 2);
                contactPhoneTable.makePhoneListEdit(row, length);
            } catch (e) {
                var length = ($("#contactPhonesTable tr").length - 2);
                var rowX = contactPhonesTable.row(length).node()
                $(rowX).addClass('modified');
                rowX.setAttribute("control", "new");
                contactPhoneTable.makePhoneListEdit(rowX, length);

            }


        },

        phoneListClick: function () {

            $('#contactPhonesTable').find('tbody').on('click', 'tr', function () {
                if ($(this).hasClass('selected')) {
                    $(this).removeClass('selected');
                } else {
                    contactPhonesTable.$('tr.selected').removeClass('selected');
                    $(this).addClass('selected');
                }
            });

        },

        phoneListDbClick: function () {
            $('#contactPhonesTable').find('tbody').on('dblclick', 'tr', function () {
                var row = contactPhonesTable.row(this).node();
                var index = contactPhonesTable.row(this).index();
                var editMode = row.getAttribute('control');
                if (editMode != 'edit') {
                    contactPhoneTable.makePhoneListEdit(row, index);
                    row.setAttribute('control', 'edit');
                }
            });

        },

        setColumnVisible: function (colNum, visible) {
            var column = contactPhonesTable.column(colNum);
            column.visible(visible);
        },
        setRowModified: function (th) {
            var row = $(th).parent().parent();
            row.attr("status", 'modified');
            row.addClass('modified');
        },
        removeModified: function (row) {
            $(row).removeAttr("status");
            $(row).removeClass('modified');
        },


        isRowModified: function (row) {
            return $(row).attr("status") != undefined ? true : false;
        },

        isRowInEditMode:function(row){
            return $(row).attr("control") != undefined ? true : false;
        },

        makePhoneListEdit: function (row, index) {

            var date = new Date().getMilliseconds();

            contactPhoneTable.setColumnVisible(4, true);
            var td0 = row.children[0];
            var td1 = row.children[1];
            var td2 = row.children[2];
            var td3 = row.children[3];
            var td4 = row.children[4];

            var td0Val = td0.innerHTML;
            var isPrimary = '<input type="radio" class="form-control" />';
            td0.innerHTML = isPrimary;
            var isPrimaryId = td0.children[0].id = "isPrimary-" + date
            var isPrimaryId = td0.children[0].name = "isPrimary";//+ date

            var td1Val = td1.innerHTML;
            var isActive = '<input type="checkbox"  class="form-control" checked/>';
            td1.innerHTML = isActive;
            var isActiveId = td1.children[0].id = "isActive-" + date
            var isActiveId = td1.children[0].name = "isActive-" + date

            var td2Val = td2.innerHTML;
            var phoneNo = '<input id="phoneNo" class="form-control" type="text"/>';
            td2.innerHTML = phoneNo;

            var phoneId = td2.children[0].id = "phoneNo-" + date;
            td2.children[0].name = "phoneNo-" + date;
            td2.children[0].value = td2Val;

            var td3Val = td3.innerHTML;
            var phoneType = '<select id="phoneType" class="form-control" type="text">' +
                '<option>MOBILE</option>' +
                '<option>PHONE</option>' +
                '<option>FAX</option>' +
                '</select>';
            td3.innerHTML = phoneType;
            var phoneTypeId = td3.children[0].id = "phoneType-" + date;
            td3.children[0].name = "phoneType-" + date;
            $("#" + phoneTypeId).children().filter(function (index) {
                if (this.text == td3Val) {
                    this.selected = true;
                }
            });


            var status = ' <div>' +

                '<a data-toggle="tooltip" title="Delete" style="margin-left: 1px;" class="glyphicon glyphicon-trash" onclick="contactPhoneTable.phoneListDeleteRow()" aria-hidden="true"></a>' +

                '<div> ';

            td4.innerHTML = status;


        },

        makeRowView: function (row) {

            if(contactPhoneTable.isRowInEditMode(row)){

                var td0 = row.children[0];
                var isPrimaryId = td0.children[0].id;
                var isPrimary = $("#" + isPrimaryId).is(":checked");

                var td1 = row.children[1];
                var isActiveId = td1.children[0].id;
                var isActive = $("#" + isActiveId).is(":checked");

                var td2 = row.children[2];
                var phoneNoId = td2.children[0].id;
                var phoneNo = $("#" + phoneNoId).val();

                var td3 = row.children[3];
                var phoneTypeId = td3.children[0].id;
                var phoneType = $("#" + phoneTypeId).find('option:selected').text();

                var obj = {isPrimary: isPrimary, isActive: isActive, phoneNo: phoneNo, phoneType: phoneType, contactId: ''};
                contactPhonesTable.row(row).data(obj);
            }
        },

        isPhoneListInEditMode: function () {
            var controls = $("#contactPhonesTable tr[control='edit'],tr[control='new']").length;
            if (controls > 0)
                return true;
            else
                return false;
        },

        phoneListDeleteRow: function () {

            if (confirm("Are you sure?")) {
                contactPhonesTable.row('.selected').remove().draw(false);
            }
            return false;


        },


        init:function(){
            contactPhoneTable.phonesList();
            contactPhoneTable.phoneListClick();
            contactPhoneTable.phoneListDbClick();

        }

    }
}();