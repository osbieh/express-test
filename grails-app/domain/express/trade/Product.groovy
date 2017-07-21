package express.trade

import express.enumurations.ProductType
import express.enumurations.Unit

class Product {
    def springSecurityService
    static transients = ['springSecurityService']
	
	String name
    ProductType productType
	String details
	String note

    Long purchasePrice
    Long sellPrice

    Unit defaultUnit

	
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
        defaultUnit( nullable: true)
        purchasePrice(display: false, nullable: true, blank: false, editable: false)
        sellPrice(display: false, nullable: true, blank: false, editable: false)
        note(display: false, nullable: true, blank: true, editable: false)
        details(display: false, nullable: true, blank: false, editable: false)
    }
}
