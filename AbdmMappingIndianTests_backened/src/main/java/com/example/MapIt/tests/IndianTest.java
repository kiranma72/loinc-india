package com.example.MapIt.tests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "IndianTestTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndianTest {
	@Id
	private String LOINC_Code;
	private String Aliases_;
	public String getLOINC_Code() {
		return LOINC_Code;
	}
	public void setLOINC_Code(String lOINC_Code) {
		LOINC_Code = lOINC_Code;
	}
	public String getAliases_() {
		return Aliases_;
	}
	public void setAliases_(String aliases_) {
		Aliases_ = aliases_;
	}
	public IndianTest(String lOINC_Code, String aliases_) {
		super();
		LOINC_Code = lOINC_Code;
		Aliases_ = aliases_;
	}
	public IndianTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "IndianTest [LOINC_Code=" + LOINC_Code + ", Aliases_=" + Aliases_ + "]";
	}
	 

}
