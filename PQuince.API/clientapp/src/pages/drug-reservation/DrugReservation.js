import React, { Component } from 'react';
import DrugsPage from './DrugsPage';
import Axios from 'axios';
import {BASE_URL} from '../../constants.js';
import PharmacyPage from './PharmacyPage';
import DrugReservationModal from '../../components/DrugReservationModal';

class DrugReservation extends Component {

    state = {
        drugsPageHidden:false,
        reservationModalShow:true,
        pharmacies: [],
        drugName:"",
        drugManufacturer:"",
        drugQuantity:"",
    }
    handleBackIcon = () => {
        this.setState({drugsPageHidden: false});
    }

    handleDrugSelect = (drug) => {
        Axios
        .get(BASE_URL + "/api/pharmacy/find-by-drug/" + drug.Id).then((res) =>{
            this.setState({pharmacies : res.data, drugsPageHidden:true, drugName: drug.EntityDTO.name, drugManufacturer: drug.EntityDTO.manufacturer.EntityDTO.name,
                            drugQuantity: drug.EntityDTO.quantity });
            console.log(res.data);
        }).catch((err) => {console.log(err);});
    }

    render() { 
        return (
            <React.Fragment>
                <DrugsPage hidden={this.state.drugsPageHidden} onDrugSelect={this.handleDrugSelect}/>
                <PharmacyPage onBackIcon={this.handleBackIcon} hidden={!this.state.drugsPageHidden} pharmacies={this.state.pharmacies} drugQuantity={this.state.drugQuantity}  drugManufacturer={this.state.drugManufacturer} drugName={this.state.drugName}/>
                <DrugReservationModal header="Drug reservation" show={this.state.reservationModalShow}/>
            </React.Fragment>
          );
    }
}
 
export default DrugReservation;