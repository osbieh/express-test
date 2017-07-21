package express.trade

import express.enumurations.MovementStatus
import express.enumurations.MovementType
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

import java.text.SimpleDateFormat

@Transactional
class DailyMovementService {
    def messageSource

    def serviceMethod() {

    }


    def getMovementsByDay(params) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def startDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.movementDay + " 00:00:00"))
        def endDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.movementDay + " 23:59:59"))

        println " params : " + params
        println " startDate : " + startDate
        println "endDate : " + endDate

        def list = DailyMovement.createCriteria().list {
            between("movementDay", startDate, endDate)
//           and { ge("movementDay",startDate) }
//           and { le("movementDay", endDate) }

        }


        list.each {
            dateToBeRender.aaData << [
                    "rowNum":it?.id,
                    "dailyMovementId": it.id,
                    "movementType"   : messageSource?.getMessage("MovementType." + it?.movementType?.defaultMessage, null, null, LocaleContextHolder.locale),
                    "accountName"    : it?.account?.accountName,
                    "total"          : it?.totalAmount,
                    "receptNo"       : it?.receptNo,
                    "note"           : it?.note,
                    "status"         : it?.status,
                    "actions"        : it.id,
                    "accountId"      : it?.account?.id,
                    "referenceId"      :it?.referenceId,
                    "movementType2"   : it?.movementType?.defaultMessage,
            ]


        }

        return dateToBeRender


    }



    def getCustomerMovementsByRange(params) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def startDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.fromDate + " 00:00:00"))
        def endDate = new SimpleDateFormat('yyyy-MM-dd hh:mm:ss').parse((params?.toDate + " 23:59:59"))
        def list = DailyMovement.createCriteria().list {
            account{
                eq('id',Long.parseLong(params?.accountId))
            }
            between("movementDay", startDate, endDate)
        }


        list.each {
            dateToBeRender.aaData << [
                    "rowNum":it?.id,
                    "dailyMovementId": it.id,
                    "movementType"   : messageSource?.getMessage("MovementType." + it?.movementType?.defaultMessage, null, null, LocaleContextHolder.locale),
                    "movementDay"    : it?.movementDay.format("yyyy-MM-dd"),
                    "total"          : it?.totalAmount,
                    "receptNo"       : it?.receptNo,
                    "note"           : it?.note,
                    "status"         : it?.status,
                    "actions"        : it.id,
                    "accountId"      : it?.account?.id,
                    "referenceId"      :it?.referenceId,
                    "movementType2"   : it?.movementType?.defaultMessage,
            ]


        }

        return dateToBeRender


    }



    def saveDailyMovementRecord(record) {

        def dailyMovement

        if(!record?.dailyMovementId)
             dailyMovement = new DailyMovement()
        else
            dailyMovement=DailyMovement.findById(record?.dailyMovementId)


        dailyMovement?.movementType= MovementType.valueOf(record?.movementType)
        dailyMovement?.movementDay= new SimpleDateFormat("yyyy-MM-dd").parse(record?.movementDay)


        if((record?.receptNo!='')&&(record?.receptNo!=null))
        dailyMovement?.receptNo= Long.parseLong(record?.receptNo)


        println " :-*-*-*-*-*-: "+ record?.totalAmount
        if((record?.totalAmount!='')&&(record?.totalAmount!=null))
        dailyMovement?.totalAmount= Long.parseLong(record?.totalAmount)

        dailyMovement?.status= MovementStatus.valueOf(record?.status)
        dailyMovement?.account= Account.get(Long.parseLong(record?.accountId))
        dailyMovement?.note= record?.note

        println "properties :: "+dailyMovement.properties
        dailyMovement.save(flush: true, failOnError: true)

        return  dailyMovement
    }


}
