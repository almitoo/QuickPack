package com.example.quickpack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.quickpack.Adapter.CheckListAdapter;
import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Utils.FirebaseUtils;
import com.example.quickpack.Models.UserItem;
import com.example.quickpack.Models.UserItems;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheckList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckListAdapter checkListAdapter;
    private UserItems userItems;
    private String header,show;
    private EditText txtAdd;
    private Button btnAdd;
    private LinearLayout linearLayout;
//1

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        Intent intent=getIntent();
        header=intent.getStringExtra(MyConstants.HEADER_SMALL);
        if((!header.equals(MyConstants.MY_SELECTIONS_CAMEL_CASE))&&(!header.equals(MyConstants.MY_LIST_CAMEL_CASE))) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return super.onCreatePanelMenu(featureId, menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btnResetDefault || item.getItemId() == R.id.btnDeleteDefault) {
            if(userItems == null || !FirebaseUtils.checkIfSignedIn(this)) {
                return true; // consumed
            }
        }

        if(item.getItemId() == R.id.btnResetDefault) {
            resetUserItemsToDefault();
            return true; // consumed  });


        } else if(item.getItemId() == R.id.btnDeleteDefault) {
            clearUserItems();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        header=intent.getStringExtra(MyConstants.HEADER_SMALL);
        show=intent.getStringExtra(MyConstants.SHOW_SMALL);

        getSupportActionBar().setTitle(header);

        txtAdd=findViewById(R.id.txtAdd);
        btnAdd=findViewById(R.id.btnAdd);
        recyclerView=findViewById(R.id.recyclerView);
        linearLayout=findViewById(R.id.linearlayout);

        if(!FirebaseUtils.checkIfSignedIn(this)) {
            return;
        }

        loadUserItems();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName= txtAdd.getText().toString();
                if(itemName!=null && !itemName.isEmpty()){
                    addNewItem(itemName);
                    Toast.makeText(CheckList.this,"Item added to the list",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(CheckList.this,"Empty cant added",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtils.checkIfSignedIn(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void resetUserItemsToDefault() {
        new AlertDialog.Builder(this)
                .setTitle("Delete all the items added by you in "+header+" category")
                .setMessage("Are you Sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        userItems.resetCategoryToDefault(header);

                        FirebaseUtils.updateUserItems(userItems, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(CheckList.this,
                                            "Could not reset category to defaults, please try again",
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

    private void clearUserItems() {
        new AlertDialog.Builder(this)
                .setTitle("Delete all the items in "+header+" category")
                .setMessage("Are you Sure?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        userItems.clearCategoryUserItems(header);

                        FirebaseUtils.updateUserItems(userItems, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(CheckList.this,
                                            "Could not clear category, please try again",
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


    private void loadUserItems() {
        FirebaseDatabase.getInstance().getReference()
                .child(FirebaseUtils.USER_ITEMS)
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {//every change in the data--> updateLayout();
                        userItems = snapshot.getValue(UserItems.class);
                        updateLayout();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private  void addNewItem(String itemName){
        UserItem userItem = new UserItem(itemName, header, false, false);
        userItems.addUserItem(userItem);
        FirebaseUtils.updateUserItems(userItems, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()) {
                    userItems.removeUserItem(userItem.getName());
                    Toast.makeText(CheckList.this, "Could not add user item, please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        recyclerView.scrollToPosition(checkListAdapter.getItemCount()-1);
        txtAdd.setText("");


    }
    private void updateLayout(){
        List<UserItem> userItemList = new ArrayList<>();

        for(String userItemName: userItems.getItemNameToUserItemMap().keySet()) {
            UserItem userItem = userItems.getItemNameToUserItemMap().get(userItemName);

            if((MyConstants.FALSE_STRING.equals(show) && userItem.isChecked())
                    || (!MyConstants.FALSE_STRING.equals(show) &&  userItem.getCategory().equals(header))) {
                userItemList.add(userItem);
            }
        }
        if(MyConstants.FALSE_STRING.equals(show)) {
            linearLayout.setVisibility(View.GONE);
        }



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        checkListAdapter=new CheckListAdapter(CheckList.this, userItemList, userItems,show);
        recyclerView.setAdapter(checkListAdapter);


    }
}