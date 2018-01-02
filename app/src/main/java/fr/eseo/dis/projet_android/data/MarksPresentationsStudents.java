package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class MarksPresentationsStudents implements Parcelable {

    private int idStudent;
    private int idJury;
    private int idMember;
    private double mark;
    private double myMark;

    public MarksPresentationsStudents(int idStudent, int idJury, int idMember, double mark) {
        this.idStudent = idStudent;
        this.idJury = idJury;
        this.idMember = idMember;
        this.mark = mark;
    }

    protected MarksPresentationsStudents(Parcel in) {
        idStudent = in.readInt();
        idJury = in.readInt();
        idMember = in.readInt();
        mark = in.readDouble();
        myMark = in.readDouble();
    }

    public static final Creator<MarksPresentationsStudents> CREATOR = new Creator<MarksPresentationsStudents>() {
        @Override
        public MarksPresentationsStudents createFromParcel(Parcel in) {
            return new MarksPresentationsStudents(in);
        }

        @Override
        public MarksPresentationsStudents[] newArray(int size) {
            return new MarksPresentationsStudents[size];
        }
    };

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public double getMyMark() {
        return myMark;
    }

    public void setMyMark(double myMark) {
        this.myMark = myMark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idStudent);
        dest.writeInt(idJury);
        dest.writeInt(idMember);
        dest.writeDouble(mark);
        dest.writeDouble(myMark);
    }
}
