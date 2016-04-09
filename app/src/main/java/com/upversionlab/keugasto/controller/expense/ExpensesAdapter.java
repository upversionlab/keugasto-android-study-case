package com.upversionlab.keugasto.controller.expense;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.model.expense.Expense;

import java.util.List;

/**
 * Created by vruzeda on 4/9/16.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ViewHolder> {

    private List<Expense> expenses;

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_expense, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.categoryTextView.setText(expense.category.name);
        holder.valueTextView.setText(Float.toString(expense.value));
    }

    public void updateExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryTextView;
        public TextView valueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryTextView = (TextView) itemView.findViewById(R.id.expense_category);
            valueTextView = (TextView) itemView.findViewById(R.id.expense_value);
        }
    }
}
