<style>
.form-control {
    width: 100%;
    height: 25px;
    padding: 0px 3px;
}


</style>


<br>

<div class="row">

    <ul class="nav nav-tabs">
        <li >
            <a href="#accountInfoTab" data-toggle="tab">Info</a>
        </li>
        <li >
            <a href="#contactTab" data-toggle="tab">Contacts</a>
        </li>
        <li>
            <a href="#phonesTab" data-toggle="tab">Phones</a>
        </li>
        <li >
            <a href="#addressTab" data-toggle="tab">address</a>
        </li>
    </ul>

    <div class="tab-content">

        <div id="accountInfoTab" class="col-md-10 tab-pane active">
            <g:render template="/templates/accounts/info"/>
        <br>
        </div>
        <div id="contactTab" class="tab-pane">
            <g:render template="/templates/accounts/contacts"/>
        </div>

        <div id="phonesTab" class="tab-pane">

        <g:render template="/templates/accounts/phones"/>


        </div>

        <div id="addressTab" class="tab-pane">
        <g:render template="/templates/accounts/address"/>

        </div>
    </div>





</div>

    <div class="modal-footer">
        <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="accountDetails.modalClose();">Close</button>
        <button class="btn btn-primary" onclick="accountDetails.manageAccount();">Save</button>
    </div>



<script>



$(document).ready(function () {

    $("#classification").rating({
        containerClass: 'is-star',
        min: 0,
        max: 5,
        step: 1,
        showCaption:false,
        class:"xs",

    });


    accountDetails.fillAccountInfo(JSON.parse("${accountInfo}".replace(/&quot;/g, '"')));

    detailsContactTable.accountContactaLink=common.projectPath+"/account/accountContacts?accountId=${params?.accountId}";
    contactList.contactDetailsLink=common.projectPath+"/contact/contactDetails";

    accountDetails.init();
    phoneTable.phonesLink= common.projectPath+"/account/getAccountPhones";
    phoneTable.masterTableId=1;
    phoneTable.init();
});




</script>