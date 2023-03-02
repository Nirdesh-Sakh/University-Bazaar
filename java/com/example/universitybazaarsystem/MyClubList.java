package com.example.universitybazaarsystem;

public class MyClubList
{
    String clubID, clubTitle, clubDescription, ContactInfo, timestamp, createdBy;

    public MyClubList()
    {
    }

    public MyClubList(String clubID, String clubTitle, String clubDescription, String contactInfo, String timestamp, String createdBy)
    {
        this.clubID = clubID;
        this.clubTitle = clubTitle;
        this.clubDescription = clubDescription;
        ContactInfo = contactInfo;
        this.timestamp = timestamp;
        this.createdBy = createdBy;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getClubTitle() {
        return clubTitle;
    }

    public void setClubTitle(String clubTitle) {
        this.clubTitle = clubTitle;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public String getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(String contactInfo) {
        ContactInfo = contactInfo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
