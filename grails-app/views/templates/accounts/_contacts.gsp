<style>


.specialRow {
    background-color: rgba(26, 112, 166, 0.33) !important;
}
.SelectSpecialRow {
    background-color: #b0bed9 !important;
}

.table > tbody > tr > td,
.table > tbody > tr > th,
.table > tfoot > tr > td,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > thead > tr > th {
    padding: 6px;
    line-height: 0.42857;
    vertical-align: top;
    border-top: 1px solid #e7ecf1;
}


</style>
<div class="row">
    <div class="col-md-1"></div>
    <div class="col-md-10">
        <table id="contactTable" class="table table-striped table-bordered table-hover" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th scope="col" style="padding: 7px 20px;" property="id">
                      <g:message code="express.contact.phone.isPrimary" default="isPrimary"/>
                    </th>
                     <th scope="col"  style="padding: 7px 20px;"  property="id">
                      <g:message code="express.contact.list.fullName" default="Contact Name"/>
                     </th>
                     <th scope="col"  style="padding: 7px 20px;"  property="id">
                      <g:message code="express.contact.phone.phoneNo" default="PhoneNo"/>
                     </th>
                     <th scope="col"  style="padding: 7px 20px;"  property="id">
                      <g:message code="express.contact.phone.action" default="Action"/>
                     </th>
                    <th scope="col"  style="padding: 7px 20px;"  property="id">
                        <g:message code="express.contact.list.fullName" default="Contact Name"/>
                    </th>
                </tr>
            </thead>
            <tbody>
            </tbody>
       </table>
    </div>
</div>
