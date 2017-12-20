package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */
public class Supervisors {

    private int idProject;
    private int idSupervisor;

    public Supervisors(int idProject, int idSupervisor) {
        this.idProject = idProject;
        this.idSupervisor = idSupervisor;
    }

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }
}
