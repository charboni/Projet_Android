package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Confidentialities {

    private int idConfid;
    private String description;

    public Confidentialities(int idConfid, String description) {
        this.idConfid = idConfid;
        this.description = description;
    }

    public int getIdConfid() {
        return idConfid;
    }

    public void setIdConfid(int idConfid) {
        this.idConfid = idConfid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
