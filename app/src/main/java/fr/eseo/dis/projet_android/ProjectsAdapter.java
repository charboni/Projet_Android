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
 * Created by Nicolas on 27/12/2017.
 */

public class ProjectsAdapter extends
        RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder> {

    private ProjectsActivity activity;
    private List<Projects> projectsList;
    private List<Integer> positionsExpanded;

    public ProjectsAdapter(ProjectsActivity projectsActivity) {
        this.activity = projectsActivity;
        setProjects(new ArrayList<Projects>());
        positionsExpanded = new ArrayList<>();
    }

    public void setProjects(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }

    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View projectView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_projects, parent, false);
        Log.d("ProjectsAdaper", "onCreateViewHolder()");
        return new ProjectsViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(ProjectsViewHolder holder, final int position) {
        Log.d("ProjectsAdaper", "onBindViewHolder()");
        final Projects projects = projectsList.get(position);

        holder.projectTitle.setText(projects.getTitle());
        holder.projectId.setText(String.valueOf(projects.getIdProject()));
        holder.projectConfi.setText(String.valueOf(projects.getConfidentiality()));
        holder.projectDesc.setText(projects.getDescription());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProjectsAdapter", "Item 'clicked'");
                activity.clickItem(projects);
            }
        });
    }

    class ProjectsViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView projectTitle;
        private final TextView projectId;
        private final TextView projectConfi;
        private final TextView projectDesc;

        public ProjectsViewHolder(View view) {
            super(view);
            Log.d("ProjectsViewHolder", "ProjectsViewHolder()");
            this.view = view;
            projectTitle = (TextView) view.findViewById(R.id.project_title);
            projectId = (TextView) view.findViewById(R.id.project_id);
            projectConfi = (TextView) view.findViewById(R.id.project_confi);
            projectDesc = (TextView) view.findViewById(R.id.project_description);
        }
    }
}
