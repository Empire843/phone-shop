package ptit.tttn.phoneshop.services;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String text) ;
}
