import React, { useState } from 'react';
import Papa from 'papaparse';
import axios from 'axios';
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

const testurl = "http://localhost:9191/FullTest/bulktest";

function BulkTest() {
  const [tests, setTests] = useState([]);

  const handleFile = async (event) => {
    Papa.parse(event.target.files[0], {
      header: true,
      skipEmptyLines: true,
      complete: async (result) => {
        const valuesArray = result.data.map((d) => Object.values(d).join(''));
        const newTests = await Promise.all(valuesArray.map(onSearchClick));
        setTests(newTests);
      }
    });
  };

  const onSearchClick = async (testValue) => {
    const response = await axios.get(testurl + "/" + encodeURIComponent(testValue));
    return response.data;
  };
  const checkrange = (val) => {
    if (val >= 90) { return 'bg-success'; }
    else if (val >= 50) {
      return 'bg-warning';
    }
    else {
      return 'bg-danger';
    }
  }
  return (
    <div className="App">
      <div className='row'>
        <input type='file' name='file' accept='.csv' onChange={handleFile}
          style={{ display: "block", margin: "10px auto", border: "4px solid blue", backgroundColor: "lightgreen" }}>
        </input>
        <CSVLink data={tests} headers={headers} filename="parents.csv">
          <button className="btn btn-primary mb-2">Export</button>
        </CSVLink></div>
      <table className="table table-bordered" style={{ margin: "10px 15px 15px" }}>
        <thead>
          <tr className="table-info" style={{
            backgroundColor: "red",
            border: "6px solid pink"
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
            val.map((value, i) => (<tr key={i} className={checkrange(value.percentage)}>
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
