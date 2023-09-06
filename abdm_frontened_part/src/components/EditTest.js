import React, { Component } from 'react'
import axios from 'axios'
import './component.css';
import deletelogo from './images/deleteimg.png'
const initialise = 2;
var addtest = ""
export default class EditTest extends Component {

    tableRowRemove(index) {
        console.log("table is clicked");
        console.log(index);
        console.log(this.props.editinfo[0].loinccode);
        axios.get("http://localhost:9191/FullTest/DeleteTest/", {
            params: { name: index }
        })
        console.log("Achaa");
    };
    handleChange = event => {
        addtest = event.target.value;
        console.log(addtest);
    };
    handleclick = event => {
        console.log("calling the final add call")
        console.log(addtest);
        if (addtest) {
            const encodeval = encodeURIComponent(addtest);
            const loincvalue = this.props.editinfo[0].loinccode;
            axios.get("http://localhost:9191/FullTest/savetest/", {
                params: { name: encodeval, code: loincvalue }
            })
        }
    }
    render() {
        return (
            <>
                {/* <h1> this is {this.props.name} namaste</h1> */}
                <div className='row justify-content-center' style={{
                    margin: "10px auto"
                }}>

                    <div className="table-container">
                        <table className="table table-bordered custom-table">
                            <thead className="custom-thead">
                                <tr style={{
                                    backgroundColor: "lightyellow",
                                    border: "6px solid pink"
                                }}>
                                    <th >LOINC code </th>
                                    <th >Alias</th>
                                    <th>
                                        Action
                                    </th>
                                </tr>
                            </thead>
                            <tbody style={{
                                backgroundColor: "lightyellow",
                                border: "6px solid pink"
                            }}>
                                {
                                    this.props.editinfo.map(
                                        (tst, i) =>
                                            <tr key={i} style={{
                                                backgroundColor: "white",
                                                border: "2px solid blue"
                                            }}>
                                                <td>{tst.loinccode}</td>
                                                <td >{tst.indianname}</td>
                                                <td> <button className="btn btn-danger" onClick={() => this.props.deleteRow(tst.indianname)}><img src={deletelogo} alt="Jai Hind" style={{ width: "100%", height: "25px" }} /></button> </td>
                                            </tr>
                                    )
                                }
                                <tr style={{
                                    backgroundColor: "white",
                                    border: "2px solid blue"
                                }}>
                                    <td>{this.props.editinfo[0].loinccode}</td>
                                    <td >{ }</td>
                                    <td>
                                        <div className="input-group">
                                            <input
                                                className="form-control custom-input"
                                                aria-label="Large"
                                                aria-describedby="inputGroup-sizing-sm"
                                                placeholder="New Alias to add"
                                                type="text"
                                                id="message"
                                                name="message"
                                                onChange={this.handleChange}
                                            />
                                            <div className="input-group-append">
                                                <button
                                                    onClick={() => this.props.addalias(addtest)}
                                                    className="btn btn-warning custom-button"
                                                >
                                                    Add Alias
                                                </button>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>

                        </table>
                    </div>

                </div>
            </>
        )
    }
}
