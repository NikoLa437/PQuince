import React, { Component } from 'react';
import TopBar from '../components/TopBar';
import Header from '../components/Header';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import { YMaps, Map, GeoObject, Placemark } from 'react-yandex-maps';
import PharmacyDermatologistModal from '../components/PharmacyDermatologistModal';



class PharmacyProfilePage extends Component {
    state = {
        pharmacy :'',
        pharmacyId:'',
        pharmacyName:'',
        pharmacyDescription:'',
        pharmacyAdress:'',
        x:'',
        y:'',
        showDermatologistModal:false
    }


    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002").then((response) =>{
            this.setState(
            {
                pharmacy : response.data,
                pharmacyId : response.data.Id,
                pharmacyName : response.data.EntityDTO.name,
                pharmacyDescription:response.data.EntityDTO.description,
                pharmacyAdress:response.data.EntityDTO.address,
                x:45.25,
                y:19.85
            });          

        }).catch((err) => {console.log(err);});
    }

    handleSubscribe = () => {
      this.setState(
        {
            showDermatologistModal:true
        });  
    }
  
    handleModalClose = () => {
      this.setState({showDermatologistModal: false});
    }


    render() { 
        const {pharmacy,pharmacyName,pharmacyDescription,pharmacyAdress,x,y}= this.state
        const mapState = { center: [x, y], zoom: 17 }
        const myStyle = {
          color: 'white',
          textAlign: 'center'
        };
        return ( 
        <React.Fragment>
            <TopBar/>
            <Header/>

            <nav className="nav-menu d-none d-lg-block" style={{marginTop:"8%"}}>
                        <ul style={{marginLeft:"28%"}}>
                          <li><a href="#" className="appointment-btn scrollto" style={myStyle} >Rezervisi lek</a></li>
                          <li><a href="#" className="appointment-btn scrollto" style={myStyle} >Proveri dostupnost leka</a></li>                          
                          <li className="drop-down">
                            <a href="#" className="appointment-btn scrollto" style={myStyle} >Zakazi pregled</a>
                              <ul>
                                <li><a href="/">Dermatolog</a></li>
                                <li><a href="/">Farmaceut</a></li>
                              </ul>
                          </li>
                        </ul>
                    </nav>

            <div className="container" style={{marginTop:"0%"}}>
                <div className="row" style={{verticalAlign:'center'}}>
                </div>
                <div className="row" style={{marginTop:"3%"}}>
                    <div className="col-xs-4" style={{width: "50%"}}>
                      <div className="form-row">                        
                        <h1>{pharmacyName}</h1>  
                        <button style={{background: "#1977cc"},{height:'20px'},{verticalAlign:'center'},{marginLeft:'5%'}} onClick = {this.handleSubscribe} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Subscribe</button>
                      </div>
                        <br></br>
                        <h7>Adresa apoteke: {pharmacyAdress}</h7>
                        <br></br>
                        <h7>Opis apoteke: {pharmacyDescription}</h7>
                        <br></br>
                        <button style={{background: "#1977cc"},{height:'30px'},{verticalAlign:'center'},{marginTop:'5%'}} onClick = {this.handleSubscribe} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Lista dermatologa</button>
                        <br></br>
                        <button style={{background: "#1977cc"},{height:'30px'},{verticalAlign:'center'},{marginTop:'1%'}} onClick = {this.handleSubscribe} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Nasi farmaceuti</button>
                        <br></br>
                        <button style={{background: "#1977cc"},{height:'30px'},{verticalAlign:'center'},{marginTop:'1%'}} onClick = {this.handleSubscribe} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Lekovi na stanju</button>
                    </div>
                    <div className="col-xs-8">
                      <YMaps >
                              <Map state={mapState} width="35em" height="500px">
                                <GeoObject
                                  geometry={{
                                    type: 'Point',
                                    coordinates: [x, y],
                                  }}
                                  properties={{
                                    iconContent: pharmacyName,
                                    
                                  }}
                                  options={{
                                    preset: 'islands#blackStretchyIcon',
                                    draggable: true,
                                  }}
                                />
                              </Map>
                      </YMaps>
                    </div>
              </div>


              <PharmacyDermatologistModal show={this.state.showDermatologistModal} onCloseModal={this.handleModalClose} pharmacyId={this.state.pharmacyId} header="Our dermatologist" />
            
            </div>
        </React.Fragment> );
    }
}
 
export default PharmacyProfilePage;