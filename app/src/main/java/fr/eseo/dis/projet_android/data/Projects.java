package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Projects implements Parcelable{

    private String poster;
    private int idProject;
    private String title;
    private String description;
    private int confidentiality;

    public Projects(){

    }

    public Projects(int idProject, String title, String description, int confidentiality) {
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.confidentiality = confidentiality;
    }

    protected Projects(Parcel in) {
        poster = in.readString();
        idProject = in.readInt();
        title = in.readString();
        description = in.readString();
        confidentiality = in.readInt();
    }

    public static final Creator<Projects> CREATOR = new Creator<Projects>() {
        @Override
        public Projects createFromParcel(Parcel in) {
            return new Projects(in);
        }

        @Override
        public Projects[] newArray(int size) {
            return new Projects[size];
        }
    };

    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getConfidentiality() {
        return confidentiality;
    }

    public void setConfidentiality(int confidentiality) {
        this.confidentiality = confidentiality;
    }

    public String getPoster() { return this.poster; }

    public void setPoster(String poster){ this.poster = poster; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poster);
        dest.writeInt(idProject);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeInt(confidentiality);
    }
}
