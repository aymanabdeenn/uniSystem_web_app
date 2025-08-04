package com.example.uniSystem_web_app.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Admin {
    @Id
    @SequenceGenerator(
            name = "admin-sequence",
            sequenceName = "admin-sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "admin-sequence"
    )
    private Long id;

    @Column(
            nullable=false
    )
    private String name;

    public Admin(){}

    public Admin(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
