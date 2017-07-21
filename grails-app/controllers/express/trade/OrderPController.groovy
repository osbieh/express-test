package express.trade

import express.enumurations.OrderStatus
import express.enumurations.Unit
import grails.converters.JSON

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class OrderPController {

    def orderPService
    def accountService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OrderP.list(params), model:[orderPCount: OrderP.count()]
    }

    def show(OrderP orderP) {
        respond orderP
    }

    def create() {
        respond new OrderP(params)
    }

    def list(){

        respond ""
    }

    def loadOrderInfo(){
        params?.orderStatus = OrderStatus.values()
        def order=OrderP.findById(params?.orderId)
        params?.accountName=order?.account?.accountName
        params?.referenceId=order?.id
        params?.orderCurrentStatus=order?.status
        params?.orderStatusVal=order?.status!=null?order?.status.toString():OrderStatus.NEW.toString()
        params?.createdBy=order?.createdBy
        params?.lastUpdatedBy=order?.lastUpdatedBy
        params?.created=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.created)
        params?.lastUpdated=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.lastUpdated)
        params?.note=order?.note

        println "-------------------------==="+  params?.note
        render template: "/templates/order/orderForm"

    }
    def loadOrderDetails(){

       render  orderPService.getOrderDetails(params?.orderId) as JSON

    }

    def getOrderList(){
        render orderPService.getOrderList(params) as JSON

    }

    def getCustomerOrdersDetails(){

        render  orderPService.getCustomerOrdersDetails(params) as JSON
    }

    @Transactional
    def manageOrder(){

        println params
        orderPService.manageWholeOrder(params)
        def result =  "-------------------------"
        render(contentType: 'text/json') {
            [
                    'results': result, 'status': 200
            ]
        }


    }
    def movementDetails(){
        println "OrderP movementDetails \n"+params



        def param=JSON.parse(params?.record)

       def dailyMovement= DailyMovement.findByReferenceId(param?.orderId)
        def order=OrderP.findById(param?.orderId)
        println "-------------" +param?.transactions
        def accountBalance=AccountBalance?.findById(param?.transactions)
        param?.currentBalance= accountService.lastAccountBalanceRecord(param?.accountId)?.totalAmount
        param?.momentBalance=accountBalance?.totalAmount
        def movement = param?.movementType



           // def dailyMovement=  DailyMovement.findByReferenceId(order?.id)

            params?.orderStatus = OrderStatus.values()
            params?.productUnits = Unit.values()
            params?.createdBy=order?.createdBy
            params?.lastUpdatedBy=order?.lastUpdatedBy
            params?.created=order?.created?.format("HH:mm dd-MM-YYYY")
            params?.lastUpdated=order?.lastUpdated?.format("HH:mm dd-MM-YYYY")

            param?.receptNo=order?.receptNo
            param?.dailyMovementId=dailyMovement?.id
            param?.referenceId=dailyMovement?.referenceId
            param?.total=order?.totalAmount
            param?.orderCurrentStatus=OrderStatus.NEW.toString()
            param?.orderCurrentStatus=(order?.status!=null)?order?.status.toString():OrderStatus.NEW.toString()

            params?.record=  param

           println "-------------------------==="+  params
            render template: "/templates/order/orderDetails"




    }
    @Transactional
    def save(OrderP orderP) {
        if (orderP == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orderP.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderP.errors, view:'create'
            return
        }

        orderP.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'orderP.label', default: 'OrderP'), orderP.id])
                redirect orderP
            }
            '*' { respond orderP, [status: CREATED] }
        }
    }

    def edit(OrderP orderP) {
        respond orderP
    }

    @Transactional
    def update(OrderP orderP) {
        if (orderP == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orderP.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderP.errors, view:'edit'
            return
        }

        orderP.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'orderP.label', default: 'OrderP'), orderP.id])
                redirect orderP
            }
            '*'{ respond orderP, [status: OK] }
        }
    }

    @Transactional
    def delete(OrderP orderP) {

        if (orderP == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        orderP.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'orderP.label', default: 'OrderP'), orderP.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderP.label', default: 'OrderP'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
