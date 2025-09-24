package com.example.d424jones.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionTitle;
    private String excursionDate;
    private int vacationID;

    public Excursion(int excursionID, String excursionTitle, int vacationID, String excursionDate){
        this.excursionID = excursionID;
        this.excursionTitle = excursionTitle;
        this.vacationID = vacationID;
        this.excursionDate = excursionDate;
    }
    //encapulation for excursion
    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}

