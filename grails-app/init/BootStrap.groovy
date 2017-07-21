import express.security.Role
import express.security.User
import express.security.UserRole

class BootStrap {

    def init = { servletContext ->

//        def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
//        def userRole = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
//        def adminRole=Role.findByAuthority('ROLE_ADMIN')
//          def testUser = new User(username: 'araouf', password: '1234').save(flush: true, failOnError: true)
//            UserRole.create testUser, adminRole


    }
    def destroy = {
    }
}
