package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Presentations {


    private int idPresentation;
    private int project;
    private String filepathPDF;

    public Presentations(int idPresentation, int project, String filepathPDF) {
        this.idPresentation = idPresentation;
        this.project = project;
        this.filepathPDF = filepathPDF;
    }

    public int getIdPresentation() {
        return idPresentation;
    }

    public void setIdPresentation(int idPresentation) {
        this.idPresentation = idPresentation;
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
