package com.example.glpi.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glpi.R;
import com.example.glpi.api.get.Ticket;
import com.example.glpi.api.interfaces.DetailTicketsInterface;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<Ticket> ticketList;
    private Context context;
    private int stateNumber;
    private int urgencyNumber;
    private final DetailTicketsInterface detailTicketsInterface;

    public ListViewAdapter(Context context, List<Ticket> ticketList,DetailTicketsInterface detailTicketsInterface) {
        this.context = context;
        this.ticketList = ticketList;
        this.detailTicketsInterface = detailTicketsInterface;
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view,parent,false);

        return new ViewHolder(view, detailTicketsInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {

        urgencyNumber = ticketList.get(position).getUrgency();

        holder.textViewUrgency.setText(String.valueOf(urgencyNumber));

        switch(urgencyNumber){
            case 1:
            case 2:
                holder.textViewUrgency.setTextColor(Color.rgb(0,255,0));
                break;
            case 3:
            case 4:
                holder.textViewUrgency.setTextColor(Color.rgb(255,170,0));
                break;
            case 5:
                holder.textViewUrgency.setTextColor(Color.rgb(255,0,0));
                break;
        }



        holder.textViewIncidenciaName.setText(ticketList.get(position).getName());
        holder.textViewIncidenciaContent.setText(ticketList.get(position).getContent());


        //holder.textViewState.setText(String.valueOf(ticketList.get(position).getStatus()));

        stateNumber = ticketList.get(position).getStatus();


        switch(stateNumber){
           case 1:
            holder.textViewState.setText("Incidencia creada");
            break;
           case 2:
               holder.textViewState.setText("Asignada");
               break;
           case 3:
               holder.textViewState.setText("Planificada");
               break;
           case 4:
               holder.textViewState.setText("En espera");
               break;
           case 5:
               holder.textViewState.setText("Resuelto");
               break;
           case 6:
               holder.textViewState.setText("Cerrado");
               break;
        }
    }

    @Override
    public int getItemCount() {
        if(ticketList== null){
            return 0;
        }
        return ticketList.size();
    }

    public void setData(@NonNull List<Ticket> listOfTickets){
        if(listOfTickets.size() > 0) {
            ticketList = listOfTickets;
        }
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewState,textViewIncidenciaContent,textViewIncidenciaName,textViewUrgency;

        public ViewHolder(@NonNull View itemView, DetailTicketsInterface detailTicketsInterface) {
            super(itemView);

            textViewUrgency = itemView.findViewById(R.id.textViewUrgency);
            textViewIncidenciaName = itemView.findViewById(R.id.textViewIncidenciaName);
            textViewIncidenciaContent = itemView.findViewById(R.id.textViewIncidenciaContent);
            textViewState = itemView.findViewById(R.id.textViewState);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(detailTicketsInterface != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            detailTicketsInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
