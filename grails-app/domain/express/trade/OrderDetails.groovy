package express.trade

import express.enumurations.Unit

class OrderDetails {
 def springSecurityService
    static transients = ['springSecurityService']
	
	OrderP orderP
	Product product
    Unit productUnit
    Double quantity
    Double  unitPrice
    String discount
    Double total

	String notes
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

        this.orderP.history+=this.toString()+"\n"
    }


    def beforeDelete() {
        this.orderP.history+=this.toString()+"\n"
        }

    static constraints = {
	
	    createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
        discount( nullable: true, blank: true)
    }

    @Override
    String toString() {
        return [user:springSecurityService.principal?.username,orderDetailsId:this.id,product:this.product.name,productUnit:this.productUnit.name(),quantity:this.quantity,unitPrice:this.unitPrice,total:this.total].toString()
    }
}
