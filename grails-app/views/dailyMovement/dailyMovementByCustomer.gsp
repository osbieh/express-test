<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 6/11/2017
  Time: 8:51 AM
--%>

<meta name="layout" content="main"/>
<style>
.datepicker-dropdown {
    top: 155px!important;
    /*left: auto !important;*/

}


</style>
<div style="margin-top:-15px;" class="portlet box blue-madison">
    <div class="portlet-title">
        <div class="caption">
            <span><i class="fa fa-user"></i> Daily Movement By Customer</span>
        </div>

        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body">

    %{--<div class="form-body">--}%

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

        <br>

        <div class="row">
            <div class="col-md-12">

                <table id="dailyMovementTable" width="100%" class="table table-striped table-bordered table-hover">
                    <thead scope="col">
                    <tr>
                        <th scope="col" property="id">
                            <g:message code="DailyMovement.rowNum" default="#"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="DailyMovement.No" default="No"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="DailyMovement.type" default="Type"/>
                        </th>
                        <th scope="col" property="id">

                            <g:message code="DailyMovement.date" default="Movement Day"/>
                            %{--<span style="color:red !important; font-size: 14px;">*</span>--}%
                        </th>
                        <th scope="col" property="id">
                            <g:message code="DailyMovement.receptNo" default="receptNo"/>
                        </th>

                        <th scope="col" property="id">
                            <g:message code="DailyMovement.total" default="Total"/>
                        </th>
                        <th scope="col">
                            <g:message code="DailyMovement.status" default="status"/>
                        </th>
                        <th scope="col">
                            <g:message code="DailyMovement.details" default="details"/>
                        </th>
                        <th scope="col">
                            <g:message code="DailyMovement.actions" default="Actions"/>
                        </th>
                        <th scope="col">
                            <g:message code="DailyMovement.type" default="dailyMoveMentType2"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    %{--</div>--}%
</div>


<div class="container">

    <!-- Default bootstrap modal example -->
    <div class="modal fade" id="dailyMovementModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" style="width:1200px;position:absolute;top:0%;left:15%;">
            <div class="modal-content" >
                <div class="modal-header portlet-title" style="background-color:#c9d8c5">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" style="font-weight: bold" id="myModalLabel"></h4>
                </div>

                <div id="modal-body" class="modal-body">

                </div>

            </div>
        </div>
    </div>
</div>
</div>
<script>
$(document).ready(function () {
    customerDailyMovement.init();
   });
</script>