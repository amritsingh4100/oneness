package com.amrit.oneness.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.amrit.oneness.Fragments.ProfileOneFragment;
import com.amrit.oneness.Fragments.ProfileThreeFragment;
import com.amrit.oneness.Fragments.ProfileTwoFragment;

public class EditProfileAdapter extends FragmentStateAdapter {
    ProfileOneFragment profileOneFragment;
    ProfileTwoFragment profileTwoFragment;
    ProfileThreeFragment profileThreeFragment;

    public EditProfileAdapter(FragmentActivity fa) {
        super(fa);
    }
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                profileOneFragment = new ProfileOneFragment();
                return profileOneFragment;
            case 1:
                profileTwoFragment =  new ProfileTwoFragment();
                return profileTwoFragment;
            case 2:
                profileThreeFragment = new ProfileThreeFragment();
                return profileThreeFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() { return 3;
    }

}
