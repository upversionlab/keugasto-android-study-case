package com.upversionlab.keugasto.controller.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.controller.base.BaseFragment;
import com.upversionlab.keugasto.model.category.CategoryDatabaseHelper;

/**
 * Created by vruzeda on 4/8/16.
 */
public class CategoriesFragment extends BaseFragment {

    private static final int ADD_CATEGORY_REQUEST_CODE = 1;

    private CategoryDatabaseHelper categoryDatabaseHelper;
    private CategoriesAdapter categoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        setActionBarTitle(R.string.categories_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        categoryDatabaseHelper = new CategoryDatabaseHelper(getContext());

        categoriesAdapter = new CategoriesAdapter();
        categoriesAdapter.updateCategories(categoryDatabaseHelper.getCategories());

        RecyclerView categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddCategoryActivity.class);
                startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CATEGORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                categoriesAdapter.updateCategories(categoryDatabaseHelper.getCategories());
            }
        }
    }
}
