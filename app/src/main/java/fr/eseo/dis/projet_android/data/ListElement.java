package fr.eseo.dis.projet_android.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Nicolas on 27/12/2017.
 */

public class ListElement extends ArrayList implements Parcelable {

    public ListElement(Parcel in) {
    }
    public static final Creator<ListElement> CREATOR = new Creator<ListElement>() {
        @Override
        public ListElement createFromParcel(Parcel in) {
            return new ListElement(in);
        }

        @Override
        public ListElement[] newArray(int size) {
            return new ListElement[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
