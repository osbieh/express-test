//import grails.plugin.springsecurity.SpringSecurityUtils
//import org.grails.web.mime.HttpServletRequestExtension
//import palsafe.Application


environments {
    development {
        expressConfig {
            reports {
                reportLogoImage = "assets\\express_Logo.png"
                jasperReportsJRXMLFilesPath = "C:\\express\\grails-app\\views\\reports\\jrxmlFiles\\"
                reportGeneratedFilesPath = "C:\\express\\grails-app\\views\\reports\\generatedReports"
            }
            applicationHomePath = "C:\\apache-tomcat-8.0.30\\webapps\\express\\" //production
        }
    }
    production{
        expressConfig {
            reports {
                reportLogoImage = "assets\\express_Logo.png"
                jasperReportsJRXMLFilesPath = "WEB-INF\\classes\\reports\\jrxmlFiles"
                reportGeneratedFilesPath = "WEB-INF\\classes\\reports\\generatedReports"
            }
            applicationHomePath = "C:\\apache-tomcat-8.0.30\\webapps\\express\\" //production
        }
    }
}


dataSource {
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = "org.hibernate.dialect.MySQLDialect"
    username = "express"
    password = "express"

}

environments {
    development {



        dataSource {
            dbCreate = 'update'//"create-drop" // one of 'create', 'create-drop', , 'validate', ''
            url = "jdbc:mysql://localhost:3306/express?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8"
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                validationQuery = "SELECT 1"
                jdbcInterceptors = "ConnectionState"
            }
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:postgresql://localhost:5432/express"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/express?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8"
            properties {
                maxActive = -1
                minEvictableIdleTimeMillis = 1800000
                timeBetweenEvictionRunsMillis = 1800000
                numTestsPerEvictionRun = 3
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                validationQuery = "SELECT 1"
                jdbcInterceptors = "ConnectionState"
            }
        }
    }
}

//log4j = {
//    debug 'org.springframework.security'
//}
// Added by the Spring Security Core plugin:

net.sf.jasperreports.extension.registry.factory.simple.font.families=net.sf.jasperreports.engine.fonts.SimpleFontExtensionsRegistryFactory
net.sf.jasperreports.extension.simple.font.families.liberationsans=jasper_fonts.xml
grails.plugin.springsecurity.successHandler.alwaysUseDefault = true
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/ar/main"
grails.plugin.springsecurity.apf.filterProcessesUrl = '/ar/login/authenticate'
grails.plugin.springsecurity.auth.loginFormUrl = '/ar/login/auth'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/ar/error'
grails.plugin.springsecurity.logout.filterProcessesUrl = '/ar/logoff'
grails.plugin.springsecurity.logout.afterLogoutUrl = '/ar/login/auth'
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.onAbstractAuthenticationFailureEvent = { e, appCtx ->
    println "\nERROR auth failed for user $e.authentication.name: $e.exception.message\n"
}


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'express.security.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'express.security.UserRole'
grails.plugin.springsecurity.authority.className = 'express.security.Role'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', access: ['permitAll']],
        [pattern: '/error', access: ['permitAll']],
        [pattern: '/index', access: ['permitAll']],
        [pattern: '/index.gsp', access: ['permitAll']],
        [pattern: '/shutdown', access: ['permitAll']],
        [pattern: '/assets/**', access: ['permitAll']],
        [pattern: '/**/assets/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/img/**', access: ['permitAll']],
        [pattern: '/**/images/icons/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']],
        [pattern: '**/logout/**', access: ['IS_AUTHENTICATED_ANONYMOUSLY']],
        [pattern: '**/login/**', access: ['IS_AUTHENTICATED_ANONYMOUSLY']],
        [pattern: '**/*.jpg', access: ['permitAll']],

]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/**/assets/**', filters: 'none'],
        [pattern: '/**/js/**', filters: 'none'],
        [pattern: '/**/css/**', filters: 'none'],
        [pattern: '/**/images/**', filters: 'none'],
        [pattern: '/**/img/**', filters: 'none'],
        [pattern: '/**/images/icons/**', filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**', filters: 'JOINED_FILTERS']
]


