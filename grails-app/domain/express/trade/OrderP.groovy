package express.trade

import express.enumurations.OrderStatus
import grails.converters.JSON

class OrderP {

   def springSecurityService
    static transients = ['springSecurityService']
	
	Account account
	OrderStatus status //toDO enum open,complete,canceled
	Long totalAmount
    String discount
    Long receptNo
    Date orderDate
    String note
    String history=""

    Date created = new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    static mapping = {
        version defaultValue: 0
        note (type: "text")
        history(type: "text")
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

        history+=this.toString()+"\n"


    }


    static constraints = {
	    createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
		totalAmount(nullable: true, blank: false, editable: false)
        note(nullable: true, blank: true, editable: false)
        receptNo(nullable: true, blank: false, editable: false)
        history(nullable: true, blank: true, editable: true)
        discount( nullable: true, blank: true)
    }

    @Override
    public String toString() {
        return [orderDate:this?.orderDate,totalAmount:this?.totalAmount,account:this.account?.toString(),receptNo:this?.receptNo,status:this?.status].toString()
    }

    public def toSet(){

        return [orderId:this.id, orderDate:this?.orderDate.format("dd-MM-YYY"),totalAmount:this?.totalAmount,status:this?.status.defaultMessage]
    }
}
