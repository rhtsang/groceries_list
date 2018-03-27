package com.example.mygrocerieslist.mygrocerieslist.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mygrocerieslist.mygrocerieslist.Database.DatabaseHandler;
import com.example.mygrocerieslist.mygrocerieslist.Model.Grocery;
import com.example.mygrocerieslist.mygrocerieslist.R;

import java.util.List;

/**
 * Created by RaymondTsang on 12/26/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Grocery> groceryList;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    private DatabaseHandler db;

    public RecyclerViewAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView groceryName;
        public TextView groceryQuantity;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            groceryName = (TextView) itemView.findViewById(R.id.groceryNameId);
            groceryQuantity = (TextView) itemView.findViewById(R.id.groceryQuantityId);
            editButton = (Button) itemView.findViewById(R.id.editButtonId);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButtonId);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.editButtonId:
                    Grocery grocery = groceryList.get(getAdapterPosition());
                    editItem(grocery);
                    //Toast.makeText(context, "Editing", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.deleteButtonId:
                    grocery = groceryList.get(getAdapterPosition());
                    deleteItem(grocery.getId());
                    //Toast.makeText(context, "Deleting", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        public void editItem(final Grocery grocery) {
            alertDialogBuilder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.enter_item_popup, null);

            final EditText groceryItem = (EditText) view.findViewById(R.id.enterItemEditTextId);
            final EditText groceryQuantity2 = (EditText) view.findViewById(R.id.enterQuantityEditTextId);
            Button saveButton = (Button) view.findViewById(R.id.saveId);

            alertDialogBuilder.setView(view);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db = new DatabaseHandler(context);
                    grocery.setItem(groceryItem.getText().toString());
                    grocery.setQuantity(groceryQuantity2.getText().toString());
                    if (!groceryItem.getText().toString().isEmpty() && !groceryQuantity.getText().toString().isEmpty()) {
                        db.updateGrocery(grocery);
                        notifyItemChanged(getAdapterPosition(), grocery);
                    }
                    alertDialog.dismiss();
                }
            });
        }

        public void deleteItem(int id) {
            db = new DatabaseHandler(context);
            db.deleteGrocery(id);
            groceryList.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }



    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Grocery grocery = groceryList.get(position);

        holder.groceryName.setText(grocery.getItem());
        holder.groceryQuantity.setText(grocery.getQuantity());

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

}
