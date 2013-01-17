package _01_register.model;

import java.io.*;
import java.sql.*;

public class MemberBean implements Serializable {
	private static final long serialVersionUID = 1L;
	int   pKey;
	String memberId;
	String password;
	String name;
	String address;
	String email;
	String tel;
	String userType;
	int expericnce;
	Timestamp ts;
	double totalAmt;

	public MemberBean(int pKey, String memberId, String name, String password, String address,  String mail,
			String tel, String userType ,int expericnce, Timestamp ts, double totalAmt) {
		super();
		this.pKey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = mail;
		this.tel = tel;
		this.userType = userType;		
		this.expericnce = expericnce;
		this.ts = ts;
		this.totalAmt = totalAmt;
	}
	
	public MemberBean(String memberId, String name, String password, String address,  String mail,
			String tel, int expericnce) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = mail;
		this.tel = tel;
		this.expericnce = expericnce;
	}
	

	public MemberBean() {
		super();
	}

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String mail) {
		email = mail;
	}

	public int getExpericnce() {
		return expericnce;
	}

	public void setExpericnce(int expericnce) {
		this.expericnce = expericnce;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String toString() {
		return "userid=" + memberId + "   password="+password;
	}

	public String getUserType() {
		return userType;
	}

	public Timestamp getTs() {
		return ts;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

}
