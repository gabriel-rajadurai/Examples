package com.example.administrator.test.contacts_sql.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.administrator.test.R;
import com.example.administrator.test.contacts_sql.ContactsAdapter;
import com.example.administrator.test.contacts_sql.ContactsModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private View cView;
    private RecyclerView contactsView;
    private ArrayList<ContactsModel> contactsModelList;

    private EditText et_Search;

    ContactsAdapter contactsAdapter;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Method to create a new instance of ContactsFragment.
     *
     * @param contactsModelList Contact list.
     * @return Returns new instance of ContactsFragment with the Contacts list is populated.
     */
    public static ContactsFragment newInstance(ArrayList<ContactsModel> contactsModelList) {
        ContactsFragment fragment = new ContactsFragment();
        fragment.contactsModelList = new ArrayList<>();


        // Cloning and populating the Contact list to avoid referencing.
        for (ContactsModel model : contactsModelList) {
            try {
                ContactsModel contactsModel = (ContactsModel) model.clone();
                fragment.contactsModelList.add(contactsModel);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cView = inflater.inflate(R.layout.fragment_contacts, container, false);
        et_Search = cView.findViewById(R.id.et_contactsSearch);
        //Initalize recycler view
        contactsView = cView.findViewById(R.id.contacts_rView);
        contactsAdapter = new ContactsAdapter(getActivity(), contactsModelList, et_Search);
        contactsView.setAdapter(contactsAdapter);
        contactsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactsView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        et_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        return cView;
    }

    void filter(String text) {
        ArrayList<ContactsModel> temp = new ArrayList<>();
        /*if(text.equals(""))
        {
            updateList(contactsComplete);
            return;
        }*/
        for (ContactsModel contact : contactsModelList) {
            if (contact.Name.toLowerCase().contains(text.toLowerCase())) {
                temp.add(contact);
            }
        }
        contactsAdapter.updateList(temp);

    }

}
