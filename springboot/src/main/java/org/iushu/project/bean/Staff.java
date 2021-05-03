package org.iushu.project.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author iuShu
 * @since 4/6/21
 */
public class Staff implements Serializable {

    private static final long serialVersionUID = 8091348339728321510L;

    private int id;
    private String name;
    private int deptId;
    private int level;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deptId=" + deptId +
                ", level=" + level +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
