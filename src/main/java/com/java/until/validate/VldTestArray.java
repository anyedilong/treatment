package com.java.until.validate;

public class VldTestArray {

	@Validate(required = true)
	private String id;

	@Validate(name = { "acc" }, required = true, type = { ValidateType.TELEPHONE })
	private String tele;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTele() {
		return tele;
	}

	public void setTele(String tele) {
		this.tele = tele;
	}

}
