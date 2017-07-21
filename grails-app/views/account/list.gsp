<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 1/31/2017
  Time: 9:35 PM
--%>
<style>

#newContactModal .modal-dialog {
    left: 10%;
    right: auto;

}


.specialRow {
    background-color: rgba(26, 112, 166, 0.33) !important;
}
.SelectSpecialRow {
    background-color: #b0bed9 !important;
}

.table > tbody > tr > td,
.table > tbody > tr > th,
.table > tfoot > tr > td,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > thead > tr > th {
    padding: 6px;
    line-height: 0.42857;
    vertical-align: top;
    border-top: 1px solid #e7ecf1;
}



</style>

<meta name="layout" content="main"/>
<div style="margin-top:-15px;" class="portlet box blue-madison">

    <div class="portlet-title">
        <div class="caption">
            <span>

                <i class="fa fa-book"></i> Account List</span>
        </div>

        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

  <div class="portlet-body">
%{--<div class="row">--}%
 %{--<div class="col-md-5">--}%
    %{--<com:typeahead id="autoContact" name="autoContact" hidden="contactId"/>--}%
%{--</div>--}%
    %{--<br>--}%
%{--</div>--}%
    <div class="row">
        <div class="col-md-12">
            <table id="accountTable" width="100%" class="table table-striped table-bordered table-hover">
                <thead scope="col">
                <tr>
                    <th scope="col" property="id">
                        <g:message code="express.contact.list.no" default="SerialNum"/>
                    </th>
                    <th scope="col" property="id">
                        <g:message code="express.contact.list.fullName" default="Primary"/>
                    </th>
                    <th scope="col" property="id">
                        <g:message code="express.contact.list.phoneNo" default="address"/>
                    </th>
                    <th scope="col" property="id">
                        <g:message code="express.contact.list.phoneNo" default="address"/>
                    </th>
                    <th scope="col" property="id">
                        <g:message code="express.contact.list.address" default="address"/>
                    </th>
                    <th scope="col">
                        <g:message code="express.contact.list.email" default="email"/>
                    </th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>




  </div>

    <div class="container">

        <!-- Default bootstrap modal example -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header portlet-title" style="background-color:#c9d8c5">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span></button>
                        <h8 class="modal-title" id="myModalLabel"></h8>
                    </div>

                    <div id="modal-body" class="modal-body">

                    </div>

                </div>
            </div>
        </div>
    </div>



    <div  id="newContactModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header" style="background-color:#c9d8c5">

                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h8 class="modal-title" id="myModalLabel"></h8>
                </div>

                <div id="contact-modal-body" class="modal-body">

                </div>

            </div>
        </div>
    </div>
</div>


<script>


    var accountLinks = {
        accountListData: "${createLink(uri:"/$params.lang/$controllerName/accountListData")}",
        typeHeadUrl: "${createLink(uri:"/$params.lang/$controllerName/contactTypeahead")}",
        accountDetails: "${createLink(uri:"/$params.lang/$controllerName/accountDetails")}",
        contactPhones: "${createLink(uri:"/$params.lang/$controllerName/getContactPhones")}",
        createAccount:"${createLink(uri:"/$params.lang/$controllerName/createAccount")}",
        manageAccount:"${createLink(uri:"/$params.lang/$controllerName/manageAccount")}",
    }


    $(document).ready(function () {
        accountList.init();
    });
</script>