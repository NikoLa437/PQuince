package quince_it.pquince.services.implementation.users.mail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;

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

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + patient.getName() + ",</p>" +
					"<p>You registered an account on PQuince portal, before being able to use your account you need to verify that this is your email address by clicking here:</p>"
					+ "<a href=\"http://localhost:8081/api/users/activate-patient/" + patient.getId()
					+ "\">Verify your account</a>.</p>" + "<p>Kind Regards, PQuince</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(patient.getEmail());
		helper.setSubject("Activate account");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendQRBuyDrugsNotificaitionAsync(User user)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + user.getName() + ",</p>" +
					"<p>You successfully bought drugs with QR code!</p>"
					+ "<p>Kind Regards, PQuince</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(user.getEmail());
		helper.setSubject("QR drugs bought");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendComplaintReplyAsync(String email, String reply)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Dir sir/madam,</p>" +
					"<p>This is reply for your complaint: </p>" + 
					reply +"<p>Feel free to write us anytime! </p>"
					+ "<p>Kind Regards, PQuince</p>"; 
		helper.setText(htmlMsg, true);
		helper.setTo(email);
		helper.setSubject("Complaint reply");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendAppointmentReservationNotificationAsync(Appointment appointment, String atWho) throws MessagingException {
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatterTime = new SimpleDateFormat("HH:mm");

		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + appointment.getPatient().getName() + ",</p>" + "<p>You successfully reserved appointment at " + atWho + " " + appointment.getStaff().getName() + " "
						+ appointment.getStaff().getSurname() + ", for date " + formatter.format(appointment.getStartDateTime()) + ", at " + formatterTime.format(appointment.getStartDateTime()) + " o'clock."+
						"</p> <p>Kind Regards, PQuince</p>";

		helper.setText(htmlMsg, true);
		helper.setTo(appointment.getPatient().getEmail());
		helper.setSubject("Appointment reservation");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);
	}
	
	@Async
	public void sendDrugReservationNotificaitionAsync(DrugReservation drugReservation)
			throws MailException, InterruptedException, MessagingException {
		System.out.println("Slanje emaila...");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<p>Hello " + drugReservation.getPatient().getName() + ",</p>" +
					"<p>You reservation code for drug " + drugReservation.getDrugInstance().getName() + " is:" + drugReservation.getId() +".</p>"
				  + "<p>Yor reservation lasts until "+ new SimpleDateFormat("yyyy-MM-dd HH:mm").format(drugReservation.getEndDate()) + ", and you can pick up your drugs at "
				  + drugReservation.getPharmacy().getName() + " pharmacy at "+ drugReservation.getPharmacy().getAddress().getStreet() + ", "
				  + drugReservation.getPharmacy().getAddress().getCity() + ", " 
				  + drugReservation.getPharmacy().getAddress().getCountry()
				  + " address.</p>"+"<p>Kind Regards, PQuince</p>"; 
		
		helper.setText(htmlMsg, true);
		helper.setTo(drugReservation.getPatient().getEmail());
		helper.setSubject("Activate account");
		helper.setFrom(env.getProperty("spring.mail.username"));
		javaMailSender.send(mimeMessage);

		System.out.println("Email poslat!");
	}
	
}
