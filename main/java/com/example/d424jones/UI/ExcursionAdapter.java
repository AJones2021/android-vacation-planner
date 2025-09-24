package com.example.d424jones.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d424jones.R;
import com.example.d424jones.entities.Excursion;

import java.util.List;
//inheritance behavior from RecyclerView.Adapter
public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {


        private final TextView excursionItemView;
        private final TextView excursuionItemView2;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView4);
            excursuionItemView2 = itemView.findViewById(R.id.textView5);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("title", current.getExcursionTitle());
                    intent.putExtra("date", current.getExcursionDate());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    context.startActivity(intent);
                }
            });

        }
    }
    public ExcursionAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
        return new ExcursionViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position){
        if(mExcursions != null){
            Excursion current = mExcursions.get(position);
            String title = current.getExcursionTitle();
            int vacationID = current.getVacationID();
            holder.excursionItemView.setText(title);
            holder.excursuionItemView2.setText(Integer.toString(vacationID));
        }
        else{
            holder.excursionItemView.setText("No excursion title");
            holder.excursuionItemView2.setText("No vacation id");
        }

    }
    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if(mExcursions!=null) return mExcursions.size();
        else return 0;
    }
}
