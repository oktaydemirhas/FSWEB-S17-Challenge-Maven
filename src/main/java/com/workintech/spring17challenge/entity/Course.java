package com.workintech.spring17challenge.entity;

public class Course {
    private Integer id;
    private String name;
    private Integer credit;
    private Grade grade;

    public Course() {}

    public Course(Integer id, String name, Integer credit, Grade grade) {
        this.id = id;
        this.name = name;
        this.credit = credit;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
} 