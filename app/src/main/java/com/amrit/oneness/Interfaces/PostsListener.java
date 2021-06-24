package com.amrit.oneness.Interfaces;

import com.amrit.oneness.Models.PostModel;
import com.amrit.oneness.Models.ProfileModel;

import java.util.List;

public interface PostsListener {
    void updatePostsList(List<PostModel> postModelList);
    void updatedPost(PostModel postModel);
}
