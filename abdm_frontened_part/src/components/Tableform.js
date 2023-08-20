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
        if (val >= 90) { return 'bg-success'; }
        else if (val >= 50) {
            return 'bg-warning';
        }
        else {
            return 'bg-danger';
        }
    }
    // editoperation(val) {
    //     console.log(val);
    //     while (ar.length > 0) { ar.pop() }
    //     axios.get(testurl + "" + val).then((res) => {
    //         ar.push(res.data);
    //         // this.setState({ edittestinginfo: res.data });
    //     });
    //     this.state.startedit = 'ok';
    //     console.log("new array=");
    //     console.log(ar);
    // }
    render() {
        return (
            <div><div className="container">
                {
                    this.props.testing.length !== 0 ?
                        <div className="table-container">
                            <table className="table table-bordered custom-table" >
                                <thead className="custom-thead">
                                    <tr className="table-info">
                                        <th>Confidence (%)</th>
                                        <th >Indian name</th>
                                        <th>LOINC code </th>
                                        <th>Component</th>
                                        <th>Property</th>
                                        <th>Time Aspct</th>
                                        <th>System</th>
                                        <th>Scale</th>
                                        <th>Method</th>
                                        <th>Class</th>
                                        <th>Classtype</th>
                                        <th>Long Common Name</th>
                                        <th >Short Name</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.props.testing.map(
                                            (tst, i) =>
                                                <tr key={i}
                                                    className={this.checkrange(tst.percentage)} >
                                                    <td>{tst.percentage}</td>
                                                    <td>{tst.indianname}</td>
                                                    <td>{tst.loincCode}</td>
                                                    <td>{tst.component}</td>
                                                    <td>{tst.property}</td>
                                                    <td>{tst.time_ASPCT}</td>
                                                    <td>{tst.system}</td>
                                                    <td>{tst.scale}</td>
                                                    <td>{tst.method}</td>
                                                    <td>{tst.class_}</td>
                                                    <td>{tst.classtype_}</td>
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
            </div></div>
        )
    }
}
