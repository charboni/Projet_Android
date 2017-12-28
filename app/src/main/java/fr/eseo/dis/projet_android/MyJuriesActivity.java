package fr.eseo.dis.projet_android;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    private List<Juries> myJuriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_juries);
        user = getIntent().getParcelableExtra("user");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {
            adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=MYJUR&user="+user.getUsername()+"&token="+user.getToken());
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

        System.out.println("id :"+id);
        System.out.println("date :"+dateStr);
        System.out.println("description :"+descriptionJson.toJSONString());

        return juries;
    }
}
