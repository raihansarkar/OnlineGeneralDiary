package com.example.raihansarkar.onlinegeneraldiary;

/**
 * Created by raiha on 1/27/2018.
 */

public class Item {
    private int idText;
    private String idString;
    private String gd_form;
    private String mobile;
    private String gd_Station;
    private String gd_email;
    private String gd_date;
    private String gd_number;

    public Item()
    {

    }

    public Item(String idString) {
        this.idString = idString;
    }

    public Item(int idText, String gd_form, String mobile, String gd_Station, String gd_email) {
        this.idText = idText;
        this.gd_form = gd_form;
        this.mobile = mobile;
        this.gd_Station = gd_Station;
        this.gd_email = gd_email;
    }

    public Item(String gd_form, String mobile, String gd_Station, String gd_email) {
        this.idText = idText;
        this.gd_form = gd_form;
        this.mobile = mobile;
        this.gd_Station = gd_Station;
        this.gd_email = gd_email;
    }

    public Item(String gd_form, String mobile, String gd_Station, String gd_email, String gd_date, String gd_number) {
        this.gd_form = gd_form;
        this.mobile = mobile;
        this.gd_Station = gd_Station;
        this.gd_email = gd_email;
        this.gd_date = gd_date;
        this.gd_number = gd_number;
    }

    public Item(String idString, String gd_form, String mobile, String gd_Station, String gd_email, String gd_date, String gd_number) {
        this.idString = idString;
        this.gd_form = gd_form;
        this.mobile = mobile;
        this.gd_Station = gd_Station;
        this.gd_email = gd_email;
        this.gd_date = gd_date;
        this.gd_number = gd_number;
    }

    public Item(int idText, String gd_form, String mobile, String gd_Station) {
        this.idText = idText;
        this.gd_form = gd_form;
        this.mobile = mobile;
        this.gd_Station = gd_Station;
    }

    public Item(int idText, String gd_form, String mobile) {
        this.idText = idText;
        this.gd_form = gd_form;
        this.mobile = mobile;
    }

    public int getIdText() {
        return idText;
    }

    public void setIdText(int idText) {
        this.idText = idText;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getGd_form() {
        return gd_form;
    }

    public void setGd_form(String gd_form) {
        this.gd_form = gd_form;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGd_Station() {
        return gd_Station;
    }

    public void setGd_Station(String gd_Station) {
        this.gd_Station = gd_Station;
    }

    public String getGd_email() {
        return gd_email;
    }

    public void setGd_email(String gd_email) {
        this.gd_email = gd_email;
    }

    public String getGd_date() {
        return gd_date;
    }

    public void setGd_date(String gd_date) {
        this.gd_date = gd_date;
    }

    public String getGd_number() {
        return gd_number;
    }

    public void setGd_number(String gd_number) {
        this.gd_number = gd_number;
    }
}
