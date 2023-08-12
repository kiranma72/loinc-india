import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import { useHistory } from "react-router-dom";
import backgr from "./images/bcgr7.jpg"
import filebackgr from "./images/filePic.png"
import { BrowserRouter, Switch, Route, withRouter } from 'react-router-dom';
import axios from 'axios';
const testurl = "http://localhost:9191/FullTest/lucene";
var tableid = 1
class TestList extends Component {
  stingch = "";
  constructor(props) {
    super(props)
    this.state = {
      testinginfo: [" "]
    }
    this.BulkTesting = this.BulkTesting.bind(this);
    this.SingleTest = this.SingleTest.bind(this);
    this.NewTests = this.NewTests.bind(this);
  }
  BulkTesting() {
    this.props.history.push('/bulkTest/');
  }
  SingleTest() {
    this.props.history.push('/');
  }
  NewTests() {
    this.props.history.push('/NewTests/');
  }
  handleChange = event => {
    this.stingch = event.target.value;
  };


  handleclick = event => {
    if (this.stingch.trim().length !== 0) {
      axios.get(testurl + "/" + encodeURIComponent(this.stingch)).then((res) => {
        this.setState({ testinginfo: res.data });
        console.log(res.data);
        console.log(this.testinginfo);
      }
      );
      console.log('value is:', event.target.value);
      tableid = 2; console.log("valueof id is", tableid);
    }
  };
  checkrange(val) {
    if (val >= 90) { return 'bg-success'; }
    else if (val >= 50) {
      return 'bg-warning';
    }
    else {
      return 'bg-danger';
    }
  }
  onsearchclick() {
    axios.get(testurl + "/" + "").then((res) => {
      this.setState({ testinginfo: res.data });
    }
    );
  }

  render() {

    return (console.log(tableid),
      <div >
        <div style={{
          backgroundImage: `url(${backgr})`, backgroundSize: "contain"
        }}>
          <br></br>
          <div className='container'>
            <div className="row">
            </div>
            <br></br>

            {/* Test Search part */}
            <div className='row'>
              <div className='col-4' >
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

              {/* Csv file input */}
            </div>
            <div className='row'>
              <button className="btn btn-light" onClick={this.BulkTesting}
                style={{
                  width: '120px',
                  height: '45px',
                  margin: '10px 20px 10px',
                  backgroundImage: `url(${filebackgr})`,
                  backgroundPosition: 'center',
                  backgroundSize: 'cover',

                  border: "3px solid red"
                }}
              ></button>
              <button className="btn btn-light" onClick={this.SingleTest}
                style={{
                  backgroundPosition: 'center',
                  backgroundSize: 'cover', backgroundColor: 'lightblue',
                  margin: '10px 10px 20px',
                  border: "4px solid blue"
                }}
              > Single Test</button>
              <button className="btn btn-light" onClick={this.NewTests}
                style={{
                  backgroundPosition: 'center', backgroundColor: 'lightblue',
                  backgroundSize: 'cover',
                  margin: '10px 10px 20px',
                  border: "4px solid blue"
                }}
              > Add New Tests</button>
            </div>

          </div>
        </div>
        <br></br>

        {/* table is starting  */}
        <div className="row">
          <table className="table table-bordered" style={{ margin: "10px 15px 15px" }}>
            <thead>
              <tr className="table-info" style={{
                backgroundColor: "red",
                border: "6px solid pink"
              }}>
                <th>Confidence (%)</th>
                <th >Indian name</th>
                <th>LOINC code </th>
                <th>Component</th>
                <th>Property</th>
                <th>Time Aspct</th>
                <th>System</th>
                <th>Scale</th>
                <th>Method</th>
                <th>Class</th>
                <th>Classtype</th>
                <th>Long Common Name</th>
                <th >Short Name</th>
              </tr>
            </thead>
            <tbody>
              {
                this.state.testinginfo.map(
                  tst =>
                    <tr key={tableid++}
                      className={this.checkrange(tst.percentage)}
                    >
                      <td>{tst.percentage}</td>
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
export default withRouter(TestList); 