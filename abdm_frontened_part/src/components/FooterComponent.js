import React, { Component } from 'react'

class FooterComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {

        }
    }

    render() {
        return (
            <div>
                <footer className="footer">
                    <h3 className="text-center" style={{
                        backgroundColor: "lightyellow",
                        color: "pink"
                    }} >Bharat Mata ki Jai Vande Matram</h3>
                </footer>
            </div>
        )
    }
}

export default FooterComponent
