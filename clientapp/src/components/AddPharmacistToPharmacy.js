import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import DatePicker from "react-datepicker";
import getAuthHeader from "../GetHeader";
import HeadingAlert from "../components/HeadingAlert";
import ModalDialog from "../components/ModalDialog";
import { YMaps, Map } from "react-yandex-maps";
import { Redirect } from "react-router-dom";
import HeadingSuccessAlert from "../components/HeadingSuccessAlert";

const mapState = {
	center: [44, 21],
	zoom: 8,
	controls: [],
};
class AddPharmacistToPharmacy extends Component {
    state = {
        pharmacists:[],
        showAddWorkTime:false,
        pharmacistIdToAdd:'',
        modalSize:'lg',    
        selectedStartDate:new Date(),
        selectedEndDate:new Date(),
        timeFrom:1,
        timeTo:2,    
        pharmacyId:'',
        email: "",
		password: "",
		name: "",
        surname: "",
		address: "",
		openModalData: false,
		phoneNumber: "",
		emailError: "none",
		passwordError: "none",
		nameError: "none",
		surnameError: "none",
		addressError: "none",
		phoneError: "none",
		emailNotValid: "none",
		openModal: false,
        selectValue:"dermathologist",
        hiddenSuccessAlert: true,
		successHeader: "",
		successMessage: "",
		hiddenFailAlert: true,
		failHeader: "",
		failMessage: "",
        userToAdd:''

    }

    
	constructor(props) {
		super(props);
		this.addressInput = React.createRef();
	}

    onYmapsLoad = (ymaps) => {
		this.ymaps = ymaps;
		new this.ymaps.SuggestView(this.addressInput.current, {
			provider: {
				suggest: (request, options) => this.ymaps.suggest(request),
			},
		});
	}; 
    componentDidMount() {
    }

    onAddClick = (id) =>{
        this.setState({
            showAddWorkTime: true,
            pharmacistIdToAdd:id,
            modalSize:'md'
        });
    }

    validateDTO = (wtDTO) =>{
        if(wtDTO.startTime < wtDTO.endTime){
            return false
        }

        return true;
    }

    validateDTO = (wtDTO) =>{
        if(wtDTO.startTime < wtDTO.endTime){
            return false
        }

        return true;
    }

    handleAdd = () => {
        let addPharmacistToPharmacyDTO = {
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo,
            email: this.state.email,
            name: this.state.name,
            surname: this.state.surname,
            address: this.state.userToAdd.address,
            phoneNumber: this.state.phoneNumber,
            password: "test",
        };

        let workTimeDTO = {
            forPharmacy : this.props.forPharmacy,
            forStaff: this.props.forStaff, 
            pharmacyName: '', 
            startDate: this.state.selectedStartDate, 
            endDate:this.state.selectedEndDate,
            startTime: this.state.timeFrom, 
            endTime:this.state.timeTo
        };
        if(this.validateDTO(workTimeDTO)){
            this.setState({
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Time from must be less than time to"});
        }else{
            Axios
        .post(BASE_URL + "/auth/signup-pharmacists", addPharmacistToPharmacyDTO, {
            headers: { Authorization: getAuthHeader() },
        }).then((res) =>{
            console.log(res.data);
            this.props.updatePharmacist();
            this.setState({showAddWorkTime: false, modalSize:'lg'});
            this.setState({
                email: "",
                password: "",
                name: "",
                surname: "",
                address: "",
                phoneNumber:''
            })
            this.handleClickOnClose();
        }).catch((err) => {
            alert('Nije moguce dodati farmaceuta, email vec postoji');
        });
        }


    }

    handleBack = (event) =>{
        this.setState({showAddWorkTime: false,modalSize:'lg'});
    }

    handleStartDateChange = (date) => {
        this.setState({
            selectedStartDate:date,
        });

        if(date>this.state.selectedEndDate){
            this.setState({
                selectedEndDate:date,
            }); 
        }
    }


    handleEndDateChange = (date) => {
        this.setState({selectedEndDate:date});
    }

    handleTimeFromChange= (event) => {
        if(event.target.value > 23){
            this.setState({timeFrom:23});
        }else if(event.target.value < 1){
            this.setState({timeFrom:1});
        }else{
            this.setState({timeFrom:event.target.value});
        }
        

    }

    handleTimeToChange = (event) => {
        if(event.target.value > 24){
            this.setState({timeTo:24});
        }else if(event.target.value < 1){
            this.setState({timeTo:1});
        }else{
            this.setState({timeTo:event.target.value});
        }      
}

    handleClickOnClose = () => {
        this.setState({
            showAddWorkTime: false, 
            modalSize:'lg',
            selectedStartDate:new Date(),
            selectedEndDate:new Date(),
            timeFrom:1,
            timeTo:2,
        });
        this.props.onCloseModal();
    }

    handleEmailChange = (event) => {
		this.setState({ email: event.target.value });
	};
	handlePharmacyChange = (event) => {
		this.setState({ pharmacy: event.target.value });
	};
	
	handlePasswordChange = (event) => {
		this.setState({ password: event.target.value });
	};

	handleNameChange = (event) => {
		this.setState({ name: event.target.value });
	};

	handleSurnameChange = (event) => {
		this.setState({ surname: event.target.value });
	};

	handleAddressChange = (event) => {
		this.setState({ address: event.target.value });
	};

	handlePhoneNumberChange = (event) => {
		this.setState({ phoneNumber: event.target.value });
	};


    validateForm = (userDTO) => {
		this.setState({
			emailError: "none",
			emailNotValid: "none",
			nameError: "none",
			surnameError: "none",
			addressError: "none",
			phoneError: "none",
		});

		if (userDTO.email === "") {
			this.setState({ emailError: "initial" });
			return false;
		} else if (!userDTO.email.includes("@")) {
			this.setState({ emailNotValid: "initial" });
			return false;
		} else if (userDTO.name === "") {
			this.setState({ nameError: "initial" });
			return false;
		} else if (userDTO.surname === "") {
			this.setState({ surnameError: "initial" });
			return false;
		} else if (this.addressInput.current.value === "") {
			this.setState({ addressError: "initial" });
			return false;
		} else if (userDTO.phoneNumber === "") {
			this.setState({ phoneError: "initial" });
			return false;
		}
		
		return true;
	};

	handleModalClose = () => {
		this.setState({ openModal: false });
	};
	
	onPharmacyChange  = (pharmacy) => {
		this.state.selectedPharmacy = pharmacy;
		console.log(pharmacy, "PHARMACy");
	
	};
	
	handleSignUp = () => {

		if(this.state.surname !== "" &&
		this.state.name !== "" &&
		this.state.phoneNumber !== "" &&
		this.state.email !== ""){

		let street;
		let city;
		let country;
		let latitude;
		let longitude;
		let found = true;

		this.ymaps
			.geocode(this.addressInput.current.value, {
				results: 1,
			})
			.then(function (res) {
				var firstGeoObject = res.geoObjects.get(0),
					coords = firstGeoObject.geometry.getCoordinates();
				latitude = coords[0];
				longitude = coords[1];
				country = firstGeoObject.getCountry();
				street = firstGeoObject.getThoroughfare();
				city = firstGeoObject.getLocalities().join(", ");
			})
			.then((res) => {
				let userDTO = {
					email: this.state.email,
					name: this.state.name,
					surname: this.state.surname,
					address: { street, country, city, latitude, longitude },
					phoneNumber: this.state.phoneNumber,
					password: "test",
				};
				if (this.validateForm(userDTO)) {
					if (found === false) {
						this.setState({ addressNotFoundError: "initial" });
					} else {
							
                        this.setState({
                            showAddWorkTime: true,
                            modalSize:'md',
                            userToAdd:userDTO
                        });
	
				}
				}
			});
		}else{
            this.setState({ 
                hiddenSuccessAlert: true,
                hiddenFailAlert: false, 
                failHeader: "Unsuccess", 
                failMessage: "Please enter valid data"
            });
		}
		
	};
    handleCloseAlertSuccess = () => {
		this.setState({ hiddenSuccessAlert: true });
    };
    
    handleCloseAlertFail = () => {
		this.setState({ hiddenFailAlert: true });
	};
	handleSelectChange  = (event) => {
		this.setState({ selectValue: event.target.value });
	};

    render() { 
        return ( 
            <Modal
                show = {this.props.show}
                size = {this.state.modalSize}
                dialogClassName="modal-80w-100h"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                >
                <Modal.Header >
                    <Modal.Title style={{marginLeft:'37%'}} id="contained-modal-title-vcenter">
                        {this.props.header}
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <HeadingSuccessAlert
						hidden={this.state.hiddenSuccessAlert}
						header={this.state.successHeader}
						message={this.state.successMessage}
						handleCloseAlert={this.handleCloseAlertSuccess}
					/>
					<HeadingAlert
						hidden={this.state.hiddenFailAlert}
						header={this.state.failHeader}
						message={this.state.failMessage}
						handleCloseAlert={this.handleCloseAlertFail}
					/>
                <div className="container">  
                <div hidden={this.state.showAddWorkTime}>
                    <div className="row section-design">
						<div className="col-lg-8 mx-auto">
							<br />
							<form id="contactForm" name="sentMessage" novalidate="novalidate">
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Email address:</label>
										<input
											placeholder="Email address"
											className="form-control"
											id="email"
											type="text"
											onChange={this.handleEmailChange}
											value={this.state.email}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.emailError }}>
										Email address must be entered.
									</div>
									<div className="text-danger" style={{ display: this.state.emailNotValid }}>
										Email address is not valid.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Name:</label>
										<input
											placeholder="Name"
											class="form-control"
											type="text"
											id="name"
											onChange={this.handleNameChange}
											value={this.state.name}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.nameError }}>
										Name must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Surname:</label>
										<input
											placeholder="Surname"
											class="form-control"
											type="text"
											id="surname"
											onChange={this.handleSurnameChange}
											value={this.state.surname}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.surnameError }}>
										Surname must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Address:</label>
										<input className="form-control" id="suggest" ref={this.addressInput} placeholder="Address" />
									</div>
									<YMaps
										query={{
											load: "package.full",
											apikey: "b0ea2fa3-aba0-4e44-a38e-4e890158ece2",
											lang: "en_RU",
										}}
									>
										<Map
											style={{ display: "none" }}
											state={mapState}
											onLoad={this.onYmapsLoad}
											instanceRef={(map) => (this.map = map)}
											modules={["coordSystem.geo", "geocode", "util.bounds"]}
										></Map>
									</YMaps>
									<div className="text-danger" style={{ display: this.state.addressError }}>
										Address must be entered.
									</div>
								</div>
								<div className="control-group">
									<div className="form-group controls mb-0 pb-2" style={{ color: "#6c757d", opacity: 1 }}>
										<label>Phone number:</label>
										<input
											placeholder="Phone number"
											class="form-control"
											id="phone"
											type="text"
											onChange={this.handlePhoneNumberChange}
											value={this.state.phoneNumber}
										/>
									</div>
									<div className="text-danger" style={{ display: this.state.phoneError }}>
										Phone number must be entered.
									</div>
								</div>

								<div className="form-group">
									<button
										style={{
											background: "#1977cc",
											marginTop: "15px",
											marginLeft: "40%",
											width: "20%",
										}}
										onClick={this.handleSignUp}
										className="btn btn-primary btn-xl"
										id="sendMessageButton"
										type="button"
									>
										Sign Up
									</button>
								</div>
							</form>
						</div>
					</div>
                    </div>    
                
                    <div hidden={!this.state.showAddWorkTime}>
                                <form >
                                    <div  className="control-group" >
                                        <div className="form-row">
                                            <button  onClick = {() => this.handleBack()} className="btn btn-link btn-xl" type="button">
                                                <i className="icofont-rounded-left mr-1"></i>
                                                Back
                                            </button>                   
                                        </div>
                                        <table style={{width:'100%'},{marginLeft:'17%'}}>
                                            <tr>
                                                <td>
                                                    <label >Date from:</label>
                                                </td>
                                                <td>
                                                    <DatePicker className="form-control"  style={{width: "15em"}} minDate={new Date()} onChange={date => this.handleStartDateChange(date)} selected={this.state.selectedStartDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Date to:</label>
                                                </td>
                                                <td>
                                                    <DatePicker  className="form-control" style={{width: "15em"}}  minDate={this.state.selectedStartDate} onChange={date => this.handleEndDateChange(date)} selected={this.state.selectedEndDate}/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <label>Time from:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time from" className="form-control" style={{width: "12.8em"}} type="number" min="1" max="23" onChange={this.handleTimeFromChange} value={this.state.timeFrom} />
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>
                                                    <label>Time to:</label>
                                                </td>
                                                <td>
                                                    <input placeholder="Time to" className="form-control" style={{width: "12.8em"}} type="number" min="2" max="24" onChange={this.handleTimeToChange} value={this.state.timeTo} />
                                                </td>
                                            </tr>
                                        </table>
                                        <div  className="form-group text-center">
                                            <Button className="mt-3"  onClick = {() => this.handleAdd()} >Add pharmacist</Button>
                                        </div>
                                    </div>
                                </form>
                    </div>
                </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={() => this.handleClickOnClose()}>Close</Button>
                </Modal.Footer>
            </Modal>
         );
    }
}
 
export default AddPharmacistToPharmacy;