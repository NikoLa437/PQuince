import "./App.css";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Link, Switch } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import UserProfilePage from "./pages/UserProfilePage";
import PharmaciesPage from "./pages/PharmaciesPage";
import DrugReservation from "./pages/drug-reservation/DrugReservation";
import PatientsDrugReservations from "./pages/drug-reservation/PatientsDrugReservations";
import PharmacyProfilePage from "./pages/PharmacyProfilePage";
import Appointments from "./pages/dermatologist-appointment/Appointments";
import AdminRegisterStaff from "./pages/admin-registration/RegisterStaff";
import AdminRegisterDrug from "./pages/admin-registration/RegisterDrug";
import AdminRegisterPharmacies from "./pages/admin-registration/RegisterPharmacies";

function App() {
	return (
		<Router>
			<Switch>
				<Link exact to="/" path="/" component={HomePage} />
				<Link exact to="/login" path="/login" component={LoginPage} />
				<Link exact to="/registration" path="/registration" component={RegisterPage} />
				<Link exact to="/profile" path="/profile" component={UserProfilePage} />
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
				<Link exact to="/drugs" path="/drugs" component={DrugReservation} />
				<Link exact to="/pharmacy" path="/pharmacy" component={PharmacyProfilePage} />
				<Link
					exact
					to="/reserve-appointment"
					path="/reserve-appointment"
					component={Appointments}
				/>
			</Switch>
		</Router>
	);
}

export default App;
