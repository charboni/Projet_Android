package fr.eseo.dis.projet_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.eseo.dis.projet_android.data.Users;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Nicolas on 28/12/2017.
 */

public class ProjectStudentsAdapter extends RecyclerView.Adapter<ProjectStudentsAdapter.StudentsViewHolder> {

    private ProjectDetailsActivity activity;
    //private final Context context;
    private ArrayList<Users> studentsList;

    //public ProjectStudentsAdapter(Context context) {

      //  this.context = context;
        //studentsList = new ArrayList<>();
    //}
    public ProjectStudentsAdapter(ProjectDetailsActivity activity) {

        this.activity = activity;
        studentsList = new ArrayList<>();
    }

    public int getItemCount() {
        return studentsList.size();
    }

    public StudentsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View studentsView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_students_card, viewGroup, false);
        return new StudentsViewHolder(studentsView);
    }


    public void onBindViewHolder(StudentsViewHolder studentsViewHolder, int i) {
        final Users student = studentsList.get(i);
        studentsViewHolder.idStudent
          .setText("id : "+student.getIdUser());
        studentsViewHolder.studentName.setText(student.getForename()+" "+student.getSurname());
        studentsViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProjectsAdapter", "Item 'clicked'");
                activity.clickItem(student);
            }
        });
    }

    public void setStudents(ArrayList<Users> studentsList) {

        Log.d("DummyData", "addRoles");
        this.studentsList = studentsList;
    }

    class StudentsViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView idStudent;
        private final TextView studentName;

        public StudentsViewHolder(View view) {
            super(view);
            this.view = view;
            idStudent = (TextView) view.findViewById(R.id.tv_id_student);
            studentName = (TextView) view.findViewById(R.id.tv_student_name);
        }
    }
}
