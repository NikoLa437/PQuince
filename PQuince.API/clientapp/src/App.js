import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserProfilePage from "./pages/UserProfilePage";
import StaffProfilePage from "./pages/StaffProfilePage";
import PharmaciesPage from "./pages/PharmaciesPage";
import DrugReservation from "./pages/drug-reservation/DrugReservation";
import PatientsDrugReservations from "./pages/drug-reservation/PatientsDrugReservations";
import PharmacyProfilePage from "./pages/PharmacyProfilePage";
import Appointments from "./pages/dermatologist-appointment/Appointments";
import AdminRegisterStaff from "./pages/admin-registration/RegisterStaff";
import AdminRegisterDrug from "./pages/admin-registration/RegisterDrug";
import AdminRegisterPharmacies from "./pages/admin-registration/RegisterPharmacies";
import PatientsAppointments from "./pages/dermatologist-appointment/PatientsAppointments";
import HistoryDermatologistAppointments from "./pages/appointment-history/HistoryDermatologistAppointments";
import PatientsDrugReservationHistory from "./pages/drug-reservation/PatientsDrugReservationHistory";
import DermatologistsPage from "./pages/dermatologist/DermatologistsPage";
import ObservePatientsPage from "./pages/ObservePatientsPage";
import PatientProfilePage from "./pages/PatientProfilePage";
import ScheduleAppointmentPage from "./pages/dermatologist-appointment/ScheduleAppointmentPage";
import ConsultationTimeSelectPage from "./pages/pharmacist-appointment/ConsultationTimeSelectPage";
import AdminComplaints from "./pages/complaints/AdminComplaints";
import LoyaltyProgram from "./pages/loyalty-program/LoyaltyProgram";
import ObservePatientsCosultation from "./pages/pharmacist-appointment/ObservePatientsCosultation";
import ObservePatientsCosultationHistory from "./pages/pharmacist-appointment/ObservePatientsConsultationHistory";
import DermatologistsPageForPatient from "./pages/dermatologist/DermatologistPageForPatient";

function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
				<Link exact to="/patient-profile" path="/patient-profile" component={PatientProfilePage} />
				<Link exact to="/staff-profile" path="/staff-profile" component={StaffProfilePage} />
				<Link exact to="/pharmacies" path="/pharmacies" component={PharmaciesPage} />
				<Link exact to="/admin-register-staff" path="/admin-register-staff" component={AdminRegisterStaff} />
				<Link exact to="/admin-register-drug" path="/admin-register-drug" component={AdminRegisterDrug} />
				<Link exact to="/admin-register-pharmacies" path="/admin-register-pharmacies" component={AdminRegisterPharmacies} />
				<Link
					exact
					to="/drugs-reservation"
					path="/drugs-reservation"
					component={PatientsDrugReservations}
				/>
				<Link
					exact
					to="/drugs-reservation-history"
					path="/drugs-reservation-history"
					component={PatientsDrugReservationHistory}
				/>
				<Link exact to="/drugs-reservation" path="/drugs-reservation" component={PatientsDrugReservations} />
				<Link exact to="/drugs-reservation-history" path="/drugs-reservation-history" component={PatientsDrugReservationHistory} />
				<Link exact to="/drugs" path="/drugs" component={DrugReservation} />
				<Link exact to="/admin-complaints" path="/admin-complaints" component={AdminComplaints} />
				<Link exact to="/patients-appointments" path="/patients-appointments" component={PatientsAppointments} />
				<Link exact to="/dermatologist-history" path="/dermatologist-history" component={HistoryDermatologistAppointments} />
				<Link exact to="/dermatologists" path="/dermatologists" component={DermatologistsPage} />
				<Link exact to="/dermatologists-for-patient" path="/dermatologists-for-patient" component={DermatologistsPageForPatient} />
				<Link exact to="/patients" path="/patients" component={ObservePatientsPage} />
				<Link exact to="/schedule-appointment" path="/schedule-appointment" component={ScheduleAppointmentPage} />
				<Link exact to="/schedule-consultation" path="/schedule-consultation" component={ConsultationTimeSelectPage} />
				<Link exact to="/loyalty-program" path="/loyalty-program" component={LoyaltyProgram} />
				<Link exact to="/observe-consultations" path="/observe-consultations" component={ObservePatientsCosultation} />
				<Link exact to="/observe-consultations-history" path="/observe-consultations-history" component={ObservePatientsCosultationHistory} />
				<Route path="/pharmacy/:id" children={<PharmacyProfilePage />} />
				<Route path="/reserve-appointment/:id" children={<Appointments />} />


			</Switch>
		</Router>
	);
}

export default App;
