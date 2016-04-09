package com.upversionlab.keugasto.controller.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.base.BaseFragment;
import com.upversionlab.keugasto.model.category.Category;
import com.upversionlab.keugasto.model.category.CategoryDAO;

/**
 * Created by vruzeda on 4/9/16.
 */
public class AddCategoryFragment extends BaseFragment {

    public static final String CATEGORY_EXTRA = "CATEGORY_EXTRA";

    private EditText nameEditText;
    private EditText limitEditText;

    private CategoryDAO categoryDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        setActionBarTitle(R.string.add_category_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        nameEditText = (EditText) view.findViewById(R.id.add_category_name);
        limitEditText = (EditText) view.findViewById(R.id.add_category_limit);

        categoryDAO = CategoryDAO.getInstance();

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_save);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                float limit = Float.parseFloat(limitEditText.getText().toString());

                Category category = categoryDAO.addCategory(name, limit);

                Intent data = new Intent();
                data.putExtra(CATEGORY_EXTRA, category);

                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }
}
