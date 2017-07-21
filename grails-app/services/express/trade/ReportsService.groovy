package express.trade

import grails.transaction.Transactional
import grails.transaction.Transactional
import groovy.json.JsonSlurper
import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JREmptyDataSource
import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JRField
import net.sf.jasperreports.engine.JRPropertiesHolder
import net.sf.jasperreports.engine.JRPropertiesMap
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter
import net.sf.jasperreports.engine.export.JRXlsExporter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimplePdfExporterConfiguration
import grails.core.*
import grails.util.Holders
import org.bouncycastle.asn1.x509.Holder
import org.springframework.context.i18n.LocaleContextHolder

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@Transactional
class ReportsService {
    //GrailsApplication grailsApplication = Holders.grailsApplication
    GrailsApplication grailsApplication
    def messageSource
    def springSecurityService
    def orderPService
    def accountBalanceService

    def serviceMethod() {

    }

    def generateReport(def arguments) {
        def reportName = arguments.reportName
        def output
        if (reportName != null && reportName != "") {

            switch (reportName) {
                case "accountSummary":
                    output = this.accountSummaryReport(arguments)
                    break;
                case "accountBalance":
                    output=this.accountBalanceReport(arguments)
                case "customerOrdersDetail":
                    this.customerOrdersDetail(arguments)
            }
            return output
        }

        return "No Data"
    }

    def customerOrdersDetail(arguments){
        def dataSet = []
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        def queryResult=accountBalanceService.getAccountBalanceReportData(arguments)
        def accountName=Account.findById(arguments?.accountId)?.accountName
        Map parameters = new HashMap();
        parameters = defaultReportsParameters();
        parameters.put("accountName",accountName)
        parameters.put("fromDate",arguments?.fromDate)
        parameters.put("toDate",arguments?.toDate)
        return this.generateToFile(arguments.reportName, queryResult, parameters)
    }

    def accountBalanceReport(arguments){
        def dataSet = []
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        def queryResult=accountBalanceService.getAccountBalanceReportData(arguments)
        def accountName=Account.findById(arguments?.accountId)?.accountName
        Map parameters = new HashMap();
        println "accountName :"+accountName
        parameters = defaultReportsParameters();
        parameters.put("accountName",accountName)
        parameters.put("fromDate",arguments?.fromDate)
        parameters.put("toDate",arguments?.toDate)
        return this.generateToFile(arguments.reportName, queryResult, parameters)
    }

    def accountSummaryReport(arguments) {
        def dataSet = []
        def accountOrders = [:]
        def queryResult=orderPService.getCustomerOrdersDetails([accountId:  4]);
        print queryResult
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
        Map<String, Object> map = new HashMap<String, Object>();
        Map parameters = new HashMap();
        parameters = defaultReportsParameters();
        parameters.put("accountName",Account.findById(1L).accountName)

        println queryResult
        return this.generateToFile(arguments.reportName, queryResult, parameters)
    }

    def defaultReportsParameters() {
        Map parameters = new HashMap();
        parameters.put("printTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        parameters.put("currentUser", springSecurityService.principal?.username)
        return parameters
    }


    def generatePDF(def reportName, def dataSet, def parameters) {
        def path = 'reports/'.concat(reportName + ".jrxml")
        println("----------------" + path)
        def generatedReportPath = 'reports/generatedReports/'
        InputStream reportPath = this.class.classLoader.getResourceAsStream(path)
        def jasperReport = JasperCompileManager.compileReport(reportPath)

        JRMapCollectionDataSource colDataSource = new JRMapCollectionDataSource(dataSet)

        JasperPrint jasperPrint
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, colDataSource)
        } catch (Exception e) {
            e.printStackTrace()
        }
        grailsApplication.config.getProperty('tasConfig.applicationHomePath')
        return JasperExportManager.exportReportToPdf(jasperPrint)
    }

    def generateToFile(def reportName, def dataSet, def parameters, def fileType = "PDF") {
        def path = grailsApplication.config.getProperty("expressConfig.reports.jasperReportsJRXMLFilesPath").concat("\\${reportName}.jrxml")
        println "*************" + path
        def generatedReportPath = grailsApplication.config.getProperty("expressConfig.reports.reportGeneratedFilesPath")
        //InputStream reportPath = this.class.classLoader.getResourceAsStream(path)
        def jasperReport = JasperCompileManager.compileReport(path)
        def colDataSource = new JRMapCollectionDataSource(dataSet)
        JasperPrint jasperPrint
        String printFileName = null;
        try {

            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, colDataSource)
        } catch (Exception e) {
            e.printStackTrace()
        }
        Date dt = new Date()
        def fileName = reportName + "_" + dt.time + "." + fileType.toLowerCase()
//        if (fileType == "PDF")
            JasperExportManager.exportReportToPdfFile(jasperPrint, generatedReportPath + "\\" + fileName)
//        else
//            JasperExportManager.exportReportToExcelFile(jasperPrint, generatedReportPath + "\\" + fileName)
        return fileName

    }

    def removeOldFiles() {
        println "Remove Files"
        Date oldDate = new Date()
        oldDate -= systemConfigService.numOfDaysToDeleteReportFiles
        new File(systemConfigService.reportGeneratedFilesPath).eachFileRecurse { file ->
            if (file.lastModified() <= oldDate.time) {
                file.delete()
            }
        }

    }


}
