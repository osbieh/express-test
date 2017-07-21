<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 4/9/2017
  Time: 2:17 AM
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
            <span>

                <i class="fa fa-book"></i> Order List</span>
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
        <table id="orderListTable" width="100%" class="table table-striped table-bordered table-hover">
            <thead scope="col">
            <tr>
                <th scope="col" property="id">
                    <g:message code="order.list.no" default="SerialNum"/>
                </th>
                <th scope="col" property="id">
                    <g:message code="order.list.ID" default="Order No"/>
                </th>
                <th scope="col" property="id">
                    <g:message code="order.accountName" default="Account Name"/>
                </th>
                <th scope="col" property="id">
                    <g:message code="order.date" default="orderDate"/>
                </th>
                <th scope="col" property="id">
                    <g:message code="order.totalAmount" default="totalAmount"/>
                </th>
                <th scope="col" property="id">
                    <g:message code="order.status" default="status"/>
                </th>
                <th scope="col">
                    <g:message code="order.actions" default="actions"/>
                </th>

            </tr>
            </thead>
            <tbody>
            </tbody>
            <tfoot>
            <tr>


                <th></th>
                <th></th>
                <th></th>
                <th><g:message code="orderDetails.total"/></th>
                <th style="font: bold 18px/19px tahoma;"></th>
                <th></th>
                <th></th>
            </tr>

            </tfoot>
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

    $(document).ready(function(){

        orderList.init();

    });

</script>