/**
 * Created by osbie on 2/5/2017.
 */
var accountDetails = function () {
    return {

        changedTabs:{info:false,contacts:false,phones:false,address:false},
        fillAccountInfo:function (data) {
            console.log("accountDetails--------------",data);
            $("#accountId").val(data.id);
            $("#accountName").val(data.accountName);
            $("#accountType option[value="+data.accountType.toString()+"]").attr('selected','selected');
            $("#classification").val(2);
            $("#taxNo").val(data.taxNo);
            $("#taxName").val(data.taxName);
            $("#email").val(data.email != null ? data.email.replace("&#64;", "@") : data.email);
            $("#status").val(data.status);
            $("#note").val(data.note);
            $("#balanceAmount").val(data.balanceAmount);
            $("#fromDate").val(data.fromDate);

        },

        manageAccount:function(){

            var accountInfo={
                    accountId:$("#accountId").val(),
                    classifications:$("#classifications").val(),
                    accountType:$("#accountType").val(),
                    accountName:$("#accountName").val(),
                    taxName:$("#taxName").val(),
                    status:$("#status").val(),
                    taxNo: $("#taxNo").val(),
                    email:$("#email").val(),
                    note:$("#note").val(),
                    balanceAmount:$("#balanceAmount").val(),
                    fromDate:$("#fromDate").val()
            };


            var rows = contactTable.rows().data().length;
            var contacts=[],phones = [];

            if(rows>0)
                for (i = 0; i < rows; i++) {
                    var row = contactTable.row(i);
                    var contactId=$(row.node().children[4]).text();
                    contacts.push({"contactId":contactId});
                }
            rows=phonesTable.rows().data().length;
            if(rows>0)
            for (i = 0; i < rows; i++) {
                var row = phonesTable.row(i);
               // if(!phoneTable.isRowInEditMode(row))
                phoneTable.makeRowView(row.node());
                phones.push(row.data());
            }

            console.log(phones);

            $.ajax({
                url: accountLinks.manageAccount,
                data: {accountInfo: JSON.stringify(accountInfo),accountContacts: JSON.stringify(contacts),accountPhones: JSON.stringify(phones)},
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {
                    //contactList.refreshTable();
                    toastr.success("contact are modified", "Success");
                    $('#myModal').modal('hide');
                    $('#ajax_loader').hide();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                       toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                     $('#myModal').modal('hide');

                }
            });

        },


        setAccountInfoFormChanged:function(){
            $('#contactDetailsForm :input').one('change keyup', function(){
                if(!accountDetails.changedTabs.info){
                    accountDetails.changedTabs.info=true;
                  //  alert("--------------------");
                }

            });


        },



        addContacts:function(){
            contactsTable=$("#contactsTable").DataTable({
                "paging": false,
                "ordering": false,
                "info": false,
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                dom: '<"internalToolbar">rtip',
            }) ;

        },
   modalClose:function(){
       $('#myModal').modal('hide');
    },

        init: function () {

            accountDetails.setAccountInfoFormChanged();
        //    accountDetails.fillAccountInfo();
       //     accountDetails.addContacts();

            detailsContactTable.init();

          //  phoneTable.init();
        }
    }
}();