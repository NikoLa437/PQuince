import './App.css';
import HomePage from './pages/HomePage'
import {BrowserRouter as Router,Link,Switch} from 'react-router-dom';


function App() {
  return (
    <Router>
        <Switch>
          <Link path='/' component={HomePage}/>
        </Switch>
    </Router>
  
  );
}

export default App;
