<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 4/9/2017
  Time: 2:17 AM
--%>

<meta name="layout" content="main"/>
<div style="margin-top:-15px;" class="portlet box blue-madison">

    <div class="portlet-title">
        <div class="caption">
            <span>

                <i class="fa fa-book"></i> Payment List</span>
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
        </div>


        <div class="row">
            <div class="col-md-12">
                <table id="paymentListTable" width="100%" class="table table-striped table-bordered table-hover">
                    <thead scope="col">
                    <tr>
                        <th scope="col" property="id">
                            <g:message code="order.list.no" default="SerialNum"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="payment.paymentId" default="paymentId"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="payment.date" default="Payment Date"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="order.totalAmount" default="totalAmount"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="order.status" default="status"/>
                        </th>
                        <th scope="col">
                            <g:message code="payment.actions" default="actions"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>

    $(document).ready(function(){

        paymentList.init();

    });

</script>