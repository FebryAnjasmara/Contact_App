package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.contactapp.helper.DatabaseHelper;
import com.example.contactapp.model.Contact;

public class AddEdit extends AppCompatActivity {
    private EditText txtId, txtName, txtNumber, txtAddress;
    private Button btSimpan, btCancel;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        databaseHelper = new DatabaseHelper(this);
        txtId = findViewById(R.id.txtId);
        txtName = findViewById(R.id.txtNama);
        txtNumber = findViewById(R.id.txtNumber);
        txtAddress = findViewById(R.id.txtAddress);

        btSimpan = findViewById(R.id.btSimpan);
        btCancel = findViewById(R.id.btCancel);

        if (getIntent().getExtras() != null) {
            setTitle("Edit Data");
            Contact contact = (Contact) getIntent().getSerializableExtra(MainActivity.TAG_CONTACT);
            txtId.setText(contact.getId());
            txtName.setText(contact.getName());
            txtNumber.setText(contact.getNumber());
            txtAddress.setText(contact.getAddress());
        } else setTitle("Add Data");

        btSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtId.getText().toString();
                String name = txtName.getText().toString();
                String number = txtNumber.getText().toString();
                String address = txtAddress.getText().toString();
                if (id.equals("") && name.equals("") && number.equals("") && address.equals("")){
                    Toast.makeText(AddEdit.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    if (getTitle().equals("Add Data")) {
                        long insert = databaseHelper.insert(new Contact(id, name, number, address));
                        if (insert > 0 ){
                            Toast.makeText(AddEdit.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(databaseHelper.update(new Contact(id, name, number, address)))
                            Toast.makeText(AddEdit.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                    }
                    clear();
                    finish();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                finish();
            }
        });
    }

    private void clear(){
        txtId.setText("");
        txtName.setText("");
        txtNumber.setText("");
        txtAddress.setText("");
    }
}