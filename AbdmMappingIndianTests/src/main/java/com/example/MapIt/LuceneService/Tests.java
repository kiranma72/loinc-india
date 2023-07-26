package com.example.MapIt.LuceneService;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tests {
	private String indianname;
	private String loinccode;
	private String methodused;
	private String phonecticIn;
	private String phonecticlc;
	private String phonecticmt;
	private String specimentype;
	public Tests(String indianname, String loinccode, String methodused, String phonecticIn, String phonecticlc,
			String phonecticmt, String specimentype, List<String> genres) {
		super();
		this.indianname = indianname;
		this.loinccode = loinccode;
		this.methodused = methodused;
		this.phonecticIn = phonecticIn;
		this.phonecticlc = phonecticlc;
		this.phonecticmt = phonecticmt;
		this.specimentype = specimentype;
		this.genres = genres;
	}
	public String getPhonecticIn() {
		return phonecticIn;
	}
	public void setPhonecticIn(String phonecticIn) {
		this.phonecticIn = phonecticIn;
	}
	public String getPhonecticlc() {
		return phonecticlc;
	}
	public void setPhonecticlc(String phonecticlc) {
		this.phonecticlc = phonecticlc;
	}
	public String getPhonecticmt() {
		return phonecticmt;
	}
	public void setPhonecticmt(String phonecticmt) {
		this.phonecticmt = phonecticmt;
	}
	private List<String> genres;
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
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Tests [indianname=" + indianname + ", loinccode=" + loinccode + ", methodused=" + methodused
				+ ", specimentype=" + specimentype + ", genres=" + genres + "]";
	}
	public Tests() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
