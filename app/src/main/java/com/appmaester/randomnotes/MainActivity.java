package com.appmaester.randomnotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> notesRandom = new ArrayList<>();
    static ArrayAdapter arrayAdapterNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getSharedPreferences("com.appmaester.randomnotes", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);

        if (set == null) {

            notesRandom.add("Example Notes");

        } else {

            notesRandom = new ArrayList(set);

        }

        arrayAdapterNotes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notesRandom);

        listView.setAdapter(arrayAdapterNotes);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                intent.putExtra("notesID", i);
                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int selectedNoteID = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete this Note")
                        .setMessage("Are you Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                notesRandom.remove(selectedNoteID);
                                arrayAdapterNotes.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getSharedPreferences("com.appmaester.randomnotes", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet(MainActivity.notesRandom);

                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_notes_menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.add_notes) {

            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);

            return true;

        } else if (item.getItemId() == R.id.help){

            Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
            startActivity(intent);

            return true;

        }

        return false;

    }
}
