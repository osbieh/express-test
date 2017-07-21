package express.enumurations

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder

/**
 * Created with IntelliJ IDEA.
 * User: OsamaSB
 * Date: 02/03/17
 * Time: 12:12 AM
 * To change this template use File | Settings | File Templates.
 */
public enum MovementType{

    ORDER('MovementType.ORDER','ORDER'),
    PAYMENT('MovementType.PAYMENT','PAYMENT'),
    EXPENSE('MovementType.EXPENSE','EXPENSE'),
    PURCHASING('MovementType.PURCHASING','PURCHASING');



    String messageKey
    String defaultMessage

    MovementType(String messageKey, String defaultMessage) {
        this.messageKey = messageKey;
        this.defaultMessage = defaultMessage;
    }

    public static getValueByMessageKey(messageKey) {
        def statusType
        switch (messageKey) {
            case MovementType.ORDER.messageKey:
                statusType = MovementType.ORDER
                break
            case MovementType.PAYMENT.messageKey:
                statusType = MovementType.PAYMENT
                break
            case MovementType.EXPENSE.messageKey:
                statusType = MovementType.EXPENSE
                break
            case MovementType.PURCHASING.messageKey:
                statusType = MovementType.PURCHASING
                break

        }
    }



//    String toString() {
//        MessageSource messageSource = ApplicationHolder.application.mainContext.getBean('messageSource')
//        def messageItem = messageSource.getMessage(this.messageKey, null, this.defaultMessage, LocaleContextHolder.getLocale())
//        return messageItem
//    }

}