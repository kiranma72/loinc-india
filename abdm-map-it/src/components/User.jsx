import React, { Component } from 'react'
import { useParams } from 'react-router-dom/cjs/react-router-dom.min'
import axios from 'axios';
const testurl = "http://localhost:9191/FullTest/lucenetry/";
const User = (test) =>{
    const {fname}=useParams();
    return (
        axios.get(testurl+""+test),
        console.log("made the request"),
        <h1>
             Ram Ram  {fname} bhai  
        </h1>
    )
}
export default User ; 
