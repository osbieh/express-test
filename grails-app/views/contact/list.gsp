<meta name="layout" content="main"/>
<style>


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
<div style="margin-top:-15px;" class="portlet box blue-madison">
    <div class="portlet-title">
        <div class="caption">
            <span>

                <i class="fa fa-book"></i> Contact List</span>
        </div>

        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

    <div class="portlet-body">

        <div class="row">

            <div class="col-md-6">
                <ul class="pager">
                    <div class="col-md-2">
                        <li class="previous">
                            <a class="btn btn-default" id="previous" href="javascript:;">&laquo;</a>
                        </li>
                    </div>

                    <div style="margin: 0 -40px -40px;" class="col-md-8">
                        <div class="form-group">
                            <div id="employeeField">
                                <input id="autoContact" type="text" data-provide="typeahead" class="typeahead form-control">
                                <input id="contactId" type="hidden">
                                <span class="input-group-btn"></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        <li class="next">
                            <a class="btn btn-default" id="next" href="javascript:;">&raquo;</a>
                        </li>
                    </div>
                </ul>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <table id="contactTable" width="100%" class="table table-striped table-bordered table-hover">
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
        </div></div></div>

<div class="container">

    <!-- Default bootstrap modal example -->
    <div class="modal fade" id="contactModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header portlet-title" style="background-color:#c9d8c5">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h8 class="modal-title" id="myModalLabel"></h8>
                </div>

                <div id="contactModal-body" class="modal-body">

                </div>

            </div>
        </div>
    </div>
</div>
</div>
<com:renderImageLoader/>




<script>


    var contactLinks = {
        contactListData: "${createLink(uri:"/$params.lang/$controllerName/contactListData")}",
        typeHeadUrl: "${createLink(uri:"/$params.lang/$controllerName/contactTypeahead")}",
        contactDetails: "${createLink(uri:"/$params.lang/$controllerName/contactDetails")}",
        contactPhones: "${createLink(uri:"/$params.lang/$controllerName/getContactPhones")}",
    }



    $(document).ready(function () {
        contactList.contactDetailsLink=contactLinks.contactDetails;
        contactList.init();
    });




</script>