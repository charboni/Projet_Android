package fr.eseo.dis.projet_android.data;

import java.util.Date;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Juries {


    private int idJury;
    private String description;
    private Date date;

    public Juries(int idJury, String description, Date date) {
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
