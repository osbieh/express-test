package express.trade

class AccountBalance {
    def springSecurityService

    Account account
    Date movementDay
    String movementType
    OrderP order
    Payment payment
    Long movementAmount
    Long totalAmount

    Date created  =new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy


    def beforeInsert() {
        created = new Date()
        lastUpdated = new Date()

        if (springSecurityService.isLoggedIn()) {
            createdBy = springSecurityService.principal?.username
            lastUpdatedBy = springSecurityService.principal?.username
        }
        else {
            createdBy = "System"
            lastUpdatedBy = "System"
        }
    }

    def beforeUpdate = {
        if (springSecurityService.isLoggedIn()) {
            lastUpdatedBy = springSecurityService.principal?.username
        }
        else {
            lastUpdatedBy = "Anonymous User"
        }
    }


    static constraints = {
        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
        order(display: false, nullable: true, blank: false, editable: false)
        payment(display: false, nullable: true, blank: false, editable: false)
    }
}
