package com.university.brailleflip.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.university.brailleflip.R;
import com.university.brailleflip.bean.BlueToothDevicesInfo;


import java.util.ArrayList;

public class MyBlueToothAdapter extends RecyclerView.Adapter<MyBlueToothAdapter.TaskViewHolder>{
    ArrayList<BlueToothDevicesInfo> list = new ArrayList<>();

    public ArrayList<BlueToothDevicesInfo> getList() {
        return list;
    }

    public void setList(ArrayList<BlueToothDevicesInfo> list) {
        this.list = list;
    }

    public void addItem(BlueToothDevicesInfo item){
        this.list.add(item);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  TaskViewHolder holder, int position) {
        BlueToothDevicesInfo blueToothDevicesInfo = list.get(position);

        holder.name.setText(blueToothDevicesInfo.getName());

        boolean pair = blueToothDevicesInfo.isPair();
        if (pair){
            holder.type.setText("Device paired!");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConnectClick!=null){
                    onConnectClick.connectClick(blueToothDevicesInfo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size();
    }

    private OnConnectClick onConnectClick;

    public void setOnConnectClick(OnConnectClick onConnectClick) {
        this.onConnectClick = onConnectClick;
    }

    public interface OnConnectClick{
        void connectClick(BlueToothDevicesInfo blueToothDevicesInfo);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        private TextView type;
        private TextView name;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);

        }
    }
}
