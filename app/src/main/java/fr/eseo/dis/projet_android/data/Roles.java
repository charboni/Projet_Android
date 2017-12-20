package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Roles {

    private int idRole;
    private String description;

    public Roles(int idRole, String description) {
        this.idRole = idRole;
        this.description = description;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
