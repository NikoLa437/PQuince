import React, { Component } from 'react';
import TopBar from '../components/TopBar';
import Header from '../components/Header';

class PharmaciesPage extends Component {

    render() { 
        return ( 
        <React.Fragment>
            <TopBar/>
            <Header/>
            
            <div className="container" style={{marginTop:"10%"}}>
                    <h5 className=" text-center  mb-0 text-uppercase" style={{marginTop: "2rem"}}>Pharmacies</h5>
            </div>
        </React.Fragment> );
    }
}
 
export default PharmaciesPage;