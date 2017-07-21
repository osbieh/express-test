package express.trade

import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class ReportsController {
    def reportsService

    def index() {}
    def getAccountOrderDetailsReport(){
        def repParam = [:]
        repParam.reportName ="accountSummary"
        def fileGeneratedName = reportsService.generateReport(repParam)
        //fileGeneratedName = grailsLinkGenerator.serverBaseURL+"/reports/generatedReports/"+fileGeneratedName
        def generatedReportPath= grailsApplication.config.getProperty("expressConfig.reports.reportGeneratedFilesPath")

        if(fileGeneratedName != null) {
            def file = new File(generatedReportPath+"/"+fileGeneratedName)

            if (file.exists()) {
                response.setContentType("application/pdf")
                response.setHeader("Content-disposition", "filename=${file.name}")
                response.outputStream << file.bytes

                response.outputStream.flush()
                response.outputStream.close()
                return
            }
        }
        render "Not Found"

        render fileGeneratedName

    }

    def getAccountBalanceReport(){
        println "START getAccountBalanceReport=== "+params
        def repParam = [:]
        params?.reportName ="accountBalance"

        def fileGeneratedName = reportsService.generateReport(params)
        //fileGeneratedName = grailsLinkGenerator.serverBaseURL+"/reports/generatedReports/"+fileGeneratedName
        def generatedReportPath= grailsApplication.config.getProperty("expressConfig.reports.reportGeneratedFilesPath")

        if(fileGeneratedName != null) {
            def file = new File(generatedReportPath+"/"+fileGeneratedName)

            if (file.exists()) {
                response.setContentType("application/pdf")
                response.setHeader("Content-disposition", "filename=${file.name}")
                response.outputStream << file.bytes

                response.outputStream.flush()
                response.outputStream.close()
                return
            }
        }
        render "Not Found"

        render fileGeneratedName
    }

    def getCustomerOrdersDetailsReport(){
        println "getCustomerOrdersDetailsReportt=== "+params
        def repParam = [:]
        params?.reportName ="customerOrdersDetail"

        def fileGeneratedName = reportsService.generateReport(params)
        //fileGeneratedName = grailsLinkGenerator.serverBaseURL+"/reports/generatedReports/"+fileGeneratedName
        def generatedReportPath= grailsApplication.config.getProperty("expressConfig.reports.reportGeneratedFilesPath")

        if(fileGeneratedName != null) {
            def file = new File(generatedReportPath+"/"+fileGeneratedName)

            if (file.exists()) {
                response.setContentType("application/pdf")
                response.setHeader("Content-disposition", "filename=${file.name}")
                response.outputStream << file.bytes

                response.outputStream.flush()
                response.outputStream.close()
                return
            }
        }
    //    render "Not Found"

        render fileGeneratedName


    }



}
