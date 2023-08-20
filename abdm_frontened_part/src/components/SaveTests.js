import React, { Component } from 'react'
import filebackgr from "./images/filePic.png"
export default class SaveTests extends Component {
  constructor(props) {
    super(props)
    this.state = {
      testinginfo: []
    }
    this.BulkTesting = this.BulkTesting.bind(this);
    this.SingleTest = this.SingleTest.bind(this);
    this.NewTests = this.NewTests.bind(this);
    this.submittest = this.submittest.bind(this);
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
  submittest() {

  }
  render() {
    return (
      <div> <div className='row'>
        {/* <div className="col-1"> */}
        <button className="btn btn-light" onClick={this.BulkTesting}
          style={{
            width: '100px',
            height: '45px',
            margin: '10px 20px 10px',
            backgroundImage: `url(${filebackgr})`,
            backgroundSize: '90px 40px',
            backgroundPosition: 'center',
            // backgroundSize: 'cover',
            border: "4px solid blue"
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
            backgroundSize: 'cover',
            backgroundColor: 'lightblue',
            margin: '10px 10px 20px',
            border: "4px solid blue"
          }}
        > Add New Tests</button>
        {/* </div>  */}
      </div>
        <h1 style={{ display: "block", margin: "20px auto", textAlign: 'center' }}><mark>Lets Save New Test</mark></h1>

        <input type="text" placeholder="Indian name" style={{ display: "block", margin: "10px auto", border: "4px solid blue", backgroundColor: "lightyellow" }} />
        <input type="password" placeholder="Loinccode" style={{ display: "block", margin: "10px auto", border: "4px solid blue", backgroundColor: "lightyellow" }} />
        <button type="button" class="btn btn-outline-info" onClick={this.submittest} style={{ display: "block", margin: "20px auto", border: "4px solid red", backgroundColor: "lightgreen" }}>Save Test</button>
      </div>

    )
  }
}
