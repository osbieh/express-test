package express.enumurations

//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.ApplicationContext
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder


/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 8/28/12
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
public enum PhoneType {
    PHONE('PhoneType.PHONE', 'PHONE'),
    MOBILE('PhoneType.MOBILE', 'MOBILE'),
    FAX('PhoneType.FAX', 'FAX');
    String messageKey
    String defaultMessage

    PhoneType(String messageKey, String defaultMessage) {
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
    }

    public static getValueByMessageKey(messageKey) {
        def statusType
        switch (messageKey) {
            case PhoneType.PHONE.messageKey:
                statusType = PhoneType.PHONE
                break
            case PhoneType.MOBILE.messageKey:
                statusType = PhoneType.MOBILE
                break
            case PhoneType.FAX.messageKey:
                statusType = PhoneType.FAX
                break
        }
    }

    String toString() {
        ApplicationContext a;

        MessageSource messageSource =  a.grailsApplication.mainContext.getBean('messageSource')
        def messageItem = messageSource.getMessage(this.messageKey, null, this.defaultMessage, LocaleContextHolder.getLocale())
        return messageSource.getMessage(this.messageKey, null, this.defaultMessage, LocaleContextHolder.getLocale())
    }

}
