package express.trade

/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 8/28/12
 * Time: 12:37 AM
 * To change this template use File | Settings | File Templates.
 */
class Address {
    def springSecurityService

    String city
    String addressLine
    String addressLine2

    boolean isPrimary
    boolean isActive
    Date created  =new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy

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

        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)

    }

    String toString() {
        StringBuilder fullAddress = new StringBuilder()
        fullAddress.append(city)
        if (addressLine != null && addressLine.size() > 0) {
            fullAddress.append(' - ')
            fullAddress.append(addressLine)
        }
        if (addressLine2 != null && addressLine2.size() > 0) {
            fullAddress.append(' - ')
            fullAddress.append(addressLine2)
        }



        return fullAddress.toString();
    }

}
