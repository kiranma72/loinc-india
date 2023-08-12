import React, { Component } from 'react'
import filebackgr from "./images/filePic.png"
import BulkTest from './BulkTest'
export default class CsvTest extends Component {
  constructor(props) {
    super(props)
    this.state = {
      testinginfo: []
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
  render() {
    return (
      <div>
        <div className='row'>
          {/* <div className="col-1"> */}
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
          {/* </div> */}
          {/* <div className="col-1"> */}
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
              backgroundPosition: 'center',
              backgroundSize: 'cover', backgroundColor: 'lightblue',
              margin: '10px 10px 20px',
              border: "4px solid blue"
            }}
          > Add New Tests</button>
          {/* </div>  */}
        </div>
        <BulkTest />
      </div>
    )
  }
}
