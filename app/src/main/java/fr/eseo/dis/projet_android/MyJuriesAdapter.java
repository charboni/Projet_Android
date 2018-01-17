package fr.eseo.dis.projet_android;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.projet_android.data.Juries;

/**
 * Created by Nicolas on 27/12/2017.
 */

public class MyJuriesAdapter extends
        RecyclerView.Adapter<MyJuriesAdapter.MyJuriesViewHolder> {

    private MyJuriesActivity activity;
    private List<Juries> myJuriesList;
    private List<Integer> positionsExpanded;

    public MyJuriesAdapter(MyJuriesActivity myJuriesActivity) {
        this.activity = myJuriesActivity;
        setJuries(new ArrayList<Juries>());
        positionsExpanded = new ArrayList<>();
    }

    public void setJuries(List<Juries> myJuriesList) {
        this.myJuriesList = myJuriesList;
    }

    @Override
    public int getItemCount() {
        return myJuriesList.size();
    }

    @Override
    public MyJuriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View juryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_juries, parent, false);
        Log.d("ProjectsAdaper", "onCreateViewHolder()");
        return new MyJuriesViewHolder(juryView);
    }

    @Override
    public void onBindViewHolder(MyJuriesViewHolder holder, final int position) {
        Log.d("ProjectsAdaper", "onBindViewHolder()");
        final Juries juries = myJuriesList.get(position);

        holder.juryId.setText("JURY "+String.valueOf(juries.getIdJury()));
        holder.juryDate.setText("Date of presentation : "+juries.getDate().toString());
        holder.juryDesc.setText("Members : "+juries.getDescription());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyJuriesAdapter", "Item 'clicked'");
                activity.clickItem(juries);
            }
        });
    }

    class MyJuriesViewHolder extends RecyclerView.ViewHolder {

        private final View view;


        private final TextView juryId;
        private final TextView juryDate;
        private final TextView juryDesc;

        public MyJuriesViewHolder(View view) {
            super(view);
            Log.d("ProjectsViewHolder", "ProjectsViewHolder()");
            this.view = view;
            juryId = (TextView) view.findViewById(R.id.juries_id);
            juryDate = (TextView) view.findViewById(R.id.jury_date);
            juryDesc = (TextView) view.findViewById(R.id.jury_description);
        }
    }
}
