import React, { Component } from 'react';
import TopBar from '../../components/TopBar';
import Header from '../../components/Header';
import Axios from 'axios';
import {BASE_URL} from '../../constants.js';
import CapsuleLogo from '../../static/capsuleLogo.png';
import PharmacyLogo from '../../static/pharmacyLogo.png';

class PharmacyPage extends Component {
    state = {
        drugId: this.props.drugId,
    }

    handleNameChange = (event) => {
        this.setState({name: event.target.value});
    }

    hangleFormToogle = () => {
        this.setState({formShowed: !this.state.formShowed});
    }

    handleGradeFromChange = (event) => {
        this.setState({gradeFrom: event.target.value});
    }
    
    handleGradeToChange = (event) => {
        this.setState({gradeTo: event.target.value});
    }

    handleDistanceFromChange = (event) => {
        this.setState({distanceFrom: event.target.value});
    }

    handleDistanceToChange = (event) => {
        this.setState({distanceTo: event.target.value});
    }

    render() { 
        return ( 
        <div hidden={this.props.hidden}>
            <TopBar/>
            <Header/>
            

            <div className="container" style={{marginTop:"10%"}}>
                    <h5 className=" text-center mb-0 mt-2 text-uppercase">Available in pharmacies</h5>
                    <form >
                                <div className="control-group">
                                    <div className="form-group controls mb-0 pb-2" style={{color: "#6c757d",opacity: 1}}>
                                    <div className="form-row">
                                        <button  onClick = {() => this.props.onBackIcon()} className="btn btn-link btn-xl" type="button">
                                            <i className="icofont-rounded-left mr-1"></i>
                                            Back
                                         </button>                   
                                    </div>
                                        <div className="form-row mt-4" width="130em">                        
                                            <div className="form-col" >
                                                <img className="img-fluid" src={CapsuleLogo} width="70em"/>
                                            </div>
                                            <div className="form-col">
                                                <div><b>Name:</b> {this.props.drugName}</div>
                                                <div><b>Manufacturer:</b> {this.props.drugManufacturer}</div>
                                                <div><b>Quantity:</b> {this.props.drugQuantity} <b>mg</b></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                   </form>
                   <h5 className=" text-center mb-0 mt-2 text-uppercase" hidden={this.props.pharmacies.length !== 0}>Currently unavailable</h5>

                    <table className="table" style={{width:"100%", marginTop:"3rem"}}>
                        <tbody>
                            {this.props.pharmacies.map(pharmacy => 
                                <tr id={pharmacy.Id} key={pharmacy.Id} onClick={() => this.props.onPharmacyClick(pharmacy.price, pharmacy.count)}>
                                    <td width="130em">
                                        <img className="img-fluid" src={PharmacyLogo} width="70em"/>
                                    </td>
                                    <td>
                                        <div><b>Name:</b> {pharmacy.EntityDTO.name}</div>
                                        <div><b>Address:</b> {pharmacy.EntityDTO.address}</div>
                                        <div><b>Drug price:</b> {pharmacy.price}<b> din</b></div>
                                    </td>
                                </tr>)}

                        </tbody>
                    </table>
            </div>
        </div> );
    }
}
 
export default PharmacyPage;