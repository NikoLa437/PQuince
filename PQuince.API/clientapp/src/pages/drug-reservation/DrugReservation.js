import React, { Component } from 'react';
import DrugsPage from './DrugsPage';
import Axios from 'axios';
import {BASE_URL} from '../../constants.js';
import PharmacyPage from './PharmacyPage';
import DrugReservationModal from '../../components/DrugReservationModal';

class DrugReservation extends Component {

    state = {
        drugsPageHidden:false,
        reservationModalShow:false,
        pharmacies: [],
        drugName:"",
        drugManufacturer:"",
        drugQuantity:"",
        maxDrugAmount:1,
        drugPrice:0,
    }
    handleBackIcon = () => {
        this.setState({drugsPageHidden: false});
    }

    handleReservation = (amount, date) => {
        console.log(amount, date)
    }

    handlePharmacyClick = (price, count) => {
        this.setState({reservationModalShow:true, maxDrugAmount: count,drugPrice:price });
    }

    handleModalClose = () => {
        this.setState({reservationModalShow: false});
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
                <PharmacyPage onBackIcon={this.handleBackIcon} onPharmacyClick={this.handlePharmacyClick} hidden={!this.state.drugsPageHidden} pharmacies={this.state.pharmacies} drugQuantity={this.state.drugQuantity}  drugManufacturer={this.state.drugManufacturer} drugName={this.state.drugName}/>
                <DrugReservationModal onCloseModal={this.handleModalClose} reserveDrugs={this.handleReservation} maxDrugAmount={this.state.maxDrugAmount} header="Drug reservation" drugPrice={this.state.drugPrice} show={this.state.reservationModalShow} drugQuantity={this.state.drugQuantity}  drugManufacturer={this.state.drugManufacturer} drugName={this.state.drugName}/>
            </React.Fragment>
          );
    }
}
 
export default DrugReservation;