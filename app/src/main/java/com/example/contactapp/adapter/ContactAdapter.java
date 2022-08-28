package com.example.contactapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.contactapp.R;
import com.example.contactapp.model.Contact;

import java.util.List;

public class ContactAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;

    private List<Contact> contacts;

    public ContactAdapter(Activity activity, List<Contact> contacts) {
        this.activity = activity;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_row, null);
        }
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvNumber = view.findViewById(R.id.tvNumber);
        TextView tvAddress = view.findViewById(R.id.tvAddress);

        Contact contact = contacts.get(i);
        tvId.setText(contact.getId());
        tvName.setText(contact.getName());
        tvNumber.setText(contact.getNumber());
        tvAddress.setText(contact.getAddress());
        return view;
    }
}
