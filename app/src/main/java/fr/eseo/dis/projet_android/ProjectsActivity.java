package fr.eseo.dis.projet_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import fr.eseo.dis.projet_android.data.ListElement;
import fr.eseo.dis.projet_android.data.Projects;

public class ProjectsActivity extends AppCompatActivity {

    private ProjectsAdapter projectsAdapter;
    private List<Projects> projectsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        projectsList = getIntent().getParcelableArrayListExtra("projectsList");
        System.out.println("size list"+ projectsList.size());

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
        //Intent intent = new Intent(this, FilmDetailsActivity.class);
        //intent.putExtra(FILM_EXTRA, film);
        //startActivity(intent);
    }
}
