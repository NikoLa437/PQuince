import './App.css';
import HomePage from './pages/HomePage'
import {BrowserRouter as Router,Link,Switch} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import UserProfilePage from './pages/UserProfilePage';
import PharmaciesPage from './pages/PharmaciesPage';


function App() {
  return (
    <Router>
        <Switch>
          <Link exact path='/' component={HomePage}/>
          <Link exact path='/login' component={LoginPage}/>
          <Link exact path='/registration' component={RegisterPage}/>
          <Link exact path='/profile' component={UserProfilePage}/>
          <Link exact path='/pharmacies' component={PharmaciesPage}/>
        </Switch>
    </Router>
  
  );
}

export default App;
