import React from 'react';
import Header from '../components/Header';
import TopBar from '../components/TopBar';

class HomePage extends React.Component{
    render(){
        return(
            <React.Fragment>
                <TopBar/>
                <Header/>
                
                <section id="hero" class="d-flex align-items-center">
                    <div class="container">
                        <h1>Welcome to PQuince</h1>
                        <a href="#about" className="btn-get-started scrollto">Register</a>
                    </div>
                </section>

                <a href="#" className="back-to-top"><i className="icofont-simple-up"></i></a>
            </React.Fragment>
        )
    }
}

export default HomePage ;