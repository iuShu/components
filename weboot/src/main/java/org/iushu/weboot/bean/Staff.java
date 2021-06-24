package org.iushu.weboot.bean;

import java.util.Arrays;
import java.util.Date;

/**
 * @author iuShu
 * @since 6/24/21
 */
public class Staff {

    private short staff_id;
    private String first_name;  // 45
    private String last_name;   // 45
    private int address_id;
    private byte[] picture;     // blob
    private String email;       // 50
    private short store_id;
    private byte active;
    private String username;    // 16
    private String password;
    private Date last_update;

    public short getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(short staff_id) {
        this.staff_id = staff_id;
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

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public short getStore_id() {
        return store_id;
    }

    public void setStore_id(short store_id) {
        this.store_id = store_id;
    }

    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
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

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_id=" + staff_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", address_id=" + address_id +
                ", picture=" + Arrays.toString(picture) +
                ", email='" + email + '\'' +
                ", store_id=" + store_id +
                ", active=" + active +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", last_update=" + last_update +
                '}';
    }
}
