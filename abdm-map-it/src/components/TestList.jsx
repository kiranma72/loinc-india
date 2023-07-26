import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import axios from 'axios';

const testurl = "http://localhost:9191/FullTest/lucene";
var tableid = 1
class TestList extends Component {
  stingch = "";
  constructor(props) {
    super(props)
    this.state = {
      testinginfo: []
    }
  }


  handleChange = event => {
    this.stingch = event.target.value;
  };


  handleclick = event => {
    if (this.stingch.trim().length !== 0) {
      axios.get(testurl + "/" + this.stingch).then((res) => {
        this.setState({ testinginfo: res.data });
      }
      );
      console.log('value is:', event.target.value);
      tableid = 2; console.log("valueof id is", tableid);
    }
  };

  onsearchclick() {
    axios.get(testurl + "/" + "").then((res) => {
      this.setState({ testinginfo: res.data });
    }
    );
  }
  render() {
    return (console.log(tableid),
      <div>
        <div>
          <div className='row'> <h1 className="container">
            <mark> Lets Find The Tests</mark>
          </h1>
          </div>
          <div className='row'>
            {/* <div className='col'></div>
             <div className='col'></div> */}
            <div className='col-10' >
              <input className="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm" placeholder="Write test name here "
                type="text"
                id="message"
                name="message"
                onChange={this.handleChange}
                style={{
                  backgroundColor: "lightpink",
                  border: "2px solid blue"
                }}
              /></div>
            <div className='col-1'>  <button onClick={this.handleclick} className="btn btn-warning" style={{
              backgroundColor: "lightgreen",
              border: "2px solid blue"
            }}>Search.</button></div>
          </div>
        </div>
        <div className="row"
        >
          <table className="table table-bordered" >
            <thead>
              <tr className="table-info" style={{
                backgroundColor: "red",
                border: "6px solid red"
                // border:"6px solid green",
              }}>
                <th >Indian name</th>
                <th>Loinc code </th>
                <th>Component</th>
                <th>Property</th>
                <th>Time Aspct</th>
                <th>System</th>
                <th>Scale</th>
                <th>Method</th>
                <th>Class</th>
                <th>Classtype</th>
                <th>Long Common Name</th>
                <th>Short Name</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.testinginfo.map(
                  tst =>
                    <tr key={tableid++} className='table-success'>
                      <td>{tst.indianname}</td>
                      <td>{tst.loincCode}</td>
                      <td>{tst.component}</td>
                      <td>{tst.property}</td>
                      <td>{tst.time_ASPCT}</td>
                      <td>{tst.system}</td>
                      <td>{tst.scale}</td>
                      <td>{tst.method}</td>
                      <td>{tst.class_}</td>
                      <td>{tst.classtype_}</td>
                      <td>{tst.long_COMMON_NAME}</td>
                      <td>{tst.shortname_}</td>
                    </tr>
                )
              }

            </tbody>

          </table>
        </div>
      </div>
    )
  }
}
export default TestList