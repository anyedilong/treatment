package com.java.until.validate;

import java.util.List;

public class VldTest {
	private List<VldTestArray> testArray;

	@Validate(fieldName = "测试")
	private VldTestArray test;

	@Validate(required = true, fieldName = "aa")
	private String id;

	@Validate(required = true, length = 11, type = { ValidateType.MOBILE,ValidateType.NUMBER }, name = { "addSys" })
	private String phone;

	@Validate(minLength = 10, maxLength = 30)
	private String remarks;

	public VldTestArray getTest() {
		return test;
	}

	public void setTest(VldTestArray test) {
		this.test = test;
	}

	public List<VldTestArray> getTestArray() {
		return testArray;
	}

	public void setTestArray(List<VldTestArray> testArray) {
		this.testArray = testArray;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
