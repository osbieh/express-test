/**
 * Created by osbie on 6/7/2017.
 */


var productList = function () {
    return {
       // common:common.projectPath,
        productListURL:"/express/ar/product/getProductList",
        manageProductDetailsLink:"/express/ar/product/manageProductDetails",
        addProductList: function () {

            productListTable = $("#productListTable").DataTable({
                "language": {
                    "url": (common.currentLang == 'en') ? common.langPath + "English.json" : common.langPath + "Arabic.json"
                },
                "lengthMenu": [[10, 20, 50, -1], [10, 20, 50, "All"]],
                "paging": true,
                "ordering": false,
                "info": true,
                "dom": 'lf<"toolbar">rt<"footer"><"bottom"ip><"clear">',
                "aaSorting": [[1, 'asc']],
                "columns": [
                    {"width": "1%", "data": "rowNum", "name": "rowNum"},
                    {"width": "1%", "data": "productName", "name": "productName", "visible": true},
                    {"width": "10%", "data": "defaultUnit", "name": "defaultUnit"},
                    {"width": "5%", "data": "productType", "name": "productType"},
                    {"width": "10%", "data": "purchasePrice", "name": "purchasePrice"},
                    {"width": "5%", "data": "sellPrice", "name": "sellPrice"},

                    {"width": "69%", "data": "actions",
                        render: function (data, type, full, meta) {

                            return ' <div style="padding-top:6px;">' +

                                '<a data-toggle="tooltip" title="Info" style="margin-left:10px;" class="glyphicon glyphicon-info-sign" onclick="productList.updateProductInfo(' + meta.row + ');" aria-hidden="true"></a>' +

                                '<div> ';

                        }
                    },
                    {"width": "5%", "data": "productId", "name": "productId",visible:false},
                ],
                initComplete: function () {
                    $("div.toolbar")
                        .html(' <a  style="margin-right:10px !important;" class="btn btn-circle btn-icon-only btn-default" onclick="productList.addNewProduct();">' +
                            ' <i class="glyphicon glyphicon-plus"></i>' +
                            ' </a>');

                },

            });


        },

        addNewProduct:function(){
            $('#modal-body').html("");
            $('#modal-body').load(productList.manageProductDetailsLink, {}, function (result) {
                $('#productModal').modal({show: true});
            });
        },
        updateProductInfo:function(index){
            var row=productListTable.row(index);
            $('#modal-body').html("");
            $('#modal-body').load(productList.manageProductDetailsLink, {record:JSON.stringify(row.data())}, function (result) {
                $('#productModal').modal({show: true});
            });
        },

        refreshTable: function () {
            var table = $("#productListTable").DataTable();
            var tableId = $("#productListTable");
            var urlData = this.productListURL;
            var data = {
                fromDate: $('#fromDate input').val(),
                toDate: $('#toDate input').val(),
                accountId: $("#accountId").val()
            }

            $.getJSON(urlData, data, function (json) {
                table = $(tableId).dataTable();
                oSettings = table.fnSettings();
                table.fnClearTable(this);

                for (var i = 0; i < json.aaData.length; i++) {

                    table.oApi._fnAddData(oSettings, json.aaData[i]);
                }
                oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
                productListTable.page(productListTable.page()).draw(false);
            });
        },

        init: function () {
            productList.addProductList();
            productList.refreshTable();

        }

    }

}();
