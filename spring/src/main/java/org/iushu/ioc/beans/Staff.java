package org.iushu.ioc.beans;

/**
 * @author iuShu
 * @since 1/5/21
 */
public class Staff {

    private int sid;
    private String name;
    private String title;
    private int dept;
    private int level;

    public void init() {
        System.out.println("[init] " + getName());
    }

    public void destroy() {
        System.out.println("[destroy] " + getName());
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", dept=" + dept +
                ", level=" + level +
                '}';
    }
}
