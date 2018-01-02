package fr.eseo.dis.projet_android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.eseo.dis.projet_android.data.MarksPresentationsStudents;
import fr.eseo.dis.projet_android.data.Posters;
import fr.eseo.dis.projet_android.data.Projects;
import fr.eseo.dis.projet_android.data.Users;

public class StudentNotationActivity extends AppCompatActivity {

    private Users user;
    private Users student;
    private MarksPresentationsStudents mark;
    private  Projects project;
    private TextView studentName;
    private TextView studentLabelNotation;
    private TextView studentId;
    private TextView comment;
    private TextView markAVG;
    private TextView myMark;
    private EditText note;
    private double noteStudent;
    private ArrayList<MarksPresentationsStudents> marksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notation);
        student = getIntent().getParcelableExtra("student");
        user = getIntent().getParcelableExtra("user");
        project = getIntent().getParcelableExtra("project");
        mark = getIntent().getParcelableExtra("mark");
        markAVG = (TextView) findViewById(R.id.student_notation_moyenne);
        myMark = (TextView) findViewById(R.id.student_notation_mine);
        studentName = (TextView) findViewById(R.id.student_notation_name);
        studentId = (TextView) findViewById(R.id.student_notation_id);
        studentLabelNotation = (TextView) findViewById(R.id.student_notation_label);
        studentName.setText(student.getForename()+" "+student.getSurname());
        studentId.setText("ID : "+student.getIdUser());
        note = (EditText) findViewById(R.id.editText_note);
        Button valid_note = (Button) findViewById(R.id.valid_note);

        if(mark!=null) {
            markAVG.setText("NOTE MOYENNE : " + mark.getMark());
            myMark.setText("Ma Note : " + mark.getMyMark());
        }else{
            markAVG.setText("");
            myMark.setText("");
            studentLabelNotation.setText("");
            note.setEnabled(false);
            valid_note.setVisibility(View.INVISIBLE);
        }

        valid_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyNote()){
                    addNote(noteStudent);
                    URL adresse = null;
                    URL adresseNotes = null;
                    try {

                        adresseNotes = new URL("https://192.168.4.10/www/pfe/webservice.php?q=NOTES&user="+user.getUsername()+"&proj="+project.getIdProject()+"&token="+user.getToken());
                        InputStream inN = WebService.sendRequest(adresseNotes, StudentNotationActivity.this);
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
                        for(int i = 0; i<jsonArray.size();i++){
                            org.json.simple.JSONObject json = (org.json.simple.JSONObject)jsonArray.get(i);//one project
                            System.out.println("jsonObject  : "+i+ " : "+json);
                            MarksPresentationsStudents mark = createMark(json);
                            marksList.add(mark);
                            System.out.println("list size : "+marksList.size());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MarksPresentationsStudents mark =null;
                    for(int i =0;i<marksList.size();i++){
                        if(marksList.get(i).getIdStudent()==student.getIdUser()){
                            mark = marksList.get(i);
                        }
                    }
                    markAVG.setText("NOTE MOYENNE : "+mark.getMark());
                    myMark.setText("Ma Note : "+mark.getMyMark());

                }
            }
        });
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
    private boolean verifyNote(){
        note = (EditText) findViewById(R.id.editText_note);
        String noteStr = note.getText().toString();
        noteStudent = Double.parseDouble(noteStr);
        System.out.println("note : "+noteStudent);
        boolean success = false;
        if(noteStudent>=0 && noteStudent<=20){
            success = true;
        }
        System.out.println("success : "+success);
        return  success;
    }
    private void addNote(Double noteStudent){
        user = getIntent().getParcelableExtra("user");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL adresse = null;
        try {
            adresse = new URL("https://192.168.4.10/www/pfe/webservice.php?q=NEWNT&user="
                    +user.getUsername()+"&proj="+project.getIdProject()+"&student="+student.getIdUser()+"&note="+noteStudent+"&token="+user.getToken());
            InputStream in = WebService.sendRequest(adresse, StudentNotationActivity.this);
            JSONObject jsonObject = JSONClass.convertingISToJson(in);
            if(this.readJson(jsonObject)){
                comment = (TextView) findViewById(R.id.student_notation_final);
                comment.setText("Uploaded with success !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean readJson(JSONObject jsonObject) throws JSONException {
        boolean insert=false;
        for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
            Object cle = iterator.next();
            Object val = jsonObject.get(String.valueOf(cle));
            if("result".equals(cle)&&"OK".equals(val))
                insert = true;
        }
        System.out.println("insert : "+insert);
        return insert;
    }
}
