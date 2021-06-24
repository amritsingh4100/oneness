package com.amrit.oneness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amrit.oneness.Customs.CustomFlexBoxLayout;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.RecyclerViewCallback;
import com.amrit.oneness.Models.InterestModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>{

    List<PostModel> postModelArrayList =new ArrayList<>();
    RecyclerViewCallback recyclerViewCallback;
    Context context;


    public PostRecyclerViewAdapter(Context context,RecyclerViewCallback recyclerViewCallback)
    {
        this.recyclerViewCallback=recyclerViewCallback;
        this.context=context;
    }

    public void updateList(List<PostModel> postModelArrayList)
    {
        this.postModelArrayList = postModelArrayList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTxt,postTitleTxt,postDetailsTxt, responseCount,timeAgo;
        ImageView userThumbnailImg;
        RelativeLayout itemPostClickLayout;
        FlexboxLayout flexboxLayout;
        public ViewHolder(View view) {
            super(view);

            userNameTxt = (TextView) view.findViewById(R.id.post_user_name_id);
            postTitleTxt = (TextView) view.findViewById(R.id.post_title_id);
            postDetailsTxt = (TextView) view.findViewById(R.id.post_details_id);
            responseCount = (TextView) view.findViewById(R.id.post_response_count_id);
            userThumbnailImg=(ImageView)view.findViewById(R.id.post_user_thumbnail_id);
            timeAgo=(TextView) view.findViewById(R.id.post_time_ago_id);
            itemPostClickLayout = view.findViewById(R.id.post_holder_item_click_id);
            flexboxLayout=view.findViewById(R.id.post_flexlayout_id);
            // Define click listener for the ViewHolder's View
        }

        public FlexboxLayout getFlexboxLayout() {
            return flexboxLayout;
        }


        public RelativeLayout getItemHolderClick() {
            return itemPostClickLayout;
        }

        public TextView getUserNameTxt() {
            return userNameTxt;
        }
        public TextView getPostTitleTxt() {
            return postTitleTxt;
        }
        public TextView getPostDetailsTxt() {
            return postDetailsTxt;
        }
        public TextView getResponseCount() {
            return responseCount;
        }
        public ImageView getUserThumbnailImg() {
            return userThumbnailImg;
        }
        public TextView getTimeAgo() {
            return timeAgo;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_inflator_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        PostModel post= postModelArrayList.get(position);

        viewHolder.getPostTitleTxt().setText(post.getPostTitle());
        viewHolder.getPostDetailsTxt().setText(post.getPostDetails());
        viewHolder.getUserNameTxt().setText(post.getPostUserName());
        String commentsCount = String.valueOf(post.getCommentModelArrayList().size());
        viewHolder.getResponseCount().setText(commentsCount);

        long timeAgo= post.getPostTimeAgo();
        String timeAgoString = Utils.timeAgo(timeAgo);
        viewHolder.getTimeAgo().setText(timeAgoString);
        // dummy for now, will need to use conversion formula later

        Glide.with(context).load(post.getPostUserThumbnailImageLink()).centerCrop()
                .placeholder(R.drawable.blue_gradient_background)
                .into(viewHolder.getUserThumbnailImg());

        List<InterestModel> interestModelArrayList =post.getInterestArrayList();

        CustomFlexBoxLayout customFlexBoxLayout = new CustomFlexBoxLayout(context);
        customFlexBoxLayout.setInterestArrayListToShow(interestModelArrayList).addViews(viewHolder.getFlexboxLayout());

        viewHolder.getItemHolderClick().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewCallback.itemClicked(post.getPostKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModelArrayList.size();
    }
}
