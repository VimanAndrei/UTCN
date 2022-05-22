package Notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsNotifier implements Notifier {
    private Notifier wrappedSms;

    public SmsNotifier(Notifier wrappedSms) {
        this.wrappedSms = wrappedSms;
    }

    @Override
    public void send(String message) {
        final String ACCOUNT_SID = "AC9b4e1ea1d7e60611cad9d5564484a4f7";
        final String AUTH_TOKEN = "f860eace94fada02c0d3b5475a5bccc0";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message mess = Message.creator(
                new PhoneNumber("+40730361848"),
                new PhoneNumber("+12562545288"),
                message).create();

        System.out.println(mess.getSid());
    }
}
