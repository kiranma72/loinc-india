import React, { Component } from 'react'
import flag from "./images/indianflag1.jpg"
class HeaderComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }
    render() {
        return (
            <div >
                <header style={{
                    backgroundColor: "lightyellow",
                    border: "2px solid blue"
                }} >
                    <div className='row' >
                        <div className='col-2'>
                            <img src={flag} alt="An image of tickets" style={{ width: "100%", height: "60px" }} />
                        </div>
                        <div className='col-8'><h3 className="text-center">LOINC India </h3></div>
                        <div className='col-2'>
                            <img src={flag} alt="An image of tickets" style={{ width: "100%", height: "60px" }} />
                        </div>
                    </div>
                </header>
            </div>
        )
    }
}

export default HeaderComponent