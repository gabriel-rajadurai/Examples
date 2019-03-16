package com.example.administrator.test.contacts_sql;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.test.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 8/23/2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    LayoutInflater inflater;
    ArrayList<ContactsModel> contactsModels = new ArrayList<>();
    EditText search;
    Context context;


    private static String TAG = "ContactsAdapter";

    public ContactsAdapter(Context context, ArrayList<ContactsModel> contactsModels, EditText search) {
        this.contactsModels = contactsModels;
        this.context = context;
        this.search = search;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ContactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cRowView = inflater.inflate(R.layout.contacts_rowlayout, parent, false);
        ContactsViewHolder holder = new ContactsViewHolder(cRowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ContactsViewHolder holder, int position) {

        ContactsModel contacts = contactsModels.get(position);

        if (contacts.profilePic != null) {
            holder.img_cProfilePic.setImageBitmap(contacts.profilePic);
        }
        else
        {
            holder.img_cProfilePic.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_profile_24dp));
        }
        holder.tv_cName.setText(contacts.Name);
        holder.tv_cPhone.setText(contacts.Phone);
        holder.tv_cEmail.setText(contacts.Email);

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + contactsModels.size());
        return contactsModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "getItemId: " + position);
        return position;
    }


    /**
     * Updating the Contact list based on the search String.
     * @param updatedList Updated contact list.
     */
    public void updateList(ArrayList<ContactsModel> updatedList) {
        contactsModels = updatedList;
        notifyDataSetChanged();
    }


    class ContactsViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cProfilePic;
        TextView tv_cName, tv_cPhone, tv_cEmail;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            img_cProfilePic = itemView.findViewById(R.id.img_cProfilePic);
            tv_cName = itemView.findViewById(R.id.tv_cName);
            tv_cPhone = itemView.findViewById(R.id.tv_cPhone);
            tv_cEmail = itemView.findViewById(R.id.tv_cEmail);
        }
    }

}


