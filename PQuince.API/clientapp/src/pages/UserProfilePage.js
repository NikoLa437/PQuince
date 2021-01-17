import React, { Component } from 'react';
import Header from '../components/Header';
import TopBar from '../components/TopBar';
import {BASE_URL} from '../constants.js';
import Axios from 'axios';
import AllergensModal from '../components/AllergensModal';
import PasswordChangeModal from '../components/PasswordChangeModal';
import ModalDialog from '../components/ModalDialog';

class UserProfilePage extends Component {
    state = {
        countries:[],
        cities:[],
        id : "",
        email:"",
        password:"",
        name:"",
        surname:"",
        address:"",
        phoneNumber:"",
        loyalityCategory:"",
        countryId:"select",
        cityId:"select",
        nameError:"none",
        surnameError:"none",
        addressError:"none",
        phoneError:"none",
        countryError:"none",
        cityError:"none",
        oldPasswordEmptyError : "none",
        newPasswordEmptyError : "none",
        newPasswordRetypeEmptyError : "none",
        newPasswordRetypeNotSameError : "none",
        openModal:false,
        openPasswordModal:false,
        openSuccessModal: false,
        userAllergens:[],
        patientPoints:"",
        loyalityCategoryColor:"#1977cc",
    }

    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/country").then((res) =>{
            this.setState({countries : res.data});
        }).catch((err) => {console.log(err);});

        Axios
        .get(BASE_URL + "/api/users/patient/22793162-52d3-11eb-ae93-0242ac130002").then((res) =>{
            console.log(res.data);
            this.setState({
                          id : res.data.Id,
                          email : res.data.EntityDTO.email,
                          name : res.data.EntityDTO.name,
                          surname : res.data.EntityDTO.surname,
                          address : res.data.EntityDTO.address,
                          phoneNumber : res.data.EntityDTO.phoneNumber,
                          cityId : res.data.EntityDTO.city.Id,
                          countryId: res.data.EntityDTO.city.EntityDTO.countryId,
                          userAllergens : res.data.EntityDTO.allergens,
                          patientPoints : res.data.EntityDTO.points,
                          loyalityCategory : res.data.EntityDTO.category,
                        });

              if(this.state.loyalityCategory === "SILVER")
                this.setState({loyalityCategoryColor:"#808080"});
              else if (this.state.loyalityCategory === "GOLD")
                this.setState({loyalityCategoryColor:"#FFCC00"});
        
            Axios
            .get(BASE_URL + "/api/city/filterByCountry/" + res.data.EntityDTO.city.EntityDTO.countryId).then((res) =>{
                this.setState({cities : res.data});
            }).catch((err) => {console.log(err);});
            
        }).catch((err) => {console.log(err);});
        

    } 

    handleEmailChange = (event) => {
        this.setState({email: event.target.value});
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
        console.log(event.target.value);
        this.setState({
            cityId: event.target.value
        });
    }

    validateForm = (userDTO) => {

        this.setState({nameError:"none", surnameError:"none", cityError:"none", addressError:"none", phoneError:"none"});

        if (userDTO.name === "") {
            this.setState({ nameError : "initial"});
            return false;
        }
        else if (userDTO.surname === ""){
            this.setState({ surnameError : "initial"});
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
        return true;
    }

    handleModalClose = () => {
        this.setState({openModal: false});
    }

    handleSuccessModalClose = () => {
        this.setState({openSuccessModal: false});
    }

    handlePasswordModalClose = () => {
        this.setState({openPasswordModal: false});
        
    }

    handleChangeInfo = () => {
        let userDTO = { name: this.state.name, surname: this.state.surname, address: this.state.address, phoneNumber: this.state.phoneNumber,
                          cityId : this.state.cityId};
        if (this.validateForm(userDTO)) {
            console.log(userDTO);
            Axios.put(BASE_URL + "/api/users/" + this.state.id, userDTO).then((res) =>{
                console.log("Success");
                this.setState({openSuccessModal: true});
            }).catch((err) => {console.log(err);});
        }
    }

    handleAllergenModal = () => {
        this.setState({openModal: true});
    }

    handlePasswordModal = () => {
        this.setState({openPasswordModal: true});
    }

    handleAlergenRemove = (allergen) => {
        let patientUserDTO = { allergenId : allergen.Id, patientId : this.state.id};
        console.log(patientUserDTO);
        Axios
        .put(BASE_URL + "/api/users/patient-allergens", patientUserDTO).then((res) =>{
            let allergens = [];
            console.log(allergens);
            for(let allerg of this.state.userAllergens){
                if(allerg.Id !== allergen.Id){
                    allergens.push(allerg);
                }
            }
            console.log(allergens);
            this.setState({userAllergens: allergens});
        }).catch((err) => {console.log(err);});
    }

    handleAllergenAdd = (allergen) => {
        let patientUserDTO = { allergenId : allergen.Id, patientId : this.state.id};
        console.log(patientUserDTO);
        Axios.post(BASE_URL + "/api/users/patient-allergens", patientUserDTO).then((res) =>{
            let allergens = [...this.state.userAllergens];
            allergens.push(allergen);
            this.setState({userAllergens: allergens});
        }).catch((err) => {console.log(err);});
    }
    
    changePassword = (oldPassword, newPassword, newPasswordRetype) => {

        this.setState({oldPasswordEmptyError:"none", newPasswordEmptyError:"none",newPasswordRetypeEmptyError:"none",newPasswordRetypeNotSameError:"none"})

        if(oldPassword === ""){
            this.setState({oldPasswordEmptyError:"initial"});
        }
        else if(newPassword === ""){
            this.setState({newPasswordEmptyError:"initial"});
        }
        else if(newPasswordRetype === ""){
            this.setState({newPasswordRetypeEmptyError:"initial"});
        }
        else if(newPasswordRetype !== newPassword){
            this.setState({newPasswordRetypeNotSameError:"initial"});
        }
    }

    render() { 
        return ( 
        <React.Fragment>
            <TopBar/>
            <Header/>

            <div className="container" style={{marginTop:"8%"}}>
                <h5 className=" text-center  mb-0 text-uppercase" style={{marginTop: "2rem"}}>User information</h5>

                <div className="row section-design">
                    <div className="col-lg-8 mx-auto">
                        <br/>
                            <form id="contactForm" name="sentMessage" novalidate="novalidate">
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <div className="form-row">
                                            <div className="form-col" style={{fontSize:"1.5em"}}>Loyality category: </div>
                                            <div className="form-col ml-2 rounded pr-2 pl-2"  style={{color:"white",background:this.state.loyalityCategoryColor,fontSize:"1.5em"}}> {this.state.loyalityCategory} </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <div className="form-row">
                                            <div className="form-col" style={{fontSize:"1.5em"}}>Number of points: </div>
                                            <div className="form-col ml-2 rounded pr-2 pl-2"  style={{color:"white",background:"#1977cc",fontSize:"1.5em"}}> {this.state.patientPoints} </div>
                                        </div>
                                    </div>
                                </div>
                                <br/>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Email address:</label>
                                        <input readOnly placeholder="Email address" className="form-control" id="name" type="text" onChange={this.handleEmailChange} value={this.state.email}/>
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Name:</label>
                                        <input placeholder="Name" class="form-control" type="text" onChange={this.handleNameChange} value={this.state.name} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.nameError}}>
                                        Name must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Surname:</label>
                                        <input placeholder="Surname" class="form-control" type="text" onChange={this.handleSurnameChange} value={this.state.surname} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.surnameError}}>
                                        Surname must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Country:</label>
                                        <select class="form-control" onChange={this.onCountryChange} value={this.state.countryId}>
                                            <option disabled={true}  value="select">Select country</option>
                                            {this.state.countries.map(country => country.Id === this.state.countryId ? <option selected id={country.Id} key={country.Id} value = {country.Id}>{country.EntityDTO.name}</option> :
                                                                                                                       <option id={country.Id} key={country.Id} value = {country.Id}>{country.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>City:</label>
                                        <select class="form-control" value={this.state.cityId} onChange={this.onCityChange}>
                                            <option disabled={true} value="select">Select city</option>
                                            {this.state.cities.map(city => city.Id === this.state.cityId ? <option selected id={city.Id} key={city.Id} value = {city.Id}>{city.EntityDTO.name}</option> :
                                                                                                           <option id={city.Id} key={city.Id} value = {city.Id}>{city.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                    <div className="text-danger" style={{display:this.state.cityError}}>
                                        City must be selected.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Address:</label>
                                        <input placeholder="Address" class="form-control" type="text" onChange={this.handleAddressChange} value={this.state.address} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.addressError}}>
                                        Address must be entered.
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Phone number:</label>
                                        <input placeholder="Phone number" class="form-control" type="text" onChange={this.handlePhoneNumberChange} value={this.state.phoneNumber} />
                                    </div>
                                    <div className="text-danger" style={{display:this.state.phoneError}}>
                                        Phone number must be entered.
                                    </div>
                                </div>
                                <div  className="form-group text-center">
                                    <button style={{background: "#1977cc",marginTop: "15px"}} onClick = {this.handleChangeInfo} className="btn btn-primary btn-xl" id="sendMessageButton" type="button">Change information</button>
                                </div>
                                <br/>
                                
                                
                                <div  className="form-group">
                                    <div className="form-group controls mb-0 pb-2">
                                        <div className="form-row">
                                            <div className="form-col">
                                                <button style={{marginTop: "15px"}} onClick = {this.handleAllergenModal} className="btn btn-outline-primary btn-xl" id="sendMessageButton" type="button">Edit Allergens</button>
                                            </div>
                                            <div className="form-col ml-3">
                                                <button style={{marginTop: "15px"}} onClick = {this.handlePasswordModal} className="btn btn-outline-primary btn-xl" id="sendMessageButton" type="button">Change Password</button>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </form>
                    </div>
                </div>
            </div>
            <AllergensModal userAllergens={this.state.userAllergens} show={this.state.openModal} onAllergenRemove={this.handleAlergenRemove} onAllergenAdd={this.handleAllergenAdd} onCloseModal={this.handleModalClose} header="Patients allergens" subheader="Add or remove patients allergens"/>
            <PasswordChangeModal oldPasswordEmptyError={this.state.oldPasswordEmptyError} newPasswordEmptyError={this.state.newPasswordEmptyError} newPasswordRetypeEmptyError={this.state.newPasswordRetypeEmptyError} newPasswordRetypeNotSameError={this.state.newPasswordRetypeNotSameError} show={this.state.openPasswordModal} changePassword={this.changePassword} onCloseModal={this.handlePasswordModalClose} header="Change password"/>
            <ModalDialog show={this.state.openSuccessModal} href="/" onCloseModal={this.handleSuccessModalClose} header="Successful" text="Your information is changed succesfully."/>
        </React.Fragment> 
        );
    }
}
 
export default UserProfilePage;