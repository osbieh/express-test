package express

import express.security.User
import org.springframework.security.access.annotation.Secured
import org.springframework.web.servlet.support.RequestContextUtils as RCU
@Secured('ROLE_ADMIN')
class MainController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index() {
      println RCU.properties
      println "LOCALE "+  RCU.getLocale(request)
        println "MainController index params " + params

        return
    }


    def getLoggedInUserPic(){
        println "getLoggedInUserPic  ----------- "
        def obj = User.findById(1L)
        def img= obj.userPic.binaryStream

        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=\"${img}\"")
        response.outputStream << img
        response.outputStream.flush()


    }


}
