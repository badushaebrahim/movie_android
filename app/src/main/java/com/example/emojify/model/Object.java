package com.example.emojify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Object {
    @SerializedName("lid")
    @Expose
    private String lid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("utype")
    @Expose
    private String utype;

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }
}
