/**
 * Created by osbie on 4/9/2017.
 */

var paymentForm = function () {

    return{
        fillPaymentInfo: function (params) {
            var info = JSON.parse(params);
            console.log("---INFO---",info);

            $("#paymentId").val(info.referenceId);
            $("#dailyMovementId").val(info.dailyMovementId);
            $("#accountName").val(info.accountName);
            $("#accountId").val(info.accountId);
            $("#totalAmount").val(info.total);
            $("#receptNo").val(info.receptNo);
            $("#orderDate").val(info.movementDay);
            $("#paymentStatus").val(info.paymentCurrentStatus);
            $("#currentBalance").val(info.currentBalance);
            $("#momentBalance").val(info.momentBalance);

            $("#cashAmount").val(info.total).trigger("change");

        },
        getPaymentFormInfo: function () {
            var payment={};

            payment.dailyMovementId=$("#dailyMovementId").val();
            payment.paymentId=$("#paymentId").val();
            payment.accountName= $("#accountName").val();
            payment.accountId= $("#accountId").val();
            payment.totalAmount= $("#totalAmount").val();
            payment.receptNo= $("#receptNo").val();
            payment.paymentDate= $("#orderDate").val();
            payment.paymentStatus=$("#paymentStatus").val();
            payment.note= $("#note").val();
            return JSON.stringify(payment);
        },
        init:function(){
            paymentForm.fillPaymentInfo();
        }

    }


}();