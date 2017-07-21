package express.enumurations

import org.springframework.context.i18n.LocaleContextHolder as LCH

//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.MessageSource

/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 8/28/12
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public enum AccountType {

    ACCESSORIES('AccountType.ACCESSORIES','ACCESSORIES'),
    BUTCHER('AccountType.BUTCHER','BUTCHER'),
    CLOTHING('AccountType.CLOTHING','CLOTHING'),
    COMPANY('AccountType.COMPANY','COMPANY'),
    GROCER('AccountType.GROCER','GROCER'),
    PHARMACY('AccountType.PHARMACY','PHARMACY'),
    SHOP('AccountType.SHOP','SHOP'),
    SUPERMARKET('AccountType.SUPERMARKET','SUPERMARKET'),
    SWEET('AccountType.SWEET','SWEET'),
    RESTAURANT('AccountType.RESTAURANT','RESTAURANT');


    String messageKey
    String defaultMessage

    AccountType(String messageKey, String defaultMessage) {
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
    }

    public static getValueByMessageKey(messageKey) {
        def statusType
        switch (messageKey) {
            case AccountType.ACCESSORIES.messageKey:
                statusType = AccountType.ACCESSORIES
                break
            case AccountType.BUTCHER.messageKey:
                statusType = AccountType.BUTCHER
                break
            case AccountType.CLOTHING.messageKey:
                statusType = AccountType.CLOTHING
                break
            case AccountType.COMPANY.messageKey:
                statusType = AccountType.COMPANY
                break
            case AccountType.GROCER.messageKey:
                statusType = AccountType.GROCER
                break
            case AccountType.PHARMACY.messageKey:
                statusType = AccountType.PHARMACY
                break
            case AccountType.SHOP.messageKey:
                statusType = AccountType.SHOP
                break
            case AccountType.SUPERMARKET.messageKey:
                statusType = AccountType.SUPERMARKET
                break
            case AccountType.SWEET.messageKey:
                statusType = AccountType.SWEET
                break
            case AccountType.RESTAURANT.messageKey:
                statusType = AccountType.RESTAURANT
                break

        }
    }



    String toString() {
        MessageSource messageSource = ApplicationHolder.application.mainContext.getBean('messageSource')
        def messageItem = messageSource.getMessage(this.messageKey, null, this.defaultMessage, LCH.getLocale())
        return messageItem
    }
}
