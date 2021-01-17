import React, { Component } from 'react';
import TopBar from '../components/TopBar';
import Header from '../components/Header';
import Axios from 'axios';
import {BASE_URL} from '../constants.js';
import { YMaps, Map, GeoObject, Placemark } from 'react-yandex-maps';



class PharmacyProfilePage extends Component {
    state = {
        pharmacy :'',
        pharmacyName:'',
        pharmacyDescription:'',
        pharmacyAdress:'',
        x:'',
        y:''
    }


    componentDidMount() {
        Axios
        .get(BASE_URL + "/api/pharmacy/get-pharmacy-profile?pharmacyId=cafeddee-56cb-11eb-ae93-0242ac130002").then((response) =>{
            this.setState(
            {
                pharmacy : response.data,
                pharmacyName : response.data.EntityDTO.name,
                pharmacyDescription:response.data.EntityDTO.description,
                pharmacyAdress:response.data.EntityDTO.address,
                x:45.25,
                y:19.85
            });          

        }).catch((err) => {console.log(err);});
    }

    handleSubscribe = () => {

      alert('subscribed')
  }



    render() { 
        const {pharmacy,pharmacyName,pharmacyDescription,pharmacyAdress,x,y}= this.state
        const mapState = { center: [x, y], zoom: 17 }
        return ( 
        <React.Fragment>
            <TopBar/>
            <Header/>
            

            <div className="container" style={{marginTop:"10%"}}>
                <div className="row" style={{verticalAlign:'center'}}>
                  <h2 style={{marginLeft:'40%'}} className=" text-center mb-0 mt-2 text-uppercase">{pharmacyName}</h2>
                  <button style={{background: "#1977cc"},{height:'30px'},{verticalAlign:'center'},{marginLeft:'3%'}} onClick = {this.handleSubscribe} className="btn btn-primary btn-xl" type="button"><i className="icofont-subscribe mr-1"></i>Subscribe</button>
                </div>
                <div className="row" style={{marginTop:"3%"}}>
                    <div className="col-xs-4" style={{width: "50%"}}>
                        <h7>Ime apoteke: {pharmacyName}</h7>
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
                      <YMaps>
                              <Map state={mapState}>
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


                    
            </div>
        </React.Fragment> );
    }
}
 
export default PharmacyProfilePage;