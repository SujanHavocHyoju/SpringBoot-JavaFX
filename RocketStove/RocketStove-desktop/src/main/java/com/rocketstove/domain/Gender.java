/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.domain;

/**
 *
 * @author SUJAN
 */
public class Gender {
    private String genderId;
    private String genderName;

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gender gender = (Gender) o;

        if (genderId != null ? !genderId.equals(gender.genderId) : gender.genderId != null) return false;
        return genderName != null ? genderName.equals(gender.genderName) : gender.genderName == null;
    }

    @Override
    public int hashCode() {
        int result = genderId != null ? genderId.hashCode() : 0;
        result = 31 * result + (genderName != null ? genderName.hashCode() : 0);
        return result;
    }
}

