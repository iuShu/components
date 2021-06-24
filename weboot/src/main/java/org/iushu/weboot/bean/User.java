package org.iushu.weboot.bean;

/**
 * the user that accesses application
 *
 * @author iuShu
 * @since 6/24/21
 */
public class User {

    private short user_id;
    private short role;
    private String first_name;
    private String last_name;
    private String username;
    private String password;

    public short getUser_id() {
        return user_id;
    }

    public void setUser_id(short user_id) {
        this.user_id = user_id;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", role=" + role +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
