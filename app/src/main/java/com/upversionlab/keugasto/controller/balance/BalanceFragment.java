package com.upversionlab.keugasto.controller.balance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.controller.base.BaseFragment;
import com.upversionlab.keugasto.model.category.CategoryDatabaseHelper;
import com.upversionlab.keugasto.model.expense.ExpenseDatabaseHelper;

/**
 * Created by vruzeda on 4/9/16.
 */
public class BalanceFragment extends BaseFragment {

    private ExpenseDatabaseHelper expenseDatabaseHelper;
    private CategoryDatabaseHelper categoryDatabaseHelper;
    private BalancesAdapter balancesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        setActionBarTitle(R.string.balance_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(false);

        expenseDatabaseHelper = new ExpenseDatabaseHelper(getContext());
        categoryDatabaseHelper = new CategoryDatabaseHelper(getContext());

        balancesAdapter = new BalancesAdapter(expenseDatabaseHelper);
        balancesAdapter.updateBalances(categoryDatabaseHelper.getCategories());

        RecyclerView categoriesRecyclerView = (RecyclerView) view.findViewById(R.id.categories_recycler_view);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerView.setAdapter(balancesAdapter);

        return view;
    }
}
