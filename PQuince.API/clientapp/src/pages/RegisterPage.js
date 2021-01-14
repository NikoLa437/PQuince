import React, { Component } from 'react';
import Header from '../components/Header';
import TopBar from '../components/TopBar';
import {BASE_URL} from '../constants.js';
import Axios from 'axios';
import ModalDialog from '../components/ModalDialog';

class RegisterPage extends Component {
    state = {
        countries:[],
        cities:[],
        email:"",
        password:"",
        name:"",
        surname:"",
        address:"",
        phoneNumber:"",
        countryId:"select",
        cityId:"select",
        emailError:"none",
        passwordError:"none",
        nameError:"none",
        surnameError:"none",
        addressError:"none",
        phoneError:"none",
        countryError:"none",
        cityError:"none",
        emailNotValid:"none",
        openModal:false,
    }

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/country").then((res) =>{
            this.setState({countries : res.data});
        }).catch((err) => {console.log(err);});
    } 

    handleEmailChange = (event) => {
        this.setState({email: event.target.value});
    }

    handlePasswordChange = (event) => {
        this.setState({password: event.target.value});
    }

    handleNameChange = (event) => {
        this.setState({name: event.target.value});
    }

    handleSurnameChange = (event) => {
        this.setState({surname: event.target.value});
    }

    handleAddressChange = (event) => {
        this.setState({address: event.target.value});
    }

    handlePhoneNumberChange = (event) => {
        this.setState({phoneNumber: event.target.value});
    }

    onCountryChange = (event) => {
        this.setState({
            countryId: event.target.value
        });
        this.setState({cityId :"select"})
        Axios
        .get(BASE_URL + "/api/city/filterByCountry/" + event.target.value).then((res) =>{
            this.setState({cities : res.data});
        }).catch((err) => {console.log(err);});
    }

    onCityChange = (event) => {
        this.setState({
            cityId: event.target.value
        });
    }

    validateForm = (userDTO) => {

        this.setState({emailError:"none", emailNotValid:"none", nameError:"none", surnameError:"none", countryError:"none", cityError:"none", addressError:"none", phoneError:"none", passwordError:"none"});

        if(userDTO.email === ""){
            this.setState({ emailError : "initial"});
            return false;
        }
        else if (!userDTO.email.includes("@")){
            this.setState({ emailNotValid : "initial"});
            return false;
        }
        else if (userDTO.name === "") {
            this.setState({ nameError : "initial"});
            return false;
        }
        else if (userDTO.surname === ""){
            this.setState({ surnameError : "initial"});
            return false;
        }
        else if (this.state.countryId === "" || this.state.countryId === "select"){
            this.setState({ countryError : "initial"});
            return false;
        }
        else if (userDTO.cityId === "" || userDTO.cityId === "select"){
            this.setState({ cityError : "initial"});
            return false;
        }
        else if (userDTO.address === ""){
            this.setState({ addressError : "initial"});
            return false;
        }
        else if (userDTO.phoneNumber === ""){
            this.setState({ phoneError : "initial"});
            return false;
        }
        else if (userDTO.password === ""){
            this.setState({ passwordError : "initial"});
            return false;
        }
        return true;
    }

    handleModalClose = () => {
        this.setState({openModal: false});
        
    }

    handleSignUp = () => {
        let userDTO = { email: this.state.email, name: this.state.name, surname: this.state.surname, address: this.state.address, phoneNumber: this.state.phoneNumber,
                          cityId : this.state.cityId, password : this.state.password};

        if (this.validateForm(userDTO)) {
            console.log(userDTO);
            Axios.post(BASE_URL + "/auth/signup", userDTO).then((res) =>{
                console.log("Success");
                this.setState({openModal: true});
            }).catch((err) => {console.log(err);});
        }
    }

    render() { 
        return ( 
        <React.Fragment>
            <TopBar/>
            <Header/>

            <div className="container" style={{marginTop:"8%"}}>
                <h5 className=" text-center  mb-0 text-uppercase" style={{marginTop: "2rem"}}>Registration</h5>

                <div className="row section-design">
                    <div className="col-lg-8 mx-auto">
                        <br/>
                            <form id="contactForm" name="sentMessage" novalidate="novalidate">
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Email address:</label>
                                        <input placeholder="Email address" className="form-control" id="email" type="text" onChange={this.handleEmailChange} value={this.state.email}/>
                                    </div>
                                    <div className="text-danger" style={{display:this.state.emailError}}>
                                        Email address must be entered.
                                    </div>
                                    <div className="text-danger" style={{display:this.state.emailNotValid}}>
                                        Email address is not valid.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Name:</label>
                                        <input placeholder="Name" class="form-control" type="text" id="name" onChange={this.handleNameChange} value={this.state.name} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.nameError}}>
                                        Name must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Surname:</label>
                                        <input placeholder="Surname" class="form-control" type="text" id="surname" onChange={this.handleSurnameChange} value={this.state.surname} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.surnameError}}>
                                        Surname must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Country:</label>
                                        <select class="form-control" onChange={this.onCountryChange} value={this.state.countryId}>
                                            <option disabled={true} selected value="select">Select country</option>
                                            {this.state.countries.map(country => <option id={country.Id} key={country.Id} value = {country.Id}>{country.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                    <div className="text-danger" style={{display:this.state.countryError}}>
                                        Country must be selected.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>City:</label>
                                        <select class="form-control" onChange={this.onCityChange} value={this.state.cityId}>
                                            <option disabled={true} selected value="select">Select city</option>
                                            {this.state.cities.map(city => <option id={city.Id} key={city.Id} value = {city.Id}>{city.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                    <div className="text-danger" style={{display:this.state.cityError}}>
                                        City must be selected.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Address:</label>
                                        <input placeholder="Address" class="form-control" id="address" type="text" onChange={this.handleAddressChange} value={this.state.address} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.addressError}}>
                                        Address must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Phone number:</label>
                                        <input placeholder="Phone number" class="form-control" id="phone" type="text" onChange={this.handlePhoneNumberChange} value={this.state.phoneNumber} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.phoneError}}>
                                        Phone number must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <label>Password:</label>
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <input placeholder="Password" class="form-control" type="password" onChange={this.handlePasswordChange} value={this.state.password} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.passwordError}}>
                                        Password must be entered.
                                    </div>
                                </div>
                                
        
                                <div  className="form-group">
                                    <button style={{background: "#1977cc",marginTop: "15px",marginLeft: "40%", width: "20%"}} onClick = {this.handleSignUp} className="btn btn-primary btn-xl" id="sendMessageButton" type="button">Sign Up</button>
                                </div>
                            </form>
                    </div>
                </div>
            </div>
            <ModalDialog show={this.state.openModal} href="/" onCloseModal={this.handleModalClose} header="Successful registration" text="You can activate your account by clicking on link sent to provided email address."/>
        </React.Fragment> 
        );
    }
}
 
export default RegisterPage;