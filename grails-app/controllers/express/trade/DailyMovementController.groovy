package express.trade


import express.enumurations.MovementType
import express.enumurations.OrderStatus
import express.enumurations.PaymentStatus
import express.enumurations.Unit
import grails.converters.JSON

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = true)
class DailyMovementController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def dailyMovementService
    def categoryService
    def accountService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond DailyMovement.list(params), model: [dailyMovementCount: DailyMovement.count()]
    }

    def show(DailyMovement dailyMovement) {
        respond dailyMovement
    }

    def create() {
        respond new DailyMovement(params)
    }

    def list() {
       params?.movementType = MovementType.values()
        params?.orderStatus = OrderStatus.values()
        respond ""
    }

    def getMovements() {
        render dailyMovementService.getMovementsByDay(params) as JSON

    }

    def dailyMovementDetails() {
         println " ::: dailyMovementDetails ::: \n"+params
        def param=JSON.parse(params?.record)
        println param
        def dailyMovement=DailyMovement.findById(param?.dailyMovementId)
        def referenceId= dailyMovement?.referenceId
        def movement = param?.movementType2
          param?.currentBalance= accountService.lastAccountBalanceRecord(param?.accountId)?.totalAmount
        if (MovementType.valueOf(movement) == MovementType.ORDER){
            params?.orderStatus = OrderStatus.values()
            params?.productUnits = Unit.values()
            param?.orderCurrentStatus=OrderStatus.NEW.toString()
            def order=OrderP.findById(referenceId)

            if(referenceId){
                params?.createdBy=order?.createdBy
                params?.lastUpdatedBy=order?.lastUpdatedBy
                params?.created=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.created)
                params?.lastUpdated=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.lastUpdated)
                param?.receptNo=order?.receptNo
                param?.note=order?.note
            }
            param?.orderCurrentStatus=(order?.status!=null)?order?.status.toString():OrderStatus.NEW.toString()
            params?.record=  param
            render template: "/templates/order/orderDetails"
        }

        if (MovementType.valueOf(movement) == MovementType.PAYMENT){
            params?.paymentStatus = PaymentStatus.values()
            params?.currencyTypes=categoryService.categoryListByDefinition(1L)
            params?.paymentTypes=categoryService.categoryListByDefinition(2L)
            def payment=Payment.findById(referenceId)
            if(referenceId){
                params?.createdBy=payment?.createdBy
                params?.lastUpdatedBy=payment?.lastUpdatedBy
                params?.created=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(payment?.created)
                params?.lastUpdated=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(payment?.lastUpdated)
            }
            param?.paymentCurrentStatus=(payment?.status!=null)?payment?.status.toString():PaymentStatus.NEW.toString()
            param?.receptNo=payment?.receptNo
            params?.record=  param
            render template: "/templates/payments/paymentDetails2"
        }


    }

    def dailyMovementByCustomer(){
        respond ""
    }
    def getCustomerMovementsByRange(){
        render dailyMovementService.getCustomerMovementsByRange(params) as JSON
    }

    @Transactional
    def saveDailyMovementRecord() {



        def record=JSON.parse(params?.record)
        println "*** saveDailyMovementRecord *** ::"+record?.accountId

        def createdList= DailyMovement.findAllByAccountAndMovementDay(Account.findById(record?.accountId),new SimpleDateFormat("yyyy-MM-dd").parse(record?.movementDay))



        if(createdList.size()>1){

            render(contentType: 'text/json') {
                [
                        'results': "---More Records were Created---", 'status': 200
                ]
            }

        }


       def results =  dailyMovementService.saveDailyMovementRecord(record)

        render(contentType: 'text/json') {
            [
                    'results': results, 'status': 200
            ]
        }
    }


    @Transactional
    def save(DailyMovement dailyMovement) {
        if (dailyMovement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (dailyMovement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dailyMovement.errors, view: 'create'
            return
        }

        dailyMovement.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dailyMovement.label', default: 'DailyMovement'), dailyMovement.id])
                redirect dailyMovement
            }
            '*' { respond dailyMovement, [status: CREATED] }
        }
    }

    def edit(DailyMovement dailyMovement) {
        respond dailyMovement
    }

    @Transactional
    def update(DailyMovement dailyMovement) {
        if (dailyMovement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (dailyMovement.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dailyMovement.errors, view: 'edit'
            return
        }

        dailyMovement.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'dailyMovement.label', default: 'DailyMovement'), dailyMovement.id])
                redirect dailyMovement
            }
            '*' { respond dailyMovement, [status: OK] }
        }
    }

    @Transactional
    def delete(DailyMovement dailyMovement) {

        if (dailyMovement == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        dailyMovement.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'dailyMovement.label', default: 'DailyMovement'), dailyMovement.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dailyMovement.label', default: 'DailyMovement'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
