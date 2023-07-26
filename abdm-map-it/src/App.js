import React, { Component }  from 'react';
import TestList from './components/TestList';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Switch } from 'react-router-dom/cjs/react-router-dom.min';
import { Route } from 'react-router-dom/cjs/react-router-dom';
import { BrowserRouter } from 'react-router-dom';
function App() {
  return (
    <>
    <div className="App">
    <TestList/>  
    </div>
    </>
  );
}

export default App;
