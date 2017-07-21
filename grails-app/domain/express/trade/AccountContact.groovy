package express.trade

class AccountContact {
    def springSecurityService
    static transients = ['springSecurityService']
	
    Account account
    Contact contact
    Boolean isPrimaryContact
	
	Date created = new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    static mapping = {
        version defaultValue: 0
    }
	
	  def beforeInsert() {
        created = new Date()
        lastUpdated = new Date()

        if (springSecurityService.isLoggedIn()) {
            createdBy = springSecurityService.principal?.username
            lastUpdatedBy = springSecurityService.principal?.username
        }
        else {
            createdBy = "system"
            lastUpdatedBy = "system"
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
	 
	 }
	
	
}
