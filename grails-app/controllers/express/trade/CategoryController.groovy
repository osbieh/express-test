package express.trade

import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.security.access.annotation.Secured;

@Secured(['permitAll'])
@Transactional(readOnly = true)
class CategoryController {

   def  categoryService
    def index() { }

    def banksTypeAHead() {

        render categoryService.autoCompleteBanks(params?.query) as JSON
    }

    def renderBanksAutoComplete(){

        render com.banksTypeAhead(params)

    }
}
