import React, { Component } from "react";
import Header from "../components/Header";
import TopBar from "../components/TopBar";
import Axios from "axios";
import { BASE_URL } from "../constants.js";
import { Button } from "react-bootstrap";

class LoginPage extends Component {
	state = {
		email: "",
		password: "",
	};
	handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};
	
	handleLogin = () => {
		let loginDTO = { username: this.state.email, password: this.state.password };
		console.log(loginDTO);
		Axios.post(BASE_URL + "/auth/login", loginDTO)
			.then((res) => {
				console.log("Success");
				console.log(res.data);
				localStorage.setItem("keyToken", res.data.accessToken);
           	    localStorage.setItem("keyRole", res.data.role);
			})
			.catch((err) => {
				console.log(err);
			});
	};
	render() {
		return (
			<React.Fragment>
				<TopBar />
				<Header />

				<div className="container" style={{ marginTop: "10%" }}>
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
								</div>

								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<input
											placeholder="Password"
											class="form-control"
											id="password"
											type="password"
											onChange={this.handlePasswordChange}
											value={this.state.password}
										/>
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
			</React.Fragment>
		);
	}
}

export default LoginPage;
