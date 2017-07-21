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

<div class="profile-sidebar">
    <!-- PORTLET MAIN -->
    <div class="col-md-12">
        <ul class="list-unstyled profile-nav">
            <li >
                <com:productPic/>
                <a href="javascript:;" class="profile-edit"> edit </a>
            </li>

            <li>
                <label class="control-label"><g:message code="product.productNo"/> </label>
                <input type="text" id="productId" class="form-control" disabled>
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

<div class="profile-content">


<div class="row">
    <div class="col-md-12">
        <div class="form-group">
            <label class="control-label"><g:message code="product.productName"/></label>
            <input type="text" id="productName" class="form-control">
            %{--<span class="help-block"> This is inline help </span>--}%
        </div>
    </div>
</div>
  <div class="row">


      <div class="col-md-6">
          <div class="form-group">
              <label class="control-label"><g:message code="product.productType"/></label>
              <g:select name="productType" class="form-control"
                        from="${express.enumurations.ProductType.values()*.name()}"
                        keys="${express.enumurations.ProductType.values()*.name()}"
                        valueMessagePrefix="productType"
              />


              %{--<span class="help-block"> This is inline help </span>--}%
          </div>
      </div>

      <div class="col-md-6">
          <div class="form-group">
              <label class="control-label"><g:message code="product.productUnit"/></label>
              <g:select name="productUnit" class="form-control"
                        from="${express.enumurations.Unit.values()*.name()}"
                        keys="${express.enumurations.Unit.values()*.name()}"
                        valueMessagePrefix="Unit"
              />
          </div>
      </div>

  </div>


<div class="row">
    <div class="col-md-6">
        <div class="form-group">
            <label class="control-label"><g:message code="product.purchasePrice"/></label>
            <input type="text" id="purchasePrice" class="form-control">
            %{--<span class="help-block"> This is inline help </span>--}%
        </div>
    </div>

    <div class="col-md-6">
        <div class="form-group">
            <label class="control-label"><g:message code="product.sellPrice"/></label>
            <input type="text" id="sellPrice" class="form-control">
            %{--<span class="help-block"> This is inline help </span>--}%
        </div>
    </div>
</div>
</div>

    <div class="row">

        <div class="col-md-12">
            <label class="control-label"><g:message code="product.note"/></label>
            <textarea rows="2" id="note" class="form-control"></textarea>
        </div>

    </div>


</div>

<div class="modal-footer">
    <button id="historyCloseBtn" type="button" class="btn btn-default" onclick="productForm.modalClose();">Close</button>
    <button class="btn btn-primary" onclick="productForm.saveProductInfo();">Save</button>
</div>


<script>
    $(document).ready(function () {
        console.log();
    if("${params?.record}".replace(/&quot;/g, '"')!="")
         productForm.fillProductForm("${params?.record}".replace(/&quot;/g, '"'));

        $("#myModalLabel").text("${ params?.modalTitle}");
        productForm.init();
    });
</script>

