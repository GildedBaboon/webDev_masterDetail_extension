package com.example.webdev_masterdetail_extension;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.webdev_masterdetail_extension.tasks.TaskContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Editing the task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                showAddItemDialog(ItemDetailActivity.this);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
//            Log.d("HELLO", "FIRST PLACE " + getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        final EditText taskEditText2 = new EditText(c);

        LinearLayout lila1= new LinearLayout(this);
        lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
        lila1.addView(taskEditText);
        lila1.addView(taskEditText2);

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Edit Task")
                .setMessage("What do you want to do?")
                .setView(lila1)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        String details = String.valueOf(taskEditText2.getText());
                        Log.d("HELLO", task +" "+details);
                        SharedPreferences sp = getSharedPreferences("Tasks", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        final Gson gson = new Gson();
                        TaskContent mTasks = gson.fromJson(sp.getString("taskContent", null), TaskContent.class);
//                        Log.d("HELLO",  sp.getString("taskContent", null));
                        Log.d("HELLO", getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
                        mTasks.editTask(getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID), task, details);
                        editor.clear();
                        editor.putString("taskContent", gson.toJson(mTasks));
                        editor.commit();
                        finish();
                        startActivity(getIntent());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
