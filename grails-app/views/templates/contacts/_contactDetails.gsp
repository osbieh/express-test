    %{--<div class="portlet-body form">--}%
        <!-- BEGIN FORM-->

    <style>
    .control-label .required, .form-group .required {
        color: #333;
    }
    .required:after { content:" *";  color: #e02222; }
    </style>

    <div class="row">

        <ul class="nav nav-tabs">
            <li >
                <a href="#contactInfoTab" data-toggle="tab">Info</a>
            </li>
            <li>
                <a href="#contactPhonesTab" data-toggle="tab">Phones</a>
            </li>
            <li >
                <a href="#contactAddressTab" data-toggle="tab">address</a>
            </li>
        </ul>

        <div class="tab-content">

            <div class="tab-content">

                <div id="contactInfoTab" class="col-md-10 tab-pane active">
                <g:render template="/templates/contacts/info"/>
                <br>
            </div>


            <div id="contactPhonesTab" class="tab-pane">

                <g:render template="/templates/contacts/phones"/>


            </div>

            <div id="contactAddressTab" class="tab-pane">
                <g:render template="/templates/contacts/address"/>

            </div>
        </div>


    </div>
</div>
    <div class="modal-footer">

        <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="contactDetails.modalClose();">Close</button>
            <button class="btn btn-primary" onclick="contactDetails.saveModel(this);">Save</button>

    </div>


    <script>

        var contactDetailVar={
            contactInfo:JSON.parse("${contact}".replace(/&quot;/g,'"')),
            contactPhones:JSON.parse("${phones}".replace(/&quot;/g,'"')),
            saveAllData:  common.projectPath+"/contact/saveContactDetails",
            contactPhones: common.projectPath+"/contact/getContactPhones"
        }

      //  common.projectPath


        $(document).ready(function () {
            phoneTable.phonesLink=contactDetailVar.contactPhones;
            phoneTable.masterTableId=contactDetailVar.contactInfo.id;
            contactDetails.init();

        });



    </script>