package express.trade

import express.enumurations.PaymentStatus

class Payment {

   def springSecurityService
    static transients = ['springSecurityService']
	
	Account account
	PaymentStatus status //toDO enum open,complete,canceld
	Long totalAmount
    Date paymentDate
    String note
    Long receptNo

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
            createdBy = "O.Sbieh"
            lastUpdatedBy = "O.Sbieh"
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
		totalAmount(nullable: true, blank: false, editable: false)
        receptNo(nullable: true, blank: true, editable: false)
        note(nullable: true, blank: true, editable: false)
    }
}
