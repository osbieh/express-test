package express.trade

import express.enumurations.AccountType
import grails.converters.JSON

class Account {

    def springSecurityService
    static transients = ['springSecurityService']
   

    String accountName
    String taxName
    String taxNo

    AccountType accountType
    String classifications
    String status

    String email
    String note

    Date created  =new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

    Set addresses
    Set phones
    static hasMany = [addresses:Address,phones:Phone]
    // amendment of createdBy and lastUpdatedBy information
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

    static mapping = {
        note (type: "text")
        version defaultValue: 0
    }
    static constraints = {

		accountName(nullable: true, blank: false, editable: false)
        taxName(nullable: true, blank: true, editable: false)
        taxNo(nullable: true, blank: true, editable: false)
        status(nullable: true, blank: true, editable: false)
        classifications (nullable: true, blank: true, editable: false)
        email(nullable: true, blank: true)
        accountType(nullable: true, blank: true)
        note(nullable: true, blank: true, editable: false)

        createdBy(display: false, nullable: true, blank: true, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: true, editable: false)
        lastUpdated(display: false, nullable: true, blank: true, editable: false)

        addresses(nullable: true, blank: true)
        phones(nullable: true, blank: true)
    }

    public String toString(){

        return [accountName:this.accountName,accountId:this.id] as JSON

    }
    public def toSet(){

        return [accountName:this.accountName,accountId:this.id]

    }



}
