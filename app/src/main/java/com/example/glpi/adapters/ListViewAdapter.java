package com.example.glpi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glpi.R;
import com.example.glpi.api.get.Ticket;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private List<Ticket> ticketList;
    private Context context;

    public ListViewAdapter(Context context, List<Ticket> ticketList) {
        this.context = context;
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public ListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.ViewHolder holder, int position) {

        holder.textViewUrgency.setText(String.valueOf(ticketList.get(position).getUrgency()));
        holder.textViewIncidenciaName.setText(ticketList.get(position).getName());
        holder.textViewIncidenciaContent.setText(ticketList.get(position).getContent());
        holder.textViewState.setText(String.valueOf(ticketList.get(position).getStatus()));

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewState,textViewIncidenciaContent,textViewIncidenciaName,textViewUrgency;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUrgency = itemView.findViewById(R.id.textViewUrgency);
            textViewIncidenciaName = itemView.findViewById(R.id.textViewIncidenciaName);
            textViewIncidenciaContent = itemView.findViewById(R.id.textViewIncidenciaContent);
            textViewState = itemView.findViewById(R.id.textViewState);

        }
    }
}
