package com.moransenyor.cashmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Time today = new Time(Time.getCurrentTimezone());
    DBAdapter myDb;
    ListView lvReceipts;

    public static String EXTRA_RECEIPT_ID = "com.moransenyor.cashmap.MainActivity.EXTRA_TASK_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvReceipts = (ListView)findViewById(R.id.list_view_receipts);
        openDB();
        populateListView();
        listItemLongClick();
        listItemClick();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewReceipt();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    public void populateListView(){
        Cursor cursor = myDb.getAllRows();
        String[] fromFieldNames = new String[] {
                DBAdapter.KEY_PRICE,
                DBAdapter.KEY_CURRENCY,
                DBAdapter.KEY_DATE,
                DBAdapter.KEY_PRODUCT,
                DBAdapter.KEY_VENDOR};

        int[] toViewIDs = new int[] {R.id.item_price, R.id.item_currency, R.id.item_date, R.id.item_product, R.id.item_vendor};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.receipts_list_item, cursor, fromFieldNames, toViewIDs,0);
        lvReceipts.setAdapter(myCursorAdapter);
    }

    public void addNewReceipt(){
        Intent intent = new Intent(this, AddNewReceipt.class);
        startActivity(intent);

    }

    @Override
    public void onResume(){
        super.onResume();
        populateListView();
    }

    private void listItemClick(){
        lvReceipts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ViewReceipt.class);
                String idStr = String.valueOf(id);
                intent.putExtra(EXTRA_RECEIPT_ID, idStr);
                startActivity(intent);
                //updateTask(id);
                //populateListView();
            }
        });

    }

    private void listItemLongClick(){
        lvReceipts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog diaBox = ConfirmDelete(id);
                diaBox.show();
                populateListView();
                return true;
            }
        });
    }



    private AlertDialog ConfirmDelete(final long id)
    {
        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to permanently delete this receipt?")
                //.setIcon(R.drawable.delete)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        if(myDb.deleteRow(id)){
                            Toast.makeText(getApplicationContext(), "Task deleted", Toast.LENGTH_LONG).show();
                            populateListView();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "FAILED to delete task!", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                })



                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myDeleteDialogBox;

    }
}
