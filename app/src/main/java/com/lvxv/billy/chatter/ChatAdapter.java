package com.lvxv.billy.chatter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 7/26/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int SELF = 1000;

    Activity context;
    ArrayList<HashMap<String,String>> messagePackage;
    String EmailId;

    public class  ViewHolder extends RecyclerView.ViewHolder{
        TextView txtViewUsername,txtViewMessage,txtViewDate;

        public ViewHolder(View view) {
            super(view);
            txtViewUsername = (TextView) itemView.findViewById(R.id.username);
            txtViewMessage = (TextView) itemView.findViewById(R.id.message);
            txtViewDate = (TextView) itemView.findViewById(R.id.receivetime);
        }
    }

    public ChatAdapter(Activity context,ArrayList<HashMap<String,String>> messagePackage,String EmailId){
        super();
        this.context = context;
        this.messagePackage = messagePackage;
        this.EmailId = EmailId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        // view type is to identify where to render the chat message
        // left or right
        if (viewType == SELF) {
            // self message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_self, parent, false);
        } else {
            // others message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bubble_friends, parent, false);
        }


        return new ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {

        if (messagePackage.get(position).get("Email").equals(EmailId)) {
            return SELF ;
        }

        return position;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).txtViewUsername.setText(messagePackage.get(position).get("Username"));
        ((ViewHolder)holder).txtViewMessage.setText(messagePackage.get(position).get("Message"));
        ((ViewHolder)holder).txtViewDate.setText(messagePackage.get(position).get("Receivetime"));
    }

    @Override
    public int getItemCount() {
        return messagePackage.size();
    }

    /*
    public ChatAdapter(Activity context,ArrayList<HashMap<String,String>> messagePackage,String EmailId){
        super();
        this.context = context;
        this.messagePackage = messagePackage;
        this.EmailId = EmailId;
    }

    @Override
    public int getCount() {
        return messagePackage.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewCase {
        TextView txtViewUsername;
        TextView txtViewMessage;
        TextView txtViewDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewCase holder;
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null){
            if(messagePackage.get(position).get("Email")== EmailId){
                convertView = inflater.inflate(R.layout.bubble_self , null);
            }else {
                convertView = inflater.inflate(R.layout.bubble_friends , null);
            }
            holder = new ViewCase();
            holder.txtViewUsername = (TextView) convertView.findViewById(R.id.username);
            holder.txtViewMessage = (TextView) convertView.findViewById(R.id.message);
            holder.txtViewDate = (TextView) convertView.findViewById(R.id.receivetime);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewCase) convertView.getTag();
        }

        holder.txtViewUsername.setText(messagePackage.get(position).get("Username"));
        holder.txtViewMessage.setText(messagePackage.get(position).get("Message"));
        holder.txtViewDate.setText(messagePackage.get(position).get("Receivetime"));

        return convertView;
    }*/
}
