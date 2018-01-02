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
import android.widget.ImageView;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.eseo.dis.projet_android.data.ListElement;
import fr.eseo.dis.projet_android.data.Posters;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class ProjectsActivity extends AppCompatActivity {

    private ProjectsAdapter projectsAdapter;
    private List<Projects> projectsList;
    private Users user;
    private HashMap<String, ArrayList<Users>> studentsProjectList;
    private HashMap<String, String> supervisorsList;
    private List<Posters> existPostersList;

    public static final String PROJECT = "project";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        user = getIntent().getParcelableExtra("user");
        String type = getIntent().getStringExtra("type");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {
            if("all".equals(type)){
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=LIPRJ&user="+user.getUsername()+"&token="+user.getToken());
            }
            if("mine".equals(type)){
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=MYPRJ&user="+user.getUsername()+"&token="+user.getToken());
            }
            if("jpo".equals(type)){
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=PORTE&user="+user.getUsername()+"&seed=optional&token="+user.getToken());
            }
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
                    existPostersList = new ArrayList<Posters>();
                    supervisorsList = new HashMap<String, String>();
                    studentsProjectList = new HashMap<String, ArrayList<Users>>();
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
        String supervisor = "";
        Set cles = supervisorsList.keySet();
        Iterator it = cles.iterator();
        while (it.hasNext()){
            Object cle = it.next();
            if(String.valueOf(project.getIdProject()).equals(cle)){
                supervisor = supervisorsList.get(cle);
            }
        }
        intent.putExtra("supervisor",supervisor);
        ArrayList<Users> studentsList = new ArrayList<Users>();
        Set clesListStudents = studentsProjectList.keySet();
        Iterator it2 = clesListStudents.iterator();
        while (it2.hasNext()){
            Object cle = it2.next();
            if(String.valueOf(project.getIdProject()).equals(cle)){
                studentsList = studentsProjectList.get(cle);
            }
        }
        intent.putParcelableArrayListExtra("studentsList",studentsList);
        boolean existPoster = false;
        for(int i=0;i<existPostersList.size();i++){
            if(existPostersList.get(i).getProject()==project.getIdProject()){
                existPoster=true;
            }
        }
        System.out.println("Poster : "+existPoster);
        intent.putExtra("existPoster",existPoster);
        intent.putExtra("user",user);
        startActivity(intent);
    }
    public Projects createProject(org.json.simple.JSONObject json){
        Projects projects = new Projects((int)(long)json.get("projectId"), (String)json.get("title"), (String)json.get("descrip"), (int)(long)json.get("confid"));

        org.json.simple.JSONObject supervisName = (org.json.simple.JSONObject)json.get("supervisor");
        org.json.simple.JSONArray studentsArray = (org.json.simple.JSONArray)json.get("students");
        ArrayList<Users> studentsList = createListUsers(studentsArray);
        studentsProjectList.put(String.valueOf(projects.getIdProject()),studentsList);
        String supervisor = (String)supervisName.get("surname") + " "+(String)supervisName.get("forename");
        supervisorsList.put(String.valueOf(projects.getIdProject()),supervisor);
        boolean existPoster = (boolean)json.get("poster");
        if(existPoster){
            Posters poster = new Posters(0,projects.getIdProject(),"");
            existPostersList.add(poster);
        }

        return projects;
    }
    private ArrayList<Users> createListUsers(org.json.simple.JSONArray jsonArray){
        ArrayList<Users> listStudents = new ArrayList<Users>();
        for(int i = 0; i<jsonArray.size();i++){
            org.json.simple.JSONObject json = (org.json.simple.JSONObject)jsonArray.get(i);//one project
            Users user = new Users();
            user.setIdUser((int)(long)json.get("userId"));
            user.setForename((String)json.get("forename"));
            user.setSurname((String)json.get("surname"));
            listStudents.add(user);
        }
        return listStudents;
    }
    public List<Projects> getProjectsList(){
        return this.projectsList;
    }
}
