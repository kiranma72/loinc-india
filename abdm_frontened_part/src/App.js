import React, { Component } from 'react';
import TestList from './components/TestList';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import { Routes, Route, Switch } from 'react-router-dom';
import BulkTest from './components/BulkTest';
function App() {

  return (
    <div>
      <div >
        <Switch>
          <Route path="/" exact component={TestList}></Route>
          <Route path="/bulkTest" component={BulkTest} />
        </Switch>
      </div>
    </div>
  );
}

export default App;
