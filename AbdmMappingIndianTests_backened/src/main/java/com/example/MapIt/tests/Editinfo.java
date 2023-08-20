package com.example.MapIt.tests;

public class Editinfo {
  String loinccode;
  String indianname;
public String getLoinccode() {
	return loinccode;
}
public void setLoinccode(String loinccode) {
	this.loinccode = loinccode;
}
public String getIndianname() {
	return indianname;
}
public Editinfo() {
	super();
	// TODO Auto-generated constructor stub
}
public Editinfo(String loinccode, String indianname) {
	super();
	this.loinccode = loinccode;
	this.indianname = indianname;
}
public void setIndianname(String indianname) {
	this.indianname = indianname;
}
}
