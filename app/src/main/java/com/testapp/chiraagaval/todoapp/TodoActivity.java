package com.testapp.chiraagaval.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class TodoActivity extends ActionBarActivity {

    private EditText etWords;
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdaptor;
    private final int REQUEST_CODE = 20;
    private int return_pos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        readItems();
        etWords = (EditText) findViewById(R.id.etWords);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items =  new ArrayList<String>();
        itemsAdaptor =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdaptor);
        setupListViewListener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(View view) {
        String fieldValue = etWords.getText().toString();
        if (!TextUtils.isEmpty(fieldValue)) {
            itemsAdaptor.add(fieldValue);
            etWords.setText("");
            writeItems();
        }

    }

    private void setupListViewListener(){
        lvItems.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditView(items.get(position));
                return_pos = position;
                writeItems();
                itemsAdaptor.notifyDataSetChanged();
                return;
            }
        });
        lvItems.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdaptor.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });

    }

    public void launchEditView(String ItemVal){

        Intent editIntent =  new Intent(TodoActivity.this, EditItemActivity.class);
        editIntent.putExtra("itemVal", ItemVal);
        startActivityForResult(editIntent, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String item = data.getExtras().getString("editItem");
            int code = data.getExtras().getInt("code", 0);
            if (!TextUtils.isEmpty(item)) {
                items.set(return_pos, item);
                writeItems();
                itemsAdaptor.notifyDataSetChanged();
            }

        }
    }
    private void readItems(){
        File filesDir = getFilesDir();
        File todoHandle = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoHandle));
        } catch (IOException e){
            items = new ArrayList<>();

        }

    }

    private void writeItems(){
        File fileDir =  getFilesDir();
        File todoHandle = new File(fileDir, "todo.txt");
        try{
            FileUtils.writeLines(todoHandle, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
