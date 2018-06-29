package com.adwait.crud.presentation.view.adapter;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
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
import com.adwait.crud.presentation.view.activity.SampleUserActivity;
import com.adwait.crud.presentation.view.adapter.SampleUserListAdapter.SampleUserListHolder;

import java.util.ArrayList;
import java.util.List;

public class SampleUserListAdapter extends RecyclerView.Adapter<SampleUserListHolder> implements SampleUserActivity.SwipeActionCallback {

    private List<SampleUser> userList;
    private int deletedPosition;
    private SampleUser deletedUser;
    private RecyclerView mRecyclerView;
    private DeleteCallback deleteCallback;

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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView  = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public void onCellSwiped(SampleUserListHolder holder, int direction, int position) {

        //remove from adapter
        deletedUser = userList.remove(position);
        deletedPosition = position;
        notifyItemRemoved(deletedPosition);

        //show snackbar
        showSnackbar();
    }

    private void showSnackbar() {
        if (mRecyclerView!=null) {
            Snackbar snackbar = Snackbar.make(mRecyclerView, deletedUser.getUsername() + " removed from list",Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    restoreItem(deletedUser, deletedPosition);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.addCallback(new Snackbar.Callback(){
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    if(DISMISS_EVENT_TIMEOUT == event){
                        invokePermanantDeleteAction(deletedPosition);
                    }
                }
            });
            snackbar.show();
        }
    }

    private void invokePermanantDeleteAction(int position) {
        if (deleteCallback!=null) {
            deleteCallback.delete(position);
        }
    }

    private void restoreItem(SampleUser deletedUser, int deletedPosition) {
        userList.add(deletedPosition,deletedUser);
        notifyItemInserted(deletedPosition);
    }

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public class SampleUserListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cardView_profile;
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

    public interface DeleteCallback {
        void delete(int position);
    }
}
