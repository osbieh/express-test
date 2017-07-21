<div class="row">
    <g:render template="/templates/payments/paymentForm"/>
</div>

<div class="row">
    <div class="col-md-12" style="background-color: transparent;">

        <div class="profile-content">
            <div class="row">

                <div class="col-md-3">
                    <form class="form-horizontal">
                        <div class="form-group"></div>
                        <div class="form-group"></div>
                        <div class="form-group"></div>
                        <div class="form-group">
                            <label class="col-md-3 control-label"><g:message code="payment.type.cash"/> </label>

                            <div class="col-md-9">
                                <input type="text" id="cashAmount" class="form-control"  value="0">
                                <input type="hidden" id="cashDetailsId">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label bold"> <g:message code="payment.type.cheque" /></label>

                            <div class="col-md-9">
                                <input type="text" id="cheque" class="form-control" value="0" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-3 control-label"><g:message code="cheque.total"/>&nbsp;</label>

                            <div class="col-md-9">
                                <input type="text" id="totalPayment" class="form-control" value="0" disabled >
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-9">
                    <g:render template="/templates/payments/cheque"/>
                </div>
            </div>
        </div>

    </div>
</div>



<div class="modal-footer">
    <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="paymentDetails.modalClose();">Close</button>
    <button class="btn btn-primary" onclick="cheque.managePaymentDetails();">Save</button>
</div>


