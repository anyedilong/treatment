package com.java.moudle.system.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.java.until.dba.BaseDomain;

/**
 * 平台信息表(tm_platform)
 * 
 * @author tz
 * @version 1.0.0 2020-01-07
 */
@Entity
@Table(name = "tm_platform")
public class TmPlatform extends BaseDomain {
    /** 版本号 */
    private static final long serialVersionUID = -1158978770270717723L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 唯一辨识 */
    @Id
    private String id;

    /** 名称 */
    private String name;

    /** 系统logo */
    private String logo;

    /** 院内审核开关（0.关闭 1.开启） */
    private String status;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取唯一辨识
     * 
     * @return 唯一辨识
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置唯一辨识
     * 
     * @param id
     *          唯一辨识
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取名称
     * 
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     * 
     * @param name
     *          名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取系统logo
     * 
     * @return 系统logo
     */
    public String getLogo() {
        return this.logo;
    }

    /**
     * 设置系统logo
     * 
     * @param logo
     *          系统logo
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * 获取院内审核开关（0.关闭 1.开启）
     * 
     * @return 院内审核开关（0
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置院内审核开关（0.关闭 1.开启）
     * 
     * @param status
     *          院内审核开关（0
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}