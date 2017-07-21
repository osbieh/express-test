<style>

.table-responsive {
    width: 100%;
    margin-bottom: 15px;
    overflow-y: visible;
overflow-x: scroll;
    -ms-overflow-style: -ms-autohiding-scrollbar;
    border: 1px solid #ddd;
    -webkit-overflow-scrolling: touch;
}

</style>
        <table id="chequeTable" width="100%" class="table table-striped table-bordered table-hover">
            <thead scope="col">
            <tr>
                <th scope="col" property="id">
                    <g:message code="cheque.no" default="#"/>
                </th>

                <th scope="col">
                    <g:message code="cheque.serialNo" default="serialNo"/>
                </th>
                <th scope="col">
                    <g:message code="cheque.bankName" default="bankName"/>
                </th>
                <th scope="col">
                    <g:message code="cheque.total" default="total"/>
                </th>
                <th scope="col">
                    <g:message code="cheque.dueDate" default="due Date"/>
                </th>

                <th scope="col">
                    <g:message code="order.list.actions" default="actions"/>
                </th>
                <th scope="col" >
                    <g:message code="paymentDetailsId" default="paymentDetailsId"/>
                </th>
                <th scope="col" >
                    <g:message code="total2" default="total2"/>
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
                <th></th>
            </tr>
            </tfoot>

        </table>

<script>


    $(document).ready(function () {
        cheque.init();
    });
</script>