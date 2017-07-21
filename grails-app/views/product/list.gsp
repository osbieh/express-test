<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 4/9/2017
  Time: 2:17 AM
--%>

<meta name="layout" content="main"/>

<div style="margin-top:-15px;" class="portlet box blue-madison">

    <div class="portlet-title">
        <div class="caption">
            <span>

                <i class="fa fa-book"></i> Product List</span>
        </div>

        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

    <div class="portlet-body">
        %{--<div class="row">--}%
        %{--<div class="col-md-5">--}%
        %{--<com:typeahead id="autoContact" name="autoContact" hidden="contactId"/>--}%
        %{--</div>--}%
        %{--<br>--}%
        %{--</div>--}%
        <div class="row">
            <div class="col-md-12">
                <table id="productListTable" width="100%" class="table table-striped table-bordered table-hover">
                    <thead scope="col">
                    <tr>
                        <th scope="col" property="id">
                            <g:message code="order.list.no" default="SerialNum"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="product.productName" default="product Name"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="product.defaultUnit" default="default Unit"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="product.productType" default="product Type"/>
                        </th>
                        <th scope="col">
                            <g:message code="product.purchasePrice" default="purchase Price"/>
                        </th>
                        <th scope="col" property="id">
                            <g:message code="product.sellPrice" default="sell Price"/>
                        </th>

                        <th scope="col">
                            <g:message code="payment.actions" default="actions"/>
                        </th>

                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div id="productModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header" style="background-color:#c9d8c5">

                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" style="font-weight: bold" id="myModalLabel"></h4>
                </div>

                <div id="modal-body" class="modal-body">

                </div>

            </div>
        </div>
    </div>



</div>





<script>

    $(document).ready(function () {

        productList.init();

    });

</script>