/**
 * Created by osbie on 6/13/2017.
 */

var productForm=function(){

    return{

        saveProductInfoUrl:"/express/ar/product/manageProduct",
        fillProductForm:function(record){
             record= JSON.parse(record);
            $("#productName").val(record.productName);
            $("#productId").val(record.productId);
            $("#sellPrice").val(record.sellPrice);
            $("#purchasePrice").val(record.purchasePrice);
            $("#productType").val(record.productTypeVal);
            $("#productUnit").val(record.defaultUnitVal);
            $("#note").val(record.note);
        },

        getFormInfo:function(){
           var product={};
            product.productName=$("#productName").val();
            product.productId=$("#productId").val();
            product.sellPrice=$("#sellPrice").val();
            product.purchasePrice=$("#purchasePrice").val();
            product.productType=$("#productType").val();
            product.productUnit=$("#productUnit").val();
            product.note=$("#note").val();
            return  JSON.stringify(product);
        },


        saveProductInfo:function(){
            $.ajax({
                url: productForm.saveProductInfoUrl,
                data: {
                    product: productForm.getFormInfo()
                },
                dataType: "json",
                type: "POST",
                beforeSend: function (xhr) {
                    $('#ajax_loader').show();
                },
                success: function (result) {
                    toastr.success("product are Managed", "Success");
                    productList.refreshTable();
                    productForm.modalClose();

                },
                error: function (result) {
                    console.log(result, ' FAIL MESSAGE ', result.message);
                    //    toastr.error(result.responseJSON.error,"Fail");
                    $('#ajax_loader').hide();
                   productForm.modalClose();


                }
            });

        },


        modalClose:function(){
            $('#productModal').modal('hide');
        },
        init:function(){


        }
    }

}();
