<style>
.form-control {
    width: 100%;
    height: 25px;
    padding: 0px 3px;
}


</style>


<div class="portlet light " id="form_wizard_1">
    %{--<div class="portlet-title">--}%
    %{--<div class="caption">--}%
    %{--<i class=" icon-layers font-red"></i>--}%
    %{--<span class="caption-subject font-red bold uppercase"> Form Wizard ---}%
    %{--<span class="step-title"> Step 1 of 4 </span>--}%
    %{--</span>--}%
    %{--</div>--}%
    %{--<div class="actions">--}%
    %{--<a class="btn btn-circle btn-icon-only btn-default" href="javascript:;">--}%
    %{--<i class="icon-cloud-upload"></i>--}%
    %{--</a>--}%
    %{--<a class="btn btn-circle btn-icon-only btn-default" href="javascript:;">--}%
    %{--<i class="icon-wrench"></i>--}%
    %{--</a>--}%
    %{--<a class="btn btn-circle btn-icon-only btn-default" href="javascript:;">--}%
    %{--<i class="icon-trash"></i>--}%
    %{--</a>--}%
    %{--</div>--}%
    %{--</div>--}%
    <div class="portlet-body form">
        <form class="form" action="#" id="submit_form" method="POST">
            <div class="form-wizard">
                <div class="form-body">
                    <ul class="nav nav-pills nav-justified steps">
                        <li>
                            <a href="#tab1" data-toggle="tab" class="step">
                                <span class="number"> 1 </span>
                                <span class="desc">
                                    <i class="fa fa-check"></i> Account Info </span>
                            </a>
                        </li>
                        <li>
                            <a href="#contactTab" data-toggle="tab" class="step">
                                <span class="number"> 2 </span>
                                <span class="desc">
                                    <i class="fa fa-check"></i> Contacts </span>
                            </a>
                        </li>
                        <li>
                            <a href="#phonesTab" data-toggle="tab" class="step active">
                                <span class="number"> 3 </span>
                                <span class="desc">
                                    <i class="fa fa-check"></i> Phones </span>
                            </a>
                        </li>
                        <li>
                            <a href="#tab4" data-toggle="tab" class="step">
                                <span class="number"> 4 </span>
                                <span class="desc">
                                    <i class="fa fa-check"></i> Confirm </span>
                            </a>
                        </li>
                    </ul>
                    %{--<div id="bar" class="progress progress-striped" role="progressbar">--}%
                    %{--<div class="progress-bar progress-bar-success"> </div>--}%
                    %{--</div>--}%
                    <div class="tab-content">
                        %{--<div class="alert alert-danger display-none">--}%
                        %{--<button class="close" data-dismiss="alert"></button> You have some form errors. Please check below. </div>--}%
                        %{--<div class="alert alert-success display-none">--}%
                        %{--<button class="close" data-dismiss="alert"></button> Your form validation is successful! </div>--}%
                        <div class="tab-pane active" id="tab1">

                            <g:render template="/templates/accounts/info"/>
                        </div>
                        <div class="tab-pane" id="contactTab">
                            %{--<h3 class="block">Provide your profile details</h3>--}%
                            <g:render template="/templates/accounts/contacts"/>
                        </div>
                        <div class="tab-pane" id="phonesTab">
                            %{--<h3 class="block">Provide your billing and credit card details</h3>--}%
                            <g:render template="/templates/accounts/phones"/>



                        </div>
                        <div class="tab-pane" id="tab4">
                          <g:render template="/templates/accounts/collections"/>
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <div class="row">
                        <div class="col-md-offset-3 col-md-9">
                            <a href="javascript:;" class="btn default button-previous">
                                <i class="fa fa-angle-right"></i> Previous </a>
                            <a href="javascript:;" class="btn btn-outline green button-next"> Next
                                <i class="fa fa-angle-left"></i>
                            </a>
                            <a href="javascript:;" class="btn green button-submit"> Submit
                                <i class="fa fa-check"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</div>

<script>

    $(document).ready(function() {
        FormWizard.init();
        contactList.contactDetailsLink=common.projectPath+"/contact/contactDetails";
        detailsContactTable.init();
        phoneTable.phonesLink= common.projectPath+"/account/getAccountPhones";
        phoneTable.masterTableId=-1;
        phoneTable.init();
    });

</script>