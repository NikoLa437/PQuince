import './App.css';
import HomePage from './pages/HomePage'
import {BrowserRouter as Router,Link,Switch} from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';


function App() {
  return (
    <Router>
        <Switch>
          <Link exact path='/' component={HomePage}/>
          <Link exact path='/login' component={LoginPage}/>
          <Link exact path='/registration' component={RegisterPage}/>

        </Switch>
    </Router>
  
  );
}

export default App;
