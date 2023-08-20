import React, { Component } from 'react'
import filebackgr from "./images/filePic.png"
import axios from 'axios'
import Service from '../Service';
import HeaderComponent from './HeaderComponent';
import SaveTests from './SaveTests';
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
        const encodeval = encodeURIComponent(addtest);
        const loincvalue = this.props.editinfo[0].loinccode;
        axios.get("http://localhost:9191/FullTest/savetest/", {
            params: { name: encodeval, code: loincvalue }
        })

    }
    render() {
        return (
            <>
                {/* <h1> this is {this.props.name} namaste</h1> */}
                <div className='row justify-content-center' style={{
                    margin: "10px auto"
                }}>

                    <div className='col-3'>
                        <table className='table table-bordered' style={{ margin: "10px auto" }}>
                            <thead>
                                <tr style={{
                                    backgroundColor: "lightyellow",
                                    border: "6px solid pink"
                                }}>
                                    <th >LOINC code </th>
                                    <th >Alias</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.props.editinfo.map(
                                        (tst, i) =>
                                            <tr key={i}>
                                                <td>{tst.loinccode}</td>
                                                <td >{tst.indianname}</td>
                                                <td> <button className="btn btn-danger" onClick={() => this.tableRowRemove(tst.indianname)}>Delete it</button> </td>
                                            </tr>
                                    )
                                }

                            </tbody>

                        </table>
                    </div>
                    <div className='col-5'>
                        <div className='row' style={{
                            margin: "10px  "
                        }}>
                            <div className='col-5' >
                                <input className="form-control" aria-label="Large" aria-describedby="inputGroup-sizing-sm" placeholder="New Alias to add"
                                    type="text"
                                    id="message"
                                    name="message"
                                    onChange={this.handleChange}
                                    style={{
                                        backgroundColor: "lightpink",
                                        border: "2px solid blue"
                                    }}
                                /></div>
                            <div className='col-3'>  <button onClick={this.handleclick} className="btn btn-warning" style={{
                                backgroundColor: "lightgreen",
                                border: "2px solid blue"
                            }}>Add Alias</button></div>

                            {/* Csv file url */}
                        </div>
                    </div>
                </div>
            </>
        )
    }
}
