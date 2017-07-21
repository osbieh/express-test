package express.trade

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class AccountController {
    def accountService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Account.list(params), model: [accountCount: Account.count()]
    }

    def show(Account account) {
        respond account
    }

    def create() {
        respond new Account(params)
    }

    def list() {
        respond ""
    }

    def accountListData() {
        //  println "contactListData "+params

        render accountService.getAllAccount(params) as JSON

    }

    def accountDetails() {
        println "accountDetails----------" + params?.accountId
        def data = accountService.getAccountDetails(params?.accountId)
        println data
        render template: "/templates/accounts/accountDetails", model: [accountInfo: data as JSON]
    }


    def createAccount() {
        render template: "/templates/accounts/accountWizard"
    }

    def balance(){
        respond ""
    }


    @Transactional
    def manageAccount() {
        println "manageAccount" + params

        def result =  accountService.manageAccount(params)
        render(contentType: 'text/json') {
            [
                    'results': result, 'status': 200
            ]
        }

    }

    def getAccountPhones() {
      render accountService.getAccountPhones(params)
    }

    def accountContacts() {

        def data = accountService.getAccountContacts(params?.accountId)
        render data as JSON

    }

    def accountTypeahead() {

        render accountService.accountAutoComplete(params?.query) as JSON
    }

    def renderAccountAutoComplete(){

        render com.accountTypeAhead(params)

    }

    def getAccountBalances(){

        render  accountService.accountBalancesList(params) as JSON
    }

    def manageAccountBalance(){
        //accountService.checkAccountBalanceByOrder(params?.orderId) as JSON//
        render  accountService.accountBalanceManager(params) //as JSON
    }



    @Transactional
    def save(Account account) {
        if (account == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (account.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond account.errors, view: 'create'
            return
        }

        account.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'account.label', default: 'Account'), account.id])
                redirect account
            }
            '*' { respond account, [status: CREATED] }
        }
    }

    def edit(Account account) {
        respond account
    }

    @Transactional
    def update(Account account) {
        if (account == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (account.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond account.errors, view: 'edit'
            return
        }

        account.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'account.label', default: 'Account'), account.id])
                redirect account
            }
            '*' { respond account, [status: OK] }
        }
    }

    @Transactional
    def delete(Account account) {

        if (account == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        account.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'account.label', default: 'Account'), account.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
