import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch } from "react-router-dom";
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

function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
				<Link
					exact
					to="/staff-profile"
					path="/staff-profile"
					component={StaffProfilePage}
				/>
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
				<Link exact to="/drugs" path="/drugs" component={DrugReservation} />
				<Link exact to="/pharmacy" path="/pharmacy" component={PharmacyProfilePage} />
				<Link
					exact
					to="/reserve-appointment"
					path="/reserve-appointment"
					component={Appointments}
				/>
				<Link
					exact
					to="/patients-appointments"
					path="/patients-appointments"
					component={PatientsAppointments}
				/>
				<Link
					exact
					to="/dermatologist-history"
					path="/dermatologist-history"
					component={HistoryDermatologistAppointments}
				/>
				<Link
					exact
					to="/dermatologists"
					path="/dermatologists"
					component={DermatologistsPage}
				/>
				<Link exact to="/patients" path="/patients" component={ObservePatientsPage} />
			</Switch>
		</Router>
	);
}

export default App;
