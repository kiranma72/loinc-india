package com.example.MapIt.tests;

public class BulkTest {
	private String indianname;
	private String LoincCode;
	public BulkTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "BulkTest [indianname=" + indianname + ", LoincCode=" + LoincCode + ", LONG_COMMON_NAME="
				+ LONG_COMMON_NAME + ", Component=" + Component + ", Property=" + Property + ", TIME_ASPCT="
				+ TIME_ASPCT + ", System=" + System + ", Scale=" + Scale + ", Method=" + Method + ", Class_=" + Class_
				+ ", CLASSTYPE_=" + CLASSTYPE_ + ", SHORTNAME_=" + SHORTNAME_ + ", STATUS_=" + STATUS_ + ", Percentage="
				+ Percentage + ", CheckingName=" + CheckingName + "]";
	}
	public BulkTest(String indianname, String loincCode, String lONG_COMMON_NAME, String component, String property,
			String tIME_ASPCT, String system, String scale, String method, String class_, String cLASSTYPE_,
			String sHORTNAME_, String sTATUS_, double percentage, String checkingName) {
		super();
		this.indianname = indianname;
		LoincCode = loincCode;
		LONG_COMMON_NAME = lONG_COMMON_NAME;
		Component = component;
		Property = property;
		TIME_ASPCT = tIME_ASPCT;
		System = system;
		Scale = scale;
		Method = method;
		Class_ = class_;
		CLASSTYPE_ = cLASSTYPE_;
		SHORTNAME_ = sHORTNAME_;
		STATUS_ = sTATUS_;
		Percentage = percentage;
		CheckingName = checkingName;
	}
	private String LONG_COMMON_NAME;
	private String Component;
	private String Property;
	private String TIME_ASPCT;
	private String System;
	private String Scale;
	private String Method;
	private String Class_;
	private String CLASSTYPE_;
	private String SHORTNAME_;
	private String STATUS_;
	private double Percentage;
	private String CheckingName;
	public String getIndianname() {
		return indianname;
	}
	public void setIndianname(String indianname) {
		this.indianname = indianname;
	}
	public String getLoincCode() {
		return LoincCode;
	}
	public void setLoincCode(String loincCode) {
		LoincCode = loincCode;
	}
	public String getLONG_COMMON_NAME() {
		return LONG_COMMON_NAME;
	}
	public void setLONG_COMMON_NAME(String lONG_COMMON_NAME) {
		LONG_COMMON_NAME = lONG_COMMON_NAME;
	}
	public String getComponent() {
		return Component;
	}
	public void setComponent(String component) {
		Component = component;
	}
	public String getProperty() {
		return Property;
	}
	public void setProperty(String property) {
		Property = property;
	}
	public String getTIME_ASPCT() {
		return TIME_ASPCT;
	}
	public void setTIME_ASPCT(String tIME_ASPCT) {
		TIME_ASPCT = tIME_ASPCT;
	}
	public String getSystem() {
		return System;
	}
	public void setSystem(String system) {
		System = system;
	}
	public String getScale() {
		return Scale;
	}
	public void setScale(String scale) {
		Scale = scale;
	}
	public String getMethod() {
		return Method;
	}
	public void setMethod(String method) {
		Method = method;
	}
	public String getClass_() {
		return Class_;
	}
	public void setClass_(String class_) {
		Class_ = class_;
	}
	public String getCLASSTYPE_() {
		return CLASSTYPE_;
	}
	public void setCLASSTYPE_(String cLASSTYPE_) {
		CLASSTYPE_ = cLASSTYPE_;
	}
	public String getSHORTNAME_() {
		return SHORTNAME_;
	}
	public void setSHORTNAME_(String sHORTNAME_) {
		SHORTNAME_ = sHORTNAME_;
	}
	public String getSTATUS_() {
		return STATUS_;
	}
	public void setSTATUS_(String sTATUS_) {
		STATUS_ = sTATUS_;
	}
	public double getPercentage() {
		return Percentage;
	}
	public void setPercentage(double percentage) {
		Percentage = percentage;
	}
	public String getCheckingName() {
		return CheckingName;
	}
	public void setCheckingName(String checkingName) {
		CheckingName = checkingName;
	}
	
}
