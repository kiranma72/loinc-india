import React, { Component } from 'react'
import flag from "./imgages/indianflag1.jpg"
export default class Headstyle extends Component {
  render() {
    return (
      <div  >
        <header   >
          <nav className="navbar navbar-expand-md navbar-dark bg-dark">
            <div className='col-4'>
              <img src={flag} alt="Jai Hind" style={{ width: "100%", height: "15px" }} />
            </div>
            <div className='col-4'><h1 className="navbar-brand">LOINC India </h1></div>
            <div className='col-4'>
              <img src={flag} alt="Jai Hind" style={{ width: "100%", height: "15px" }} />
            </div>
          </nav>
        </header>
      </div>
    )
  }
}
