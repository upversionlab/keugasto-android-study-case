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
import android.widget.TextView;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.base.BaseFragment;
import com.upversionlab.keugasto.model.category.Category;
import com.upversionlab.keugasto.model.category.CategoryDAO;

import java.util.List;

/**
 * Created by vruzeda on 4/8/16.
 */
public class CategoriesFragment extends BaseFragment {

    private static final int ADD_CATEGORY_REQUEST_CODE = 1;

    private CategoriesAdapter categoriesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        setActionBarTitle(R.string.categories_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        categoriesAdapter = new CategoriesAdapter();

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
                categoriesAdapter.updateCategories();
            }
        }
    }

    private class CategoriesAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

        private CategoryDAO categoryDAO;
        private List<Category> categories;

        CategoriesAdapter() {
            categoryDAO = CategoryDAO.getInstance();
            updateCategories();
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        @Override
        public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_category, parent, false);
            return new ExpenseViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ExpenseViewHolder holder, int position) {
            Category category = categories.get(position);
            holder.nameTextView.setText(category.name);
        }

        public void updateCategories() {
            categories = categoryDAO.getCategories();
            notifyDataSetChanged();
        }
    }

    private class ExpenseViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.category_name);
        }
    }
}
