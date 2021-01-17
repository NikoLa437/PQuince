package quince_it.pquince.services.implementation.users.mail;

import java.text.SimpleDateFormat;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.users.Patient;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;

	// TODO: href dynamicaly from env variable
	@Async
	public void sendSignUpNotificaitionAsync(Patient patient)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(patient.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Activate yout account");
		 
		mail.setText("Hello " + patient.getName() + ",\n" +
					"You registered an account on PQuince portal, before being able to use your account you need to verify that this is your email address by clicking here:\n"
					+ "<a href=\"http://localhost:8081/api/users/activate-patient/" + patient.getId()
					+ "\">Verify your account</a>" + "\n\nKind Regards, PQuince"); 
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendDrugReservationNotificaitionAsync(DrugReservation drugReservation)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(drugReservation.getPatient().getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Activate yout account");
		 
		mail.setText("Hello " + drugReservation.getPatient().getName() + ",\n\n" +
					"You reservation code for drug " + drugReservation.getDrugInstance().getName() + " is:" + drugReservation.getId() +".\n"
				  + "Yor reservation lasts until "+ new SimpleDateFormat("yyyy-MM-dd HH-mm").format(drugReservation.getEndDate()) + ", and you can pick up your drugs at "
				  + drugReservation.getPharmacy().getName() + " pharmacy at " + drugReservation.getPharmacy().getAddress() + " address."+"\n\nKind Regards, PQuince"); 
		javaMailSender.send(mail);

		System.out.println("Email poslat!");
	}

}
