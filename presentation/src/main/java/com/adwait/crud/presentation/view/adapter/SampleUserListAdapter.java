package com.adwait.crud.presentation.view.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adwait.crud.presentation.R;
import com.adwait.crud.presentation.model.SampleUserList.SampleUser;
import com.adwait.crud.presentation.view.adapter.SampleUserListAdapter.SampleUserListHolder;

import java.util.ArrayList;
import java.util.List;

public class SampleUserListAdapter extends RecyclerView.Adapter<SampleUserListHolder>{

    private List<SampleUser> userList;

    public SampleUserListAdapter() {
        userList = new ArrayList<>();
    }

    @Override
    public SampleUserListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_swipe_delete_user, parent, false);
        return new SampleUserListHolder(view);
    }

    @Override
    public void onBindViewHolder(SampleUserListHolder holder, int position) {
        try {
            SampleUser user = userList.get(position);
            if (user!=null) {
                holder.cardView_profile.setTag(user);
                holder.textView_name.setText(user.getUsername());
                holder.textView_email.setText(user.getEmail());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userList==null?0:userList.size();
    }

    public void setUserList(List<SampleUser> userList){
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    public class SampleUserListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView_profile;
        private RelativeLayout relativeLayout_delete;
        private TextView textView_delete;
        private ImageView imageView_delete;
        private TextView textView_email;
        private TextView textView_name;
        private ImageView imageView_profile;

        public SampleUserListHolder(View itemView) {
            super(itemView);
            cardView_profile = itemView.findViewById(R.id.cardView_profile);
            imageView_profile = itemView.findViewById(R.id.imageView_profile);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_email = itemView.findViewById(R.id.textView_email);
            relativeLayout_delete = itemView.findViewById(R.id.relativeLayout_delete);
            textView_delete = itemView.findViewById(R.id.textView_delete);
            imageView_delete = itemView.findViewById(R.id.imageView_delete);

            cardView_profile.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.cardView_profile:

                    break;
            }
        }
    }
}
