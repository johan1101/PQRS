package Clases;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * EnvioCorreo
 * @author Johan- María
 */
public class EnvioCorreo {
    /**
     * Atributos
     */
    private static String emailFrom = "";
    private static String passwordFrom = "";
    private String emailTo;
    private String subject;
    private String content;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;
    
    /**
     * Constructor
     */
    public EnvioCorreo() {
        mProperties = new Properties();
    }
    /**
     * Getter and Setter
     */
    
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public void setPasswordFrom(String passwordFrom) {
        this.passwordFrom = passwordFrom;
    }

    public void setTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }
    /**
     * Metodo para enviar el email
     * @throws MessagingException 
     */
    public void sendEmail() throws MessagingException {
        createEmail();

        Transport mTransport = mSession.getTransport("smtp");
        mTransport.connect(emailFrom, passwordFrom);
        mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
        mTransport.close();
    }
    /**
     * Metodo para crear el email
     * @throws MessagingException 
     */
    private void createEmail() throws MessagingException {
        // Configurar propiedades del servidor SMTP
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        // Obtener la sesión
        mSession = Session.getDefaultInstance(mProperties);

        // Crear el mensaje de correo
        mCorreo = new MimeMessage(mSession);
        mCorreo.setFrom(new InternetAddress(emailFrom));
        mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        mCorreo.setSubject(subject);
        mCorreo.setText(content, "ISO-8859-1", "html");
    }
}

