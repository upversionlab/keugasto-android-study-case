package com.upversionlab.keugasto.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.base.BaseFragment;

/**
 * Created by vruzeda on 4/9/16.
 */
public class AddCategoryFragment extends BaseFragment {

    public static final String CATEGORY_EXTRA = "CATEGORY_EXTRA";

    private CategoryDAO categoryDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        setActionBarTitle(R.string.add_category_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        categoryDAO = CategoryDAO.getInstance();

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_save);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category category = categoryDAO.addCategory("Category", 100);

                Intent data = new Intent();
                data.putExtra(CATEGORY_EXTRA, category);

                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }
}
