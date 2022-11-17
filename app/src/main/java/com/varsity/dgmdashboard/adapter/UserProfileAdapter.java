package com.varsity.dgmdashboard.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.varsity.dgmdashboard.fragment.PersonalProfileFragment;
import com.varsity.dgmdashboard.fragment.ProfessionalProfileFragment;

import java.util.Objects;

public class UserProfileAdapter extends FragmentStateAdapter {

    private int totalTabs;

    public UserProfileAdapter(@NonNull FragmentActivity fragment, int totalTabs) {
        super(fragment);
        this.totalTabs = totalTabs;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new PersonalProfileFragment();
        }
        if (position == 1) {
            fragment = new ProfessionalProfileFragment();


        }

        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}