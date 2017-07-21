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

.toggle.ios, .toggle-on.ios, .toggle-off.ios { border-radius: 20px; }
.toggle.ios .toggle-handle { border-radius: 20px; }

</style>

<div class="row">

    <g:render template="/templates/order/orderForm"/>


</div>


<div class="row">
    <div class="col-md-12">
        <table id="orderDetailsTable" width="100%" class="table table-striped table-bordered table-hover">
            <thead scope="col">
            <tr>
                <th scope="col" property="id">
                    <g:message code="order.list.no" default="SerialNum"/>
                </th>

                <th scope="col">
                    <g:message code="order.list.productName" default="ProductName"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.productUnit" default="Product Unit"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.quantity" default="quantity"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.unitPrice" default="unit Price"/>
                </th>
                <th scope="col">
                    <g:message code="orderDetails.discount" default="discount"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.totalPrice" default="total Price"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.actions" default="actions"/>
                </th>
                <th scope="col" >
                    <g:message code="orderDetailsId" default="orderDetailsId"/>
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
                <th></th>
                <th></th>
                <th><g:message code="orderDetails.total"/></th>
                <th style="font: bold 18px/19px tahoma;"></th>
                <th></th>
            </tr>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th><g:message code="orderDetails.discount"/></th>
                <th style="font: bold 18px/19px tahoma;"></th>
                <th></th>
            </tr>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th><g:message code="orderDetails.totalAmount"/></th>
                <th style="font: bold 18px/19px tahoma;"></th>
                <th></th>
            </tr>
            </tfoot>

        </table>
    </div>
</div>

<div class="row">

    <div class="col-md-12">
        <label class="control-label">Note</label>
        <textarea rows="2" id="note" class="form-control"></textarea>
    </div>

</div>


<div class="modal-footer">
    <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="orderDetails.modalClose();">Close</button>
    <button class="btn btn-primary" onclick="orderDetails.manageOrder();">Save</button>
</div>




<script>

    var orderDetailVars = {
        productUnits: '<pub:select  class="form-control" from="${params?.productUnits}" valueMessagePrefix="Unit" name="units" />',
        manageOrder:""
    };


    $(document).ready(function () {
        orderDetails.init();
    });

</script>