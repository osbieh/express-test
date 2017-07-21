/**
 * Created by osbie on 12/30/2016.
 */

var accountList = function () {
    return {

        addAccountListTable: function () {
            accountTable = $("#accountTable").DataTable({
                    "lengthMenu": [[5, 25, 50, -1], [5, 25, 50, "All"]],
                    "paging": true,
                    "ordering": true,
                    "info": true,
                    "ajax": accountLinks.accountListData,
                    "columns": [
                        {"width": "5%",  "data": "accountId"},
                        {"width": "20%", "data": "accountName"},
                        {"width": "20%", "data": "taxName"},
                        {"width": "10%", "data": "accountType"},
                        {"width": "20%", "data": "address"},
                        {"width": "5%", "data": "email","visible": true},
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
                            ' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="accountList.createAccount();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>' +
                            '</div>'
                        );
                    },


                }
            );
        },
        onTableDbClick: function (th) {
            var row = accountTable.row(th);
            var accountId=row.data().accountId

            $('#modal-body').load(accountLinks.accountDetails, {accountId:accountId}, function (result) {
                $('#myModal').modal({show: true});
            });

        },

        createAccount:function(){

            $('#modal-body').load(accountLinks.createAccount, {}, function (result) {
                $('#myModal').modal({show: true});
            });

        },

        addAccountListTableEvents: function () {

            ////manage Style
            //$('#contactTable tbody').on('click', 'tr', function () {
            //    accountTable.onTableClick(this);
            //});


            //manage Row details
            $('#accountTable tbody').on('dblclick', 'tr', function () {
                accountList.onTableDbClick(this);
            });


        },
        init: function () {
            accountList.addAccountListTable();
            accountList.addAccountListTableEvents();
        }


    }
}();
