package com.upversionlab.keugasto.controller.expense;

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
import com.upversionlab.keugasto.model.expense.ExpenseDatabaseHelper;

/**
 * Created by vruzeda on 4/8/16.
 */
public class ExpensesFragment extends BaseFragment {

    private static final int ADD_EXPENSE_REQUEST_CODE = 1;

    private ExpenseDatabaseHelper expenseDatabaseHelper;
    private ExpensesAdapter expensesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        setActionBarTitle(R.string.expenses_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        expenseDatabaseHelper = new ExpenseDatabaseHelper(getContext());

        expensesAdapter = new ExpensesAdapter();
        expensesAdapter.updateExpenses(expenseDatabaseHelper.getExpenses());

        RecyclerView expensesRecyclerView = (RecyclerView) view.findViewById(R.id.expenses_recycler_view);
        expensesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        expensesRecyclerView.setHasFixedSize(true);
        expensesRecyclerView.setAdapter(expensesAdapter);

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddExpenseActivity.class);
                startActivityForResult(intent, ADD_EXPENSE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_EXPENSE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                expensesAdapter.updateExpenses(expenseDatabaseHelper.getExpenses());
            }
        }
    }
}
