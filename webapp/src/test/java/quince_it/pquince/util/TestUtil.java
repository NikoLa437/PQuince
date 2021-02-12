package quince_it.pquince.util;

import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ID;
import static quince_it.pquince.constants.Constants.PHARMACY_ID;
import static quince_it.pquince.constants.Constants.WORKTIME_START_DATE;
import static quince_it.pquince.constants.Constants.WORKTIME_END_DATE;
import static quince_it.pquince.constants.Constants.PATIENT_ID;
//import static quince_it.pquince.constants.Constants.PHARMACY_ID;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_APPOINTMENT_START_DATE_TIME;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_APPOINTMENT_END_DATE_TIME;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ABSENCE_START_DATE_TIME;
import static quince_it.pquince.constants.Constants.DERMATOLOGIST_ABSENCE_END_DATE_TIME;
import static quince_it.pquince.constants.Constants.ABSENCE_ID_FOR_APPROVE;
import static quince_it.pquince.constants.Constants.CREATED_APPOINTMENT_ID;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentStatus;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.drugs.Allergen;
import quince_it.pquince.entities.drugs.OfferStatus;
import quince_it.pquince.entities.drugs.Offers;
import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Absence;
import quince_it.pquince.entities.users.AbsenceStatus;
import quince_it.pquince.entities.users.Address;
import quince_it.pquince.entities.users.ComplaintStaff;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.Staff;
import quince_it.pquince.entities.users.StaffType;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.entities.users.WorkTime;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentCreateDTO;
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
	
	public static String json(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        
        return mapper.writeValueAsString(object);
    }

	public static Appointment CreateAppointment(Appointment app) {
		Date start = new Date(new Date().getTime() + 604800000);
		Date end = new Date(start.getTime() + 900000);
		return new Appointment(app.getPharmacy(), app.getStaff(), app.getPatient(), start , end, app.getPrice(), app.getAppointmentType(), AppointmentStatus.SCHEDULED);
	}
	
	public static User getDermatologist() {
		return new User(DERMATOLOGIST_ID, "", "", "","", null, "", true);
	}
	public static Pharmacy getPharmacy() {
		return new Pharmacy(PHARMACY_ID, "", "", null,1000);
	}
	public static List<WorkTime> getWorkTimes() {
		List<WorkTime> workTimes = new ArrayList<WorkTime>();
		workTimes.add(new WorkTime(getPharmacy(), null, WORKTIME_START_DATE, WORKTIME_END_DATE, 8, 20));
		return workTimes;
	}
	public static List<Offers> getOffers() {
		List<Offers> offers = new ArrayList<Offers>();
		Date dateToDelivery = new Date();
		Offers offer = new Offers(dateToDelivery, 2000.0, OfferStatus.WAITING);
		offers.add(offer);
		return offers;
	}
	
	public static Offers getOffer() {
		Date dateToDelivery = new Date();
		return new Offers(dateToDelivery, 2000.0, OfferStatus.WAITING);
	}
	
	public static ComplaintStaff getComplaintStaff() {
		return new ComplaintStaff(new Staff(), new Patient(), "Test", "Stefan", "Stefic", "dermathologist","mail@example.com");
	}

	public static ComplaintStaff getComplaintStaffWithReply() {
		ComplaintStaff complaintStaff =  new ComplaintStaff(new Staff(), new Patient(), "Test", "Stefan", "Stefic", "dermathologist","mail@example.com");
		complaintStaff.setReply("TEST");
		
		return complaintStaff;
	}

	public static ComplaintStaff getSavedComplaint() {
		ComplaintStaff complaintStaff =  new ComplaintStaff(new Staff(), new Patient(), "Test", "Stefan", "Stefic", "dermathologist","mail@example.com");
		complaintStaff.setReply("TEST");
		
		return complaintStaff;
	}
	
	public static Staff getDermatologistForWorkTime() {
		return new Staff(DERMATOLOGIST_ID, "", "", "","", null, "", null,StaffType.DERMATOLOGIST);
	}

	public static AppointmentCreateDTO getAppointmentCreateDTO() {
		return new AppointmentCreateDTO(DERMATOLOGIST_ID,PHARMACY_ID,PATIENT_ID,AppointmentStatus.CREATED,DERMATOLOGIST_APPOINTMENT_START_DATE_TIME,DERMATOLOGIST_APPOINTMENT_END_DATE_TIME,1500);
	}
	
	public static List<Absence> getAbsenceForCreateAppointment(){
		List<Absence> absences = new ArrayList<Absence>();

		absences.add(new Absence(ABSENCE_ID_FOR_APPROVE,getDermatologistForWorkTime(),getPharmacy(),DERMATOLOGIST_ABSENCE_START_DATE_TIME,DERMATOLOGIST_ABSENCE_END_DATE_TIME,AbsenceStatus.ACCEPTED,""));
		
		return absences;
	}
	
	
	public static Absence GetAbsenceForApprove() {
		return new Absence(ABSENCE_ID_FOR_APPROVE,getDermatologistForWorkTime(),getPharmacy(),DERMATOLOGIST_ABSENCE_START_DATE_TIME,DERMATOLOGIST_ABSENCE_END_DATE_TIME,AbsenceStatus.WAIT,"");
	}


	@SuppressWarnings("deprecation")
	public static List<Appointment> getAppointmentsForAbsenceReject() {
		List<Appointment> appointments = new ArrayList<Appointment>();
		appointments.add(new Appointment(CREATED_APPOINTMENT_ID,getPharmacy(),getDermatologistForWorkTime(),getPatient(UUID.randomUUID(),null),new Date(2020,1,20,10,00),new Date(2020,1,20,10,30),1500,AppointmentType.EXAMINATION,AppointmentStatus.SCHEDULED));
		return appointments;
	}
	

}
