package express.trade

import express.enumurations.OrderStatus
import express.enumurations.PaymentStatus
import express.enumurations.Unit
import grails.converters.JSON

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
class AccountBalanceController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def accountService
    def categoryService
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AccountBalance.list(params), model:[accountBalanceCount: AccountBalance.count()]
    }

    def show(AccountBalance accountBalance) {
        respond accountBalance
    }

    def create() {
        respond new AccountBalance(params)
    }

    def movementDetails(){

        def param=JSON.parse(params?.record)
        println "-------------" +param?.transactions
        def accountBalance=AccountBalance?.findById(param?.transactions)
        param?.currentBalance= accountService.lastAccountBalanceRecord(param?.accountId)?.totalAmount
        param?.momentBalance=accountBalance?.totalAmount
        def movement = param?.movementType

        if(param?.movementType=="ORDER"){
            def order=accountBalance?.order
            def dailyMovement=  DailyMovement.findByReferenceId(order?.id)

            params?.orderStatus = OrderStatus.values()
            params?.productUnits = Unit.values()
            params?.createdBy=order?.createdBy
            params?.lastUpdatedBy=order?.lastUpdatedBy
            params?.created=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.created)
            params?.lastUpdated=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(order?.lastUpdated)

            param?.receptNo=order?.receptNo
            param?.dailyMovementId=dailyMovement?.id
            param?.referenceId=dailyMovement?.referenceId
            param?.total=order?.totalAmount
            param?.orderCurrentStatus=OrderStatus.NEW.toString()
            param?.orderCurrentStatus=(order?.status!=null)?order?.status.toString():OrderStatus.NEW.toString()

            params?.record=  param
            render template: "/templates/order/orderDetails"

        }else{
            def payment=accountBalance?.payment
            def dailyMovement=  DailyMovement.findByReferenceId(payment?.id)
            params?.paymentStatus = PaymentStatus.values()
            params?.currencyTypes=categoryService.categoryListByDefinition(1L)
            params?.paymentTypes=categoryService.categoryListByDefinition(2L)
            params?.createdBy=payment?.createdBy
            params?.lastUpdatedBy=payment?.lastUpdatedBy
            params?.created=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(payment?.created)
            params?.lastUpdated=new SimpleDateFormat("HH:mm dd-MM-YYYY").format(payment?.lastUpdated)

            param?.dailyMovementId=dailyMovement?.id
            param?.referenceId=dailyMovement?.referenceId
            param?.paymentCurrentStatus=(payment?.status!=null)?payment?.status.toString():PaymentStatus.NEW.toString()
            param?.receptNo=payment?.receptNo
            param?.total=payment?.totalAmount
            params?.record=  param
            render template: "/templates/payments/paymentDetails2"
        }




    }

    @Transactional
    def save(AccountBalance accountBalance) {
        if (accountBalance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountBalance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountBalance.errors, view:'create'
            return
        }

        accountBalance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'accountBalance.label', default: 'AccountBalance'), accountBalance.id])
                redirect accountBalance
            }
            '*' { respond accountBalance, [status: CREATED] }
        }
    }

    def edit(AccountBalance accountBalance) {
        respond accountBalance
    }

    @Transactional
    def update(AccountBalance accountBalance) {
        if (accountBalance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (accountBalance.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond accountBalance.errors, view:'edit'
            return
        }

        accountBalance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'accountBalance.label', default: 'AccountBalance'), accountBalance.id])
                redirect accountBalance
            }
            '*'{ respond accountBalance, [status: OK] }
        }
    }

    @Transactional
    def delete(AccountBalance accountBalance) {

        if (accountBalance == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        accountBalance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountBalance.label', default: 'AccountBalance'), accountBalance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountBalance.label', default: 'AccountBalance'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
