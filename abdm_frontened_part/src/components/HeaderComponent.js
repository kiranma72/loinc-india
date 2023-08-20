import React, { Component } from 'react'
import flag from "./images/indianflag1.jpg"
import './component.css';
class HeaderComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }
    render() {
        return (
            <div>
                <header className="header-container">
                    <div className="row">
                        <div className="col-2">
                            <img src={flag} alt="An image of tickets" className="header-image" />
                        </div>
                        <div className="col-8">
                            <h3 className="header-title">LOINC India</h3>
                        </div>
                        <div className="col-2">
                            <img src={flag} alt="An image of tickets" className="header-image" />
                        </div>
                    </div>
                </header>
            </div>

        )
    }
}

export default HeaderComponent