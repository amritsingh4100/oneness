package com.amrit.oneness.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amrit.oneness.Customs.CustomFlexBoxLayout;
import com.amrit.oneness.Customs.Utils;
import com.amrit.oneness.Interfaces.RecyclerViewCallback;
import com.amrit.oneness.Models.CommentModel;
import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.R;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

public class PostCommentsRecyclerViewAdapter extends RecyclerView.Adapter<PostCommentsRecyclerViewAdapter.ViewHolder>{

    PostModel postModel;
    ArrayList<PostModel> postModelArrayList =new ArrayList<>();
    Context context;

    String TAG= getClass().getSimpleName();


    public PostCommentsRecyclerViewAdapter(Context context)
    {
        this.context=context;
    }

    public void updateList(PostModel postModel)
    {
        this.postModel = postModel;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //upperview objects
        TextView userNameTxt,postTitleTxt,postDetailsTxt, responseCount,timeAgo;
        ImageView userThumbnailImg, mainUpperImage;
        FlexboxLayout flexboxLayout;

        //comments objects
        ImageView commentInflatorThumbnail;
        TextView commentInflatorUsername,commentInflatorContent, commentInflatorTimeAgo;


        public ViewHolder(View view) {
            super(view);

            //post comments upperview
            userNameTxt = (TextView) view.findViewById(R.id.post_comment_username_id);
            postTitleTxt = (TextView) view.findViewById(R.id.post_comment_title_id);
            postDetailsTxt = (TextView) view.findViewById(R.id.post_comment_details_id);
            responseCount = (TextView) view.findViewById(R.id.post_comments_responses_count_id);
            userThumbnailImg=(ImageView)view.findViewById(R.id.post_comments_thumbnail_id);
            timeAgo=(TextView) view.findViewById(R.id.post_comment_timeago_id);
            flexboxLayout=view.findViewById(R.id.post_comment_flexlayout_id);
            mainUpperImage= view.findViewById(R.id.post_comment_main_upper_image);
            // comments inflator view

            commentInflatorThumbnail= view.findViewById(R.id.comment_inflator_thumbnail_id);
            commentInflatorUsername= view.findViewById(R.id.comment_inflator_username_id);
            commentInflatorContent=view.findViewById(R.id.comment_inflator_content_id);
            commentInflatorTimeAgo= view.findViewById(R.id.comment_inflator_time_ago);
        }

        //making functions for upperview

        public FlexboxLayout getFlexboxLayout() {
            return flexboxLayout;
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
        public ImageView getMainUpperImage(){ return mainUpperImage; }

        // making functions for comments

        public TextView getCommentInflatorUsername() {
            return commentInflatorUsername;
        }
        public TextView getCommentInflatorContent() {
            return commentInflatorContent;
        }
        public TextView getCommentInflatorTimeAgo() {
            return commentInflatorTimeAgo;
        }
        public ImageView getCommentInflatorThumbnail() {
            return commentInflatorThumbnail;
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View upperView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_comments_upperview_recyclerview_layout, viewGroup, false);

        View lowerView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comments_inflater_layout, viewGroup, false);
        if(viewType==0) return new ViewHolder(upperView);
        else return new ViewHolder(lowerView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ArrayList<CommentModel> commentModelArrayList = postModel.getCommentModelArrayList();
        Utils.print(TAG,"position is:"+position);

        if(position==0)
        {
            viewHolder.getPostTitleTxt().setText(postModel.getPostTitle());
            viewHolder.getPostDetailsTxt().setText(postModel.getPostDetails());
            viewHolder.getUserNameTxt().setText(postModel.getPostUserName());
            viewHolder.getUserNameTxt().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.openProfilePage(context,postModel.getUserID());
                }
            });
            String commentsCount = String.valueOf(postModel.getCommentModelArrayList().size());
            viewHolder.getResponseCount().setText(commentsCount);

            long timeAgo= postModel.getPostTimeAgo();
            viewHolder.getTimeAgo().setText("5 hours ago");
            // dummy for now, will need to use conversion formula later

            Glide.with(context).load(postModel.getPostUserThumbnailImageLink()).centerCrop()
                    .placeholder(R.drawable.blue_gradient_background)
                    .into(viewHolder.getUserThumbnailImg());
            if(!postModel.getPostImageLink().isEmpty())
            {
                viewHolder.getMainUpperImage().setVisibility(View.VISIBLE);
                viewHolder.getMainUpperImage().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                   Utils.openZoomableImage(context,postModel.getPostImageLink());
                    }
                });
                Glide.with(context).load(postModel.getPostImageLink()).centerCrop().placeholder(R.drawable.blue_gradient_background)
                        .into(viewHolder.getMainUpperImage());
            }
            else viewHolder.getMainUpperImage().setVisibility(View.GONE);

            CustomFlexBoxLayout customFlexBoxLayout = new CustomFlexBoxLayout(context);
            customFlexBoxLayout.setInterestArrayListToShow(postModel.getInterestArrayList()).addViews(viewHolder.getFlexboxLayout());
        }
        else
        {
            int commentPosition= position - 1;
            Utils.print(TAG,"comment position: "+commentPosition+" comment data"+ commentModelArrayList.get(commentPosition));

//             making object for that position
            CommentModel commentModel = commentModelArrayList.get(commentPosition);
            Utils.print("Bankai","comment data : "+ commentModel.getCommentContent());
            viewHolder.getCommentInflatorUsername().setText(commentModel.getCommentUserName());
            viewHolder.getCommentInflatorContent().setText(commentModel.getCommentContent());
            viewHolder.getCommentInflatorTimeAgo().setText(commentModel.getCommentTimeAgo());
            Glide.with(context).load(commentModel.getCommentThumbnailImageLink()).centerCrop()
                    .placeholder(R.drawable.blue_gradient_background)
                    .into(viewHolder.getCommentInflatorThumbnail());

        }
    }


    @Override
    public int getItemViewType(int position) {

        if(position==0) return 0;
        else return 1;
    }

    @Override
    public int getItemCount() {
        return postModel.getCommentModelArrayList().size()+1;
    }
}
