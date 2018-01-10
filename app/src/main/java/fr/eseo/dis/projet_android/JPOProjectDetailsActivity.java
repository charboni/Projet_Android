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
    private Projects projects;
    private String poster;
    private TextView title;
    private TextView id;
    private TextView resume;
    private ImageView imgPoster;
    private TextView descPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpoproject_details);
        user = (Users)getIntent().getParcelableExtra("user");
        projects = (Projects)getIntent().getParcelableExtra("project");
        title = (TextView) findViewById(R.id.jpo_project_details_title);
        id = (TextView) findViewById(R.id.jpo_project_details_id);
        resume = (TextView) findViewById(R.id.jpo_project_details_resume);
        descPoster = (TextView) findViewById(R.id.jpo_project_poster_desc);
        Log.d("JpoProjectsAdapter", "Tets jpo details : " + projects.getIdProject());
        Log.d("JpoProjectsAdapter", "Tets jpo details : " + projects.getDescription());
        Log.d("JpoProjectsAdapter", "Tets jpo details : " + projects.getTitle());
        Log.d("JpoProjectsAdapter", "Tets jpo details : " + projects.getPoster());
        id.setText("ID : "+projects.getIdProject());
        resume.setText(projects.getDescription());
        title.setText("Titre : " + projects.getTitle());
        if(projects.getPoster()!=null) {
            descPoster.setText("");
            imgPoster = (ImageView) findViewById(R.id.jpo_project_poster);
            byte[] decodedString = Base64.decode(projects.getPoster(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            imgPoster.setImageBitmap(decodedByte);
        }
    }
}
