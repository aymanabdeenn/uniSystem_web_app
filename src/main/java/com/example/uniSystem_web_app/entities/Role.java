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
    String name;

    @ManyToMany(
            mappedBy="roles"
    )
    List<Account> accounts = new ArrayList<Account>();

    public Role(){}

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
