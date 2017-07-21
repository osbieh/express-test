package express.enumurations

//import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 9/27/12
 * Time: 1:51 AM
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentStatus {
    NEW('Payment.NEW','NEW'),
    PENDING('Payment.PENDING','PENDING'),
    CANCELLED('Payment.CANCELLED','CANCELLED'),
    COMPLETED('Payment.COMPLETED','COMPLETED');

    String messageKey
    String defaultMessage

    PaymentStatus(String messageKey, String defaultMessage) {
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
