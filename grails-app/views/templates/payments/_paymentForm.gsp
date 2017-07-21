<style>

.profile-sidebar ul.profile-nav li {
    color: #557386;
    display: block;
    font-size: 14px;
    padding: 8px 10px;
    margin-bottom: 1px;
    background: #f0f6fa;
    border-left: solid 2px #c4d5df;
    font-weight: bold;
}

.profile-sidebar label {
    font-weight: 100;
}

</style>


<div class="col-md-12" style="background-color: transparent;position: relative;">
    <!-- BEGIN PROFILE SIDEBAR -->
    <div class="profile-sidebar">
        <!-- PORTLET MAIN -->
        <div class="col-md-12">
            <ul class="list-unstyled profile-nav">

                <li>
                    <label for="dailyMovementId"><g:message code="order.dailyMovementId"/></label>
                    <input type="text" id="dailyMovementId" disabled/>
                </li>
                <li>
                    <label for="paymentId"><g:message code="payment.paymentId"/></label>
                    <span><input type="text" id="paymentId" disabled/></span>
                </li>

                <li>
                    <span><g:message code="order.createdBy"/></span> &nbsp;&nbsp;:
                    <span>${params?.createdBy}</span>
                </li>
                <li>
                    <span><g:message code="order.createdDate"/></span>&nbsp;&nbsp;:
                    <span>${params?.created}</span>
                </li>
                <li>
                    <span><g:message code="order.lastUpdatedBy"/></span>&nbsp;&nbsp;:
                    <span>${params?.lastUpdatedBy}</span>
                </li>
                <li>
                    <span><g:message code="order.lastUpdated"/></span>&nbsp;&nbsp;:
                    <span>${params?.lastUpdated}</span>
                </li>
            </ul>

        </div>
    </div>
    <!-- END BEGIN PROFILE SIDEBAR -->
    <!-- BEGIN PROFILE CONTENT -->
    <div class="profile-content">
        <div class="row">

            <form id="orderForm" action="#" class="horizontal-form" style="padding: 20px;">
                <div class="form-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label"><g:message code="express.account.list.name"
                                                                        default="Account Name"/></label>
                                <input type="text" id="accountName" class="form-control" disabled>
                                <input type="hidden" id="accountId" class="form-control">
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>


                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label"><g:message code="payment.paymentDate"/> </label>
                                <input type="text" id="orderDate" class="form-control">
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label"><g:message code="order.currentBalance"/></label>
                                <input type="text" id="currentBalance" class="form-control" disabled>
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>

                    </div>

                    <div class="row">

                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label">Status</label>
                                <g:select class="form-control" from="${params?.paymentStatus}"
                                          valueMessagePrefix="Payment" name="paymentStatus"/>

                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group">

                                <label class="control-label">Recept No</label>
                                <input type="text" id="receptNo" class="form-control">
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>

                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label"><g:message code="payment.momentBalance"/></label>
                                <input type="text" id="momentBalance" class="form-control" disabled>
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="control-label"><g:message code="order.list.totalPrice"/></label>
                                <input type="text" id="totalAmount" class="form-control" disabled>
                                %{--<span class="help-block"> This is inline help </span>--}%
                            </div>
                        </div>

                    </div>

                </div>
            </form>

        </div>
    </div>
    <!-- END PROFILE CONTENT -->
</div>




<script>

    $(document).ready(function () {
        paymentForm.fillPaymentInfo("${params?.record}".replace(/&quot;/g, '"'));
    });



</script>