package express.trade

import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class AccountBalanceService {
    def messageSource

    def serviceMethod() {

    }

    def getAccountBalanceReportData(params) {
        println "START GET AccountBalanceReportData :: " + params
        def dataToBeRender = [:]
        dataToBeRender.aaData = []
        def masterData = [:]

        def accountBalanceList = AccountBalance.createCriteria().list() {
            account {
                eq('id', Long.parseLong(params?.accountId))
            }

            order("created", "desc")
        }
        List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
        def rowNum = 1
        accountBalanceList.each {
            Map<String, Object> accountBalance = new HashMap<String, Object>();
            accountBalance.put("rowNum", rowNum++)
            accountBalance.put("movementDay", it?.movementDay.format("yyyy-MM-dd"))
            //    accountBalance.put("movementType", messageSource?.getMessage("MovementType." + it?.movementType, null, null, LocaleContextHolder.locale))

            if (it?.movementType == "ORDER") {
                    if (it?.order?.totalAmount >= 0) {
                        accountBalance.put("amount", "+" + it?.order?.totalAmount)
                        accountBalance.put("movementType", messageSource?.getMessage("MovementType." + it?.movementType, null, null, LocaleContextHolder.locale))

                    } else {
                        accountBalance.put("amount", it?.order?.totalAmount)
                        accountBalance.put("movementType", messageSource?.getMessage("MovementType.backOrder", null, null, LocaleContextHolder.locale))
                    }

            } else{
                accountBalance.put("amount", "-" + it?.payment?.totalAmount)
                accountBalance.put("movementType", messageSource?.getMessage("MovementType." + it?.movementType, null, null, LocaleContextHolder.locale))
            }


            if (it?.totalAmount > 0)
                accountBalance.put("totalAmount", "+" + it?.totalAmount)
            else
                accountBalance.put("totalAmount", "-" + it?.totalAmount)

            maps.add(accountBalance)
        }

        println " ::: " + maps
        return maps;


    }
}
