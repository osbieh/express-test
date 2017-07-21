package express.security
/* Copyright 2013-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.springframework.security.access.annotation.Secured
import org.springframework.security.web.RedirectStrategy
import grails.plugin.springsecurity.SpringSecurityUtils
import javax.servlet.http.HttpServletResponse
import java.util.logging.Logger

@Secured('permitAll')
class LogoutController {

    /** Dependency injection for RedirectStrategy. */
    RedirectStrategy redirectStrategy

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def afterInterceptor = [action:'index',only:['loginPage']]

    def index() {
        println "**********-----logout1-----**********"
//        def url3 = createLink(uri: "/en/j_spring_security_logout", absolute: true)
//        redirect url: url3

        redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
    }
   //      redirect uri: "/en"+SpringSecurityUtils.securityConfig.logout.filterProcessesUrl


    def logout(){
        println "**********-----logout2-----**********"

        redirect uri: "/en"+SpringSecurityUtils.securityConfig.logout.filterProcessesUrl
    }


    def loginPage(){
        println '******************'
        redirect createLink(controller:'login', action:'auth')

    }


//    def index() {
//        println "----------------------"
//        if (!request.post && SpringSecurityUtils.getSecurityConfig().logout.postOnly) {
//            response.sendError HttpServletResponse.SC_METHOD_NOT_ALLOWED // 405
//            return
//        }

//        // TODO put any pre-logout code here
//        redirectStrategy.sendRedirect request, response, SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/logoff'
//        response.flushBuffer()
//    }
}