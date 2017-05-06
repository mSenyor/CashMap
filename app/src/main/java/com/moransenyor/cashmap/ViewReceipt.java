package com.moransenyor.cashmap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ViewReceipt extends AppCompatActivity {

    DBAdapter myDb;

    String idStr = null;

    public static String EXTRA_RECEIPT_ID = "com.moransenyor.cashmap.ViewReceipt.EXTRA_TASK_ID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        idStr = intent.getStringExtra(MainActivity.EXTRA_RECEIPT_ID);

        openDB();
        // on version 1: id, price, currency, date, product, vendor, category, receipt, behalf, tags, notes

        TextView tvPrice = (TextView)findViewById(R.id.view_receipt_price);
        TextView tvCurrency = (TextView)findViewById(R.id.view_receipt_currency);
        TextView tvDate = (TextView)findViewById(R.id.view_receipt_date);
        TextView tvProduct = (TextView)findViewById(R.id.view_receipt_product);
        TextView tvVendor = (TextView)findViewById(R.id.view_receipt_vendor);
        TextView tvCategory = (TextView)findViewById(R.id.view_receipt_category);
        TextView tvReceipt = (TextView)findViewById(R.id.view_receipt_image);
        TextView tvBehalf = (TextView)findViewById(R.id.view_receipt_behalf);
        TextView tvTags = (TextView)findViewById(R.id.view_receipt_tags);
        TextView tvNotes = (TextView)findViewById(R.id.view_receipt_notes);

        long entry = Long.parseLong(idStr);
        Cursor cursor = myDb.getRow(entry);

        tvPrice.setText(cursor.getString(DBAdapter.COL_PRICE));
        tvCurrency.setText(cursor.getString(DBAdapter.COL_CURRENCY));
        tvDate.setText(cursor.getString(DBAdapter.COL_DATE));
        tvProduct.setText(cursor.getString(DBAdapter.COL_PRODUCT));
        tvVendor.setText(cursor.getString(DBAdapter.COL_VENDOR));
        tvCategory.setText(cursor.getString(DBAdapter.COL_CATEGORY));
        tvReceipt.setText(cursor.getString(DBAdapter.COL_RECEIPT));
        tvBehalf.setText(cursor.getString(DBAdapter.COL_BEHALF));
        tvTags.setText(cursor.getString(DBAdapter.COL_TAGS));
        tvNotes.setText(cursor.getString(DBAdapter.COL_NOTES));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewReceipt.this, EditReceipt.class);
                intent.putExtra(EXTRA_RECEIPT_ID, idStr);
                startActivity(intent);
            }
        });
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    @Override
    public void onResume(){
        super.onResume();
        TextView tvPrice = (TextView)findViewById(R.id.view_receipt_price);
        TextView tvCurrency = (TextView)findViewById(R.id.view_receipt_currency);
        TextView tvDate = (TextView)findViewById(R.id.view_receipt_date);
        TextView tvProduct = (TextView)findViewById(R.id.view_receipt_product);
        TextView tvVendor = (TextView)findViewById(R.id.view_receipt_vendor);
        TextView tvCategory = (TextView)findViewById(R.id.view_receipt_category);
        TextView tvReceipt = (TextView)findViewById(R.id.view_receipt_image);
        TextView tvBehalf = (TextView)findViewById(R.id.view_receipt_behalf);
        TextView tvTags = (TextView)findViewById(R.id.view_receipt_tags);
        TextView tvNotes = (TextView)findViewById(R.id.view_receipt_notes);

        long entry = Long.parseLong(idStr);
        Cursor cursor = myDb.getRow(entry);

        tvPrice.setText(cursor.getString(DBAdapter.COL_PRICE));
        tvCurrency.setText(cursor.getString(DBAdapter.COL_CURRENCY));
        tvDate.setText(cursor.getString(DBAdapter.COL_DATE));
        tvProduct.setText(cursor.getString(DBAdapter.COL_PRODUCT));
        tvVendor.setText(cursor.getString(DBAdapter.COL_VENDOR));
        tvCategory.setText(cursor.getString(DBAdapter.COL_CATEGORY));
        tvReceipt.setText(cursor.getString(DBAdapter.COL_RECEIPT));
        tvBehalf.setText(cursor.getString(DBAdapter.COL_BEHALF));
        tvTags.setText(cursor.getString(DBAdapter.COL_TAGS));
        tvNotes.setText(cursor.getString(DBAdapter.COL_NOTES));

    }

}
