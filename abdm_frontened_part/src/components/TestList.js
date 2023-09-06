import React, { Component } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import jwt_decode from "jwt-decode";
import './component.css';
import LOINClogo from "./images/loinc-logo-tmp.png"
import { BrowserRouter, Switch, Route, withRouter } from 'react-router-dom';
import axios from 'axios';
import Tableform from './Tableform';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import EditTest from './EditTest';
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
    this.editoperation = this.editoperation.bind(this);
    this.addalias = this.addalias.bind(this);
    this.handleDeleteRow = this.handleDeleteRow.bind(this);
  }

  BulkTesting() {
    this.props.history.push('/bulkTest/');
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

  editoperation = async (code) => {
    console.log("The checking is proceeding and code is " + code);
    console.log(this.state.loginid);
    this.editval = code;
    try {
      const response = await axios.get(checkid + "" + this.state.loginid);
      const correctid = response.data;
      console.log(this.state.loginid);
      if (correctid === "Yes") {
        console.log(code + " the value is loincode ");
        const res = await axios.get(editurl + "" + code);
        const updatedEdittestinginfo = res.data;
        this.setState({
          edittestinginfo: updatedEdittestinginfo,
          correctid: correctid
        }, () => {
          console.log("Updated edittestinginfo:", this.state.edittestinginfo);
        });
      }
      else {
        toast.error('Please log in with an authentic Gmail account', {
          position: toast.POSITION.TOP_CENTER,
          autoClose: 3000,
        });
        console.log("the id is not assigned")
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
      console.log("the loiccode whose data is to be find is ==" + this.editval);
      if (loincvalue !== "") {
        axios.get("http://localhost:9191/FullTest/savetest/", {
          params: { name: encodeval, code: loincvalue }
        })
      }
      console.log("the loiccode whose data is to be find is ==" + this.editval);
      axios.get(editurl + "" + this.editval).then((res) => {
        this.setState({ edittestinginfo: res.data });
        console.log(res.data);
      });
      console.log("the loiccode whose data is to be find is ==" + this.editval);
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
        <div className="navbar">
          <div className="nav-column">
            <img src={LOINClogo} alt="Jai Hind" style={{ width: "100%", height: "25px" }} />
          </div>
          <div className="nav-column">
            <span>FIND THE LOINC CODE FOR YOUR LAB TESTS</span>
          </div>
          <div className="nav-column">
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
              /></GoogleOAuthProvider>
          </div>
        </div>
        <ToastContainer />
        {/* Search bar  */}
        <div className="search-container">
          <div className="search-bar">
            <input type="text" placeholder="Lab Test Name.." onChange={this.handleChange} />
            <button onClick={this.handleclick}>Search</button>
          </div>
          <button className="bulk-button" onClick={this.BulkTesting}>Bulk Map Tests</button>
        </div>
        <br></br>

        <br></br>
        {/* Catch function for showing editing table for the loincode and it should be recall from tableform */}
        <Tableform testing={this.state.testinginfo} editoperation={this.editoperation} />


        {this.state.correctid === "Yes" ? <EditTest editinfo={this.state.edittestinginfo} deleteRow={this.handleDeleteRow} addalias={this.addalias} /> : <><p style={{
          fontSize: '18px',
          color: 'red',
          textAlign: 'center',
          margin: '20px'
        }}>

        </p></>
        }

      </div>
    )
  }
}
export default withRouter(TestList); 