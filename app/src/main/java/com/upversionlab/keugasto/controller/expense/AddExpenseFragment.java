package com.upversionlab.keugasto.controller.expense;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.upversionlab.keugasto.R;
import com.upversionlab.keugasto.base.BaseFragment;
import com.upversionlab.keugasto.controller.category.AddCategoryActivity;
import com.upversionlab.keugasto.controller.category.AddCategoryFragment;
import com.upversionlab.keugasto.model.category.Category;
import com.upversionlab.keugasto.model.category.CategoryDatabaseHelper;
import com.upversionlab.keugasto.model.expense.Expense;
import com.upversionlab.keugasto.model.expense.ExpenseDatabaseHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vruzeda on 4/9/16.
 */
public class AddExpenseFragment extends BaseFragment {

    public static final String EXPENSE_EXTRA = "EXPENSE_EXTRA";

    private static final int ADD_CATEGORY_REQUEST_CODE = 1;

    private ExpenseDatabaseHelper expenseDatabaseHelper;
    private CategoryDatabaseHelper categoryDatabaseHelper;

    private EditText categoryEditText;
    private EditText valueEditText;
    private EditText dateEditText;
    private EditText userDescriptionEditText;

    private Category selectedCategory;
    private Calendar selectedDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);
        setActionBarTitle(R.string.add_expense_title);
        setHasOptionsMenu(false);
        setHasFloatingActionButton(true);

        expenseDatabaseHelper = new ExpenseDatabaseHelper(getContext());
        categoryDatabaseHelper = new CategoryDatabaseHelper(getContext());

        categoryEditText = (EditText) view.findViewById(R.id.add_expense_category);
        valueEditText = (EditText) view.findViewById(R.id.add_expense_value);
        dateEditText = (EditText) view.findViewById(R.id.add_expense_date);
        userDescriptionEditText = (EditText) view.findViewById(R.id.add_expense_user_description);

        categoryEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryPickerFragment categoryPickerFragment = new CategoryPickerFragment();
                categoryPickerFragment.setCategoryDatabaseHelper(categoryDatabaseHelper);
                categoryPickerFragment.setCategoryPickerListener(new CategoryPickerListener());
                categoryPickerFragment.setCategoryPickerAddCategoryListener(new CategoryPickerAddCategoryListener());
                categoryPickerFragment.show(getFragmentManager(), "categoryPicker");
            }
        });

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setDatePickerListener(new DatePickerListener());
                datePickerFragment.show(getFragmentManager(), "datePicker");
            }
        });

        return view;
    }

    @Override
    public void onCreateFloatingActionButton(FloatingActionButton floatingActionButton) {
        floatingActionButton.setImageResource(android.R.drawable.ic_menu_save);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float value = Float.parseFloat(valueEditText.getText().toString());
                String userDescription = userDescriptionEditText.getText().toString();

                Expense expense = expenseDatabaseHelper.addExpense(selectedCategory, value, selectedDate, userDescription);

                Intent data = new Intent();
                data.putExtra(EXPENSE_EXTRA, expense);

                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_CATEGORY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                selectedCategory = (Category) data.getSerializableExtra(AddCategoryFragment.CATEGORY_EXTRA);

                categoryEditText.setText(selectedCategory.name);
            }
        }
    }

    public static class CategoryPickerFragment extends DialogFragment {

        private CategoryDatabaseHelper categoryDatabaseHelper;
        private CategoryPickerListener categoryPickerListener;
        private CategoryPickerAddCategoryListener categoryPickerAddCategoryListener;

        private List<String> categoryNames;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            categoryNames = new ArrayList<>();
            for (Category category : categoryDatabaseHelper.getCategories()) {
                categoryNames.add(category.name);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.add_expense_category)
                    .setItems(categoryNames.toArray(new String[0]), categoryPickerListener)
                    .setPositiveButton(R.string.add_expense_add_category, categoryPickerAddCategoryListener)
                    .setNegativeButton(android.R.string.cancel, null);

            return builder.create();
        }

        public void setCategoryDatabaseHelper(CategoryDatabaseHelper categoryDatabaseHelper) {
            this.categoryDatabaseHelper = categoryDatabaseHelper;
        }

        public void setCategoryPickerListener(CategoryPickerListener listener) {
            this.categoryPickerListener = listener;
        }

        public void setCategoryPickerAddCategoryListener(CategoryPickerAddCategoryListener listener) {
            this.categoryPickerAddCategoryListener = listener;
        }
    }

    private class CategoryPickerListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            selectedCategory = categoryDatabaseHelper.getCategories().get(which);

            categoryEditText.setText(selectedCategory.name);
        }
    }

    private class CategoryPickerAddCategoryListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
            startActivityForResult(intent, ADD_CATEGORY_REQUEST_CODE);
        }
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerListener datePickerListener;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
        }

        public void setDatePickerListener(DatePickerListener listener) {
            this.datePickerListener = listener;
        }
    }

    private class DatePickerListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            selectedDate = Calendar.getInstance();
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, monthOfYear);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            selectedDate.set(Calendar.HOUR_OF_DAY, 0);
            selectedDate.set(Calendar.MINUTE, 0);
            selectedDate.set(Calendar.SECOND, 0);
            selectedDate.set(Calendar.MILLISECOND, 0);

            DateFormat dateFormat = DateFormat.getDateInstance();
            dateEditText.setText(dateFormat.format(selectedDate.getTime()));
        }
    }
}
