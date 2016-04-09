package com.upversionlab.keugasto.model.expense;

import com.upversionlab.keugasto.model.category.Category;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by vruzeda on 4/8/16.
 */
public class Expense implements Serializable {

    public long id;
    public Category category;
    public float value;
    public Calendar date;
    public String userDescription;

}
