package express.trade

import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class ChequeService {

    def serviceMethod() {

    }
    def getChequesList(params){
        def dataToBeRender = [:]
        dataToBeRender.aaData = []

        def paymentDetailsList = Cheque.createCriteria().list {

        }
        paymentDetailsList.each {
            dataToBeRender.aaData << [
                    "rowNum"     :it?.id,
                    "chequeNo"   :it?.chequeNo,
                    "bankName"   :it?.bankName,
                    "dueDate"    :it?.dueDate.format("yyyy-MM-dd"),
                    "amount"     :it?.amount,
                    "insertDate" :it?.created.format("yyyy-MM-dd"),
                    "actions"    :it?.id,
            ]
        }
        return dataToBeRender

    }

}
