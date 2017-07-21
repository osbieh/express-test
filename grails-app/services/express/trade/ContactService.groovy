package express.trade

import express.enumurations.PhoneType
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class ContactService {

    def phoneService

    def serviceMethod() {

    }

    def getAllContact(params) {
        println "getAllContact " + params
        def dateToBeRender = [:]
        dateToBeRender.aaData = []
        def contactList = Contact.createCriteria().list {
            if (params?.contactId)
                eq("id", Long.parseLong(params?.contactId))

        }

        contactList.each {
            dateToBeRender.aaData << [
                    "contactNo"  : it?.id,
                    "contactName": it?.fullName,
                    "PhoneNo"    : it?.phones?.phoneNo,
                    "address"    : it?.addresses?.toString(),
                    "email"      : it?.email,
            ]
        }
        return dateToBeRender

    }


    def contactAutoComplete(q) {

        def contactList = Contact.createCriteria().list() {
            like("fullName", '%' + q + '%') //
        }
        def result = [:];
        result.aaData = []

        contactList.each {
            result.aaData << [
                    contactName: it?.fullName,
                    contactId  : it?.id,
                    phoneNo    :  it?.phones?.phoneNo
            ]
        }

        return result
    }


    def contactPhones(params) {

        def dateToBeRender = [:]
        dateToBeRender.aaData = []

        def contactId=null
        if (params?.masterTableId) {
            contactId= params?.masterTableId
            def contact = Contact.findById(Long.parseLong(contactId))

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

    def manageContact(params) {

        def cont = JSON.parse(params.contact)
        def contactPhones = JSON.parse(params.phones)
        def contact = null


        if (cont?.contactId) {
            contact = Contact.findById(cont?.contactId)

//            contact.phones.each {
//                phoneService.delete(it)
//            }
//
//            contact.phones=null


        } else {
            contact = new Contact()
        }
        contact.firstName = cont?.firstName
        contact.secondName = cont?.secondName
        contact.lastName = cont?.lastName!=null?cont?.lastName:""
        contact.note = cont?.note
        contact.jobtitle = cont?.jobTitle
        contact.email = cont?.email

        def listPhones = []

        contactPhones.each { phone ->

          def ph=  new Phone(isActive: phone?.isActive, isPrimary: phone?.isPrimary, phoneNo: phone?.phoneNo, phoneType: PhoneType?.valueOf(phone?.phoneType))

          //def pho=PhoneService.createPhone(map)
            listPhones.push(ph)

        }
        contact.phones = listPhones
        contact.save(flush: true, failOnError: true)

        return contact

    }


}
