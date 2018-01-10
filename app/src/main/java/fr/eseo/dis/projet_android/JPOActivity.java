package fr.eseo.dis.projet_android;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.eseo.dis.projet_android.data.Posters;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class JPOActivity extends AppCompatActivity {

    private Users user;
    private ArrayList<Projects> jpoProjectsList;
    private JPOAdapter jpoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpo);
        user = getIntent().getParcelableExtra("user");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {

            adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=PORTE&user="+user.getUsername()+"&token="+user.getToken());

            InputStream in = WebService.sendRequest(adresse, JPOActivity.this);
            JSONObject jsonObject = JSONClass.convertingISToJson(in);
            JSONParser parser = new JSONParser();
            org.json.simple.JSONArray jsonArray = null;
            for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                Object cle = iterator.next();
                Object val = jsonObject.get(String.valueOf(cle));
                System.out.println("cle : " + cle +" val : "+val);
                if("projects".equals(cle)) {
                    jpoProjectsList = new ArrayList<Projects>();
                    jsonArray = (org.json.simple.JSONArray) parser.parse(val.toString()); //list of all random projects
                }
            }
            for(int i = 0; i<jsonArray.size();i++){
                org.json.simple.JSONObject json = (org.json.simple.JSONObject)jsonArray.get(i);//one project
                Projects projects = createProject(json);
                jpoProjectsList.add(projects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardListJPO);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);
        jpoAdapter = new JPOAdapter(this);
        recycler.setAdapter(jpoAdapter);

        loadAllProjectData();
    }

    private void loadAllProjectData(){
        jpoAdapter.setProjects(jpoProjectsList);
    }

    private Projects createProject(org.json.simple.JSONObject json) throws JSONException {
        Projects projects = new Projects();
        projects.setPoster((String)json.get("poster"));
        projects.setIdProject((int)(long)json.get("idProject"));
        projects.setTitle((String)json.get("title"));
        projects.setDescription((String)json.get("description"));

        Log.d("Poster", ""+ projects.getPoster());
        return projects;
    }

    public void clickItem(Projects projects) {

        Log.d("JpoProjectsAdapter", "Test fonctionnel");
        Intent intent = new Intent(this, JPOProjectDetailsActivity.class);
        intent.putExtra("project", projects);
        //System.out.print("Test 2"+projects);
        Log.d("Poster click", ""+ projects.getPoster());
        startActivity(intent);
    }
}
