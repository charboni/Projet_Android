package fr.eseo.dis.projet_android.data;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Users {

    private int idUser;
    private String username;
    private char salt;
    private char password;
    private String forename;
    private String surname;

    public Users(int idUser, String username, char salt, char password, String forename, String surname) {
        this.idUser = idUser;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char getSalt() {
        return salt;
    }

    public void setSalt(char salt) {
        this.salt = salt;
    }

    public char getPassword() {
        return password;
    }

    public void setPassword(char password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
