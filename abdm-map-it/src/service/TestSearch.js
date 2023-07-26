import axios from 'axios';
import { Component } from 'react';
const testurl = "http://localhost:9191/FullTest/lucene";
const loinctests = "http://localhost:9191/LoincTest/findall";
class TestSearch  {
    getTests(testid){
        return axios.get(testurl+"/"+testid);
    }
    getalltests(testids){
        console.log("getingtherequest");
        return axios.get(loinctests+"/"+testids);
    }
}
export default new TestSearch()

