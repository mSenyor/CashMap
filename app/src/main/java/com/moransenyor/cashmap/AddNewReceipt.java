package com.moransenyor.cashmap;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewReceipt extends AppCompatActivity {

    DBAdapter myDb;
    Time today = new Time(Time.getCurrentTimezone());

    // on version 1: id, price, currency, date, product, vendor, category, receipt, behalf, tags, notes

    EditText editText_price, editText_currency, editText_date, editText_product,
            editText_vendor, editText_category, editText_receipt, editText_behalf,
            editText_tags, editText_notes;

    Button btnToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_price = (EditText)findViewById(R.id.new_receipt_price);
        editText_currency = (EditText)findViewById(R.id.new_receipt_currency);
        editText_date = (EditText)findViewById(R.id.new_receipt_date);
        editText_product = (EditText)findViewById(R.id.new_receipt_product);
        editText_vendor = (EditText)findViewById(R.id.new_receipt_vendor);
        editText_category = (EditText)findViewById(R.id.new_receipt_category);
        editText_receipt = (EditText)findViewById(R.id.new_receipt_image);
        editText_behalf = (EditText)findViewById(R.id.new_receipt_behalf);
        editText_tags = (EditText)findViewById(R.id.new_receipt_tags);
        editText_notes = (EditText)findViewById(R.id.new_receipt_notes);

        btnToday = (Button)findViewById(R.id.new_receipt_today_button);


        openDB();
        setToday();
        resetToday();
        

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editText_receipt.getText().toString())){
                    myDb.insertRow(editText_price.getText().toString(),
                            editText_currency.getText().toString(),
                            editText_date.getText().toString(),
                            editText_product.getText().toString(),
                            editText_vendor.getText().toString(),
                            editText_category.getText().toString(),
                            editText_receipt.getText().toString(),
                            editText_behalf.getText().toString(),
                            editText_tags.getText().toString(),
                            editText_notes.getText().toString());
                    Toast.makeText(getApplicationContext(), "Receipt added", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "FAILED to add Receipt!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    public String makeTimestamp(){
        today.setToNow();
        return today.format("%Y-%m-%d");
    }

    private void setToday(){
        editText_date.setText(makeTimestamp());
    }

    private void resetToday(){
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToday();
            }
        });
    }

}
