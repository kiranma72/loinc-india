package com.example.MapIt.tests;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Idstore")
@Data
public class IDstore {
	@Id
  String emailid;

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public IDstore() {
		// TODO Auto-generated constructor stub
	}

	public IDstore(String emailid) {
		this.emailid = emailid;
	} 
}
