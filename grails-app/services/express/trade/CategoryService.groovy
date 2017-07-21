package express.trade

import grails.transaction.Transactional
import org.springframework.web.context.request.RequestContextHolder as RCH

@Transactional
class CategoryService {

    def serviceMethod() {

    }

    def categoryListByDefinition(Long definitionId){

        def params = RCH.requestAttributes.params

        def category=Category.createCriteria().list(){
            definition{
                eq("id",definitionId)
            }
        }
        def result = [:];
        def aaData = []
        category.each {
            aaData << [
                    'name': (params.lang == 'ar')?it?.nameAr:it?.nameEn,
                    'id'  : it?.id,
            ]

        }
        return aaData
    }


    def autoCompleteBanks(q){
        println q
        def banksList= Category.createCriteria().list() {
            definition{
                eq('nameEn','Banks')
            }
            like('nameAr', "%${q}%") //
        }
        println banksList
        def result = [:]
        result.aaData = []

        banksList.each {
            result.aaData << [
                    bankName: it?.nameAr,
                    bankId  : it?.id,
            ]
        }

        return result

    }

}
