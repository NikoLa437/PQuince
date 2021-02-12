import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";
import { Redirect } from "react-router-dom";
import HeadingAlert from "../components/HeadingAlert";
import getAuthHeader from "../GetHeader";
import PasswordChangeModal from "../components/PasswordChangeModal";


class LoginPage extends Component {
	state = {
		errorHeader: "",
		errorMessage: "",
		hiddenErrorAlert: true,
		email: "",
		password: "",
		redirect: false,
		emailError: "none",
		passwordError: "none",
		openPasswordModal: false,
		oldPasswordEmptyError: "none",
		newPasswordEmptyError: "none",
		newPasswordRetypeEmptyError: "none",
		newPasswordRetypeNotSameError: "none",
		errorPasswordHeader: "",
		errorPasswordMessage: "",
		hiddenPasswordErrorAlert: true
	};
	handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

	handleLogin = () => {
		this.setState({ hiddenErrorAlert: true, emailError: "none", passwordError: "none" });

		if (this.validateForm()) {
			let loginDTO = { username: this.state.email, password: this.state.password };
			console.log(loginDTO);
			Axios.post(BASE_URL + "/auth/login", loginDTO, { validateStatus: () => true })
				.then((res) => {
					if (res.status === 401) {
						this.setState({ errorHeader: "Bad credentials!", errorMessage: "Wrong username or password.", hiddenErrorAlert: false });
					} else if (res.status === 500) {
						this.setState({ errorHeader: "Internal server error!", errorMessage: "Server error.", hiddenErrorAlert: false });
					} else if (res.status === 302) {
						this.setState({ openPasswordModal: true });
					} else {
						console.log(res.data);
						localStorage.setItem("keyToken", res.data.accessToken);
						localStorage.setItem("keyRole", JSON.stringify(res.data.roles));
						localStorage.setItem("expireTime", new Date(new Date().getTime() + res.data.expiresIn).getTime());

						if (JSON.stringify(res.data.roles) === '["ROLE_PHARMACYADMIN"]') {
							Axios.get(BASE_URL + "/api/users/get-pharmacyid-for-admin", {
								headers: {
									Authorization: getAuthHeader(),
								},
							})
								.then((response) => {
									localStorage.setItem("keyPharmacyId", response.data);
								})
								.catch((err) => {
									console.log(err);
								});
						}
						this.setState({ redirect: true });
					}
				})
				.catch ((err) => {
			console.log(err);
		});
	}
};

validateForm = () => {
	if (this.state.email === "") {
		this.setState({ emailError: "inline" });
		return false;
	} else if (this.state.password === "") {
		this.setState({ passwordError: "inline" });
		return false;
	}

	return true;
};

handleCloseAlert = () => {
	this.setState({ hiddenErrorAlert: true });
};

handlePasswordModalClose = () => {
	this.setState({ openPasswordModal: false });
};

handleCloseAlertPassword = () => {
	this.setState({ hiddenPasswordErrorAlert: true });
};

changePassword = (oldPassword, newPassword, newPasswordRetype) => {
	console.log(oldPassword, newPassword, newPasswordRetype);

	this.setState({
		hiddenPasswordErrorAlert: true,
		errorPasswordHeader: "",
		errorPasswordMessage: "",
		hiddenEditInfo: true,
		oldPasswordEmptyError: "none",
		newPasswordEmptyError: "none",
		newPasswordRetypeEmptyError: "none",
		newPasswordRetypeNotSameError: "none",
		hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
	});

	if (oldPassword === "") {
		this.setState({ oldPasswordEmptyError: "initial" });
	} else if (newPassword === "") {
		this.setState({ newPasswordEmptyError: "initial" });
	} else if (newPasswordRetype === "") {
		this.setState({ newPasswordRetypeEmptyError: "initial" });
	} else if (newPasswordRetype !== newPassword) {
		this.setState({ newPasswordRetypeNotSameError: "initial" });
	} else {
		let passwordChangeDTO = { oldPassword, newPassword };
		Axios.post(BASE_URL + "/auth/change-first-password", passwordChangeDTO, {
			validateStatus: () => true
		})
			.then((res) => {
				if (res.status === 403) {
					this.setState({
						hiddenPasswordErrorAlert: false,
						errorPasswordHeader: "Bad credentials",
						errorPasswordMessage: "You entered wrong password.",
					});
				} else if (res.status === 400) {
					this.setState({
						hiddenPasswordErrorAlert: false,
						errorPasswordHeader: "Invalid new password",
						errorPasswordMessage: "Invalid new password.",
					});
				} else if (res.status === 500) {
					this.setState({
						hiddenPasswordErrorAlert: false,
						errorPasswordHeader: "Internal server error",
						errorPasswordMessage: "Server error.",
					});
				} else if (res.status === 204) {
					this.setState({
						hiddenSuccessAlert: false,
						successHeader: "Success",
						successMessage: "You successfully changed your password.",
						openPasswordModal: false,
					});
				}
				console.log(res);
			})
			.catch((err) => {
				console.log(err);
			});
	}
};

render() {
	if (this.state.redirect) return <Redirect push to="/" />;
	return (
		<React.Fragment>
			<TopBar />
			<Header />

			<div className="container" style={{ marginTop: "10%" }}>
				<HeadingAlert
					hidden={this.state.hiddenErrorAlert}
					header={this.state.errorHeader}
					message={this.state.errorMessage}
					handleCloseAlert={this.handleCloseAlert}
				/>
				<h5 className=" text-center  mb-0 text-uppercase" style={{ marginTop: "2rem" }}>
					Login
					</h5>

				<div className="row section-design">
					<div className="col-lg-8 mx-auto">
						<br />
						<form id="contactForm" name="sentMessage" novalidate="novalidate">
							<div className="control-group">
								<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
									<input
										placeholder="Email address"
										className="form-control"
										id="name"
										type="text"
										onChange={this.handleEmailChange}
										value={this.state.email}
									/>
								</div>
								<div className="text-danger" style={{ display: this.state.emailError }}>
									Email must be entered.
									</div>
							</div>

							<div className="control-group">
								<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
									<input
										placeholder="Password"
										className="form-control"
										id="password"
										type="password"
										onChange={this.handlePasswordChange}
										value={this.state.password}
									/>
								</div>
								<div className="text-danger" style={{ display: this.state.passwordError }}>
									Password must be entered.
									</div>
							</div>

							<div className="form-group">
								<Button
									style={{ background: "#1977cc", marginTop: "15px", marginLeft: "40%", width: "20%" }}
									onClick={this.handleLogin}
									className="btn btn-primary btn-xl"
									id="sendMessageButton"
									type="button"
								>
									Login
									</Button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<PasswordChangeModal
				handleCloseAlertPassword={this.handleCloseAlertPassword}
				hiddenPasswordErrorAlert={this.state.hiddenPasswordErrorAlert}
				errorPasswordHeader={this.state.errorPasswordHeader}
				errorPasswordMessage={this.state.errorPasswordMessage}
				oldPasswordEmptyError={this.state.oldPasswordEmptyError}
				newPasswordEmptyError={this.state.newPasswordEmptyError}
				newPasswordRetypeEmptyError={this.state.newPasswordRetypeEmptyError}
				newPasswordRetypeNotSameError={this.state.newPasswordRetypeNotSameError}
				show={this.state.openPasswordModal}
				changePassword={this.changePassword}
				onCloseModal={this.handlePasswordModalClose}
				header="Change password"
			/>
		</React.Fragment>
	);
}


}

export default LoginPage;
