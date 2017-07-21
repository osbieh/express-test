package express.trade

class Definition {
    def springSecurityService
    String nameAr
    String nameEn
    String description


    Date created = new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    def beforeInsert() {
        created = new Date()
        lastUpdated = new Date()

        if (springSecurityService.isLoggedIn()) {
            createdBy = springSecurityService.principal
            lastUpdatedBy = springSecurityService.principal
        } else {
            createdBy = "System"
            lastUpdatedBy = "System"
        }

    }

    def beforeUpdate = {
        if (springSecurityService.isLoggedIn()) {
            lastUpdatedBy = springSecurityService.principal
        } else {
            lastUpdatedBy = "System"
        }
    }

    static constraints = {
        description(nullable: true)
        nameEn(nullable: true)
        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
    }
}

