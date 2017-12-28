package fr.eseo.dis.projet_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.eseo.dis.projet_android.data.ListElement;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class ProjectsActivity extends AppCompatActivity {

    private ProjectsAdapter projectsAdapter;
    private List<Projects> projectsList;
    private Users user;
    public static final String PROJECT = "project";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        user = getIntent().getParcelableExtra("user");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {
            adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user="+user.getUsername()+"&token="+user.getToken());
            InputStream in = WebService.sendRequest(adresse, ProjectsActivity.this);
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

        for(int i =0;i<projectsList.size();i++){
            System.out.println ("id" + projectsList.get(i).getIdProject());
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardList);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);
        projectsAdapter = new ProjectsAdapter(this);
        recycler.setAdapter(projectsAdapter);

        loadAllProjectData();
    }
    private void loadAllProjectData(){
        projectsAdapter.setProjects(projectsList);
    }

    public void clickItem(Projects project) {
        System.out.println("project : "+project.getTitle());
        Intent intent = new Intent(this, ProjectDetailsActivity.class);
        intent.putExtra(PROJECT, project);
        startActivity(intent);
    }
    public Projects createProject(org.json.simple.JSONObject json){
        long idL = (long)json.get("projectId");
        int id = (int)idL;
        String title = (String)json.get("title");
        String description = (String)json.get("descrip");
        long confidentialityL = (long)json.get("confid");
        int confidentiality = (int)confidentialityL;
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
