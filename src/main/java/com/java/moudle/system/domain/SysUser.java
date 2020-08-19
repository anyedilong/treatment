package com.java.moudle.system.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.java.until.dba.BaseDomain;
import com.java.until.dict.DictUtil;

/**
 * 用户表(tm_user)
 * 
 * @author tz
 * @version 1.0.0 2020-01-03
 */
@Entity
@Table(name = "tm_user")
public class SysUser extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = -2585675974565868471L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 唯一标示 */
    @Id
    private String id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 真实姓名 */
    private String name;

    /** 状态 */
    private String status;
    
    private String depId;//所属科室
    private String profession;//职称
    private String pwd;//密码明文
    private String authorities;//角色标识
    private String orgId;
    
    @Transient
    private String orgName;
    @Transient
    private String depName;
    @Transient
    private String professionName;
    @Transient
    private SysRole role;
    @Transient
    private String isDel;//是否删除

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取唯一标示
     * 
     * @return 唯一标示
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置唯一标示
     * 
     * @param id
     *          唯一标示
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置用户名
     * 
     * @param username
     *          用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     * 
     * @param password
     *          密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取真实姓名
     * 
     * @return 真实姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置真实姓名
     * 
     * @param name
     *          真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取状态
     * 
     * @return 状态
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置状态
     * 
     * @param status
     *          状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.professionName = DictUtil.getDictValue("yszc", profession);
		this.profession = profession;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProfessionName() {
		return professionName;
	}

	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

    /* This code was generated by TableGo tools, mark 2 end. */
}