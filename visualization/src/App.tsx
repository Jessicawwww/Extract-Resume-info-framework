import React from 'react';
import './App.css';
import { BrowserRouter as Router,Route, Switch} from 'react-router-dom';
import ResumeSetup from './pages/ResumeSetup';
import PersonalPage from './pages/PersonalPage';

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path='/' component={ResumeSetup} />
        <Route path='/personal_page' component={PersonalPage} />
      </Switch>
    </Router>
  );
}

export default App;
