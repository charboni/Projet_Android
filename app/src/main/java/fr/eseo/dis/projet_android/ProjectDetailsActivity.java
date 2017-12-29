package fr.eseo.dis.projet_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.eseo.dis.projet_android.data.Posters;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;


public class ProjectDetailsActivity extends AppCompatActivity {

    private Users user;
    private Projects project;
    private  String superviseur;
    private boolean existPoster;
    private ArrayList<Users> studentsList;

    private TextView title;
    private TextView id;
    private TextView confidentiality;
    private TextView supervisor;
    private TextView resume;
    private ImageView imgPoster;
    private ProjectStudentsAdapter psa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        user = getIntent().getParcelableExtra("user");
        project = getIntent().getParcelableExtra("project");
        superviseur = getIntent().getStringExtra("supervisor");
        existPoster = getIntent().getBooleanExtra("existPoster",false);
        System.out.println("existPoster "+existPoster);

        studentsList = getIntent().getParcelableArrayListExtra("studentsList");
        title = (TextView) findViewById(R.id.project_details_title);
        id = (TextView) findViewById(R.id.project_details_id);
        confidentiality = (TextView) findViewById(R.id.project_details_confid);
        supervisor = (TextView) findViewById(R.id.project_details_superviseur);
        resume = (TextView) findViewById(R.id.project_details_resume);
        imgPoster = (ImageView) findViewById(R.id.project_poster);
        title.setText(project.getTitle());
        id.setText("ID : "+String.valueOf(project.getIdProject()));
        confidentiality.setText("Confidentiality : " +project.getConfidentiality());
        resume.setText(project.getDescription());
        supervisor.setText("Supervisor : "+superviseur);
        if(existPoster){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL adresse = null;
            try {
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=POSTR&user="+user.getUsername()+"&proj="+project.getIdProject()+"&style=FULL"+"&token="+user.getToken());
                InputStream in = WebService.sendRequest(adresse, ProjectDetailsActivity.this);
                Bitmap poster = BitmapFactory.decodeStream(in);
                imgPoster.setImageBitmap(poster);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        RecyclerView recList = (RecyclerView) findViewById(R.id.project_details_students);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        //psa = new ProjectStudentsAdapter(this);
        psa = new ProjectStudentsAdapter(this);
        recList.setAdapter(psa);
        loadStudentsDetails();
    }
    private void loadStudentsDetails(){
        psa.setStudents(studentsList);
    }

    public void clickItem(Users student) {
        System.out.println(student.getForename() + " "+student.getSurname()+" "+student.getIdUser());
    }
}
