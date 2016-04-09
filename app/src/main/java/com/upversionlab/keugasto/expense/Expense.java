package com.upversionlab.keugasto.expense;

import com.upversionlab.keugasto.category.Category;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by vruzeda on 4/8/16.
 */
public class Expense implements Serializable {

    public Category category;
    public float value;
    public Calendar date;
    public String userDescription;

}
