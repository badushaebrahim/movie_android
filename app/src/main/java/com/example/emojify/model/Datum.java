package com.example.emojify.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    public Datum(String eid, String mid, String pcount, String ucount, String mname, String arating, String mlink, String movie, String singer, String director, String language) {
        this.eid = eid;
        this.mid = mid;
        this.pcount = pcount;
        this.ucount = ucount;
        this.mname = mname;
        this.arating = arating;
        this.mlink = mlink;
        this.movie = movie;
        this.singer = singer;
        this.director = director;
        this.language = language;

    }
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("sid")
    @Expose
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @SerializedName("ticket_id")
    @Expose
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @SerializedName("bdate")
    @Expose
    private String bdate;

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    @SerializedName("qrcode")
    @Expose
    private String qrcode;

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    @SerializedName("numticket")
    @Expose
    private String numticket;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("mpic")
    @Expose
    private String mpic;

    public String getNumticket() {
        return numticket;
    }

    public void setNumticket(String numticket) {
        this.numticket = numticket;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMpic() {
        return mpic;
    }

    public void setMpic(String mpic) {
        this.mpic = mpic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @SerializedName("director")
    @Expose
    private String director;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @SerializedName("scap")
    @Expose
    private String scap;
    @SerializedName("taddress")
    @Expose
    private String taddress;
    @SerializedName("tphone")
    @Expose
    private String tphone;

    public String getScap() {
        return scap;
    }

    public void setScap(String scap) {
        this.scap = scap;
    }

    public String getTaddress() {
        return taddress;
    }

    public void setTaddress(String taddress) {
        this.taddress = taddress;
    }

    public String getTphone() {
        return tphone;
    }

    public void setTphone(String tphone) {
        this.tphone = tphone;
    }

    @SerializedName("tname")
    @Expose
    private String tname;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    @SerializedName("mname")
    @Expose
    private String mname;
    @SerializedName("mimg")
    @Expose
    private String mimg;
    @SerializedName("star")
    @Expose
    private String star;

    public String getMimg() {
        return mimg;
    }

    public void setMimg(String mimg) {
        this.mimg = mimg;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("eid")
    @Expose
    private String eid;
    @SerializedName("emogy")
    @Expose
    private String emogy;
    @SerializedName("emotion_name")
    @Expose
    private String emotion_name;
    @SerializedName("mid")
    @Expose
    private String mid;
    @SerializedName("pcount")
    @Expose
    private String pcount;
    @SerializedName("ucount")
    @Expose
    private String ucount;

    public String getPcount() {
        return pcount;
    }

    public void setPcount(String pcount) {
        this.pcount = pcount;
    }

    public String getUcount() {
        return ucount;
    }

    public void setUcount(String ucount) {
        this.ucount = ucount;
    }

    @SerializedName("arating")
    @Expose
    private String arating;

    public String getArating() {
        return arating;
    }

    public void setArating(String arating) {
        this.arating = arating;
    }

    @SerializedName("mlink")
    @Expose
    private String mlink;
    @SerializedName("movie")
    @Expose
    private String movie;
    @SerializedName("singer")
    @Expose
    private String singer;

    @SerializedName("language")
    @Expose
    private String language;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMlink() {
        return mlink;
    }

    public void setMlink(String mlink) {
        this.mlink = mlink;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getEmogy() {
        return emogy;
    }

    public void setEmogy(String emogy) {
        this.emogy = emogy;
    }

    public String getEmotion_name() {
        return emotion_name;
    }

    public void setEmotion_name(String emotion_name) {
        this.emotion_name = emotion_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @SerializedName("rid")
    @Expose
    private String rid;

    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("uid")
    @Expose
    private String uid;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



}
