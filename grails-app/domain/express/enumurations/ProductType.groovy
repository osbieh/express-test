package express.enumurations

//import org.codehaus.groovy.grails.commons.ApplicationHolder
/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 9/27/12
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public enum ProductType {
    PLASTIC('productType.PLASTIC','PLASTIC'),
    CARTON('productType.CARTON','CARTON'),
    FOAM('productType.FOAM','FOAM'),
    ALUMINUM('productType.ALUMINUM','ALUMINUM'),
    TISSUE('productType.TISSUE','Tissue'),
    ;


    String messageKey
    String defaultMessage

    ProductType(String messageKey, String defaultMessage) {
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
    }
    public static getValueByMessageKey(messageKey) {
        def statusType
        switch (messageKey) {

        }
    }
//    String toString() {
//        MessageSource messageSource = ApplicationHolder.application.mainContext.getBean('messageSource')
//        def messageItem = messageSource.getMessage(this.messageKey, null, this.defaultMessage, LocaleContextHolder.getLocale())
//        return messageItem
//    }
}
