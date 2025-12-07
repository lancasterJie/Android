package com.example.data_homework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class RecordListActivity extends AppCompatActivity {
    private ListView listView;
    private TextView textEmpty;
    private DatabaseHelper databaseHelper;
    private NoteAdapter noteAdapter;
    private List<DatabaseHelper.Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_records);
        }

        // Initialize views
        listView = findViewById(R.id.list_notes);
        textEmpty = findViewById(R.id.text_empty);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Set up list item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper.Note note = noteList.get(position);
                openNoteDetail(note);
            }
        });

        // Set up list item long click listener for delete
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper.Note note = noteList.get(position);
                showDeleteDialog(note);
                return true;
            }
        });

        // Load notes
        loadNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload notes when returning to this activity
        loadNotes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadNotes() {
        noteList = databaseHelper.getAllNotes();
        
        if (noteList.isEmpty()) {
            listView.setVisibility(View.GONE);
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.GONE);
            
            noteAdapter = new NoteAdapter(this, noteList);
            listView.setAdapter(noteAdapter);
        }
    }

    private void openNoteDetail(DatabaseHelper.Note note) {
        Intent intent = new Intent(RecordListActivity.this, RecordDetailActivity.class);
        intent.putExtra("note_id", note.getId());
        intent.putExtra("note_title", note.getTitle());
        intent.putExtra("note_content", note.getContent());
        intent.putExtra("note_time", note.getTimestamp());
        startActivity(intent);
    }

    private void showDeleteDialog(final DatabaseHelper.Note note) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote(note);
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void deleteNote(DatabaseHelper.Note note) {
        databaseHelper.deleteNote(note.getId());
        Toast.makeText(this, R.string.note_deleted, Toast.LENGTH_SHORT).show();
        loadNotes(); // Reload the list
    }
}
