package fr.eseo.dis.projet_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.eseo.dis.projet_android.data.Projects;

public class ProjectDetailsActivity extends AppCompatActivity {

    private Projects project;
    private  String superviseur;

    private TextView title;
    private TextView id;
    private TextView confidentiality;
    private TextView realisateur;
    private TextView supervisor;
    private TextView resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);
        project = getIntent().getParcelableExtra("project");
        superviseur = getIntent().getStringExtra("supervisor");
        title = (TextView) findViewById(R.id.project_details_title);
        id = (TextView) findViewById(R.id.project_details_id);
        confidentiality = (TextView) findViewById(R.id.project_details_confid);
        supervisor = (TextView) findViewById(R.id.project_details_superviseur);
        resume = (TextView) findViewById(R.id.project_details_resume);

        title.setText(project.getTitle());
        id.setText("ID : "+String.valueOf(project.getIdProject()));
        confidentiality.setText("Confidentiality : " +project.getConfidentiality());
        resume.setText(project.getDescription());
        supervisor.setText("Supervisor : "+superviseur);
    }
}
