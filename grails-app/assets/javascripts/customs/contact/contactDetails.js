/**
 * Created by osbie on 12/6/2016.
 */
var contactDetails = function () {
    return {

        fillContactInfo: function () {
            console.log("----", (contactDetailVar.contactInfo));
            var contact = contactDetailVar.contactInfo;

            $("#firstName").val(contact.firstName);
            $("#secondName").val(contact.secondName);
            $("#lastName").val(contact.lastName);
            $("#email").val(contact.email != null ? contact.email.replace("&#64;", "@") : contact.email);
            $("#jobTitle").val(contact.jobtitle);
            $("#note").val(contact.note);

        },




        addressList: function () {

            addressTable = $("#addressTable").DataTable({
                "paging": false,
                "ordering": false,
                "info": false,
                "ajax": {
                    url: contactLinks.contactPhones,
                    "data": {contactId: contactDetailVar.contactInfo.id}
                },
                dom: '<"toolbar">rtp',
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "columns": [
                    {
                        "render": function (data, type, full, meta) {

                            if (data == true) {
                                return '<input type="radio" name="addressButton"  value="wibble" checked>';
                            }
                            return '<input type="radio" name="touchbutton" value="wibble">';
                        }, "width": "5%", "data": "isPrimary"
                    },  // here's a radio button, modify to use data to populate it

                    {
                        "render": function (data, type, full, meta) {
                            return '<input type="checkbox">';
                        }, "width": "5%", "data": "isActive"
                    },
                    {"width": "15%", "data": "Address"},
                    {"width": "15%", "data": "phoneType"},
                    {"visible": false, "width": "5%", "data": "contactId"},

                ],
                initComplete: function () {
                    $("div.toolbar")
                        .html('<div class="tools">' +
                            ' <div  class="actions">' +
                            ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="contractsList.contractAddWizard();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>' +
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                                //' <a class="btn btn-circle btn-icon-only btn-default" href="#">'+
                            '</div>');
                },


            });


        },


        isTableInEditMode: function () {
            var controls = $("#timesheetDetails tr[control='edit'],tr[control='new']").length;
            if (controls > 0)
                return true;
            else
                return false;

        },


        saveModel: function (form) {

            var rows = phonesTable.rows().data().length;
            var phones = [];

            for (i = 0; i < rows; i++) {
                var row = phonesTable.row(i);
                phoneTable.makeRowView(row.node());
                phones.push(row.data());
            }

            var contact = {
                contactId: contactDetailVar.contactInfo.id, firstName: $("#firstName").val(),
                secondName: $("#secondName").val(), lastName: $("#lastName").val(),
                email: $("#email").val(), jobTitle: $("#jobTitle").val(), note: $("#note").val(),


            };


            $.ajax({
                url: contactDetailVar.saveAllData,
                data: {contact: JSON.stringify(contact), phones: JSON.stringify(phones)},
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {
                    contactList.refreshTable();
                    toastr.success("contact are modified", "Success");
                   contactDetails.modalClose();
                    $('#ajax_loader').hide();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                    //    toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                    // $('#myModal').modal('hide');

                }
            });


        },

        modalClose: function () {
            $('#contactModal').modal('hide');
        },


        init: function () {
            contactDetails.fillContactInfo();
            phoneTable.init();
            //contactDetails.phonesList();
            //contactDetails.phoneListClick();
            //contactDetails.phoneListDbClick();
            //  contactDetails.addressList();
        }

    }

}();
