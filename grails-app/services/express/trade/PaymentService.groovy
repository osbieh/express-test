package express.trade

import express.enumurations.MovementType
import express.enumurations.OrderStatus
import express.enumurations.PaymentStatus
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

import java.text.SimpleDateFormat

@Transactional
class PaymentService {
    def dailyMovementService
    def accountService
    def serviceMethod() {

    }

    def getPaymentsList(params){
        def dataToBeRender = [:]
        dataToBeRender.aaData = []
        def fromDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.fromDate +" 00:00:00"))
        def toDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.toDate+ " 23:59:59"))

        def orderList = Payment.createCriteria().list {
            if(params?.accountId!="-150")
                account{
                    eq('id',Long.parseLong(params?.accountId))
                }

            between("paymentDate", fromDate, toDate)
            order("paymentDate", "asc")

        }
        orderList.each {
            dataToBeRender.aaData << [
                    rowNum     : it?.id,
                    accountName    : it?.account?.accountName,
                    accountId  : it?.account?.id,
                    paymentDate  : it?.paymentDate?.format("YYYY-MM-dd"),
                    totalAmount: it?.totalAmount,
                    status     : it?.status.toString(),
                    receptNo    :it?.receptNo,
                    paymentId    : it?.id,

            ]

        }

        println " -:-:-getPaymentList-:-:- "+dataToBeRender
        return dataToBeRender
    }

    def getPaymentDetails(paymentId) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []

        def paymentDetailsList = PaymentDetails.createCriteria().list {
            payment {
                eq("id", Long?.parseLong(paymentId == "" ? "0" : paymentId))
            }
        }
        paymentDetailsList.each {
            dateToBeRender.aaData << [
                    "rowNum"          : it?.id,
                    "paymentDetailsId": it?.id,
                    "paymentType"     : Category.findByNameEn(it?.paymentType)?.nameAr,
                    "currency"        : Category.findById(1L)?.nameAr,
                    "total"           : it?.amount,
                    "actions"         : it?.id,
                    "rowStatus"       : "",
                    "rowMode"         : "",
                    "total2"          : it?.amount,
                    "paymentTypeId"   : Category.findByNameEn(it?.paymentType)?.id,

            ]
        }
        return dateToBeRender


    }

    def getPaymentDetails2(paymentId) {

        def dataToBeRender = [:]
        dataToBeRender.aaData = []
        dataToBeRender.cashData=[]

        def paymentDetailsList = PaymentDetails.createCriteria().list {
            payment {
                eq("id", Long?.parseLong(paymentId == "" ? "0" : paymentId))
            }
        //    eq("paymentType",Category.findById(4))
        }

        println "paymentDetailsList "+paymentDetailsList
        paymentDetailsList.each {
            if(it?.paymentType?.nameEn=="cash"){
                dataToBeRender.cashData<<[
                        cashAmount:it?.amount,
                        cashDetailsId:it?.id
                ]
            }else{
                def cheque=it?.cheque
                dataToBeRender.aaData << [
                        "rowNum"          : it?.id,
                        "chequeNo": cheque?.chequeNo,
                        "bankName":cheque?.bankName,
                        "amount": cheque?.amount,
                        "dueDate": cheque?.dueDate?.format("yyyy-MM-dd"),
                        "paymentDetailsId": it?.id,
                        "chequeId":cheque?.id,
                        "paymentType":it?.paymentType?.nameEn,
                        "total2": cheque?.amount,


                ]
            }

        }
        println dataToBeRender
        return dataToBeRender


    }



    def manageWholePayment(params) {

        def paymentParams = JSON.parse(params?.payment)
     //   def paymentDetailsParams = JSON.parse(params?.details)
        def cheques=JSON.parse(params?.cheques)
        def cash=JSON.parse(params?.cash)

        println "-----------manageWholePayment------------ \n"+params+"\n ----------------------\n"
        def dailyMovement
        if (paymentParams?.dailyMovementId) {
            dailyMovement = DailyMovement?.findById(paymentParams?.dailyMovementId as long)
        } else {

            def dailyMovementRecord = [
                "movementType":MovementType.PAYMENT.toString(),
                "movementDay":paymentParams?.paymentDate,
                "receptNo":paymentParams?.receptNo ,
                "status":paymentParams?.paymentStatus ,
                "accountId":paymentParams?.accountId
           ]
            dailyMovement = dailyMovementService.saveDailyMovementRecord(dailyMovementRecord)
        }


        def payment = managePayment(paymentParams)
        println "********* manageWholePayment ************ "+payment
        managePaymentDetailsCheques(payment,cheques)

        if(cheques.size()<=0){
            cash?.cashAmount=payment?.totalAmount
        }

        if(cash?.cashAmount>0){
            def detail=[:]
          //  detail?.cash=
            detail?.paymentDetailsId=cash?.cashDetailsId
            detail?.payment = payment
            detail?.amount = cash?.cashAmount
            detail?.currency = "IL"
            detail?.paymentDate = payment?.paymentDate
            //  detail.paymentType = Category?.findById(4)?.nameEn
            detail?.categoryId=3L
            managePaymentDetails(detail);
            }

        calculatePayment(payment)
        dailyMovement.referenceId = payment?.id
        dailyMovement.totalAmount = payment?.totalAmount
        dailyMovement.status=payment.status.defaultMessage
        dailyMovement.save(flush: true, failOnError: true);

        //to manage in account balance
        if(payment?.status==PaymentStatus.COMPLETED) {
            params?.movementId = payment?.id
            params?.payment = payment
            params?.movementDay = payment?.paymentDate
            params?.accountId = payment?.account?.id
            params?.amount = payment?.totalAmount
            params?.movementType = "PAYMENT"
            accountService.accountBalanceManager(params)
        }

    }

    def calculatePayment(payment){
        def totalAmount = 0
        def paymentDetailsList = PaymentDetails.findAllByPayment(payment)
        paymentDetailsList.each { paymentDetail ->
            totalAmount += paymentDetail.amount
        }
        payment.totalAmount = totalAmount
        payment.save(flush: true, failOnError: true)
        return payment
    }

    def managePayment(paymentParams) {
        def payment
        if (paymentParams?.paymentId) {
            payment = Payment.findById(paymentParams?.paymentId)
        } else {
            payment = new Payment();
        }
        payment?.account = Account.findById(paymentParams?.accountId as long)
        payment?.paymentDate = new SimpleDateFormat("yyyy-MM-dd")?.parse(paymentParams?.paymentDate)
        payment?.status = PaymentStatus.valueOf(paymentParams?.paymentStatus)
        if (paymentParams?.receptNo != "")
            payment?.receptNo = Long.parseLong(paymentParams?.receptNo)
        payment?.totalAmount = Long.parseLong(paymentParams?.totalAmount)
        payment?.note = paymentParams?.note
        payment.save(flush: true, failOnError: true)
        return payment;

    }

 /*   def managePaymentDetails(payment, paymentDetailsParams) {
        println " :::: "+paymentDetailsParams

        paymentDetailsParams.each { detail ->
            def paymentDetails
            if (detail?.paymentDetailsId) {
                paymentDetails = PaymentDetails.findById(detail?.paymentDetailsId)
            } else {
                paymentDetails = new PaymentDetails()
            }
            paymentDetails.payment = payment
            paymentDetails.amount = Long.parseLong(""+detail?.total2)
            paymentDetails.currency = "IL"
            paymentDetails.paymentDate = payment?.paymentDate
            paymentDetails.paymentType = Category?.findById(detail?.paymentType)?.nameEn
            paymentDetails.save(failOnError: true, flush: true)
        }
    }
    */

    def managePaymentDetailsCheques(payment,chequesList){

        chequesList.each{cheque->
            def che= manageCheques(cheque)
            def detail=[:]
            detail?.cheque=che
            detail?.paymentDetailsId=cheque?.paymentDetailsId
            detail?.payment = payment
            detail?.amount = che?.amount
            detail?.currency = "IL"
            detail?.paymentDate = payment?.paymentDate
          //  detail.paymentType = Category?.findById(4)?.nameEn
            detail?.categoryId=4L

            managePaymentDetails(detail)
        }



    }

    def managePaymentDetailsCash(payment,cash){



    }

    def manageCheques(params){
        def cheque
        if(params.chequeId){

            cheque=Cheque.findById(params?.chequeId)
        }
        else{
            cheque=new Cheque()
        }
        cheque.abaRouting=params?.abaRouting
        cheque.ownerId=params?.accountId
        cheque?.owner=params?.accountName
        cheque?.amount=Long.parseLong(params?.amount)
        cheque?.dueDate=new SimpleDateFormat("yyyy-MM-dd").parse(params?.dueDate)
        cheque?.bankName=params?.bankName
        cheque?.bankAccount=params?.bankAccount
        cheque?.description=params?.description
        cheque?.chequeNo=params?.chequeNo

        cheque.save(failOnError: true,flush: true)
        return cheque
    }


    def managePaymentDetails(detail){
        println "///////////////////////////////////////////"
        println " ::: "+ detail+" ::: "
        println "///////////////////////////////////////////"
        def paymentDetails
        if (detail?.paymentDetailsId) {
            paymentDetails = PaymentDetails.findById(detail?.paymentDetailsId)
        } else {
            paymentDetails = new PaymentDetails()
        }
        paymentDetails.cheque=detail?.cheque
        paymentDetails.payment = detail?.payment
        paymentDetails.amount = detail?.amount
        paymentDetails.currency = "IL"
        paymentDetails.paymentDate = detail?.paymentDate
        paymentDetails.paymentType = Category.findById(detail?.categoryId)

        paymentDetails.save(failOnError: true, flush: true)
    }

}
