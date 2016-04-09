package com.upversionlab.keugasto.controller.base;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.upversionlab.keugasto.R;

/**
 * Created by vruzeda on 4/8/16.
 */
public abstract class BaseFragment extends Fragment {

    public void setActionBarTitle(int resourceId) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(resourceId);
    }

    public void setHasFloatingActionButton(boolean hasFloatingActionButton) {
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        if (hasFloatingActionButton) {
            fab.setVisibility(View.VISIBLE);
            onCreateFloatingActionButton(fab);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
    }

    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        // Do nothing!
    }
}
