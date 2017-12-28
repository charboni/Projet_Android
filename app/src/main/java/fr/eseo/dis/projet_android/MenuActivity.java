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
        //Button Posters
        Button mPostersButton = (Button) findViewById(R.id.button_posters);
        mPostersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL adresse = null;
                try {
                    adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user="+user.getUsername()+"&token="+user.getToken());
                    InputStream in = WebService.sendRequest(adresse, MenuActivity.this);
                    JSONObject jsonObject = JSONClass.convertingISToJson(in);
                    JSONParser parser = new JSONParser();
                    org.json.simple.JSONArray jsonArray = null;
                    for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                        Object cle = iterator.next();
                        Object val = jsonObject.get(String.valueOf(cle));
                        System.out.println("cle : " + cle +" val : "+val);
                        if("projects".equals(cle)) {
                            projectsList = new ArrayList<Projects>();
                            jsonArray = (org.json.simple.JSONArray) parser.parse(val.toString()); //list of all projects
                            System.out.println("jsonArray  : "+jsonArray);
                        }
                    }
                    for(int i = 0; i<jsonArray.size();i++){
                        org.json.simple.JSONObject json = (org.json.simple.JSONObject)jsonArray.get(i);//one project
                        System.out.println("jsonObject  : "+i+ " : "+json);
                        Projects projects = createProject(json);
                        projectsList.add(projects);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(token);
                Intent projectsActivity = new Intent(MenuActivity.this, ProjectsActivity.class);
                System.out.println("size : "+getProjectsList().size());
                projectsActivity.putParcelableArrayListExtra("projectsList", (ArrayList<? extends Parcelable>) projectsList);
                //projectsActivity.putExtra("projectsList", (Parcelable) getProjectsList());
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
                startActivity(projectsActivity);
            }}
        );
    }
    public Projects createProject(org.json.simple.JSONObject json){
        long id = (long)json.get("projectId");
        String title = (String)json.get("title");
        String description = (String)json.get("descrip");
        long confidentiality = (long)json.get("confid");
        Projects projects = new Projects(id, title, description, confidentiality);
        System.out.println("id "+id);
        System.out.println("title "+title);
        System.out.println("description "+description);
        System.out.println("confidentiality "+confidentiality);
        return projects;
    }

    public List<Projects> getProjectsList(){
        return this.projectsList;
    }
}
