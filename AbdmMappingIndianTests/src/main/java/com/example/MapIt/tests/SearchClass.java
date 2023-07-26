package com.example.MapIt.tests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.codec.language.Soundex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SearchTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchClass {
	@Id
	private String indianname;
	private String loinccode;
	private String methodused;
	private String specimentype;
	private String phoneticmethodused;
	private String phoneticspecimentype;
	private String phoneticindianname;
	public String getIndianname() {
		return indianname;
	}
	public void setIndianname(String indianname) {
		this.indianname = indianname;
	}
	public String getLoinccode() {
		return loinccode;
	}
	public void setLoinccode(String loinccode) {
		this.loinccode = loinccode;
	}
	public String getMethodused() {
		return methodused;
	}
	public void setMethodused(String methodused) {
		this.methodused = methodused;
	}
	public String getSpecimentype() {
		return specimentype;
	}
	public void setSpecimentype(String specimentype) {
		this.specimentype = specimentype;
	}
	public String getPhoneticmethodused() {
		return phoneticmethodused;
	}
	public void setPhoneticmethodused(String phoneticmethodused) {
		Soundex soundex = new Soundex();
		phoneticmethodused = soundex.encode(phoneticmethodused);
	}
	public String getPhoneticspecimentype() {
		return phoneticspecimentype;
	}
	public void setPhoneticspecimentype(String phoneticspecimentype) {
		Soundex soundex = new Soundex();
		phoneticspecimentype = soundex.encode(phoneticspecimentype);
	}
	public String getPhoneticindianname() {
		return phoneticindianname;
	}
	public void setPhoneticindianname(String phoneticindianname) {
		Soundex soundex = new Soundex();
		phoneticindianname = soundex.encode(phoneticindianname);
	}
	
	@Override
	public String toString() {
		return "SearchClass [indianname=" + indianname + ", loinccode=" + loinccode + ", methodused=" + methodused
				+ ", specimentype=" + specimentype + ", Phoneticmethodused=" + phoneticmethodused
				+ ", Phoneticspecimentype=" + phoneticspecimentype + ", Phoneticindianname=" + phoneticindianname + "]";
	}
	public SearchClass(String indianname, String loinccode, String methodused, String specimentype,
			String phoneticmethodused, String phoneticspecimentype, String phoneticindianname) {
		super();
		this.indianname = indianname;
		this.loinccode = loinccode;
		this.methodused = methodused;
		this.specimentype = specimentype;
		this.phoneticmethodused = phoneticmethodused;
		this.phoneticspecimentype = phoneticspecimentype;
		this.phoneticindianname = phoneticindianname;
	}
	public SearchClass() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
