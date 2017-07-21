package express.trade

import express.enumurations.PhoneType
import grails.transaction.Transactional

@Transactional
class PhoneService {

    def serviceMethod() {

    }

    @Transactional
    def createPhone(def phone) {
        def pho = "";
        pho.save(flush: true, failOnError: true)
        return pho

    }

    def delete(Phone phone) {

        if (phone == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        phone.delete flush: true
        return "Phone Is Deleted"
    }
}
