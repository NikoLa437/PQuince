package quince_it.pquince.services.implementation.users.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.users.Patient;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	
	//TODO: href dynamicaly from env variable
	@Async
	public void sendSignUpNotificaitionAsync(Patient patient) throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "Hello " + patient.getName() + ",\n\n" + "You registered an account on PQuince portal, before being able to use your account you need to verify that this is your email address by clicking here:\n"
				+ "<a href=\"http://localhost:8081/api/users/activate-patient/" + patient.getId() + "\">Verify your account</a>" + "\n\nKind Regards, PQuince";
		//mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
		//mimeMessage.setContent(htmlMsg, "text/html");
		helper.setText(htmlMsg, true); // Use this or above line.
		helper.setTo(patient.getEmail());
		helper.setSubject("Activate yout account");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		
		/*
		 * SimpleMailMessage mail = new SimpleMailMessage();
		 * mail.setTo(patient.getEmail());
		 * mail.setFrom(env.getProperty("spring.mail.username"));
		 * mail.setSubject("Activate yout account");
		 * 
		 * mail.setText("Hello " + patient.getName() + ",\n" +
		 * "You registered an account on PQuince portal, before being able to use your account you need to verify that this is your email address by clicking here:\n"
		 * + "<button href=\"http://localhost:8081/api/users/activate-patient/" +
		 * patient.getId() + "\">Verify your account</button>" +
		 * "\nKind Regards, PQuince"); javaMailSender.send(mail);
		 */

		System.out.println("Email poslat!");
	}


}
