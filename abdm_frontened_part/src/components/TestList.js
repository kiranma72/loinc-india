import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState } from 'react';
import { useHistory } from "react-router-dom";
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
// import backgr from "./images/bcgr7.jpg"
import jwt_decode from "jwt-decode";
import { CSVLink } from 'react-csv';
import './component.css';
import filebackgr from "./images/filePic.png"
import { BrowserRouter, Switch, Route, withRouter } from 'react-router-dom';
import axios from 'axios';
import './component.css';
import Tableform from './Tableform';
import EditTest from './EditTest';
import { useAuth0 } from '@auth0/auth0-react';
const checkid = "http://localhost:9191/FullTest/check/";
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
      loginid: 'a',
      correctid: "No"
    }
    this.BulkTesting = this.BulkTesting.bind(this);
    this.SingleTest = this.SingleTest.bind(this);
  }

  BulkTesting() {
    this.props.history.push('/bulkTest/');
  }
  SingleTest() {
    this.props.history.push('/');
  }
  handleChange = event => {
    this.stingch = event.target.value;
  };
  handleedit = event => {
    this.editval = event.target.value;
  };
  handleDeleteRow = (targetvalue) => {
    const target = encodeURIComponent(targetvalue);
    axios.get("http://localhost:9191/FullTest/DeleteTest/", {
      params: { name: target }
    })
    const filteredArray = this.state.edittestinginfo.filter((item, index) => item.indianname !== targetvalue);
    this.setState({
      edittestinginfo: filteredArray,
    });
  };


  editoperation = async event => {
    console.log("The checking is proceeding");
    console.log(this.state.loginid);

    try {
      const response = await axios.get(checkid + "" + this.state.loginid);
      const correctid = response.data;

      console.log(this.state.loginid);

      if (correctid === "Yes") {
        const res = await axios.get(editurl + "" + this.editval);
        const updatedEdittestinginfo = res.data;

        this.setState({
          edittestinginfo: updatedEdittestinginfo,
          correctid: correctid
        }, () => {
          console.log("Updated edittestinginfo:", this.state.edittestinginfo);
          // Now you can perform further actions with updatedEdittestinginfo
        });
      } else {
        this.setState({ correctid: "No" });
      }
    } catch (error) {
      console.error("Error:", error);
    }
  }

  addalias = (addtest) => {
    if (addtest) {
      const encodeval = encodeURIComponent(addtest);
      const loincvalue = this.editval;
      if (loincvalue !== "") {
        axios.get("http://localhost:9191/FullTest/savetest/", {
          params: { name: encodeval, code: loincvalue }
        })
      }
      axios.get(editurl + "" + this.editval).then((res) => {
        this.setState({ edittestinginfo: res.data });
        console.log(res.data);
      });
    }

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


  render() {

    return (console.log(tableid),
      <div >
        {/* <Loginbutton /> */}
        <div style={{
          backgroundSize: "contain"
        }}>
          {/* Csv file url */}
          <div className='container'>
            <div style={{ display: "flex", justifyContent: "flex-start", paddingLeft: "900px", paddingRight: "20px" }}>
              <GoogleOAuthProvider
                clientId="572493508359-p9ecfavakbksmm6j53hei1ntot9cintk.apps.googleusercontent.com"
                style={{ display: "flex", justifyContent: "flex-end", paddingRight: "20px" }}>
                <GoogleLogin onSuccess={credentialResponse => {
                  var decoded = jwt_decode(credentialResponse.credential);
                  this.setState({ loginid: decoded.email })
                }}
                  onError={() => {
                    console.log('Login Failed');
                  }}
                />Login</GoogleOAuthProvider>;
            </div>
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
        <Tableform testing={this.state.testinginfo} />
        {this.state.correctid === "Yes" ? <EditTest editinfo={this.state.edittestinginfo} deleteRow={this.handleDeleteRow} addalias={this.addalias} /> : <><p style={{
          fontSize: '18px',
          color: 'red',
          textAlign: 'center',
          margin: '20px'
        }}>
          Please Login first to Add/Delete an Alias
        </p></>
        }


      </div>
    )
  }
}
export default withRouter(TestList); 