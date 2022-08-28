package com.example.contactapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactapp.adapter.ContactAdapter;
import com.example.contactapp.helper.DatabaseHelper;
import com.example.contactapp.model.Contact;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.contactapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ContactAdapter adapter;
    private DatabaseHelper databaseHelper;
    private List<Contact> contacts;
    private AlertDialog.Builder builder;

    private ActivityMainBinding binding;

    public static final String TAG_CONTACT = "CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        contacts = new ArrayList<>();
        adapter = new ContactAdapter(MainActivity.this, contacts);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String id = contacts.get(i).getId();
            String name = contacts.get(i).getName();
            String number = contacts.get(i).getNumber();
            String address = contacts.get(i).getAddress();

            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(true);
            builder.setItems(new String[]{"Edit", "Delete"}, (dialogInterface, i1) -> {
                if (i1 == 0){
                    Intent intent = new Intent(MainActivity.this, AddEdit.class);
                    intent.putExtra(TAG_CONTACT, new Contact(id, name, number, address));
                    startActivity(intent);
                } else {

//                    delete data
                    if(databaseHelper.delete(id)){
                        Toast.makeText(MainActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        getAllData();
                    }
                }
            }).show();
        });

        getAllData();

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

    @Override
    protected void onResume() {
        super.onResume();
        getAllData();
    }

    private void getAllData(){
        List<Contact> all = databaseHelper.getAll();
        contacts.clear();
        contacts.addAll(all);
        adapter.notifyDataSetChanged();
    }
}