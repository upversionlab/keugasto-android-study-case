package com.upversionlab.keugasto.controller.balance;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.controller.expense.ExpensesAdapter;
import com.upversionlab.keugasto.model.category.Category;
import com.upversionlab.keugasto.model.expense.ExpenseDatabaseHelper;

import java.util.List;

/**
 * Created by vruzeda on 4/9/16.
 */
public class BalancesAdapter extends RecyclerView.Adapter<BalancesAdapter.ViewHolder> {

    private ExpenseDatabaseHelper expenseDatabaseHelper;
    private List<Category> categories;

    public BalancesAdapter(ExpenseDatabaseHelper expenseDatabaseHelper) {
        this.expenseDatabaseHelper = expenseDatabaseHelper;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_category_balance, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.nameTextView.setText(category.name);
        holder.expenseValuesTextView.setText(Float.toString(expenseDatabaseHelper.sumExpensesValuesByCategory(category)));
        holder.expensesAdapter.updateExpenses(expenseDatabaseHelper.getExpensesByCategory(category));
    }

    public void updateBalances(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView expenseValuesTextView;
        public ExpensesAdapter expensesAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.category_name);
            expenseValuesTextView = (TextView) itemView.findViewById(R.id.expense_values);
            expensesAdapter = new ExpensesAdapter();

            RecyclerView expensesRecyclerView = (RecyclerView) itemView.findViewById(R.id.expenses_recycler_view);
            expensesRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            expensesRecyclerView.setHasFixedSize(true);
            expensesRecyclerView.setAdapter(expensesAdapter);

        }
    }
}
