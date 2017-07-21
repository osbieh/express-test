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

</style>


<div class="row">

    <g:render template="/templates/payments/paymentForm"/>

    %{--<g:render template="/templates/payments/cheque"/>--}%

    %{--<div class="row">--}%
        %{--<div class="col-md-4">--}%
            %{--<div class="form-group">--}%
                %{--<label class="control-label">Cash Amount</label>--}%
                %{--<input type="text" id="cashAmount" class="form-control">--}%
            %{--</div>--}%
        %{--</div>--}%
    %{--</div>--}%

%{--</div>--}%
</div>
<div class="row">
    <div class="col-md-11">
        <table id="paymentDetailsTable" width="100%" class="table table-striped table-bordered table-hover">
            <thead scope="col">
            <tr>
                <th scope="col" property="id">
                    <g:message code="cheque.no" default="SerialNum"/>
                </th>
                <th scope="col">
                    <g:message code="paymentDetails.paymentType" default="paymentType"/>
                </th>
                <th scope="col">
                    <g:message code="paymentDetails.total" default="total"/>
                </th>
                <th scope="col">
                    <g:message code="payment.currency" default="currency"/>
                </th>
                <th scope="col">
                    <g:message code="order.list.actions" default="actions"/>
                </th>
                <th scope="col" >
                    <g:message code="paymentDetailsId" default="paymentDetailsId"/>
                </th>
                <th scope="col" property="total2">

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
                <th style="font: bold 18px/19px tahoma;"></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>

        </table>
    </div>
</div>




<div class="modal-footer">
    <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="paymentDetails.modalClose();">Close</button>
    <button class="btn btn-primary" onclick="paymentDetails.managePayment();">Save</button>
</div>


<script>

    var paymentDetailVars={
        currencyType: '<pub:select  class="form-control" from="${params?.currencyTypes}" name="currency" optionKey="id" optionValue="name"/>',
        paymentType:  '<pub:select  class="form-control" from="${params?.paymentTypes}" name="paymentType" optionKey="id" optionValue="name"/>',

    };


    $(document).ready(function () {

        paymentDetails.init();


    });

</script>