package quince_it.pquince.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.services.contracts.dto.appointment.ConsultationRequestDTO;

public class TestUtil {

	
	@SuppressWarnings("deprecation")
	public static int getHours(Date endDateTime) {
		
		int hours = endDateTime.getMinutes() == 0 ? endDateTime.getHours() : endDateTime.getHours() + 1;
		if(hours > 23) hours = 0;
		return hours;
	}
	
	@SuppressWarnings("deprecation")
	public static Date getEndDate(Date endDateTime) {
		
		int hours = endDateTime.getMinutes() == 0 ? endDateTime.getHours() : endDateTime.getHours() + 1;
		long endDate = new Date(endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDate(),0,0,0).getTime();
		
		if(hours > 23) endDate += 24*60*60000;
		
		return new Date(endDate);
	}
	
	public static Staff getPharmacistAsStaff(UUID staffId, Address address) {
		return new Staff(staffId, "", "", "", "", address, "", new ArrayList<Absence>(), StaffType.PHARMACIST);
	}
	
	public static Pharmacy getPharmacy(UUID pharmacyId, Address address) {
		return new Pharmacy(pharmacyId, "", "", address, 420);
	}
	
	
	public static ConsultationRequestDTO getConsultationRequestDTO(UUID pharmacistId, Date startDateTime) {
		return new ConsultationRequestDTO(pharmacistId, startDateTime);
	}
	
	public static List<Appointment> getAppointment(Pharmacy pharmacyMock, Staff staffMock, Patient patientMock,Date startDateTime, Date endDateTime, AppointmentStatus scheduled,AppointmentType consultation, double price) {
		
		List<Appointment> app = new ArrayList<Appointment>();
		app.add(new Appointment(pharmacyMock, staffMock, patientMock, startDateTime, endDateTime, price,consultation, scheduled));
		return app;
	}

	public static ActionAndPromotion getActionAndPromotions(UUID actionId) {
		return new ActionAndPromotion(actionId, new Date(), new Date(), new Pharmacy(), 10, ActionAndPromotionType.CONSULTATIONDISCOUNT);
	}

	public static Patient getPatient(UUID patientId, Address address) {
		return new Patient(patientId, "", "", "","",address,"",true, 0, new ArrayList<Allergen>(),0);
	}
}
