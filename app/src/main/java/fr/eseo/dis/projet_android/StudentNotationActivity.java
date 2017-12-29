package fr.eseo.dis.projet_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.eseo.dis.projet_android.data.Users;

public class StudentNotationActivity extends AppCompatActivity {

    private Users user;
    private Users student;
    private TextView studentName;
    private TextView studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_notation);
        student = getIntent().getParcelableExtra("student");
        user = getIntent().getParcelableExtra("user");
        studentName = (TextView) findViewById(R.id.student_notation_name);
        studentId = (TextView) findViewById(R.id.student_notation_id);
        studentName.setText(student.getForename()+" "+student.getSurname());
        studentId.setText("ID : "+student.getIdUser());
    }
}
