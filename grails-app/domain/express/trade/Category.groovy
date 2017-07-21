package express.trade

class Category {

    def springSecurityService
    Definition definition
    String nameAr
    String nameEn
    String description
    boolean enabled

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
        enabled defaultValue: true
    }

    static constraints = {
        description(nullable: true)
        nameEn(nullable: true)
        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
    }
}
