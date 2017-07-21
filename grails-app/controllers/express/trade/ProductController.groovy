package express.trade

import grails.converters.JSON
import org.springframework.context.i18n.LocaleContextHolder

import java.text.SimpleDateFormat

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class ProductController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def productService
    def messageSource

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Product.list(params), model: [productCount: Product.count()]
    }

    def show(Product product) {
        respond product
    }

    def create() {
        respond new Product(params)
    }

    def productTypeahead() {

        render productService.productAutoComplete(params?.query) as JSON
    }

    def productAutoComplete() {

        render com.productTypeAhead(params)

    }

    def list() {

        respond ""
    }

    def getProductList() {
        render productService.getProductList(params) as JSON
    }

    def manageProductDetails() {



        if (params?.record) {
            def param = JSON.parse(params?.record)
            params?.modalTitle = messageSource?.getMessage("product.title.updateProductInfo", null, null, LocaleContextHolder.locale)
            def product = Product.findById(param?.productId)
            param?.defaultUnitVal=product.defaultUnit.defaultMessage
            param?.productTypeVal=product?.productType?.defaultMessage
            param?.note=product?.note
            params?.record=param
            params?.createdBy=product?.createdBy
            params?.lastUpdatedBy=product?.lastUpdatedBy
            params?.created=product?.created?.format("HH:mm dd-MM-YYYY")
            params?.lastUpdated=product?.lastUpdated?.format("HH:mm dd-MM-YYYY")


            println  product.productType.defaultMessage
        } else {
            params?.modalTitle = messageSource?.getMessage("product.title.addNewProduct", null, null, LocaleContextHolder.locale)
        }
        render template: "/templates/products/productForm"
    }
    @Transactional
    def manageProduct(){
        println " ---- manageProduct ---- "+params
        productService.manageProduct(JSON.parse(params?.product)) as JSON
        def result =  "-------------------------"
        render(contentType: 'text/json') {
            [
                    'results': result, 'status': 200
            ]
        }

    }

    @Transactional
    def save(Product product) {
        if (product == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (product.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond product.errors, view: 'create'
            return
        }

        product.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: CREATED] }
        }
    }

    def edit(Product product) {
        respond product
    }

    @Transactional
    def update(Product product) {
        if (product == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (product.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond product.errors, view: 'edit'
            return
        }

        product.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect product
            }
            '*' { respond product, [status: OK] }
        }
    }

    @Transactional
    def delete(Product product) {

        if (product == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        product.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), product.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
