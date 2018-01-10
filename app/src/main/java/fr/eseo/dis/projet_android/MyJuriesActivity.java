package fr.eseo.dis.projet_android;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.eseo.dis.projet_android.data.Juries;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class MyJuriesActivity extends AppCompatActivity {

    private Users user;
    private  String type;
    private List<Juries> myJuriesList;
    private MyJuriesAdapter myJuriesAdapter;
    public static final String JURY = "jury";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_juries);
        user = getIntent().getParcelableExtra("user");
        type = getIntent().getStringExtra("type");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {
            if ("mine".equals(type)) {
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=MYJUR&user="+user.getUsername()+"&token="+user.getToken());
            }
            if("all".equals(type)){
                adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=LIJUR&user="+user.getUsername()+"&token="+user.getToken());
            }
            InputStream in = WebService.sendRequest(adresse, MyJuriesActivity.this);
            JSONObject jsonObject = JSONClass.convertingISToJson(in);
            JSONParser parser = new JSONParser();
            org.json.simple.JSONArray jsonArray = null;
            for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
                Object cle = iterator.next();
                Object val = jsonObject.get(String.valueOf(cle));
                System.out.println("cle : " + cle +" val : "+val);
                if("juries".equals(cle)) {
                    myJuriesList = new ArrayList<Juries>();
                    jsonArray = (org.json.simple.JSONArray) parser.parse(val.toString()); //list of my juries
                    System.out.println("jsonArray  : "+jsonArray);
                }
            }
            for(int i = 0; i<jsonArray.size();i++){
                org.json.simple.JSONObject json = (org.json.simple.JSONObject)jsonArray.get(i);//one project
                System.out.println("jsonObject  : "+i+ " : "+json);
                Juries juries = createJury(json);
                myJuriesList.add(juries);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i =0;i<myJuriesList.size();i++){
            System.out.println ("id" + myJuriesList.get(i).getIdJury());
        }

        RecyclerView recycler = (RecyclerView) findViewById(R.id.cardListJuries);
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);
        myJuriesAdapter = new MyJuriesAdapter(this);
        recycler.setAdapter(myJuriesAdapter);

        loadAllJuriesData();
    }
    private void loadAllJuriesData(){
        myJuriesAdapter.setJuries(myJuriesList);
    }

    public Juries createJury(org.json.simple.JSONObject json) throws ParseException {
        long idL = (long)json.get("idJury");
        int id = (int) idL;
        org.json.simple.JSONObject descriptionJson = (org.json.simple.JSONObject)json.get("info");
        JSONParser jsonParser = new JSONParser();
        String dateStr = (String)json.get("date");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(dateStr);

        Juries juries = new Juries(id,descriptionJson.toJSONString(),date);
        return juries;
    }

    public void clickItem(Juries juries) {
        System.out.println("jury : "+juries.getIdJury());
        Intent intent = new Intent(this, JuryDetailsActivity.class);
        intent.putExtra(JURY, juries);
        intent.putExtra("user",user);
        startActivity(intent);
    }
}
