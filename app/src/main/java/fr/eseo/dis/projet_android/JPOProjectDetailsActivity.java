package fr.eseo.dis.projet_android;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import fr.eseo.dis.projet_android.data.MarksPresentationsStudents;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class JPOProjectDetailsActivity extends AppCompatActivity {

    private Users user;
    private Projects project;

    private TextView title;
    private TextView id;
    private TextView resume;
    private ImageView imgPoster;
    private TextView descPoster;
    private ProjectStudentsAdapter psa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpoproject_details);
        user = getIntent().getParcelableExtra("user");

        project = getIntent().getParcelableExtra("project");
        Log.d("JpoProjectsAdapter", "Tets jpo details : " + project.getPoster());
        title = (TextView) findViewById(R.id.jpo_project_details_title);
        id = (TextView) findViewById(R.id.jpo_project_details_id);
        resume = (TextView) findViewById(R.id.jpo_project_details_resume);
        descPoster = (TextView) findViewById(R.id.jpo_project_poster_desc);
        id.setText("ID : "+project.getIdProject());
        resume.setText(project.getDescription());
        title.setText("Titre : " + project.getTitle());
        if(project.getPoster()!=null) {
            imgPoster = (ImageView) findViewById(R.id.jpo_project_poster);
            byte[] decodedString = Base64.decode(project.getPoster(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            imgPoster.setImageBitmap(decodedByte);
        }
    }
}
