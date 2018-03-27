package com.example.mygrocerieslist.mygrocerieslist;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mygrocerieslist.mygrocerieslist.Adapter.RecyclerViewAdapter;
import com.example.mygrocerieslist.mygrocerieslist.Database.DatabaseHandler;
import com.example.mygrocerieslist.mygrocerieslist.Model.Grocery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private EditText groceryItem;
    private EditText groceryQuantity;
    private Button saveButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private List<Grocery> groceryList = new ArrayList<>();
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = db.getAllGroceries();

        recyclerViewAdapter = new RecyclerViewAdapter(this, groceryList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopup();
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

    private void createPopup() {
        alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.enter_item_popup, null);

        groceryItem = (EditText) view.findViewById(R.id.enterItemEditTextId);
        groceryQuantity = (EditText) view.findViewById(R.id.enterQuantityEditTextId);
        saveButton = (Button) view.findViewById(R.id.saveId);

        alertDialogBuilder.setView(view);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!groceryItem.getText().toString().isEmpty() && !groceryQuantity.getText().toString().isEmpty()) {
                    saveGroceryToDB(v);
                }
            }
        });

    }

    private void saveGroceryToDB(View v) {

        Grocery grocery = new Grocery();
        grocery.setItem(groceryItem.getText().toString());
        grocery.setQuantity(groceryQuantity.getText().toString());
        db.addGrocery(grocery);

        groceryList = db.getAllGroceries();
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, groceryList);
        recyclerView.setAdapter(recyclerViewAdapter);
        //recyclerViewAdapter.notifyDataSetChanged();

        Snackbar.make(v, "Item saved!", Snackbar.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        }, 500);
    }

}
