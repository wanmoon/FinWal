package com.wanmoon.finwal.activity;

/**
 * Created by pimpischaya on 8/22/2017 AD.
 */

public class UserInformation {

    public String email;
    public String name;
    public String address;
    public String phoneNumber;
   // public String gender;


    public UserInformation(String email, String name, String address, String phoneNumber){
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
      //  this.gender = gender;
    }

    public UserInformation(){

    }


    public String getAddress(){
        return address;
    }

    public String getname(){
        return name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

//    public String getGender(){
//        return gender;
//    }


    public void setAddress(String address){
        this.address = address;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

//    public void setGender(String gender){
//        this.gender = gender;
//    }

}
