import React, { Component } from 'react';
import Header from '../components/Header';
import TopBar from '../components/TopBar';
import {BASE_URL} from '../constants.js';
import Axios from 'axios';

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
        console.log(event.target.value);
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

    handleSignUp = () => {
        let userDTO = { email: this.state.email, name: this.state.name, surname: this.state.surname, address: this.state.address, phoneNumber: this.state.phoneNumber,
                          cityId : this.state.cityId, password : this.state.password};
        console.log(userDTO);
        Axios.post(BASE_URL + "/auth/signup", userDTO).then((res) =>{
            console.log("Success")
        }).catch((err) => {console.log(err);});
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
                                    <label>Email address:</label>
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <input placeholder="Email address" className="form-control" id="name" type="text" onChange={this.handleEmailChange} value={this.state.email}/>
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Name:</label>
                                        <input placeholder="Name" class="form-control" type="text" onChange={this.handleNameChange} value={this.state.name} />
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Surname:</label>
                                        <input placeholder="Surname" class="form-control" type="text" onChange={this.handleSurnameChange} value={this.state.surname} />
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Country:</label>
                                        <select class="form-control" onChange={this.onCountryChange} value={this.state.value}>
                                            <option disabled={true} selected value="select">Select country</option>
                                            {this.state.countries.map(country => <option id={country.Id} key={country.Id} value = {country.Id}>{country.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>City:</label>
                                        <select class="form-control" onChange={this.onCityChange}>
                                            <option disabled={true} selected value="select">Select city</option>
                                            {this.state.cities.map(city => <option id={city.Id} key={city.Id} value = {city.Id}>{city.EntityDTO.name}</option>)}
                                        </select>
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Street and number:</label>
                                        <input placeholder="Street and number" class="form-control" id="password" type="text" onChange={this.handleAddressChange} value={this.state.address} />
                                    </div>
                                </div>
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <label>Phone number:</label>
                                        <input placeholder="Phone number" class="form-control" id="password" type="text" onChange={this.handlePhoneNumberChange} value={this.state.phoneNumber} />
                                    </div>
                                </div>
                                <div className="control-group">
                                    <label>Password:</label>
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                        <input placeholder="Password" class="form-control" id="password" type="password" onChange={this.handlePasswordChange} value={this.state.password} />
                                    </div>
                                </div>
                                
        
                                <div  className="form-group">
                                    <button style={{background: "#1977cc",marginTop: "15px",marginLeft: "40%", width: "20%"}} onClick = {this.handleSignUp} className="btn btn-primary btn-xl" id="sendMessageButton" type="button">Sign Up</button>
                                </div>
                            </form>
                    </div>
                </div>
            </div>
        </React.Fragment> 
        );
    }
}
 
export default RegisterPage;