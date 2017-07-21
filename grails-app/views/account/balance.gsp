<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 1/31/2017
  Time: 9:35 PM
--%>
<style>


.table > tbody > tr > td,
.table > tbody > tr > th,
.table > tfoot > tr > td,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > thead > tr > th {
    padding: 6px;
    line-height: 0.42857;
    vertical-align: middle;
    border-top: 1px solid #e7ecf1;
}

table.dataTable thead .sorting_asc,
table.dataTable thead .sorting_desc,
table.dataTable thead .sorting
{
    padding: 6px 20px;
}

.datepicker-dropdown {
    top: 155px!important;
    /*left: auto !important;*/

}

.dropdown-menu:before {
    position: absolute;
    top: -7px;
    left: 9px;
    display: inline-block;
    border-right: 7px solid transparent;
    border-bottom: 7px solid #ccc;
    border-left: 7px solid transparent;
    border-bottom-color: rgba(0, 0, 0, 0.2);
    content: '';
}

.dropdown-menu:after {
    position: absolute;
    top: -6px;
    left: 10px;
    display: inline-block;
    border-right: 6px solid transparent;
    border-bottom: 6px solid #ffffff;
    border-left: 6px solid transparent;
    content: '';
}

</style>

<meta name="layout" content="main"/>
<div style="margin-top:-15px;" class="portlet box blue-madison">

    <div class="portlet-title">
        <div class="caption">
            <span>

                <i class="fa fa-book"></i> Accounts Balances</span>
        </div>

        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

    <div class="portlet-body">
        <div class="row">

            <div class="col-md-4">
                %{--<ul class="pager">--}%
                    <div class="col-md-2">
                        %{--<li class="previous">--}%
                            <a class="btn btn-default" style="margin-right:45px" id="previous" href="javascript:;">&laquo;</a>
                        %{--</li>--}%
                    </div>

                    <div  class="col-md-8">
                        <div class="form-group">
                            <div id="employeeField">
                                <input id="autoAccount" type="text" data-provide="typeahead" class="typeahead form-control">
                                <input id="accountId" type="hidden">
                                <span class="input-group-btn"></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-2">
                        %{--<li class="next">--}%
                            <a class="btn btn-default" style="margin-right: -25px;" id="next" href="javascript:;">&raquo;</a>
                        %{--</li>--}%
                    </div>
                %{--</ul>--}%
            </div>
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
            <div class="col-md-2">
                <div id="printBtn" class="btn-group"  >
                    <button type="button" class="btn btn-primary" style="height: 35px;">  <span class="glyphicon glyphicon-print pull-left"></span></button>
                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">PDF</a></li>
                        <li><a href="#">EXCEL</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <table id="balanceTable" width="100%" class="table table-striped table-bordered table-hover">
                    <thead scope="col">
                    <tr>
                        <th scope="col" property="id">
                            <g:message code="DailyMovement.rowNum" default="#"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="express.contact.list.fullName" default="full Name"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="accountBalance.movementType" default="Movement Type"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="accountBalance.date" default="Date"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="accountBalance.value" default="Value"/>
                        </th>
                        <th scope="col">
                            <g:message code="accountBalance.totalAmount" default="Total Amount"/>
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


</div>


<script>


    %{--var accountLinks = {--}%
        %{--accountListData: "${createLink(uri:"/$params.lang/$controllerName/accountListData")}",--}%
        %{--typeHeadUrl: "${createLink(uri:"/$params.lang/$controllerName/contactTypeahead")}",--}%
        %{--accountDetails: "${createLink(uri:"/$params.lang/$controllerName/accountDetails")}",--}%
        %{--contactPhones: "${createLink(uri:"/$params.lang/$controllerName/getContactPhones")}",--}%
        %{--createAccount:"${createLink(uri:"/$params.lang/$controllerName/createAccount")}",--}%
        %{--manageAccount:"${createLink(uri:"/$params.lang/$controllerName/manageAccount")}",--}%
    %{--}--}%


    $(document).ready(function () {
        accountBalanceList.init();
    });
</script>