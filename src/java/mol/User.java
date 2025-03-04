/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mol;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class User {

    private int id;
    private String user;
    private String password;
    private int isSell;
    private int isAdmin;
    private String email;
    private String phone;
    private String address;
    private String birthdate;
    private String gender;
    private String avatar;

    public User() {
    }

    public User(int id, String user, String password, int isSell, int isAdmin, String email, String phone, String address, String gender, String birthdate , String avatar) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.isSell = isSell;
        this.isAdmin = isAdmin;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthdate = birthdate;
        this.gender = gender;
        this.avatar = avatar;
    }

   
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

   
    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getIsSell() {
        return isSell;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsSell(int isSell) {
        this.isSell = isSell;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", user=" + user + ", password=" + password + ", isSell=" + isSell + ", isAdmin=" + isAdmin + ", email=" + email + ", phone=" + phone + ", address=" + address + ", birthdate=" + birthdate + ", gender=" + gender + ", avatar=" + avatar + '}';
    }

   
   
}
