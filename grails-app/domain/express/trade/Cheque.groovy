package express.trade

class Cheque {
    def springSecurityService

    String chequeNo
    String owner
    String ownerId
    String abaRouting
    String bankAccount
    String bankName
    Date dueDate
    Long amount


    String description
    Date created  =new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    //TODO: Add security APIs to get the current username
    def beforeInsert() {
        created = new Date()
        lastUpdated = new Date()

        if (springSecurityService.isLoggedIn()) {
            createdBy = springSecurityService.principal.username
            lastUpdatedBy = springSecurityService.principal.username
        }
        else {
            createdBy = "System"
            lastUpdatedBy = "System"
        }

    }

    def beforeUpdate = {
        if (springSecurityService.isLoggedIn()) {
            lastUpdatedBy = springSecurityService.principal.username
        }
        else {
            lastUpdatedBy = "System"
        }
    }

    static mapping = {

    }

    static constraints = {
        description(nullable: true)
        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
        owner(display: false, nullable: true, blank: false, editable: false)
        ownerId(display: false, nullable: true, blank: false, editable: false)
        abaRouting(display: false, nullable: true, blank: false, editable: false)
        bankAccount(display: false, nullable: true, blank: false, editable: false)
        bankName(display: false, nullable: true, blank: false, editable: false)
    }
}
