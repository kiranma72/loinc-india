import React, { Component } from 'react';
import TestList from './components/TestList';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import SaveTests from './components/SaveTests';
import Papa from 'papaparse';
import BulkTest from './components/BulkTest';
import filebackgr from "./components/images/StarsBackgr1.jpg"
import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import { Routes, Route, Switch } from 'react-router-dom';
import { BrowserRouter } from 'react-router-dom';
import CsvTest from './components/CsvTest';
import EditTest from './components/EditTest';

import LoginbuttonAuth from './components/LoginbuttonAuth';
function App() {

  return (
    <div style={{
      backgroundImage: `url(${filebackgr})`,
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center center',
      /* Additional styles for the container */
      width: '100vw', // Set the width to full viewport width
      height: '400vh', // Set the height to full viewport height
    }}> <HeaderComponent />
      <div >
        <Switch>
          <Route path="/" exact component={TestList}></Route>
          <Route path="/bulkTest" component={CsvTest} />
          <Route path="/NewTests/" component={SaveTests} />
          <Route path="/Edittest/" component={EditTest} />
        </Switch>
      </div>
      {/* <FooterComponent /> */}
    </div>
  );
}

export default App;
