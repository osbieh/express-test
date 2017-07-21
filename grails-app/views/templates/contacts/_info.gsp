<form id="contactDetailsForm" action="#" class="horizontal-form">
    <div class="form-body">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label required">First Name</label>
                    <input type="text" id="firstName" class="form-control" placeholder="Osama">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>
            <!--/span-->
            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">Second Name</label>
                    <input type="text" id="secondName" class="form-control" placeholder="Lim">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>
            <!--/span-->
            <div class="col-md-4">
                <div class="form-group">
                    <label class="control-label">Last Name</label>
                    <input type="text" id="lastName" class="form-control" placeholder="Lim">
                    %{--<span class="help-block"> This is inline help </span>--}%
                </div>
            </div>
            <!--/span-->
        </div>
        <!--/row-->
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">Job Title</label>
                    <input type="text" id="jobTitle" class="form-control" placeholder="Lim">
                </div>
            </div>
            <!--/span-->
            <div class="col-md-6">
                <div class="form-group">
                    <label class="control-label">Email</label>
                    <input type="text" id="email" class="form-control" > </div>
            </div>
            <!--/span-->
        </div>

        <div class="row">
            <div class="col-md-12">
                <label class="control-label">Note</label>
                <textarea rows="2" id="note"  class="form-control"></textarea>
            </div>
        </div>
    </div>
    <br>
    %{--<div class="form-actions right">--}%
    %{--<button type="button" class="btn default">Cancel</button>--}%
    %{--<button type="submit" class="btn blue">--}%
    %{--<i class="fa fa-check"></i> Save</button>--}%
    %{--</div>--}%
</form>