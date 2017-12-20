package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class JuryProjects {

    private int idJury;
    private int idProject;

    public JuryProjects(int idJury, int idProject) {
        this.idJury = idJury;
        this.idProject = idProject;
    }

    public int getIdJury() {
        return idJury;
    }

    public void setIdJury(int idJury) {
        this.idJury = idJury;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }
}
