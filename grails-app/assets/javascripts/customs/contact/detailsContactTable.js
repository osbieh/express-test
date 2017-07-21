/**
 * Created by osbie on 2/12/2017.
 */
var detailsContactTable= function () {

return{
    accountContactaLink:"",
    addContactListTable: function () {
        contactTable = $("#contactTable").DataTable({
                "lengthMenu": [[3, 25, 50, -1], [3, 25, 50, "All"]],
                "paging": true,
                "ordering": true,
                "info": true,
                "ajax": detailsContactTable.accountContactaLink,
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                dom:'<"internalToolbar">tirp',

            "columns": [
                {
                    "render": function (data, type, full, meta) {

                        if (data == true) {
                            return '<input type="radio"  name="isPrimaryContactButton" checked >';
                        }
                        return '<input type="radio" name="isPrimaryContactButton" disabled>';
                    },"width": "5%","data": "isPrimaryContact"
                },
                {"width": "40%", "data": "contactName"},
                {"width": "55%", "data": "phoneNo"},
                {"render":function(data, type, full, meta){

                    return  ' <div>' +

                        '<a data-toggle="tooltip" title="Delete" style="margin-left: 1px;" class="glyphicon glyphicon-trash" onclick="detailsContactTable.deleteContactListRow('+meta.row+')" aria-hidden="true"></a>' +

                        '<div> '
                }

                ,"visible": true, "width": "5%", "data": "contactId"},
                {"visible": true,"width": "40%", "data": "contactId"},
                 ],

                initComplete: function ( settings, json ) {
                    $("#contactTab div.internalToolbar").html('<div class="tools">' +
                        ' <div  class="actions">' +
                        ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="detailsContactTable.addNewContact();">' +
                        ' <i class="glyphicon glyphicon-plus"></i>' +
                        ' </a>' +
                        ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="contactList.accountCreateContact();">' +
                        ' <i class="fa fa-male"></i>' +
                        ' </a>' +
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

    contactListClick: function () {

        $('#contactTable').find('tbody').on('click', 'tr', function () {
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
            } else {
                contactTable.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
            }
        });

    },
    addNewContact: function () {
        var row;
        try {

            row = contactTable.row.add({
                isPrimaryContact: '',
                contactName: '',
                phoneNo: '',
                contactId: ''
            }).draw();
            var length = ($("#contactTable tr").length - 2);
            detailsContactTable.makePhoneListEdit(row, length);
        } catch (e) {
            var length = ($("#contactTable tr").length - 2);
            var rowX = contactTable.row(length).node()
            $(rowX).addClass('modified');
            rowX.setAttribute("control", "new");
            detailsContactTable.makePhoneListEdit(rowX, length);

        }



    },
    setColumnVisible: function (colNum, visible) {
        var column = contactTable.column(colNum);
        column.visible(visible);
    },
    makePhoneListEdit: function (row, index) {
        console.log(row, index);
        var date = new Date().getMilliseconds();

     //   detailsContactTable.setColumnVisible(4, true);
        var td0 = row.children[0];
        var td1 = row.children[1];
        var td2 = row.children[2];
        var td3 = row.children[3];


        var td0Val = td0.innerHTML;
        var isPrimary = '<input type="radio" class="form-control" checked/>';
        td0.innerHTML = isPrimary;
        var isPrimaryId = td0.children[0].id = "isPrimary-" + date
        td0.children[0].name = "isPrimaryContactButton";//+ date

        var td1Val = td1.innerHTML;

        var name="contact-"+ date
        var id="contact-"+ date
        var hidden="contactId-"+ date
        var contact = $.ajax({
            url: common.projectPath+"/contact/renderContactAutoComplete",
            data:{id:id ,name:name, hidden:hidden},
            success: function (response) {
                $(td1).html(response);
            },


        });
        td1.innerHTML = contact;
        td1.children[0].id = "contact-" + date;
        td1.children[0].name = "contact-" + date;




    },
    deleteContactListRow: function (rowNo) {


        var isOk = window.confirm("Are you sure?");
        if (isOk) {
            var row=contactTable.row(rowNo);
            if ($(row).hasClass('selected')) {
                $(row).removeClass('selected');
            } else {
                contactTable.$('tr.selected').removeClass('selected');
                $(row).addClass('selected');
            }
            setTimeout(function(){
                contactTable.row('.selected').remove().draw(false);

            },10);
        }
        return false;


    },
    init: function () {


        detailsContactTable.addContactListTable();
        detailsContactTable.contactListClick();

    }



}




}();