package express.trade

import express.enumurations.ProductType
import express.enumurations.Unit
import grails.transaction.Transactional
import org.springframework.context.i18n.LocaleContextHolder

@Transactional
class ProductService {
    def messageSource

    def serviceMethod() {

    }


    def productAutoComplete(q) {

        def contactList = Product.createCriteria().list() {
            like("name", '%' + q + '%') //
        }
        def result = [:];
        result.aaData = []

        contactList.each {
            result.aaData << [
                    productName: it?.name,
                    productId  : it?.id,
                    defaultUnit: it?.defaultUnit.toString(),
                    price      : it?.sellPrice

            ]
        }

        return result
    }


    def getProductList(params) {

        def dataToBeRender = [:]
        dataToBeRender.aaData = []

        def paymentDetailsList = Product.createCriteria().list {

        }
        paymentDetailsList.each {

            dataToBeRender.aaData << [
                    "rowNum"       : it?.id,
                    "productName"  : it?.name,
                    "defaultUnit"  : messageSource?.getMessage("Unit." + it?.defaultUnit.defaultMessage, null, null, LocaleContextHolder.locale),
                    "productType"  : messageSource?.getMessage("productType." + it?.productType?.defaultMessage, null, null, LocaleContextHolder.locale),
                    "purchasePrice": it?.purchasePrice,
                    "sellPrice"    : it?.sellPrice,
                    "actions"      : it?.id,
                    productId      : it?.id,
            ]
        }
        return dataToBeRender
    }


    def manageProduct(params) {
        println "*-*-*-*-"+params?.sellPrice

        def product
        if (params?.productId) {
            product = Product.findById(params?.productId)
        } else {
            product = new Product()
        }
        product?.name = params?.productName
        product?.sellPrice = Long.parseLong(params?.sellPrice)
        if(params?.purchasePrice!="")
        product?.purchasePrice = Long.parseLong(params?.purchasePrice)
        product?.productType = ProductType.valueOf(params?.productType)
        product?.defaultUnit = Unit.valueOf(params?.productUnit)
        product?.note = params?.note

        return product.save(flush: true, failOnError: true)


    }


}
