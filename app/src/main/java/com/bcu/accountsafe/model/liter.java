package com.bcu.accountsafe.model;

public class liter {
    private String literL;
    private Boolean has;

    public String getLiterL() {
        return literL;
    }

    public void setLiterL(String literL) {
        this.literL = literL;
    }

    public Boolean getHas() {
        return has;
    }

    public void setHas(Boolean has) {
        this.has = has;
    }

    public liter(String literL, Boolean has) {
        this.literL = literL;
        this.has = has;
    }
}
