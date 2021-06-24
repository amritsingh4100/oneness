package com.amrit.oneness.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.amrit.oneness.Fragments.LoginFragment;
import com.amrit.oneness.Fragments.RegisterFragment;
import com.amrit.oneness.Interfaces.FragmentInterface;

public class LoginAndRegisterFragmentAdapter extends FragmentStateAdapter {
    FragmentInterface fragmentInterface;
    public LoginAndRegisterFragmentAdapter(FragmentActivity fa,FragmentInterface fragmentInterface) {
        super(fa);
        this.fragmentInterface=fragmentInterface;
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new LoginFragment(fragmentInterface);
            case 1:
                return new RegisterFragment(fragmentInterface);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
