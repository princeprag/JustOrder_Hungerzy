package com.example.justorder;

public class items_part {
    String ROOMNO;
    String IMAGEURL;
    String MOBILENO;
    String NAME;
    String PRICE;
    String ID,ORDER1VEG,ORDER2VEG,ORDER3VEG,ORDER1VEGN,ORDER2VEGN,ORDER3VEGN;
    String ORDER1NV;
    String ORDER2NV;
    String ORDER3NV;
    String ORDER1NVN;
    String ORDER2NVN;

    public String getORDER1VEGN() {
        return ORDER1VEGN;
    }

    public void setORDER1VEGN(String ORDER1VEGN) {
        this.ORDER1VEGN = ORDER1VEGN;
    }

    public String getORDER2VEGN() {
        return ORDER2VEGN;
    }

    public void setORDER2VEGN(String ORDER2VEGN) {
        this.ORDER2VEGN = ORDER2VEGN;
    }

    public String getORDER3VEGN() {
        return ORDER3VEGN;
    }

    public void setORDER3VEGN(String ORDER3VEGN) {
        this.ORDER3VEGN = ORDER3VEGN;
    }

    public String getORDER1NVN() {
        return ORDER1NVN;
    }

    public void setORDER1NVN(String ORDER1NVN) {
        this.ORDER1NVN = ORDER1NVN;
    }

    public String getORDER2NVN() {
        return ORDER2NVN;
    }

    public void setORDER2NVN(String ORDER2NVN) {
        this.ORDER2NVN = ORDER2NVN;
    }

    public String getORDER3NVN() {
        return ORDER3NVN;
    }

    public void setORDER3NVN(String ORDER3NVN) {
        this.ORDER3NVN = ORDER3NVN;
    }

    String ORDER3NVN;
    String PARENTID;



    String USERID;

    public items_part() {

    }


    public items_part(String PARENTID, String ROOMNO,String USERID, String IMAGEURL, String MOBILENO, String NAMEOFITEM, String NAME, String PRICE, String ID,String ORDER1VEG,String ORDER2VEG,String ORDER3VEG) {
        this.ROOMNO = ROOMNO;
        this.IMAGEURL = IMAGEURL;
        this.MOBILENO = MOBILENO;

        this.NAME = NAME;
        this.PRICE = PRICE;
        this.ID = ID;
        this.ORDER1VEG=ORDER1VEG;
        this.ORDER3VEG=ORDER3VEG;
        this.ORDER2VEG=ORDER2VEG;
        this.PARENTID=PARENTID;
        this.USERID=USERID;
    }


    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getROOMNO() {
        return ROOMNO;
    }

    public void setROOMNO(String ROOMNO) {
        this.ROOMNO = ROOMNO;
    }

    public String getORDER1VEG() {
        return ORDER1VEG;
    }

    public void setORDER1VEG(String ORDER1VEG) {
        this.ORDER1VEG = ORDER1VEG;
    }

    public String getORDER2VEG() {
        return ORDER2VEG;
    }

    public void setORDER2VEG(String ORDER2VEG) {
        this.ORDER2VEG = ORDER2VEG;
    }

    public String getORDER3VEG() {
        return ORDER3VEG;
    }

    public String getORDER1NV() {
        return ORDER1NV;
    }

    public void setORDER1NV(String ORDER1NV) {
        this.ORDER1NV = ORDER1NV;
    }

    public String getORDER2NV() {
        return ORDER2NV;
    }

    public void setORDER2NV(String ORDER2NV) {
        this.ORDER2NV = ORDER2NV;
    }

    public String getORDER3NV() {
        return ORDER3NV;
    }

    public void setORDER3NV(String ORDER3NV) {
        this.ORDER3NV = ORDER3NV;
    }

    public void setORDER3VEG(String ORDER3VEG) {
        this.ORDER3VEG = ORDER3VEG;
    }

   public void setPARENTID(String PARENTID) {
       this.PARENTID = PARENTID;
    }

    public String getIMAGEURL() {
        return IMAGEURL;
    }

    public String getMOBILENO() {
        return MOBILENO;
    }



    public String getNAME() {
        return NAME;
    }

    public String getPRICE() {
        return PRICE;
    }







   public String getID() {
        return ID;
   }
    public String getPARENTID(){
      return PARENTID;
  }



    public void setIMAGEURL(String IMAGEURL) {
        this.IMAGEURL = IMAGEURL;
    }

    public void setMOBILENO(String MOBILENO) {
        this.MOBILENO = MOBILENO;
    }



    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }



    public void setID(String ID) {
        this.ID = ID;

    }
}
