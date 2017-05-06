package com.moransenyor.cashmap;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

public class EditReceipt extends AppCompatActivity {

    DBAdapter myDb;

    String idStr = null;

    Button btnToday;

    Time today = new Time(Time.getCurrentTimezone());






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idStr = intent.getStringExtra(ViewReceipt.EXTRA_RECEIPT_ID);

        openDB();
        // on version 1: id, price, currency, date, product, vendor, category, receipt, behalf, tags, notes


        final EditText etPrice = (EditText) findViewById(R.id.edit_receipt_price);
        final EditText etCurrency = (EditText) findViewById(R.id.edit_receipt_currency);
        final EditText etDate = (EditText) findViewById(R.id.edit_receipt_date);
        final EditText etProduct = (EditText) findViewById(R.id.edit_receipt_product);
        final EditText etVendor = (EditText) findViewById(R.id.edit_receipt_vendor);
        final EditText etCategory = (EditText) findViewById(R.id.edit_receipt_category);
        final EditText etReceipt = (EditText) findViewById(R.id.edit_receipt_image);
        final EditText etBehalf = (EditText) findViewById(R.id.edit_receipt_behalf);
        final EditText etTags = (EditText) findViewById(R.id.edit_receipt_tags);
        final EditText etNotes = (EditText) findViewById(R.id.edit_receipt_notes);

        final long entry = Long.parseLong(idStr);
        Cursor cursor = myDb.getRow(entry);

        etPrice.setText(cursor.getString(DBAdapter.COL_PRICE));
        etCurrency.setText(cursor.getString(DBAdapter.COL_CURRENCY));
        etDate.setText(cursor.getString(DBAdapter.COL_DATE));
        etProduct.setText(cursor.getString(DBAdapter.COL_PRODUCT));
        etVendor.setText(cursor.getString(DBAdapter.COL_VENDOR));
        etCategory.setText(cursor.getString(DBAdapter.COL_CATEGORY));
        etReceipt.setText(cursor.getString(DBAdapter.COL_RECEIPT));
        etBehalf.setText(cursor.getString(DBAdapter.COL_BEHALF));
        etTags.setText(cursor.getString(DBAdapter.COL_TAGS));
        etNotes.setText(cursor.getString(DBAdapter.COL_NOTES));

        btnToday = (Button)findViewById(R.id.edit_receipt_today_button);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDate.setText(makeTimestamp());
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(etReceipt.getText().toString())){
                    myDb.updateRow(entry,
                            etPrice.getText().toString(),
                            etCurrency.getText().toString(),
                            etDate.getText().toString(),
                            etProduct.getText().toString(),
                            etVendor.getText().toString(),
                            etCategory.getText().toString(),
                            etReceipt.getText().toString(),
                            etBehalf.getText().toString(),
                            etTags.getText().toString(),
                            etNotes.getText().toString());
                    Toast.makeText(getApplicationContext(), "Receipt updated", Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "FAILED to update Receipt!", Toast.LENGTH_LONG).show();
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


}
