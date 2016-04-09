package com.upversionlab.keugasto.expense;

import com.upversionlab.keugasto.category.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vruzeda on 4/8/16.
 */
public class ExpenseDAO {

    private static ExpenseDAO instance = null;

    public static ExpenseDAO getInstance() {
        if (instance == null) {
            instance = new ExpenseDAO();
        }
        return instance;
    }

    private List<Expense> expenses;

    private ExpenseDAO() {
        expenses = new ArrayList<>();
    }

    public Expense addExpense(Category category, float value, Date date, String userDescription) {
        Expense expense = new Expense();
        expense.category = category;
        expense.value = value;
        expense.date = date;
        expense.userDescription = userDescription;
        expenses.add(expense);
        return expense;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

}
