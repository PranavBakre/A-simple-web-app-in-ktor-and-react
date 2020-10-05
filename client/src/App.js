import React from 'react';
import 'static/App.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import background from  'static/background.jpg'
import Header from './components/Header';
import { Switch,Route } from 'react-router-dom';
import Index from 'routes/index'
import Home from 'routes/home'
import Profile from 'routes/profile'
import AuthenticatedRoute from 'components/AuthenticatedRoute';


function App() {
  return (
    <div className="App" style={{backgroundImage:`url(${background})`,backgroundSize:"cover",height:"100vh"}}>
      <Header></Header>
      <Switch>
          <AuthenticatedRoute path="/home" component={Home}></AuthenticatedRoute>
          <AuthenticatedRoute path="/profile" component={Profile}></AuthenticatedRoute>
          <Route path="/" component={Index}></Route>
      </Switch>

    </div>
  );
}

export default App;
