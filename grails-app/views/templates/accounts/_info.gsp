<%@ page import="express.enumurations.AccountType" %>


<form id="contactDetailsForm" action="#" class="horizontal-form">
    <div class="form-body">
        <div class="row">
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label bold">Account Id</label>
                    <input type="text" id="accountId" class="form-control disabled" disabled>
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label class="control-label">Account Name</label>
                    <input type="text" id="accountName" class="form-control">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label class="control-label">Type</label>
                    <g:select name="accountType" class="form-control"
                              from="${express.enumurations.AccountType.values()*.name()}"
                              keys="${express.enumurations.AccountType.values()*.name()}"
                              valueMessagePrefix="ACCOUNTTYPE"
                    />
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">Classification</label>
                    <input id="classification" dir="rtl" type="number" data-size="xs" class="rating rating-loading">
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">Tax No</label>
                    <input type="text" id="taxNo" class="form-control">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">Tax Name</label>
                    <input type="text" id="taxName" class="form-control">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label class="control-label">Email</label>
                    <input type="text" id="email" class="form-control">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div class="col-md-2">
                <div class="form-group">
                    <label class="control-label">Status</label>
                    <input type="text" id="status" class="form-control">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label class="control-label"><g:message code="account.firstBalanceAmount"/> </label>
                    <input type="text" id="balanceAmount" class="form-control" >
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>

            <div  class="col-md-4">
                <div class="form-group">
                    <label class="control-label"><g:message code="account.firstBalanceDate"/></label>
                    <div class='input-group date' id='fromDate'>
                        <input type='text' style="height: 31px;" class="form-control"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>



        </div>

        <div class="row">
            <div class="col-md-12">
                <label class="control-label">Note</label>
                <textarea rows="2" id="note" class="form-control"></textarea>
            </div>

        </div>
    </div>
</form>


<script>



</script>