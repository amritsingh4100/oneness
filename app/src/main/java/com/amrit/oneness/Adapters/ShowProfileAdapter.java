package com.amrit.oneness.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.amrit.oneness.Fragments.ShowProfileOneFragment;
import com.amrit.oneness.Fragments.ShowProfileThreeFragment;
import com.amrit.oneness.Fragments.ShowProfileTwoFragment;
import com.amrit.oneness.Models.ProfileModel;

public class ShowProfileAdapter extends FragmentStateAdapter {
    ProfileModel profileModel;
    public ShowProfileAdapter(FragmentActivity fa,ProfileModel profileModel) {
        super(fa);
        this.profileModel=profileModel;
    }
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                 return new ShowProfileOneFragment(profileModel);
            case 1:
                return new ShowProfileTwoFragment(profileModel);
            case 2:
                return new ShowProfileThreeFragment(profileModel);
        }
        return null;
    }

    @Override
    public int getItemCount() { return 3;
    }
}
