package express.trade

import express.enumurations.MovementType


class DailyMovement {
  
    def springSecurityService
    static transients = ['springSecurityService']

    Date movementDay
	MovementType movementType
	Account account
    Long referenceId
	Long totalAmount
    Long receptNo
    String note
    String status
	
    Date created = new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    static mapping = {
        note (type: "text")
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
        receptNo(nullable: true, blank: true)
        totalAmount(nullable: true, blank: true)
        referenceId(nullable: true, blank: true)
        note(nullable: true, blank: true)
    }
}
