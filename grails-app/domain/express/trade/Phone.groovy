package express.trade

import express.enumurations.PhoneType
import grails.converters.JSON


/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 8/27/12
 * Time: 11:51 PM
 * To change this template use File | Settings | File Templates.
 */
class Phone {
    def  springSecurityService
    String phoneNo
    PhoneType phoneType
    String phoneGroup
    boolean isPrimary
    boolean isActive


    Date created  =new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy


    static mapping = {
        version defaultValue: 0
    }

def beforeInsert() {
    created=new Date()
    lastUpdated=new Date()

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
        phoneGroup(display: false, nullable: true, blank: false, editable: false)
        isPrimary( nullable: true, blank: true)
        isActive( nullable: true, blank: true)
        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)

    }

    public String toString(){
        return [phoneNo:this.phoneNo,phoneType:this.phoneType] as JSON

    }

}