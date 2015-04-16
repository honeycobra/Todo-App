package com.testapp.chiraagaval.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    private EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        Intent i = getIntent();
        if (i.hasExtra("itemVal")){
            String value = i.getStringExtra("itemVal");
            etEditItem.setText(value);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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

    public void submitItem(View view) {
        String fieldValue = etEditItem.getText().toString();
        Intent returnItem = new Intent(EditItemActivity.this, TodoActivity.class);
        returnItem.putExtra("editItem", fieldValue);
        returnItem.putExtra("code", 200);
        setResult(RESULT_OK, returnItem);
        finish();

    }
}
