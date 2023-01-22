package com.driver.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="Admin")
public class Admin{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String username;

    private String password;

    public int getAdminId() {
        return id;
    }

    public void setAdminId(int id) {
        this.id = id;
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

    public Admin() {
    }

    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}