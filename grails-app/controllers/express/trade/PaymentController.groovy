package express.trade

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class PaymentController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def paymentService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Payment.list(params), model: [paymentCount: Payment.count()]
    }

    def show(Payment payment) {
        respond payment
    }

    def create() {
        respond new Payment(params)
    }

    def list() {
        respond ""
    }

    def loadPaymentDetails(){
      render paymentService.getPaymentDetails(params?.paymentId) as JSON

    }

    def loadPaymentDetails2(){
        render paymentService.getPaymentDetails2(params?.paymentId) as JSON

    }

    def getPaymentList(){
        render paymentService.getPaymentsList(params) as JSON

    }
    @Transactional
    def save(Payment payment) {
        if (payment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (payment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond payment.errors, view: 'create'
            return
        }

        payment.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'payment.label', default: 'Payment'), payment.id])
                redirect payment
            }
            '*' { respond payment, [status: CREATED] }
        }
    }

    def edit(Payment payment) {
        respond payment
    }

    @Transactional
    def manageWholePayment() {

        println params
        paymentService.manageWholePayment(params)
        def result = "-------------------------"
        render(contentType: 'text/json') {
            [
                    'results': result, 'status': 200
            ]
        }

    }


    @Transactional
    def update(Payment payment) {
        if (payment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (payment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond payment.errors, view: 'edit'
            return
        }

        payment.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'payment.label', default: 'Payment'), payment.id])
                redirect payment
            }
            '*' { respond payment, [status: OK] }
        }
    }

    @Transactional
    def delete(Payment payment) {

        if (payment == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        payment.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'payment.label', default: 'Payment'), payment.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'payment.label', default: 'Payment'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
