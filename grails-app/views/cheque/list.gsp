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

                <i class="fa fa-book"></i> Cheques List</span>
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

        <div  class="col-md-3">
            <div class="form-group">
                <div class='input-group date' id='fromDate'>
                    <input type='text' class="form-control"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="form-group">
                <div class='input-group date' id='toDate'>
                    <input type='text' class="form-control"/>
                    <span class="input-group-addon">
                        <span class="glyphicon glyphicon-calendar"></span>
                    </span>
                </div>
            </div>
        </div>

    </div>


        <div class="row">
            <div class="col-md-12">
                <table id="chequeTable" width="100%" class="table table-striped table-bordered table-hover">
                    <thead scope="col">
                    <tr>
                        <th scope="col" property="id">
                            <g:message code="express.contact.list.no" default="SerialNum"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="cheque.serialNo" default="Primary"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="cheque.bankName" default="bankName"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="cheque.dueDate" default="dueDate"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="cheque.total" default="amount"/>
                        </th>
                        <th scope="col">
                            <g:message code="cheque.insertDate" default="insertDate"/>
                        </th>
                        <th scope="col">
                            <g:message code="accountBalance.actions" default="actions"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>

        <script>

            $(document).ready(function () {
                chequeList.init();

            });



        </script>