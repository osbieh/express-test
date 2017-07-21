package express.trade

class Contact {
    def springSecurityService
    static transients = ['springSecurityService']
    String firstName
    String secondName
    String lastName
    String fullName=getFullName();
    String title
    String gender
    String jobtitle
    String pereferedMethodContact
    String email
    String note
    Set addresses
    Set phones

    Date created = new Date();
    String createdBy
    Date lastUpdated
    String lastUpdatedBy



    static hasMany = [addresses:Address,phones:Phone]

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

    static constraints = {
        firstName(nullable: false, blank: true)
        secondName(nullable: true, blank: true)
        lastName(nullable: true, blank: false)
		fullName(nullable: true)
        gender(nullable: true, blank: true, inList: ["Male", "Female"])
        title(nullable: true, blank: true)
		jobtitle(nullable: true, blank: true)
        pereferedMethodContact(nullable: true, blank: true)
        email(nullable: true, blank: true)
        note(nullable: true, blank: true)

        createdBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdatedBy(display: false, nullable: true, blank: false, editable: false)
        lastUpdated(display: false, nullable: true, blank: false, editable: false)
		

        addresses(nullable: true, blank: true)
        phones(nullable: true, blank: true)
    }

    String getFullName() {
        StringBuilder fullName = new StringBuilder()
        fullName.append(firstName)
        if (secondName != null && secondName.size() > 0) {
            fullName.append(' ')
            fullName.append(secondName)
        }
        if (lastName != null && lastName.size() > 0) {
            fullName.append(' ')
            fullName.append(lastName)
        }
        return fullName.toString();
    }
    String toString() {
        StringBuilder fullName = new StringBuilder()
        fullName.append(firstName)
        if (secondName != null && secondName.size() > 0) {
            fullName.append(' ')
            fullName.append(secondName)
        }
        if (lastName != null && lastName.size() > 0) {
            fullName.append(' ')
            fullName.append(lastName)
        }
        return fullName.toString();
    }


}
