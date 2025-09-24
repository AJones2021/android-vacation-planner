package com.example.d424jones.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424jones.R;
import com.example.d424jones.database.Repository;
import com.example.d424jones.entities.Excursion;
import com.example.d424jones.entities.Vacation;
import java.util.ArrayList;
import java.util.List;


public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    private List<Vacation> vacationList = new ArrayList<>();
    private String timestamp;
    private Repository repository;

    public ReportAdapter(Repository repository) {
        this.repository = repository;
    }

    public void setData(List<Vacation> vacations, String timestamp) {
        this.vacationList = vacations;
        this.timestamp = timestamp;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_item, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Vacation vacation = vacationList.get(position);


        holder.vacationTitle.setText(vacation.getVacationTitle());
        holder.vacationDates.setText(vacation.getStartDate() + " - \n " + vacation.getEndDate());


        holder.reportTimestamp.setText(timestamp);

        List<Excursion> excursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacation.getVacationID()) {
                excursions.add(e);
            }
        }
        if (excursions.isEmpty()) {
            holder.vacationExcursions.setText("None");
        } else {
            StringBuilder excursionText = new StringBuilder();
            for (int i = 0; i < excursions.size(); i++) {
                Excursion e = excursions.get(i);
                excursionText.append(e.getExcursionTitle())
                        .append(" (").append(e.getExcursionDate()).append(")");
                if (i < excursions.size() - 1) {
                    excursionText.append(", ");
                }
            }
            holder.vacationExcursions.setText(excursionText.toString());
        }
    }
/*
        List<Excursion> excursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationID() == vacation.getVacationID()) {
                excursions.add(e);
            }
        }

        StringBuilder excursionText = new StringBuilder();
        if (excursions.isEmpty()) {
            excursionText.append("No excursions");
        } else {
            for (int i = 0; i < excursions.size(); i++) {
                Excursion e = excursions.get(i);
                excursionText.append("Excursion ").append(i + 1).append(": ")
                        .append(e.getExcursionTitle())
                        .append(" (").append(e.getExcursionDate()).append(")\n");
            }
        }
        holder.vacationExcursions.setText(excursionText.toString());
    }
*/
    @Override
    public int getItemCount() {
        return vacationList.size();
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder {
        TextView vacationTitle, vacationDates, vacationExcursions, reportTimestamp;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationTitle = itemView.findViewById(R.id.reportVacationTitle);
            vacationDates = itemView.findViewById(R.id.reportVacationDates);
            vacationExcursions = itemView.findViewById(R.id.reportVacationExcursions);
            reportTimestamp = itemView.findViewById(R.id.reportTimestamp);
        }
    }
}

