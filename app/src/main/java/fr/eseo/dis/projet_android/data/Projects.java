package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gregoire on 20/12/2017.
 */

public class Projects implements Parcelable{


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
        idProject = (int) in.readLong();
        title = in.readString();
        description = in.readString();
        confidentiality = (int) in.readLong();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idProject);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(confidentiality);
    }
}
