package com.upversionlab.keugasto.expense;

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
import com.upversionlab.keugasto.category.CategoryDAO;

import java.util.Date;

/**
 * Created by vruzeda on 4/9/16.
 */
public class AddExpenseFragment extends BaseFragment {

    public static final String EXPENSE_EXTRA = "EXPENSE_EXTRA";

    private ExpenseDAO expenseDAO;
    private CategoryDAO categoryDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        setActionBarTitle(R.string.add_expense_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        expenseDAO = ExpenseDAO.getInstance();
        categoryDAO = CategoryDAO.getInstance();

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_save);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense expense = expenseDAO.addExpense(categoryDAO.getCategories().get(0), 100, new Date(), null);

                Intent data = new Intent();
                data.putExtra(EXPENSE_EXTRA, expense);

                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }
}
