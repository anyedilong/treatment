package com.java.until.validation;

import com.java.until.validate.Validate;
import com.java.until.validate.ValidateType;
import com.java.until.validate.VldTestArray;

import java.util.List;

public class VldTest {



    @Validate(name={"save"},required = true)
    private String id;


    private String phone;


    private String remarks;


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
