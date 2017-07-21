package express.trade

import express.enumurations.MovementType
import express.enumurations.OrderStatus
import express.enumurations.Unit
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

import java.text.SimpleDateFormat

@Transactional
class OrderPService {
    def messageSource
    def dailyMovementService
    def accountService
    def serviceMethod() {

    }

    def getCustomerOrdersDetails(params){
        def dataToBeRender = [:]
        dataToBeRender.aaData = []
        def masterData=[:]
        def account=Account.findById(params?.accountId)
        def orderList = OrderDetails.createCriteria().list() {
            orderP{
                eq('account',account)
            }
        }
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();

         def rowNum=1
        orderList.each {
            Map<String, Object> orderDetails = new HashMap<String, Object>();
                    orderDetails.put("rowNum"        , rowNum++)
                    orderDetails.put("orderId"       , it?.orderP?.id)
                    orderDetails.put("totalAmount"   , it?.orderP?.totalAmount)
                    orderDetails.put("orderDate"     , it?.orderP?.orderDate.format("yyyy-MM-dd"))
                    orderDetails.put("status"        , it?.orderP?.status)
                    orderDetails.put("receptNo"      , it?.orderP?.receptNo)
                    orderDetails.put("orderDetailsId",it?.id)
                    orderDetails.put("productName"   ,it?.product?.name)
                    orderDetails.put("productUnit"   ,messageSource?.getMessage("Unit." + it?.productUnit?.defaultMessage, null, null, LocaleContextHolder.locale))
                    orderDetails.put("quantity"      ,it?.quantity)
                    orderDetails.put("unitPrice"     ,it?.unitPrice)
                    orderDetails.put("total"         ,it?.total)

            maps.add(orderDetails)
        }

        return maps;

    }


    def getOrderList(params) {
        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def fromDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.fromDate + " 00:00:00"))
        def toDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.toDate + " 23:59:59"))

        def orderList = OrderP.createCriteria().list {
            if(params?.accountId!="-150")
            account{
                eq('id',Long.parseLong(params?.accountId))
            }

            between("orderDate", fromDate, toDate)
            order("orderDate", "asc")

        }
        orderList.each {
            dateToBeRender.aaData << [
                    rowNum     : it?.id,
                    account    : it?.account?.accountName,
                    orderDate  : it?.orderDate?.format("yyyy-MM-dd"),
                    totalAmount: it?.totalAmount,
                    status     : it?.status.toString(),
                    orderId    : it?.id,
                    accountId  : it?.account?.id,
                    receptNo    :it?.receptNo
            ]

        }

        println " -:-:-getOrderList-:-:- "+dateToBeRender
        return dateToBeRender
    }

    def getOrderDetails(orderId) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def orderDetailsList = OrderDetails.createCriteria().list {
            orderP {
                eq("id", Long?.parseLong(orderId == "" ? "0" : orderId))
            }
        }

        orderDetailsList.each {
            dateToBeRender.aaData << [
                    "rowNum"        : it?.id,
                    "orderDetailsId": it?.id,
                    "productName"   : it?.product?.name,
                    "productUnit"   : messageSource?.getMessage("Unit." + it?.productUnit?.defaultMessage, null, null, LocaleContextHolder.locale),
                    "productUnit2"  : it?.productUnit?.defaultMessage,
                    "quantity"      : it?.quantity,
                    "unitPrice"     : it?.unitPrice,
                    "discount"      : it?.discount,
                    "total"         : it?.total,
                    "actions"       : it?.id,
                    "productId"     : it?.product?.id,
                    "rowStatus"     : "",
                    "rowMode"       : "",

            ]
        }
        return dateToBeRender


    }

    def calculateOrderTotalAmount(order) {
        def totalAmount = 0
        def orderDetailsList = OrderDetails.findAllByOrderP(order)
        orderDetailsList.each { orderDetail ->
            println "orderDetail.total= " + orderDetail.total
            totalAmount += orderDetail.total
        }
        if(orderDetailsList.size()>0)
          order.totalAmount = totalAmount

        order.save(flush: true, failOnError: true)
        return order
    }

    def completedOrder(order) {
         println "Start Set  DailyMovement On Completed Order Id:"+order?.Id
        def dailyMovement = DailyMovement.findByReferenceId(order?.Id)
        if (order.status == OrderStatus.COMPLETED) {
            dailyMovement.status = OrderStatus.COMPLETED.toString()
            dailyMovement.save(failOnError: true, flush: true)
        }
        println "End Set  DailyMovement On Completed Order Id:"+order?.Id+" with Status:"+dailyMovement?.status
    }

    def manageWholeOrder(params) {
       println " START manageWholeOrder ----::::---- " +params
        def orderParams = JSON.parse(params?.order)
        def orderDetailsParams = JSON.parse(params?.details)

        def dailyMovement
        if (orderParams?.dailyMovementId) {//check dailyMovement found or Not
            dailyMovement = DailyMovement?.findById(orderParams?.dailyMovementId as long)
        } else {

            def dailyMovementRecord = [
                    "movementType": MovementType.ORDER.toString(),
                    "movementDay" : orderParams?.orderDate,
                    "receptNo"    : orderParams?.receptNo,
                    "status"      : orderParams?.orderStatus,
                    "accountId"   : orderParams?.accountId
            ]
            dailyMovement = dailyMovementService.saveDailyMovementRecord(dailyMovementRecord)
        }

        def order = manageOrder(orderParams)


        manageOrderDetails(order, orderDetailsParams)

        calculateOrderTotalAmount(order)

        dailyMovement.referenceId = order.id
        dailyMovement.totalAmount = order.totalAmount
        dailyMovement.status=order.status.defaultMessage
        dailyMovement.save(flush: true, failOnError: true);

        //to manage in account balance and DailyMovement
        if(order.status==OrderStatus.COMPLETED){
            params?.movementId=order?.id
            params?.order=order
            params?.movementDay=order?.orderDate
            params?.accountId=order?.account?.id
            params?.amount=order?.totalAmount
            params?.movmentType="ORDER"
            accountService.accountBalanceManager(params)




        }


    }

    def manageOrder(order) {

        def ord;
        if (order?.orderId) {
            ord = OrderP.findById(Long.parseLong(order?.orderId))
        } else {
            ord = new OrderP();
        }
        ord.account = Account.findById(order?.accountId as long)
        ord.orderDate = new SimpleDateFormat("yyyy-MM-dd")?.parse(order?.orderDate)
        ord.status = OrderStatus.valueOf(order?.orderStatus).toString()

        if (order?.receptNo != null && order?.receptNo != "")
            ord.receptNo = Long.parseLong(order?.receptNo)

        if (order?.totalAmount != null && order?.totalAmount != "")
            ord.totalAmount = Long.parseLong(order?.totalAmount)

        ord.note = order?.note
        ord.save(flush: true, failOnError: true)
        return ord;
    }

    def manageOrderDetails(order, orderDetailParams) {
        orderDetailParams.each { detail ->
            def orderDetails
            if (detail?.orderDetailsId) {
                orderDetails = OrderDetails.findById(detail?.orderDetailsId)
            } else {
                orderDetails = new OrderDetails()
            }
            orderDetails.orderP = OrderP.findById(order.id)
            orderDetails.product = Product.findById(detail?.productId)
            orderDetails.productUnit = Unit.valueOf(detail?.productUnit)
            orderDetails.quantity = Double.parseDouble(detail?.quantity)
            orderDetails.unitPrice =Double.parseDouble(detail?.unitPrice)
            orderDetails.discount=detail?.discount
            orderDetails.total = Double.parseDouble(detail?.total)
            orderDetails.notes = "---"
            orderDetails.save(flush: true, failOnError: true)

        }
        return "orderDetails Created"
    }


    def getCustomerOrdersDetailReportData(){

        println "getCustomerOrdersDetailReportData :: " + params
        def dataToBeRender = [:]
        dataToBeRender.aaData = []
        def masterData = [:]

        def ordersList=OrderDetails.createCreteria().list(){
            orderP{

            }
        }
    }

}
