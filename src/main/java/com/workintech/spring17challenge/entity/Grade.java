package com.workintech.spring17challenge.entity;

public class Grade {
    private Integer coefficient;
    private String note;

    public Grade() {}

    public Grade(Integer coefficient, String note) {
        this.coefficient = coefficient;
        this.note = note;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
} 