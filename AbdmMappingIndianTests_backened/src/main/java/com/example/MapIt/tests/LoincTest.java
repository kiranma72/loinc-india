package com.example.MapIt.tests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "LoincTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoincTest {
	@Id
	private String LOINC_NUM;
	private String COMPONENT_;
	private String PROPERTY_;
	private String TIME_ASPCT;
	private String SYSTEM_;
	private String SCALE_TYP;
	private String METHOD_TYP;
	private String CLASS_;
	private String CLASSTYPE_;
	private String LONG_COMMON_NAME;
	private String SHORTNAME_;
	private String STATUS_;
	private String VersionFirstReleased_;
	private String VersionLastChanged_;
	public String getLOINC_NUM() {
		return LOINC_NUM;
	}
	public void setLOINC_NUM(String lOINC_NUM) {
		LOINC_NUM = lOINC_NUM;
	}
	public String getCOMPONENT_() {
		return COMPONENT_;
	}
	public void setCOMPONENT_(String cOMPONENT_) {
		COMPONENT_ = cOMPONENT_;
	}
	public String getPROPERTY_() {
		return PROPERTY_;
	}
	public void setPROPERTY_(String pROPERTY_) {
		PROPERTY_ = pROPERTY_;
	}
	public String getTIME_ASPCT() {
		return TIME_ASPCT;
	}
	public void setTIME_ASPCT(String tIME_ASPCT) {
		TIME_ASPCT = tIME_ASPCT;
	}
	public String getSYSTEM_() {
		return SYSTEM_;
	}
	public void setSYSTEM_(String sYSTEM_) {
		SYSTEM_ = sYSTEM_;
	}
	public String getSCALE_TYP() {
		return SCALE_TYP;
	}
	public void setSCALE_TYP(String sCALE_TYP) {
		SCALE_TYP = sCALE_TYP;
	}
	public String getMETHOD_TYP() {
		return METHOD_TYP;
	}
	public void setMETHOD_TYP(String mETHOD_TYP) {
		METHOD_TYP = mETHOD_TYP;
	}
	public String getCLASS_() {
		return CLASS_;
	}
	public void setCLASS_(String cLASS_) {
		CLASS_ = cLASS_;
	}
	public String getCLASSTYPE_() {
		return CLASSTYPE_;
	}
	public void setCLASSTYPE_(String cLASSTYPE_) {
		CLASSTYPE_ = cLASSTYPE_;
	}
	public String getLONG_COMMON_NAME() {
		return LONG_COMMON_NAME;
	}
	public void setLONG_COMMON_NAME(String lONG_COMMON_NAME) {
		LONG_COMMON_NAME = lONG_COMMON_NAME;
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
	public String getVersionFirstReleased_() {
		return VersionFirstReleased_;
	}
	public void setVersionFirstReleased_(String versionFirstReleased_) {
		VersionFirstReleased_ = versionFirstReleased_;
	}
	public String getVersionLastChanged_() {
		return VersionLastChanged_;
	}
	public void setVersionLastChanged_(String versionLastChanged_) {
		VersionLastChanged_ = versionLastChanged_;
	}
	public LoincTest(String lOINC_NUM, String cOMPONENT_, String pROPERTY_, String tIME_ASPCT, String sYSTEM_,
			String sCALE_TYP, String mETHOD_TYP, String cLASS_, String cLASSTYPE_, String lONG_COMMON_NAME,
			String sHORTNAME_, String sTATUS_, String versionFirstReleased_,
			String versionLastChanged_) {
		super();
		LOINC_NUM = lOINC_NUM;
		COMPONENT_ = cOMPONENT_;
		PROPERTY_ = pROPERTY_;
		TIME_ASPCT = tIME_ASPCT;
		SYSTEM_ = sYSTEM_;
		SCALE_TYP = sCALE_TYP;
		METHOD_TYP = mETHOD_TYP;
		CLASS_ = cLASS_;
		CLASSTYPE_ = cLASSTYPE_;
		LONG_COMMON_NAME = lONG_COMMON_NAME;
		SHORTNAME_ = sHORTNAME_;
		STATUS_ = sTATUS_;
		VersionFirstReleased_ = versionFirstReleased_;
		VersionLastChanged_ = versionLastChanged_;
	}
	public LoincTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LoincTest [LOINC_NUM=" + LOINC_NUM + ", COMPONENT_=" + COMPONENT_ + ", PROPERTY_=" + PROPERTY_
				+ ", TIME_ASPCT=" + TIME_ASPCT + ", SYSTEM_=" + SYSTEM_ + ", SCALE_TYP=" + SCALE_TYP + ", METHOD_TYP="
				+ METHOD_TYP + ", CLASS_=" + CLASS_ + ", CLASSTYPE_=" + CLASSTYPE_ + ", LONG_COMMON_NAME="
				+ LONG_COMMON_NAME + ", SHORTNAME_=" + SHORTNAME_ +
				 ", STATUS_=" + STATUS_ + ", VersionFirstReleased_="
				+ VersionFirstReleased_ + ", VersionLastChanged_=" + VersionLastChanged_ + "]";
	}
	
	
}
