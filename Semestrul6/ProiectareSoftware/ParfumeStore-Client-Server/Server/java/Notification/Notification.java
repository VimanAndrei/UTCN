package Notification;

public class Notification implements Notifier{
    @Override
    public void send(String message) {
        Notifier en = new EmailNotifier(this);
        Notifier sn = new SmsNotifier(this);
        en.send(message);
        sn.send(message);
    }
}
