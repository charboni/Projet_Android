package fr.eseo.dis.projet_android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.eseo.dis.projet_android.data.Juries;
import fr.eseo.dis.projet_android.data.MarksPresentationsStudents;
import fr.eseo.dis.projet_android.data.Posters;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;


public class ProjectDetailsActivity extends AppCompatActivity {

    private Users user;
    private Projects project;
    private  String superviseur;
    private boolean existPoster;
    private ArrayList<Users> studentsList;
    private ArrayList<MarksPresentationsStudents> marksList;
    private Animator mCurrentAnimator;

    private int mShortAnimationDuration;
    private TextView title;
    private TextView id;
    private TextView confidentiality;
    private TextView supervisor;
    private TextView resume;
    private ImageView imgPoster;
    private TextView descPoster;
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
        descPoster = (TextView) findViewById(R.id.project_poster_desc);
        title.setText(project.getTitle());
        id.setText("ID : "+String.valueOf(project.getIdProject()));
        confidentiality.setText("Confidentiality : " +project.getConfidentiality());
        resume.setText(project.getDescription());
        supervisor.setText("Supervisor : "+superviseur);

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL adresse = null;
            URL adresseNotes = null;
            try {
                if(existPoster) {
                    adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=POSTR&user=" + user.getUsername() + "&proj=" + project.getIdProject() + "&style=FULL" + "&token=" + user.getToken());
                    InputStream in = WebService.sendRequest(adresse, ProjectDetailsActivity.this);
                    descPoster.setText("");
                    final Bitmap poster = BitmapFactory.decodeStream(in);
                    imgPoster.setImageBitmap(poster);
                }
                adresseNotes = new URL("https://192.168.4.10/www/pfe/webservice.php?q=NOTES&user="+user.getUsername()+"&proj="+project.getIdProject()+"&token="+user.getToken());
                InputStream inN = WebService.sendRequest(adresseNotes, ProjectDetailsActivity.this);
                JSONObject jsonObject = JSONClass.convertingISToJson(inN);
                JSONParser parser = new JSONParser();
                org.json.simple.JSONArray jsonArray = null;
                for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                    Object cle = iterator.next();
                    Object val = jsonObject.get(String.valueOf(cle));
                    System.out.println("cle : " + cle +" val : "+val);
                    if("notes".equals(cle)) {
                        marksList = new ArrayList<MarksPresentationsStudents>();
                        jsonArray = (org.json.simple.JSONArray) parser.parse(val.toString());
                        System.out.println("jsonArray  : "+jsonArray);
                    }
                }
                if(jsonArray!=null) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        org.json.simple.JSONObject json = (org.json.simple.JSONObject) jsonArray.get(i);//one project
                        System.out.println("jsonObject  : " + i + " : " + json);
                        MarksPresentationsStudents mark = createMark(json);
                        marksList.add(mark);
                        System.out.println("list size : " + marksList.size());
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
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

    public MarksPresentationsStudents createMark(org.json.simple.JSONObject json) throws ParseException {

        MarksPresentationsStudents mark = new MarksPresentationsStudents(0,0,0,0);
        mark.setIdStudent((int)(long)json.get("userId"));
        double markD = 0;
        double myMarkD = 0;
        if(json.get("avgNote") instanceof java.lang.Long ){
            markD = (double)(long)json.get("avgNote");
        }
        if(json.get("avgNote") instanceof java.lang.Double ){
            markD = (double)json.get("avgNote");
        }
        if(json.get("mynote") instanceof java.lang.Long ){
            myMarkD = (double)(long)json.get("mynote");
        }
        if(json.get("mynote") instanceof java.lang.Double ){
            myMarkD = (double)json.get("mynote");
        }
        //long myMarkL = (long)json.get("mynote");
        //double myMarkD = (double)markL;
        mark.setMark(markD);
        mark.setMyMark(myMarkD);
        System.out.println("id "+mark.getIdStudent());
        System.out.println("notes "+mark.getMark());
        System.out.println("my notes "+mark.getMyMark());
        return mark;
    }
    public void clickItem(Users student) {
        System.out.println(student.getForename() + " "+student.getSurname()+" "+student.getIdUser());
        Intent intent = new Intent(this, StudentNotationActivity.class);
        intent.putExtra("student", student);
        intent.putExtra("user",user);
        intent.putExtra("project",project);
        MarksPresentationsStudents mark =null;
        if(marksList!=null) {
            for (int i = 0; i < marksList.size(); i++) {
                if (marksList.get(i).getIdStudent() == student.getIdUser()) {
                    mark = marksList.get(i);

                }
            }
        }
        intent.putExtra("mark",mark);
        startActivity(intent);
    }

}
