<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 3/5/2017
  Time: 12:25 AM
--%>



<head>
    <meta name="layout" content="main"/>
    <title>Daily Movement</title>
</head>

<style>

.datepicker-dropdown {
    top: 118px !important;
    left: auto !important;

}

#dailyMovementTable .glyphicon {
    font-size: 25px;
}




</style>

<div class="portlet-body">

    <div class="form-body">

        <div class="row">

            <div class="col-md-2">
                <div class="form-group">
                    <div class='input-group date' id='dailyMovmentDatePicker'>
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

                            <g:message code="DailyMovement.account" default="Account"/>
                            <span style="color:red !important; font-size: 14px;">*</span>
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
    </div>
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

    var dailyMovementsVars = {
        select: '<pub:select  class="form-control" from="${params?.movementType}" valueMessagePrefix="MovementType" name="cc" />',
        orderStatus: '<pub:select style="padding: unset;"  class="form-control" from="${params?.orderStatus}" valueMessagePrefix="MovementType" name="order" />',

    };


    $(document).ready(function () {
        dailyMovement.init();
    });

</script>


