package express.security

import express.security.User

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


import grails.converters.JSON
import org.eclipse.aether.repository.Authentication
import org.springframework.security.access.annotation.Secured
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.AuthenticationTrustResolver
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.WebAttributes
import grails.plugin.springsecurity.SpringSecurityUtils

import javax.servlet.http.HttpServletResponse

@Secured('permitAll')
class LoginController {

    /** Dependency injection for the authenticationTrustResolver. */
    AuthenticationTrustResolver authenticationTrustResolver
    def userService
    /** Dependency injection for the springSecurityService. */
    def springSecurityService

    /** Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise. */
    def index() {
      //  println "index " + params
        if (springSecurityService.isLoggedIn()) {
          //  println "auth index " + params
          //  println "auth index " + conf.successHandler.defaultTargetUrl
            redirect uri: conf.successHandler.defaultTargetUrl
        } else {
            redirect action: 'auth', params: params
        }
    }

    /** Show the login page. */
    def auth() {
//        println "-----------------------------------------------------"
//        println "auth params " + params
//        println "-----------------------------------------------------"
        def conf = getConf()
//        println "lang ::: "+ params.lang
//        println "conf :"+ conf

        if(params.lang=="en"){
//            println "language==en"
            conf.successHandler.defaultTargetUrl="en"
        }else{
//            println "language==ar"

        }


        if (springSecurityService.isLoggedIn()) {
//            println "auth isLoggedIn " + params
//            println "auth isLoggedIn " + conf.successHandler.defaultTargetUrl
            redirect uri: conf.successHandler.defaultTargetUrl
            return
        }
        String postUrl
//        println "request.contextPath " + request.contextPath
//        println "conf.apf.filterProcessesUrl " + conf.apf.filterProcessesUrl

        if(params.lang=='ar'){
            postUrl =  request.contextPath+"/"+params.lang+"/login/authenticate"
       }else{
            postUrl = request.contextPath + conf.apf.filterProcessesUrl
       }

//
//        println "request.contextPath " + request.contextPath
//        println "postUrl " + postUrl

        render view: 'auth', model: [postUrl            : postUrl,
                                     rememberMeParameter: conf.rememberMe.parameter,
                                     usernameParameter  : conf.apf.usernameParameter,
                                     passwordParameter  : conf.apf.passwordParameter,
                                     gspLayout          : conf.gsp.layoutAuth]
    }

//    def authenticate = {
//        println "-------------- authenticate " + params
//        def user = User.findByLoginAndPassword(params.login, params.password)
//        if (user) {
//            session.user = user
//            flash.message = "Hello ${user.name}!"
//         //   redirect(lang: params.lang, controller: "main", action: "index")
//            redirect(uri:"/"+params.lang+"/main")
//        } else {
//            println "--------------authfail() " + authfail()
////            flash.message = "Sorry, ${params.login}. Please try again."
////            redirect(action:"login")
//        }
//    }

    /** The redirect action for Ajax requests. */
    def authAjax() {
        response.setHeader 'Location', conf.auth.ajaxLoginFormUrl
        render(status: HttpServletResponse.SC_UNAUTHORIZED, text: 'Unauthorized')
    }

    /** Show denied page. */
    def denied() {
        println "denied "

        if (springSecurityService.isLoggedIn() && authenticationTrustResolver.isRememberMe(authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY (or the equivalent expression)
            redirect action: 'full', params: params
            return
        }

        [gspLayout: conf.gsp.layoutDenied]
    }

    /** Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page. */
    def full() {
        println " full() Try To Authenticate"
        def conf = getConf()
        render view: 'auth', params: params,
                model: [hasCookie          : authenticationTrustResolver.isRememberMe(authentication),
                        postUrl            : request.contextPath + conf.apf.filterProcessesUrl,
                        rememberMeParameter: conf.rememberMe.parameter,
                        usernameParameter  : conf.apf.usernameParameter,
                        passwordParameter  : conf.apf.passwordParameter,
                        gspLayout          : conf.gsp.layoutAuth]
    }

    /** Callback after a failed login. Redirects to the auth page with a warning message. */
    def authfail() {

        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = message(code: 'springSecurity.errors.login.expired')
            } else if (exception instanceof CredentialsExpiredException) {
                msg = message(code: 'springSecurity.errors.login.passwordExpired')
            } else if (exception instanceof DisabledException) {
                msg = message(code: 'springSecurity.errors.login.disabled')
            } else if (exception instanceof LockedException) {
                msg = message(code: 'springSecurity.errors.login.locked')
            } else {
                msg = message(code: 'springSecurity.errors.login.fail')
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        } else {
            flash.message = msg
            render lang: params.lang, action: 'auth', params: params
        }
    }

    /** The Ajax success redirect url. */
    def ajaxSuccess() {
        render([success: true, username: authentication.name] as JSON)
    }

    /** The Ajax denied redirect url. */
    def ajaxDenied() {
        render([error: 'access denied'] as JSON)
    }

    protected Authentication getAuthentication() {
        SecurityContextHolder.context?.authentication
    }

    protected ConfigObject getConf() {
        SpringSecurityUtils.securityConfig
    }
}