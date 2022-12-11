package com.example.finalproject.MapPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapSearchAdapter extends RecyclerView.Adapter<MapSearchAdapter.ViewHolder> {
    ArrayList<MapSearchVO> itemList;
    Context context;

    public MapSearchAdapter(Context context, ArrayList<MapSearchVO> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.road_address.setText(itemList.get(position).getRoad_address());
        holder.location_address.setText(itemList.get(position).getLocation_address());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,road_address,location_address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           name=itemView.findViewById(R.id.tv_list_name);
           road_address=itemView.findViewById(R.id.tv_list_road);
           location_address=itemView.findViewById(R.id.tv_list_address);
        }
    }
}
