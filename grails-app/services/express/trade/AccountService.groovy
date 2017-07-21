package express.trade

import express.enumurations.AccountType
import express.enumurations.PhoneType
import grails.converters.JSON
import grails.core.GrailsApplication
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder as LCH

import java.text.SimpleDateFormat

@Transactional
class AccountService {
    def messageSource
    def phoneService

    def serviceMethod() {

    }

    def getAllAccount(params) {
        println "getAllContact " + params
        def dateToBeRender = [:]
        dateToBeRender.aaData = []

        def accountList = Account.createCriteria().list {
            if (params?.accountId)
                eq("id", Long.parseLong(params?.accountId))

        }

        accountList.each {
            dateToBeRender.aaData << [
                    "accountId"  : it?.id,
                    "accountName": it?.accountName,
                    "taxName"    : it?.taxName,
                    "accountType": messageSource?.getMessage("ACCOUNTTYPE." + it?.accountType?.defaultMessage, null, null, LCH.locale),
                    "address"    : it?.addresses?.toString(),
                    "email"      : it?.email,

            ]
        }
        return dateToBeRender

    }

    def getAccountDetails(accountId) {
        println " Account Id   " + accountId
        def acc = Account.findById(Long.parseLong(accountId))

        return [id             : acc?.id,
                accountName    : acc?.accountName,
                taxName        : acc.taxName,
                taxNo          : acc.taxNo,
                accountType    : acc?.accountType?.defaultMessage,
                email          : acc.email,
                classifications: acc.classifications,
                status         : acc.status,
                note           : acc.note,
                created        : acc.created,
                createdBy      : acc.createdBy


        ]


    }

    def getAccountContacts(accountId) {
        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def accountContactList = AccountContact.createCriteria().list {
            account {
                eq("id", Long.parseLong(accountId))
            }

        }

        accountContactList.each {
            dateToBeRender.aaData << [
                    "accountContactId": it?.id,
                    "contactId"       : it?.contact?.id,
                    "isPrimaryContact": it?.isPrimaryContact,
                    "contactName"     : it?.contact?.fullName,
                    "phoneNo"         : it?.contact?.phones?.phoneNo,
                    "contactId"       : it?.contact?.id,

            ]
        }

        return dateToBeRender
    }


    def getAccountPhones(params) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []

        def contactId = null
        if (params?.masterTableId) {
            contactId = params?.masterTableId
            def contact = Account.findById(Long.parseLong(contactId))

            contact?.phones.each {
                dateToBeRender.aaData << [
                        "isPrimary": it?.isPrimary,
                        "isActive" : it?.isActive,
                        "phoneNo"  : it?.phoneNo,
                        "phoneType": it?.phoneType?.defaultMessage,
                        "contactId": it?.id,
                        "action"   : it?.id
                ]

            }
        }

        return dateToBeRender as JSON
    }


    def manageAccount(params) {

        def accountInfo = JSON.parse(params.accountInfo)
        def accountContacts = JSON.parse(params.accountContacts)
        def accountPhones = JSON.parse(params.accountPhones)

        println("accountInfo : " + accountInfo)
        println("accountContacts : " + accountContacts)
        println("accountPhones : " + accountPhones)


        def account = null
        def listPhones = []
        def listContacts = []
        if (accountInfo?.accountId) {
            account = Account.findById(accountInfo?.accountId)

            AccountContact.findAllByAccount(account).each {
                deleteAccountContact(it)
            }

            account.phones.each {
                listPhones.push(it)

            }
            account.phones = [];
            listPhones.each {
                phoneService.delete(it)
            }

            listPhones = []
        } else {
            account = new Account()
        }

        account.accountName = accountInfo?.accountName
        account.accountType = AccountType.valueOf(accountInfo?.accountType)
        account?.taxNo = accountInfo?.taxNo
        account?.taxName = accountInfo?.taxName
        account?.email = accountInfo?.email
        account?.status = accountInfo?.status
        account?.note = accountInfo?.note
        accountPhones.each { phone ->
            def ph = new Phone(isActive: phone?.isActive, isPrimary: phone?.isPrimary, phoneNo: phone?.phoneNo, phoneType: PhoneType?.valueOf(phone?.phoneType))

            listPhones.push(ph)

        }



        accountContacts.each { contactId ->
            new AccountContact(contact: Contact.findById(contactId?.contactId as long), account: account, isPrimaryContact: true).save(flush: true, failOnError: true)
        }

        account.phones = listPhones
        account.save(flush: true, failOnError: true)

        return account
    }


    def deleteAccountContact(AccountContact accountContact) {
        if (accountContact == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }
        accountContact.delete flush: true
        return "accountContact Is Deleted"
    }


    def accountAutoComplete(q) {
        def accountList = Account.createCriteria().list() {
            if(q!="ALL")
            like("accountName", '%' + q + '%') //
        }
        def result = [:];
        result.aaData = []

        result.aaData << [
                accountName: "الجميع",
                accountId  : '-150',
                phoneNo    : "---"
        ]

        accountList.each {
            result.aaData << [
                    accountName: it?.accountName,
                    accountId  : it?.id,
                    phoneNo    : it?.phones?.phoneNo
            ]
        }

        return result
    }

    def accountBalanceManager(params) {
        println "==START== accountBalanceManager "+params
        def acc=null
        if(!this.checkAccountBalanceByMovement(params?.movementType,params?.movementId)){
           manageAccountBalance(params)
        }

        println "==END== accountBalanceManager "+acc
        return acc

    }

    def manageAccountBalance(params){
            println "manageAccountBalance params::::"+params
        def accountBalance
        if(params?.accountBalanceId){
            accountBalance=AccountBalance.findById(params?.accountBalanceId)
        }else{
            accountBalance=new AccountBalance()
        }

        accountBalance.movementDay=params?.movementDay
        accountBalance.account=Account?.findById(params?.accountId)
        accountBalance.movementAmount=params?.amount
        def lastRecord=lastAccountBalanceRecord(params?.accountId)
        println params?.amount+"------manageAccountBalance--------::::"+lastRecord?.totalAmount
        if(params?.movmentType=="ORDER"){
            accountBalance.movementType="ORDER"
            accountBalance.order=params?.order
            accountBalance.totalAmount=(lastRecord?.totalAmount==null?0:lastRecord?.totalAmount)+params?.amount
        }else{
            accountBalance.movementType="PAYMENT"
            accountBalance.payment=params?.payment
            accountBalance.totalAmount=(lastRecord?.totalAmount==null?0:lastRecord?.totalAmount)-params?.amount
        }
        accountBalance.save(flush: true,failOnError: true)
       return accountBalance
    }

    def lastAccountBalanceRecord(accountId){
        println "=====lastAccountBalanceRecord========="+accountId
        def acc
        def accountBalance = AccountBalance.createCriteria().list() {
            account {
                eq('id',Long.parseLong(""+accountId))
            }
            projections {
                max("id", "accountBalanceId")
            }
        }

        acc = AccountBalance.findById(accountBalance[0])
        return acc
    }

    def checkAccountBalanceByMovement(movementType ,movementId) {
        println ":::--BEGIN--- checkAccountBalanceByMovement -- "+movementType+" ::: "+movementId
        def accountBalance = AccountBalance?.createCriteria().list{
          if(movementType=="ORDER")
            order{ eq('id',movementId) }
          else
            payment{ eq('id',movementId)}
        }
        println ":::--END-- checkAccountBalanceByOrder -----:::"+accountBalance
        return accountBalance
    }



    def accountBalancesList(params) {
        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def fromDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.fromDate + " 00:00:00"))
        def toDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.toDate + " 23:59:59"))
        def criteria = AccountBalance.createCriteria()
       def accountBalancesList=criteria.list {
          account{
              eq('id',Long.parseLong(params?.accountId))
          }
           between("movementDay", fromDate, toDate)
           order('id',"desc")
        }
       println  accountBalancesList



        accountBalancesList.each{
            dateToBeRender.aaData<<[
                    "rowNum":"",
                    "accountName": it?.account?.accountName,
                    "movementType":it?.movementType,
                    "movementDay":it?.movementDay?.format("yyyy-MM-dd"),
                    "movementValue":it?.movementAmount,
                    "totalAmount":it?.totalAmount,
                    "transactions":it?.id,
                    "accountId": it?.account?.id,

            ]
        }
        return dateToBeRender

    }



}
