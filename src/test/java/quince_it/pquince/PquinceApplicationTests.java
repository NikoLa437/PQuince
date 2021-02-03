package quince_it.pquince;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import quince_it.pquince.entities.drugs.DrugStorage;
import quince_it.pquince.entities.users.Patient;
import quince_it.pquince.repository.drugs.DrugStorageRepository;
import quince_it.pquince.repository.users.PatientRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PquinceApplicationTests {
	
	@Autowired
	private DrugStorageRepository drugStorageRepository;
	
	@Autowired
	private PatientRepository patientRepository;

	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void twoRequestForReduceAmountOfDrugInDrugStorageInARow() throws Throwable {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {

			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");
		        
		        DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(UUID.fromString("dac2b818-5838-11eb-ae93-0242ac130002"), UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002"));
				drugStorage.reduceAmount(20);

				try { Thread.sleep(3000); } catch (InterruptedException e) {}
				
				drugStorageRepository.save(drugStorage);	

			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");

				DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(UUID.fromString("dac2b818-5838-11eb-ae93-0242ac130002"), UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002"));
				drugStorage.reduceAmount(20);
				drugStorageRepository.save(drugStorage);	

			}
		});
		try {
		    future1.get(); 
		} catch (ExecutionException e) {
		    System.out.println("Exception from thread " + e.getCause().getClass());
		    throw e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}
	
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void twoRequestForAddAmountOfDrugInDrugStorageInARow() throws Throwable {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {

			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");

		        DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(UUID.fromString("dac2b818-5838-11eb-ae93-0242ac130002"), UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002"));
				drugStorage.addAmount(5);

				try { Thread.sleep(3000); } catch (InterruptedException e) {}
				
				drugStorageRepository.save(drugStorage);
				
				
			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");

				DrugStorage drugStorage = drugStorageRepository.findByDrugIdAndPharmacyId(UUID.fromString("dac2b818-5838-11eb-ae93-0242ac130002"), UUID.fromString("cafeddee-56cb-11eb-ae93-0242ac130002"));
				drugStorage.addAmount(5);					
				drugStorageRepository.save(drugStorage);

			}
		});
		try {
		    future1.get();
		} catch (ExecutionException e) {
		    System.out.println("Exception from thread " + e.getCause().getClass());
		    throw e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}
	
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void concurentRequestForAddingPenaltyAndUpdatePatientsProfile() throws Throwable {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new Runnable() {

			@Override
			public void run() {
		        System.out.println("Startovan Thread 1");

		        Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
				patient.addPenalty(1);

				try { Thread.sleep(3000); } catch (InterruptedException e) {}
				
				patientRepository.save(patient);
				
				
			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
		        System.out.println("Startovan Thread 2");

		        Patient patient = patientRepository.findById(UUID.fromString("22793162-52d3-11eb-ae93-0242ac130002")).get();
				patient.setName("Test");
				patient.setPhoneNumber("Test");
				patient.setSurname("Test");
				patientRepository.save(patient);

			}
		});
		try {
		    future1.get();
		} catch (ExecutionException e) {
		    System.out.println("Exception from thread " + e.getCause().getClass());
		    throw e.getCause();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executor.shutdown();
	}

}
