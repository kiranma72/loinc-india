import React, { Component } from 'react'
import EditTest from './EditTest';
import axios from 'axios';
import './component.css';
export default class Tableform extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }

    checkrange(val) {
        if (val >= 90) { return 'PaleGreen'; }
        else if (val >= 50) {
            return 'LightGoldenRodYellow';
        }
        else {
            return 'BlanchedAlmond';
        }
    }

    render() {
        return (
            <div>
                {
                    this.props.testing.length !== 0 ?
                        <div className="table-container">
                            <table className="table table-bordered custom-table" >
                                <thead className="custom-thead" style={{
                                    border: "6px solid black"
                                }}>
                                    <tr style={{
                                        backgroundColor: "#404040",
                                        border: "6px solid black",
                                        color: "white",
                                    }}>
                                        <th>Confidence (%)</th>
                                        <th >Indian name</th>
                                        <th>LOINC code (edit) </th>
                                        <th>Component</th>
                                        <th>Property</th>
                                        <th>Time Aspct</th>
                                        <th>System</th>
                                        <th>Scale</th>
                                        <th>Method</th>
                                        <th>Class</th>
                                        <th>Long Common Name</th>
                                        <th >Short Name</th>
                                    </tr>
                                </thead>
                                <tbody
                                    style={{
                                        backgroundColor: "lightyellow",
                                        border: "6px solid black"
                                    }}>
                                    {
                                        this.props.testing.map(
                                            (tst, i) =>
                                                <tr key={i}
                                                    style={{ backgroundColor: this.checkrange(tst.percentage) }}>
                                                    <td>{tst.percentage}</td>
                                                    <td>{tst.indianname}</td>
                                                    <td className="highlight-on-hover"
                                                        onClick={() => this.props.editoperation(tst.loincCode)}>{tst.loincCode}</td>
                                                    <td>{tst.component}</td>
                                                    <td>{tst.property}</td>
                                                    <td>{tst.time_ASPCT}</td>
                                                    <td>{tst.system}</td>
                                                    <td>{tst.scale}</td>
                                                    <td>{tst.method}</td>
                                                    <td>{tst.class_}</td>
                                                    <td>{tst.long_COMMON_NAME}</td>
                                                    <td>{tst.shortname_}</td>
                                                </tr>
                                        )
                                    }

                                </tbody>

                            </table>
                        </div>
                        : <></>
                }
            </div>
        )
    }
}
