package express.trade

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class ChequeController {
    def chequeService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cheque.list(params), model:[chequeCount: Cheque.count()]
    }

    def show(Cheque cheque) {
        respond cheque
    }

    def create() {
        respond new Cheque(params)
    }

    def list(){
        respond ""
    }

    def chequesCalendar(){
        respond ""
    }

    def getChequeList(){
       render chequeService.getChequesList() as JSON
    }

    @Transactional
    def save(Cheque cheque) {
        if (cheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cheque.errors, view:'create'
            return
        }

        cheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cheque.label', default: 'Cheque'), cheque.id])
                redirect cheque
            }
            '*' { respond cheque, [status: CREATED] }
        }
    }

    def edit(Cheque cheque) {
        respond cheque
    }

    @Transactional
    def update(Cheque cheque) {
        if (cheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (cheque.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cheque.errors, view:'edit'
            return
        }

        cheque.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cheque.label', default: 'Cheque'), cheque.id])
                redirect cheque
            }
            '*'{ respond cheque, [status: OK] }
        }
    }

    @Transactional
    def delete(Cheque cheque) {

        if (cheque == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        cheque.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cheque.label', default: 'Cheque'), cheque.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cheque.label', default: 'Cheque'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
