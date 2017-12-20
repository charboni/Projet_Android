package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Teams {

    private int idProject;
    private int idMember;

    public Teams(int idProject, int idMember) {
        this.idProject = idProject;
        this.idMember = idMember;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }
}
