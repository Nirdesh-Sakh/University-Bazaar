package com.example.universitybazaarsystem;

public class Users {

        String uid;
        String firstName;
        String itemName;



    public Users(){

        }
    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

        public Users(String itemName) {
            this.itemName = itemName;
            this.itemName = itemName;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
}
