import React, { useState } from 'react';
import Papa from 'papaparse';
import LOINClogo from "./images/loinc-logo-tmp.png"
import axios from 'axios';
import dragdroppic from "./images/upload.png"
import { CSVLink } from 'react-csv';
const headers = [
  { label: "Confidence (%)", key: "percentage" },
  { label: "Name Provided", key: "checkingName" },
  { label: "Indian name", key: "indianname" },
  { label: "LOINC code", key: "loincCode" },
  { label: "Component", key: "component" },
  { label: "Property", key: "property" },
  { label: "Time Aspct", key: "time_ASPCT" },
  { label: "System", key: "system" },
  { label: "Scale", key: "scale" },
  { label: "Method", key: "method" },
  { label: "Class", key: "class_" },
  { label: "Classtype", key: "classtype_" },
  { label: "Long Common Name", key: "long_COMMON_NAME" },
  { label: "Short Name", key: "shortname_" },
];

var testdata = [];
function BulkTest() {
  const [tests, setTests] = useState([]);
  const handleFile = async (event) => {
    const selectedFile = event.target.files[0];

    if (selectedFile) {
      Papa.parse(event.target.files[0], {
        header: true,
        skipEmptyLines: true,
        complete: async (result) => {
          const valuesArray = result.data.map((d) => Object.values(d).join(''));
          const newTests = await Promise.all(valuesArray.map(onSearchClick));
          csvdataPrep(newTests);
          setTests(newTests);
        }
      });
    }
  };
  const csvdataPrep = (alltests) => {
    for (let t = 0; t < alltests.length; t++) {
      for (let g = 0; g < alltests[t].length; g++) {
        testdata.push(alltests[t][g]);
      }
    }
  }


  const onSearchClick = async (testValue) => {
    const encodeval = encodeURIComponent(testValue);
    const response = await axios.get("http://localhost:9191/FullTest/bulktest/", { params: { name: encodeval } });
    return response.data;
  };
  const checkrange = (val) => {
    if (val >= 90) { return 'PaleGreen'; }
    else if (val >= 50) {
      return 'LightGoldenRodYellow';
    }
    else {
      return 'BlanchedAlmond';
    }
  }
  return (
    <div className="App">
      <div className="navbar" style={{ width: '100%' }}>
        <div className="nav-column">
          <img src={LOINClogo} alt="Jai Hind" style={{ width: "100%", height: "25px" }} />
        </div>
        <div className="nav-column">
          <span>BULK TEST MAPPING</span>
        </div>
        <div className="nav-column">
          <CSVLink data={testdata} headers={headers} filename="TestResults.csv" style={{ backgroundColor: "lightgreen" }}>
            <button className="btn btn-primary mb-2">Download CSV File</button>
          </CSVLink>
        </div>
      </div>
      <div className="container" style={{ margin: '25px auto ' }}>
        <div className="row">
          <div className="col-md-9 d-flex justify-content-left align-items-center">
            <input id="inputtag" type='file' name='file' accept='.csv' onChange={handleFile} style={{
              justifyContent: 'center',
            }}></input>
          </div>


        </div>
      </div>
      <table className="table table-bordered" style={{ margin: "10px auto" }}>
        <thead>
          <tr style={{
            backgroundColor: "transparent",
            border: "3px solid black",
            color: "black",

          }}>
            <th>Confidence (%)</th>
            <th >Name Provided</th>
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
            {/* ... Add more headers as needed */}
          </tr>
        </thead>
        <tbody>
          {tests.map((val, index) => (
            val.map((value, i) => (<tr key={i} style={{ backgroundColor: checkrange(value.percentage) }}>
              <td>{value.percentage}</td>
              <td>{value.checkingName}</td>
              <td key={index}>{value.indianname}</td>
              <td>{value.loincCode}</td>
              <td>{value.component}</td>
              <td>{value.property}</td>
              <td>{value.time_ASPCT}</td>
              <td>{value.system}</td>
              <td>{value.scale}</td>
              <td>{value.method}</td>
              <td>{value.class_}</td>
              <td>{value.classtype_}</td>
              <td>{value.long_COMMON_NAME}</td>
              <td>{value.shortname_}</td>
            </tr>)
            )

          ))}
        </tbody>
      </table>
    </div>
  );
}

export default BulkTest;
