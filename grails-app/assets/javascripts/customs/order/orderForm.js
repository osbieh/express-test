/**
 * Created by osbie on 4/2/2017.
 */

var orderForm = function () {

    return {

        fillOrderInfo: function (params) {
            var info = JSON.parse(params);
            $("#orderId").val(info.referenceId);
            $("#dailyMovementId").val(info.dailyMovementId);
            $("#accountName").val(info.accountName);
            $("#accountId").val(info.accountId);
            $("#totalAmount").val(info.total);
            $("#receptNo").val(info.receptNo);
            $("#orderDate").val(info.movementDay);
            $("#orderStatus").val(info.orderCurrentStatus);
            $("#currentBalance").val(info.currentBalance);
            $("#momentBalance").val(info.momentBalance);
            $("#note").val(info.note);

            if($("#orderStatus").val()=="COMPLETED") {
                $("#orderStatus").attr("disabled", "disabled");
            }
        },

        getOrderFormInfo: function () {
            var record={};
            record.orderId=$("#orderId").val();
            record.dailyMovementId=$("#dailyMovementId").val();
            record.accountName= $("#accountName").val();
            record.accountId= $("#accountId").val();
            record.totalAmount= $("#totalAmount").val();
            record.orderStatus= $("#orderStatus").val();
            record.receptNo= $("#receptNo").val();
            record.orderDate= $("#orderDate").val();
            record.note= $("#note").val();
           return JSON.stringify(record);
        },


        init: function () {


        }
    }
}();
