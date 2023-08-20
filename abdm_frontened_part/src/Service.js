import axios from 'axios';
import reactRouter from 'react-router';
import React, { Component } from 'react'
var edittest = 'abc';
class Service {

    // constructor(props) {

    //     this.Setedittest = this.Setedittest.bind(this);
    //     this.Getedittest = this.Getedittest.bind(this);
    // }
    Setedittest(test) {
        console.log("Settingit in the Service");
        edittest = test;
        console.log(test);
        console.log(edittest)
        console.log("Setit");
    }
    Getedittest() {
        return edittest;
    }
}

export default new Service()