package fr.eseo.dis.projet_android;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.projet_android.data.Projects;

/**
 * Created by Gregoire on 10/01/2018.
 */

public class JPOAdapter extends
        RecyclerView.Adapter<JPOAdapter.JpoProjectsViewHolder> {

    private JPOActivity activity;
    private List<Projects> jpoProjectsList;
    private List<Integer> positionsExpanded;

    public JPOAdapter(JPOActivity jpoActivity) {
        this.activity = jpoActivity;
        setProjects(new ArrayList<Projects>());
        positionsExpanded = new ArrayList<>();
    }

    public void setProjects(List<Projects> projectsList) {
        this.jpoProjectsList = projectsList;
    }

    @Override
    public int getItemCount() {
        return jpoProjectsList.size();
    }

    @Override
    public JpoProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_jpo_projects, parent, false);
        Log.d("JpoProjectsAdapter", "onCreateViewHolder()");
        return new JpoProjectsViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(JpoProjectsViewHolder holder, final int position) {
        Log.d("ProjectsAdaper", "onBindViewHolder()");
        final Projects projects = jpoProjectsList.get(position);

        holder.projectTitle.setText("PROJECT : "+projects.getTitle());
        holder.projectId.setText("ID  :"+String.valueOf(projects.getIdProject()));

        String description = projects.getDescription();
        if(projects.getDescription().length()>101){
            description = projects.getDescription().substring(0,100)+"...";
        }
        holder.projectDesc.setText(description);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JpoProjectsAdapter", "Item 'clicked'");
                activity.clickItem(projects);
                Log.d("Test Adapter", ""+projects);

            }
        });
    }

    class JpoProjectsViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView projectTitle;
        private final TextView projectId;
        private final TextView projectDesc;

        public JpoProjectsViewHolder(View view) {
            super(view);
            Log.d("JpoProjectsViewHolder", "ProjectsViewHolder()");
            this.view = view;
            projectTitle = (TextView) view.findViewById(R.id.jpoProject_title);
            projectId = (TextView) view.findViewById(R.id.jpoProject_id);
            projectDesc = (TextView) view.findViewById(R.id.jpoProject_description);
        }
    }
}