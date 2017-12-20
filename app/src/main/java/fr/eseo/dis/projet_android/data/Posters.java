package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Posters {

    private int idPoster;
    private int project;
    private String filepathPDF;

    public Posters(int idPoster, int project, String filepathPDF) {
        this.idPoster = idPoster;
        this.project = project;
        this.filepathPDF = filepathPDF;
    }

    public int getIdPoster() {
        return idPoster;
    }

    public void setIdPoster(int idPoster) {
        this.idPoster = idPoster;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public String getFilepathPDF() {
        return filepathPDF;
    }

    public void setFilepathPDF(String filepathPDF) {
        this.filepathPDF = filepathPDF;
    }
}
