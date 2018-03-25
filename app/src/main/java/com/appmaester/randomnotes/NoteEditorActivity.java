package com.appmaester.randomnotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;
import java.util.StringTokenizer;

public class NoteEditorActivity extends AppCompatActivity {

    int notesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editNotes = (EditText) findViewById(R.id.editNotes);

        Intent intent = getIntent();
        notesID = intent.getIntExtra("notesID", -1);

        if (notesID != -1){

            editNotes.setText(MainActivity.notesRandom.get(notesID));

        } else {

            MainActivity.notesRandom.add("");
            notesID = MainActivity.notesRandom.size() - 1;
            MainActivity.arrayAdapterNotes.notifyDataSetChanged();

        }

        editNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.notesRandom.set(notesID, String.valueOf(charSequence));
                MainActivity.arrayAdapterNotes.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getSharedPreferences("com.appmaester.randomnotes", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(MainActivity.notesRandom);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
