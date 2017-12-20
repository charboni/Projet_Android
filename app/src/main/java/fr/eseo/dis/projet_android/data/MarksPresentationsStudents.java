package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class MarksPresentationsStudents {

    private int idStudent;
    private int idJury;
    private int idMember;
    private float mark;

    public MarksPresentationsStudents(int idStudent, int idJury, int idMember, float mark) {
        this.idStudent = idStudent;
        this.idJury = idJury;
        this.idMember = idMember;
        this.mark = mark;
    }

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

    public float getMark() {
        return mark;
    }

    public void setMark(float mark) {
        this.mark = mark;
    }
}
