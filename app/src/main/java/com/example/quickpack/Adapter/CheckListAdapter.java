package com.example.quickpack.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Utils.FirebaseUtils;
import com.example.quickpack.R;
import com.example.quickpack.Models.UserItem;
import com.example.quickpack.Models.UserItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    private Activity activity;
    private UserItems userItems;
    private List<UserItem> itemsList;
    private String show;


    public CheckListAdapter() {
    }

    public CheckListAdapter(Activity activity, List<UserItem> itemsList, UserItems userItems, String show) {
        this.activity = activity;
        this.itemsList = itemsList;
        this.userItems = userItems;
        this.show = show;
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListViewHolder(LayoutInflater.from(activity).inflate(R.layout.check_list_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        UserItem userItem = itemsList.get(position);
        holder.checkBox.setText(userItem.getName());
        holder.checkBox.setChecked(userItem.isChecked());

        if(MyConstants.FALSE_STRING.equals(show)){
            holder.btnDelete.setVisibility(View.GONE);
        }
        
        else {
            if (itemsList.get(position).isChecked()) {
                holder.layout.setBackgroundColor(Color.parseColor("#F48FB1"));
            }
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FirebaseUtils.checkIfSignedIn(activity)) {
                    return;
                }


                userItems.setUserItemChecked(userItem.getName(), !userItem.isChecked());

                FirebaseUtils.updateUserItems(userItems, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()) {
                            userItems.setUserItemChecked(userItem.getName(),  !userItem.isChecked());
                            Toast.makeText(activity, "Could not change checked status, please try again",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("Delete ("+userItem.getName()+")")
                        .setMessage("Are you Sure?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(!FirebaseUtils.checkIfSignedIn(activity)) {
                                    return;
                                }

                                userItems.removeUserItem(userItem.getName());

                                FirebaseUtils.updateUserItems(userItems, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()) {
                                            userItems.addUserItem(userItem);
                                            Toast.makeText(activity, "Could not remove user item, please try again",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setIcon(R.drawable.baseline_delete_24).show();


            }
        });

    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
class CheckListViewHolder extends RecyclerView.ViewHolder{

    LinearLayout layout;
    CheckBox checkBox;
    Button btnDelete;

    public CheckListViewHolder(@NonNull View itemView) {
        super(itemView);
        layout=itemView.findViewById(R.id.linearlayout);
        checkBox=itemView.findViewById(R.id.checkbox);
        btnDelete=itemView.findViewById(R.id.buttonDelete);


    }
}
