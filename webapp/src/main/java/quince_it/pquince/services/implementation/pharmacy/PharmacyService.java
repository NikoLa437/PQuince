package quince_it.pquince.services.implementation.pharmacy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quince_it.pquince.entities.appointment.Appointment;
import quince_it.pquince.entities.appointment.AppointmentType;
import quince_it.pquince.entities.drugs.DrugReservation;
import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.drugs.EReceipt;
import quince_it.pquince.entities.drugs.EReceiptItems;
import quince_it.pquince.entities.drugs.EReceiptStatus;
import quince_it.pquince.entities.pharmacy.ActionAndPromotion;
import quince_it.pquince.entities.pharmacy.ActionAndPromotionType;
import quince_it.pquince.entities.pharmacy.Pharmacy;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.entities.users.User;
import quince_it.pquince.repository.appointment.AppointmentRepository;
import quince_it.pquince.repository.drugs.DrugPriceForPharmacyRepository;
import quince_it.pquince.repository.drugs.DrugReservationRepository;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.drugs.EReceiptItemsRepository;
import quince_it.pquince.repository.drugs.EReceiptRepository;
import quince_it.pquince.repository.pharmacy.ActionAndPromotionsRepository;
import quince_it.pquince.repository.pharmacy.PharmacyRepository;
import quince_it.pquince.repository.users.PatientRepository;
import quince_it.pquince.repository.users.UserRepository;
import quince_it.pquince.services.contracts.dto.drugs.PharmacyERecipeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.DrugsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.EditPharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.ExaminationsStatisticsDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyDrugPriceDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyFiltrationDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradeDTO;
import quince_it.pquince.services.contracts.dto.pharmacy.PharmacyGradePriceDTO;
import quince_it.pquince.services.contracts.identifiable_dto.IdentifiableDTO;
import quince_it.pquince.services.contracts.interfaces.appointment.IAppointmentService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyFeedbackService;
import quince_it.pquince.services.contracts.interfaces.pharmacy.IPharmacyService;
import quince_it.pquince.services.contracts.interfaces.users.ILoyaltyProgramService;
import quince_it.pquince.services.contracts.interfaces.users.IUserService;
import quince_it.pquince.services.implementation.users.mail.EmailService;
import quince_it.pquince.services.implementation.util.LocationUtil;
import quince_it.pquince.services.implementation.util.pharmacy.PharmacyMapper;

@Service
public class PharmacyService implements IPharmacyService {


	@Autowired 
	private EReceiptItemsRepository eReceiptItemsRepository;
	
	@Autowired 
	private EReceiptRepository eReceiptRepository;
	
	@Autowired
	private PharmacyRepository pharmacyRepository;
	
	@Autowired
	private IPharmacyFeedbackService pharmacyFeedbackService;
	
	@Autowired
	private IAppointmentService appointmentService;
	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private DrugReservationRepository drugReservationRepository;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILoyaltyProgramService loyalityProgramService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private  EmailService emailService;
	
	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
	@Autowired
	private DrugPriceForPharmacyRepository drugPriceForPharmacyRepository;
	
	@Autowired
	private ActionAndPromotionsRepository actionAndPromotionsRepository;
	
	@Override
	public List<IdentifiableDTO<PharmacyDTO>> findAll() {
		List<IdentifiableDTO<PharmacyDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(p)));
	
		return pharmacies;
	}

	@Override
	public IdentifiableDTO<PharmacyDTO> findById(UUID id) {
		return PharmacyMapper.MapPharmacyPersistenceToPharmacyIdentifiableDTO(pharmacyRepository.getOne(id));
	}
	
	@Override
	public IdentifiableDTO<PharmacyGradeDTO> findByIdWithGrade(UUID id) {
		return MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(pharmacyRepository.getOne(id));
	}

	@Override
	public UUID create(PharmacyDTO entityDTO) {
		// TODO Auto-generated method stub
		Pharmacy pharmacy = new Pharmacy(entityDTO.getName(), entityDTO.getDescription(),
						entityDTO.getAddress(), entityDTO.getConsultationPrice());

		pharmacyRepository.save(pharmacy);
		
		return pharmacy.getId();
	}

	@Override
	public void update(PharmacyDTO entityDTO, UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(UUID id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGrades() {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findAll().forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}
	
	public IdentifiableDTO<PharmacyGradeDTO> MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();
		
		double avgGrade = getAvgGradeForPharmacy(pharmacy.getId());

		return new IdentifiableDTO<PharmacyGradeDTO>(pharmacy.getId(), new PharmacyGradeDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),avgGrade,pharmacy.getConsultationPrice()));
	}
	
	
	private double getAvgGradeForPharmacy(UUID pharmacyId) {
		double avgGrade;
		
		try {
			avgGrade = pharmacyFeedbackService.findAvgGradeForPharmacy(pharmacyId);
		} catch (AopInvocationException e) {
			avgGrade = 0.0;
		}
		
		return avgGrade;
	}
	
	public IdentifiableDTO<PharmacyGradePriceDTO> MapPharmacyPersistenceToPharmacyGradePriceIdentifiableDTO(Pharmacy pharmacy){
		if(pharmacy == null) throw new IllegalArgumentException();
		
		UUID patientId = getLoggedUserId();
		
		double avgGrade = getAvgGradeForPharmacy(pharmacy.getId());
		double getDiscountConsultationPrice = loyalityProgramService.getDiscountAppointmentPriceForPatient(pharmacy.getConsultationPrice(), AppointmentType.CONSULTATION, patientId);
		
		ActionAndPromotion action = actionAndPromotionsRepository.findCurrentActionAndPromotionForPharmacyForActionType(pharmacy.getId(), ActionAndPromotionType.CONSULTATIONDISCOUNT);
		
		if(action != null) {
			getDiscountConsultationPrice -= (action.getPercentOfDiscount()/ 100.0) * pharmacy.getConsultationPrice();
		}
	
		// TODO : DONE add discount price based on actions and benefits
		
		return new IdentifiableDTO<PharmacyGradePriceDTO>(pharmacy.getId(), new PharmacyGradePriceDTO(pharmacy.getName(), pharmacy.getAddress(), pharmacy.getDescription(),avgGrade, pharmacy.getConsultationPrice(), getDiscountConsultationPrice));
	}
	

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findByNameGradeAndDistance(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		List<IdentifiableDTO<PharmacyGradeDTO>> tempPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();

		if(pharmacyFiltrationDTO.getGradeFrom() == 0) {
			tempPharmacies = findByNameAndCity(pharmacyFiltrationDTO.getName(), pharmacyFiltrationDTO.getCity());
			for(IdentifiableDTO<PharmacyGradeDTO> pharmacy : tempPharmacies) {
				if(pharmacy.EntityDTO.getGrade() <= pharmacyFiltrationDTO.getGradeTo() || pharmacyFiltrationDTO.getGradeTo() == 0)
					pharmacies.add(pharmacy);
			}
		}
		else
			pharmacies = pharmacyFeedbackService.findByNameCityAndGrade(pharmacyFiltrationDTO);
		
		if(pharmacyFiltrationDTO.getLatitude() < -500 || pharmacyFiltrationDTO.getDistanceFrom() == pharmacyFiltrationDTO.getDistanceTo()) return pharmacies;
		
		return findByDistance(pharmacies, pharmacyFiltrationDTO);
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByDistance(List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies, PharmacyFiltrationDTO pharmacyFiltrationDTO){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> returnPharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		
		for(IdentifiableDTO<PharmacyGradeDTO> pharmacy : pharmacies) {
			double distance = LocationUtil.calculateDistance(pharmacy.EntityDTO.getAddress().getLatitude(), pharmacy.EntityDTO.getAddress().getLongitude(), pharmacyFiltrationDTO.getLatitude(), pharmacyFiltrationDTO.getLongitude());
			if(isDistanceInRange(distance, pharmacyFiltrationDTO.getDistanceFrom(), pharmacyFiltrationDTO.getDistanceTo()))
				returnPharmacies.add(pharmacy);
		}
		
		return returnPharmacies;
	}
	


	private boolean isDistanceInRange(double distance, double distanceFrom, double distanceTo) {		
		if(distanceTo == 0) distanceTo = Double.MAX_VALUE;
		
		return distance >= distanceFrom && distance <= distanceTo;
	}
	
	private List<IdentifiableDTO<PharmacyGradeDTO>> findByNameAndCity(String name, String city){
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacyRepository.findByNameAndCity(name.toLowerCase(), city.toLowerCase()).forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));

		return pharmacies;
	}


	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPrice(Date startDateTime) {
		
		long time = startDateTime.getTime();
		Date endDateTime= new Date(time + (Integer.parseInt(env.getProperty("consultation_time")) * 60000));
				
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradePriceDTO>>();
		appointmentService.findAllDistinctPharmaciesForAppointmentTime(startDateTime, endDateTime).forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradePriceIdentifiableDTO(p)));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeAscending(Date startDateTime) {

		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));

		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByGradeDescending(Date startDateTime) {

		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getGrade(), s2.EntityDTO.getGrade()));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceAscending(Date startDateTime) {
		
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getDiscountPrice(), s2.EntityDTO.getDiscountPrice()));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradePriceDTO>> findAllPharmaciesFreeForPeriodWithGradesAndPriceSortByPriceDescending(Date startDateTime) {
		
		List<IdentifiableDTO<PharmacyGradePriceDTO>> pharmacies = findAllPharmaciesFreeForPeriodWithGradesAndPrice(startDateTime);
		Collections.sort(pharmacies, (s1, s2) -> Double.compare(s1.EntityDTO.getDiscountPrice(), s2.EntityDTO.getDiscountPrice()));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {

		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getName().compareTo(p2.EntityDTO.getName())));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getName().compareTo(p2.EntityDTO.getName())));
		Collections.reverse(pharmacies);
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getAddress().getCity())));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByCityNameDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getAddress().getCity())));
		Collections.reverse(pharmacies);
	
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeAscending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		
		return pharmacies;
	}



	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllPharmaciesWithGradesByNameGradeAndDistanceSortByGradeDescending(PharmacyFiltrationDTO pharmacyFiltrationDTO) {
		
		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		pharmacies = pharmacyFiltrationDTO == null ? findAllPharmaciesWithGrades() : findByNameGradeAndDistance(pharmacyFiltrationDTO);
		
		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		Collections.reverse(pharmacies);
	
		return pharmacies;
	}

	@Override
	public void updatePharmacy(EditPharmacyDTO editPharmacyDTO) {
		Pharmacy pharmacy = pharmacyRepository.getOne(editPharmacyDTO.getPharmacyId());		
		
		pharmacy.setAddress(editPharmacyDTO.getAddress());
		pharmacy.setName(editPharmacyDTO.getName());
		pharmacy.setDescription(editPharmacyDTO.getDescription());
		pharmacy.setConsultationPrice(editPharmacyDTO.getConsultationPrice());
		
		pharmacyRepository.save(pharmacy);
	}

	@Override
	public List<IdentifiableDTO<PharmacyGradeDTO>> findAllSubscribedPharmaciesWithGrades() {

		List<IdentifiableDTO<PharmacyGradeDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyGradeDTO>>();
		UUID patientId = getLoggedUserId();
		Patient patient = patientRepository.findById(patientId).get();	
				
		patient.getPharmacies().forEach((p) -> pharmacies.add(MapPharmacyPersistenceToPharmacyGradeIdentifiableDTO(p)));
	
		return pharmacies;
	}
	
	private UUID getLoggedUserId() {
		
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String email = currentUser.getName();
		User user = userRepository.findByEmail(email);	
		
		return user.getId();
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findWithQR(UUID id) {
	
		List<EReceiptItems> items = eReceiptItemsRepository.findAllByEReceiptId(id);
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = new ArrayList<IdentifiableDTO<PharmacyDrugPriceDTO>>();
		double price;

		double avgGrade;
		for(Pharmacy p : allPharmacies) {
			if((price = allDrugsAreInPharmacy(items,p)) != -1) {
				avgGrade = getAvgGradeForPharmacy(p.getId());
				pharmacies.add(PharmacyMapper.MapPharmacyPersistenceToPharmacyDrugPriceIdentifiableDTO(p, avgGrade, price));
			}
		}
		
		return pharmacies;
	}
	
	
	private double allDrugsAreInPharmacy(List<EReceiptItems> items, Pharmacy p) {
		boolean var = false;
		double price = 0;
		double priceForDrug;
		List<DrugStorage> drugs = drugStorageRepository.findAllBPharmacyId(p.getId());
		
		for(EReceiptItems item: items) {
			var = false;
			for(DrugStorage drug: drugs) {
				if(drug.getDrugInstance().getId().equals(item.getDrugInstance().getId())) {
					if(drug.getCount() >= item.getAmount()) {
						var = true;
						priceForDrug =  loyalityProgramService.getDiscountDrugPriceForPatient(drugPriceForPharmacyRepository.findCurrentDrugPrice(drug.getDrugInstance().getId(), p.getId()), userService.getLoggedUserId());
						
						ActionAndPromotion action = actionAndPromotionsRepository.findCurrentActionAndPromotionForPharmacyForActionType(p.getId(), ActionAndPromotionType.DRUGDISCOUNT);
						if(action != null) {
							priceForDrug -= (action.getPercentOfDiscount()/ 100.0) *  drugPriceForPharmacyRepository.findCurrentDrugPrice(drug.getDrugInstance().getId(), p.getId());
						}
						price += priceForDrug;
						continue;
					}
				}
			}
			if(!var)
				return -1;
		}
		
		return price;
	}

	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameAscending(UUID id) {

		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);
		
		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getName().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getName())));
		
		return pharmacies;
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByNameGradeAndDistanceSortByNameDescending(UUID id) {

		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getName().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getName())));
		Collections.reverse(pharmacies);

		return pharmacies;
	}
	
	@Override
	@Transactional
	public UUID buyWithQR(PharmacyERecipeDTO pharmacyERecipeDTO) {
		
		List<EReceiptItems> items = eReceiptItemsRepository.findAllByEReceiptId(pharmacyERecipeDTO.geteRecipeId());
		
		for(EReceiptItems item: items) {
			DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(item.getDrugInstance().getId(), pharmacyERecipeDTO.getPharmacyId());
			drugStorage.reduceAmount(item.getAmount());
			drugStorageRepository.save(drugStorage);
		}
		
		EReceipt eReceipt = eReceiptRepository.getOne(pharmacyERecipeDTO.geteRecipeId());
		eReceipt.setStatus(EReceiptStatus.PROCESSED);
		eReceipt.setPharmacy(pharmacyRepository.getOne(pharmacyERecipeDTO.getPharmacyId()));
		eReceipt.setPrice(pharmacyERecipeDTO.getPrice());
		eReceiptRepository.save(eReceipt);

		User patient = userRepository.getOne(userService.getLoggedUserId());
		
		try {
			emailService.sendQRBuyDrugsNotificaitionAsync(patient);
		} catch (MailException | InterruptedException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByCityAscending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getAddress().getCity())));

		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByCityDescending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> (p1.EntityDTO.getPharmacy().EntityDTO.getAddress().getCity().compareTo(p2.EntityDTO.getPharmacy().EntityDTO.getAddress().getCity())));
		Collections.reverse(pharmacies);

		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByGradeDescending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		Collections.reverse(pharmacies);

		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByGradeAscending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getGrade(), p2.EntityDTO.getGrade()));
		
		return pharmacies;
	}
	
	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByPriceDescending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPrice(), p2.EntityDTO.getPrice()));
		Collections.reverse(pharmacies);

		return pharmacies;
	}

	@Override
	public List<IdentifiableDTO<PharmacyDrugPriceDTO>> findQrPharmaciesWithGradesByPriceAscending(UUID id) {
		List<IdentifiableDTO<PharmacyDrugPriceDTO>> pharmacies = findWithQR(id);

		Collections.sort(pharmacies, (p1, p2) -> Double.compare(p1.EntityDTO.getPrice(), p2.EntityDTO.getPrice()));
		
		return pharmacies;
	}

	@Override
	public IdentifiableDTO<PharmacyGradePriceDTO> findPharmacyByPharmacyId(UUID pharmacyId) {
		return MapPharmacyPersistenceToPharmacyGradePriceIdentifiableDTO(pharmacyRepository.findById(pharmacyId).get());
	}

	@Override
	public ExaminationsStatisticsDTO findStatisticsForExaminationsAndColsutations() {
		// TODO Auto-generated method stub
		UUID pharmacyId= userService.getPharmacyIdForPharmacyAdmin();
		ExaminationsStatisticsDTO examinationStatisticsDTO = new ExaminationsStatisticsDTO();

		this.calculateStatisticsForMontly(pharmacyId,examinationStatisticsDTO);
		this.calculateStatisticsForQuartals(pharmacyId,examinationStatisticsDTO);
		this.calculateStatisticsForYears(pharmacyId,examinationStatisticsDTO);
		
		return examinationStatisticsDTO;
	}

	@Override
	public DrugsStatisticsDTO findStatisticsForDrugs() {
		// TODO Auto-generated method stub
		UUID pharmacyId= userService.getPharmacyIdForPharmacyAdmin();
		DrugsStatisticsDTO drugStatisticsDTO = new DrugsStatisticsDTO();

		this.calculateDrugStatisticsForMontly(pharmacyId,drugStatisticsDTO);
		this.calculateDrugStatisticsForQuartals(pharmacyId,drugStatisticsDTO);
		this.calculateDrugStatisticsForYears(pharmacyId,drugStatisticsDTO);
		
		return drugStatisticsDTO;
	}
	

	private void calculateDrugStatisticsForYears(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date currentDate = new Date();
		
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear(),0,1), currentDate);
		drugStatisticsDTO.setThisYearValue(this.sumOfReservationsAmount(reservations));

		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear()-1,0,1), new Date(currentDate.getYear()-1,11,31));
		drugStatisticsDTO.setLastYearValue(this.sumOfReservationsAmount(reservations));

		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear()-2,0,1), new Date(currentDate.getYear()-2,11,31));
		drugStatisticsDTO.setPrecededYearValue(this.sumOfReservationsAmount(reservations));
		
	}

	private void calculateDrugStatisticsForQuartals(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		if(new Date().getMonth()<3)
			this.calculateDrugForFirstQuartal(pharmacyId, drugStatisticsDTO);
		else if(new Date().getMonth()<6)
			this.calculateDrugForSecondQuartal(pharmacyId, drugStatisticsDTO);
		else if(new Date().getMonth()<9)
			this.calculateDrugForThirdQuartal(pharmacyId, drugStatisticsDTO);
		else
			this.calculateDrugForFourthQuartal(pharmacyId, drugStatisticsDTO);
	}

	private void calculateDrugForFourthQuartal(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date dateTo= new Date();
		
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		drugStatisticsDTO.setFirstQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),3,1), new Date(dateTo.getYear(),5,30));
		
		drugStatisticsDTO.setSecondQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),6,1), new Date(dateTo.getYear(),8,30));
		
		drugStatisticsDTO.setThirdQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),9,1), dateTo);
		
		drugStatisticsDTO.setFourthQuartalValue(this.sumOfReservationsAmount(reservations));	
	}

	private void calculateDrugForThirdQuartal(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date dateTo= new Date();
		
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		drugStatisticsDTO.setFirstQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),3,1), new Date(dateTo.getYear(),5,30));
		
		drugStatisticsDTO.setSecondQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),6,1), dateTo);
		
		drugStatisticsDTO.setThirdQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		drugStatisticsDTO.setFourthQuartalValue(this.sumOfReservationsAmount(reservations));	
	}

	private void calculateDrugForSecondQuartal(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date dateTo= new Date();
		
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		drugStatisticsDTO.setFirstQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,3,1), dateTo);
		
		drugStatisticsDTO.setSecondQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,6,1), new Date(dateTo.getYear()-1,8,30));
		
		drugStatisticsDTO.setThirdQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		drugStatisticsDTO.setFourthQuartalValue(this.sumOfReservationsAmount(reservations));			
	}

	private void calculateDrugForFirstQuartal(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date dateTo= new Date();
		
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date());
		
		drugStatisticsDTO.setFirstQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,3,1), new Date(dateTo.getYear()-1,5,30));
		
		drugStatisticsDTO.setSecondQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,6,1), new Date(dateTo.getYear()-1,8,30));
		
		drugStatisticsDTO.setThirdQuartalValue(this.sumOfReservationsAmount(reservations));
		
		reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		drugStatisticsDTO.setFourthQuartalValue(this.sumOfReservationsAmount(reservations));		
	}

	private int sumOfReservationsAmount(List<DrugReservation> reservations) {
		int sum=0;
		for(DrugReservation drugReservation : reservations) {
			sum+= drugReservation.getAmount();
		}
		return sum;
	}

	private void calculateDrugStatisticsForMontly(UUID pharmacyId, DrugsStatisticsDTO drugStatisticsDTO) {
		Date dateTo= new Date();
		Date dateFrom= new Date(dateTo.getYear()-1,dateTo.getMonth()+1,1);
	
		List<DrugReservation> reservations = drugReservationRepository.findAllReservationForPharmacyInDateRange(pharmacyId,dateFrom, dateTo);
	
		
		for(DrugReservation reservation : reservations) {
			drugStatisticsDTO.incrementDrugMontlyMap(reservation.getStartDate().getMonth(),reservation.getAmount());
		}
	}

	private void calculateStatisticsForYears(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date currentDate = new Date();
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear(),0,1), currentDate);
		examinationStatisticsDTO.setThisYearValue(appointments.size());

		appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear()-1,0,1), new Date(currentDate.getYear()-1,11,31));
		examinationStatisticsDTO.setLastYearValue(appointments.size());
		
		appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(currentDate.getYear()-2,0,1), new Date(currentDate.getYear()-2,11,31));
		examinationStatisticsDTO.setPrecededYearValue(appointments.size());

	}

	private void calculateStatisticsForMontly(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date dateTo= new Date();
		Date dateFrom= new Date(dateTo.getYear()-1,dateTo.getMonth()+1,1);
	
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,dateFrom, dateTo);
	
		
		for(Appointment appointment : appointments) {
			examinationStatisticsDTO.incrementMap(appointment.getStartDateTime().getMonth());
		}
	}
	
	private void calculateStatisticsForQuartals(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		if(new Date().getMonth()<3)
			this.calculateForFirstQuartal(pharmacyId, examinationStatisticsDTO);
		else if(new Date().getMonth()<6)
			this.calculateForSecondQuartal(pharmacyId, examinationStatisticsDTO);
		else if(new Date().getMonth()<9)
			this.calculateForThirdQuartal(pharmacyId, examinationStatisticsDTO);
		else
			this.calculateForFourthQuartal(pharmacyId, examinationStatisticsDTO);

	}

	private void calculateForFourthQuartal(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date dateTo= new Date();
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		examinationStatisticsDTO.setFirstQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),3,1), new Date(dateTo.getYear(),5,30));
		
		examinationStatisticsDTO.setSecondQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),6,1), new Date(dateTo.getYear(),8,30));
		
		examinationStatisticsDTO.setThirdQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),9,1), dateTo);
		
		examinationStatisticsDTO.setFourthQuartalValue(appointments.size());
	}

	private void calculateForThirdQuartal(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date dateTo= new Date();
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		examinationStatisticsDTO.setFirstQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),3,1), new Date(dateTo.getYear(),5,30));
		
		examinationStatisticsDTO.setSecondQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),6,1), dateTo);
		
		examinationStatisticsDTO.setThirdQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		examinationStatisticsDTO.setFourthQuartalValue(appointments.size());
	}

	private void calculateForSecondQuartal(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date dateTo= new Date();
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date(dateTo.getYear(),2,31));
		
		examinationStatisticsDTO.setFirstQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,3,1), dateTo);
		
		examinationStatisticsDTO.setSecondQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,6,1), new Date(dateTo.getYear()-1,8,30));
		
		examinationStatisticsDTO.setThirdQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		examinationStatisticsDTO.setFourthQuartalValue(appointments.size());
	}

	private void calculateForFirstQuartal(UUID pharmacyId, ExaminationsStatisticsDTO examinationStatisticsDTO) {
		Date dateTo= new Date();
		
		List<Appointment> appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear(),0,1), new Date());
		
		examinationStatisticsDTO.setFirstQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,3,1), new Date(dateTo.getYear()-1,5,30));
		
		examinationStatisticsDTO.setSecondQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,6,1), new Date(dateTo.getYear()-1,8,30));
		
		examinationStatisticsDTO.setThirdQuartalValue(appointments.size());
		
	    appointments = appointmentRepository.findAllAppointmentForPharmacyInDateRange(pharmacyId,new Date(dateTo.getYear()-1,9,1), new Date(dateTo.getYear()-1,11,31));
		
		examinationStatisticsDTO.setFourthQuartalValue(appointments.size());
	}

	private Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }

	@Override
	public boolean findIfPharmacyHasQRCode(UUID pharamcyId, UUID qrID) {
		List<EReceiptItems> items = eReceiptItemsRepository.findAllByEReceiptId(qrID);
		List<Pharmacy> allPharmacies = pharmacyRepository.findAll();

		for(Pharmacy p : allPharmacies) {
			if((allDrugsAreInPharmacy(items,p)) != -1 && p.getId().equals(pharamcyId)) {
				return true;
			}
		}
		
		return false;
	}
	

}
