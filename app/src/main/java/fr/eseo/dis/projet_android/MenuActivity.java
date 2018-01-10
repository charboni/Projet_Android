package fr.eseo.dis.projet_android;

import android.content.Intent;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.eseo.dis.projet_android.data.ListElement;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class MenuActivity extends AppCompatActivity {

    private String login;
    private String token;
    private Users user;
    private List<Projects> projectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //login = getIntent().getStringExtra("login");
        //token = getIntent().getStringExtra("token");
        user = getIntent().getParcelableExtra("user");

        //Button Projects
        Button mProjectsButton = (Button) findViewById(R.id.button_projects);
        mProjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent projectsActivity = new Intent(MenuActivity.this, ProjectsActivity.class);
                projectsActivity.putExtra("user",user);
                projectsActivity.putExtra("type","all");
                startActivity(projectsActivity);
            }
        }
        );

        //Button MyJuries
        Button mMyJuriesButton = (Button) findViewById(R.id.button_juries);
        mMyJuriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent projectsActivity = new Intent(MenuActivity.this, MyJuriesActivity.class);
                projectsActivity.putExtra("user",user);
                projectsActivity.putExtra("type","mine");
                startActivity(projectsActivity);
            }}
        );
        Button mMyProjectsButton = (Button) findViewById(R.id.button_myprojects);
        mMyProjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent projectsActivity = new Intent(MenuActivity.this, ProjectsActivity.class);
                projectsActivity.putExtra("user",user);
                projectsActivity.putExtra("type","mine");
                startActivity(projectsActivity);
            }}
        );
        Button mJpoButton = (Button) findViewById(R.id.button_jpo);

    }

}
