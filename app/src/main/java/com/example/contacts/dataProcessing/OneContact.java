package com.example.contacts.dataProcessing;

import androidx.annotation.NonNull;

public class OneContact {
    private String name, gander, phone, email, address, signature;
    private boolean hobby1_singing, hobby2_dancing, hobby3_sport;

    public OneContact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public OneContact(String name, String gander, String phone, String email, String address, String signature, boolean hobby1_singing, boolean hobby2_dancing, boolean hobby3_sport) {
        this.name = name;
        this.gander = gander;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.signature = signature;
        this.hobby1_singing = hobby1_singing;
        this.hobby2_dancing = hobby2_dancing;
        this.hobby3_sport = hobby3_sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGander() {
        return gander;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isHobby1_singing() {
        return hobby1_singing;
    }

    public void setHobby1_singing(boolean hobby1_singing) {
        this.hobby1_singing = hobby1_singing;
    }

    public boolean isHobby2_dancing() {
        return hobby2_dancing;
    }

    public void setHobby2_dancing(boolean hobby2_dancing) {
        this.hobby2_dancing = hobby2_dancing;
    }

    public boolean isHobby3_sport() {
        return hobby3_sport;
    }

    public void setHobby3_sport(boolean hobby3_sport) {
        this.hobby3_sport = hobby3_sport;
    }

    @NonNull
    @Override
    public String toString() {
        return name + ":" + gander + ":" + phone + ":" + email + ":" + address + ":" + signature;
    }
}
