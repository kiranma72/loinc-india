import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import { useHistory } from "react-router-dom";
// import backgr from "./images/bcgr7.jpg"
import { CSVLink } from 'react-csv';
import './component.css';
import filebackgr from "./images/filePic.png"
import { BrowserRouter, Switch, Route, withRouter } from 'react-router-dom';
import axios from 'axios';
import './component.css';
import Loginbutton from './Loginbutton';
import Service from '../Service';
import Tableform from './Tableform';
import EditTest from './EditTest';
const testurl = "http://localhost:9191/FullTest/lucene/";
const editurl = "http://localhost:9191/FullTest/EditTest/";
var tableid = 1

var ar = [{ indianname: 'BASOPHILS', property: 'NFr', method: '', scale: 'Qn', long_COMMON_NAME: 'Basophils/100 leukocytes in Blood', method: "", percentage: 100, property: "NFr", scale: "Qn", shortname_: "Basophils/leuk NFr Bld", status_: "ACTIVE", system: "Bld", time_ASPCT: "Pt" }]
class TestList extends Component {
  stingch = "";
  editval = "";
  constructor(props) {
    super(props)
    this.state = {
      testinginfo: [],
      edittestinginfo: [],
      startedit: 'abc',
    }
    this.BulkTesting = this.BulkTesting.bind(this);
    this.SingleTest = this.SingleTest.bind(this);
    this.NewTests = this.NewTests.bind(this);
    this.EditTests = this.EditTests.bind(this);
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
  handleedit = event => {
    this.editval = event.target.value;
  };

  EditTests(LOINCvalue) {
    console.log("Setting the loinc code");
    console.log(LOINCvalue);
    console.log("Settingit");
    Service.Setedittest(LOINCvalue);
    this.props.history.push('/Edittest/');
  }
  editoperation = event => {
    while (ar.length > 0) { ar.pop() }
    axios.get(editurl + "" + this.editval).then((res) => {
      //ar.push(res.data);
      this.setState({ edittestinginfo: res.data });
      console.log(res.data);
    });
    console.log("THe edit code is");
    console.log(this.state.edittestinginfo);
    console.log("The code is setup");
  }

  handleclick = event => {
    if (this.stingch.trim().length !== 0) {
      const encodeval = encodeURIComponent(this.stingch);
      axios.get("http://localhost:9191/FullTest/lucene/", { params: { name: encodeval } }).then((res) => {
        this.setState({ testinginfo: res.data });
        ar.push(res.data);
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
        {/* <Loginbutton /> */}
        <div style={{
          backgroundSize: "contain"
        }}>
          {/* Csv file url */}
          <div className='container'>
            <div className="container">
              <br></br>
              <div className="row heading-buttons">
                <button
                  className="btn btn-light file-button"
                  onClick={this.BulkTesting}
                  style={{
                    width: '100px',
                    height: '55px',
                    margin: '10px 20px 5px',
                    backgroundImage: `url(${filebackgr})`,
                    backgroundSize: '90px 40px',
                    backgroundPosition: 'center',
                    // backgroundSize: 'cover',
                    border: "4px solid blue"
                  }}
                >
                </button>
                <button
                  className="btn btn-light single-button"
                  onClick={this.SingleTest}
                >
                  Single Test
                </button>
              </div>
            </div>


            {/* Test Search part */}
            <div className='row'>

              <div className="search-bar">
                <input
                  className="form-control"
                  placeholder="Search for a test..."
                  type="text"
                  id="message"
                  name="message"
                  onChange={this.handleChange}
                />
                <button
                  onClick={this.handleclick}
                  className="btn btn-warning search-button"
                >
                  Search
                </button>
              </div>
            </div>




          </div>

          <div className="button-container">
            <input
              className="form-control edit-input"
              placeholder="LOINC-Code "
              type="text"
              id="message"
              name="message"
              onChange={this.handleedit}
            />

            <button
              onClick={this.editoperation}
              className="btn btn-warning edit-button"
            >
              Add/Delete
            </button>
          </div>


        </div>
        <br></br>
        <EditTest editinfo={this.state.edittestinginfo} />
        <Tableform testing={this.state.testinginfo} />
        {/* {checkingshow == 'abc' ? <Editdata testing={this.state.testinginfo} /> : console.log("Not equal")} */}

        {/* style={{
                                backgroundColor: "red",
                                border: "6px solid pink"
                            }} */}
      </div>
    )
  }
}
export default withRouter(TestList); 