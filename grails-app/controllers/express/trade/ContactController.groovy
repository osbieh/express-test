package express.trade

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class ContactController {

    def contactService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Contact.list(params), model: [contactCount: Contact.count()]
    }

    def show(Contact contact) {
        respond contact
    }

    def create() {
        respond new Contact(params)
    }

    def list() {
        respond new Contact(params)
    }


    def contactListData() {
        //  println "contactListData "+params

        render contactService.getAllContact(params) as JSON

    }

    def contactTypeahead() {

        render contactService.contactAutoComplete(params?.query) as JSON

    }

    def contactDetails() {
        println "contactDetails :::" + params
        def contact = Contact.findById(params?.contactId);

        def phones = contact?.phones?.toString()
        render template: "/templates/contacts/contactDetails", model: [phones: phones ? phones : [], contact: contact ? contact as JSON : []]


    }


    def renderContactAutoComplete(){

        render com.typeahead(params)

    }

    def getContactPhones() {
             render contactService.contactPhones(params)
    }

    @Transactional
    def saveContactDetails() {

        def result=contactService.manageContact(params);
        render(contentType: 'text/json') {
            [
                    'results': result, 'status': 200
            ]
        }

    }

    @Transactional
    def save(Contact contact) {
        if (contact == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contact.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contact.errors, view: 'create'
            return
        }

        contact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect contact
            }
            '*' { respond contact, [status: CREATED] }
        }
    }

    def edit(Contact contact) {
        respond contact
    }

    @Transactional
    def update(Contact contact) {
        if (contact == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (contact.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond contact.errors, view: 'edit'
            return
        }

        contact.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect contact
            }
            '*' { respond contact, [status: OK] }
        }
    }

    @Transactional
    def delete(Contact contact) {

        if (contact == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        contact.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
