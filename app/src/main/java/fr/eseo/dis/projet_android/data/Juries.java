package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Juries implements Parcelable {


    private int idJury;
    private String description;
    private Date date;

    public Juries(int idJury, String description, Date date) {
        this.idJury = idJury;
        this.description = description;
        this.date = date;
    }

    protected Juries(Parcel in) {
        idJury = in.readInt();
        description = in.readString();
    }

    public static final Creator<Juries> CREATOR = new Creator<Juries>() {
        @Override
        public Juries createFromParcel(Parcel in) {
            return new Juries(in);
        }

        @Override
        public Juries[] newArray(int size) {
            return new Juries[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idJury);
        dest.writeString(description);
    }
}
