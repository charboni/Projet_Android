package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Users implements Parcelable {

    private int idUser;
    private String username;
    private char salt;
    private char password;
    private String forename;
    private String surname;
    private String token;

    public Users(){

    }
    public Users(int idUser, String username, char salt, char password, String forename, String surname) {
        this.idUser = idUser;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

    protected Users(Parcel in) {
        idUser = in.readInt();
        username = in.readString();
        salt = (char) in.readInt();
        password = (char) in.readInt();
        forename = in.readString();
        surname = in.readString();
        token = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idUser);
        dest.writeString(username);
        dest.writeInt((int) salt);
        dest.writeInt((int) password);
        dest.writeString(forename);
        dest.writeString(surname);
        dest.writeString(token);
    }
}
