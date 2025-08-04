package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Role {

    @Id
    @SequenceGenerator(
            name = "role-sequence",
            sequenceName = "role-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role-sequence"
    )
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    String roleName;

    @ManyToMany(
            mappedBy="roles"
    )
    List<Account> accounts = new ArrayList<Account>();

    public Role(){}

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
