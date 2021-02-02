package quince_it.pquince.services.implementation.appointment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.users.DateRange;
import quince_it.pquince.services.contracts.dto.appointment.AppointmentPeriodResponseDTO;

public class AppointmentScheduler {
	private DateRange workTime;
	private int duration;
	private List<Appointment> scheduledAppointments;
	
	public AppointmentScheduler(DateRange workTime, List<Appointment> scheduledAppointments, int duration) {
		super();
		this.workTime = workTime;
		//this.scheduledAppointments = scheduledAppointments;
		this.duration=duration;
		this.scheduledAppointments = SortScheduledAppointment(scheduledAppointments);
	}

	private List<Appointment> SortScheduledAppointment(List<Appointment> scheduledAppointments) {
		// TODO Auto-generated method stub
		Collections.sort(scheduledAppointments, new Comparator<Appointment>() {
			  public int compare(Appointment o1, Appointment o2) {
			      return o1.getStartDateTime().compareTo(o2.getStartDateTime());
			  }
			});
		
		return scheduledAppointments;
	}

	public List<AppointmentPeriodResponseDTO> GetFreeAppointment(){
		List<AppointmentPeriodResponseDTO> freeAppointmentsPeriods = new ArrayList<AppointmentPeriodResponseDTO>();
		
        LocalDateTime startTime = workTime.getStartDateTime();
        LocalDateTime endTime;
        //da li postoji
        if(scheduledAppointments!=null) {
        	for (Appointment appointment : scheduledAppointments)
            {
                 endTime = convertToLocalDateTimeViaInstant(appointment.getStartDateTime());
                 freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
                 startTime = convertToLocalDateTimeViaInstant(appointment.getEndDateTime());
            }
        }
        
        endTime = workTime.getEndDateTime();
        freeAppointmentsPeriods.addAll(FillFreeInterval(startTime, endTime));
        return freeAppointmentsPeriods;
	}

	private Collection<? extends AppointmentPeriodResponseDTO> FillFreeInterval(LocalDateTime startTime, LocalDateTime endTime) {
		List<AppointmentPeriodResponseDTO> freeAppointments = new ArrayList<AppointmentPeriodResponseDTO>();
		
		while (Duration.between(startTime, endTime).toMinutes() >= Duration.ofMinutes(duration).toMinutes())
        {
            DateRange dateRange = new DateRange(startTime, startTime.plusMinutes(duration));
            AppointmentPeriodResponseDTO freeAppointmentPeriod = new AppointmentPeriodResponseDTO(convertToDateViaInstant(dateRange.getStartDateTime()),convertToDateViaInstant(dateRange.getEndDateTime()));
            startTime = convertToLocalDateTimeViaInstant(freeAppointmentPeriod.getEndDate());
            freeAppointments.add(freeAppointmentPeriod);
        }
        return freeAppointments;
	}
	
	private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	    	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	    	      .toInstant());
	    }

	
	
	 
}
