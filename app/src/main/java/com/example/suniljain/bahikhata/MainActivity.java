package com.example.suniljain.bahikhata;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    static int no_of_persons = 0;
    SharedPreferences sharedpreferences;
    EditText etAmount;
    String name;
    int amount;                         //TODO: currently, only integers are accepted....MODIFICATION NEEDED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: ArrayList is not  a permanent way to store key, value pairs. Extract everything from sharedpreferences somehow.
        /*final ArrayList<String> namesArrayList = new ArrayList<>();
        final ArrayList<Integer> amountsArrayList = new ArrayList<>();*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        listView = (ListView) findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext());
        listView.setAdapter(customAdapter);

        sharedpreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
        //sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add new person");
                View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_new_member_alert, (ViewGroup) findViewById(android.R.id.content), false);
                // Set up the input
                final EditText etName = (EditText) viewInflated.findViewById(R.id.etName);
                etAmount = (EditText) viewInflated.findViewById(R.id.etAmount);

                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = etName.getText().toString();
                        amount = Integer.parseInt(etAmount.getText().toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        //if that person already exists, then modify his/her record in the map instead of adding him/her again
                        //TODO: this has to be MODIFIED because option for value incrementation/decrementation should be there with the amount on list
                        //TODO: Auto-complete of name feature has to be added

                        if (sharedpreferences.contains(name)) {
//                            editor.putInt(name + amount, amount + sharedpreferences.getInt(name, 0));

                            int prevAmount = 0;
                            Map<String, ?> allEntries = sharedpreferences.getAll();
                            for (Map.Entry<String, ?> entry : allEntries.entrySet()){
                                if(entry.getValue().equals(name)){
                                    editor.remove(entry.getKey());
                                    prevAmount = sharedpreferences.getInt("amount" + entry.getKey(), 0);
                                    editor.remove("amount" + entry.getKey());
                                }
                            }
                            editor.apply();

                            no_of_persons++;

                            editor.putString(no_of_persons + "", name);
                            amount = prevAmount + amount;
                            editor.putInt("amount" + no_of_persons + "", amount);
                            editor.commit();
                            //getSharedPreferences("sharedprefs", Context.MODE_PRIVATE).edit().putInt(name, amount + Integer.parseInt(sharedpreferences.getString(name, ""))).apply();


/*                            int indexToBeModified = namesArrayList.indexOf(name);
                            amountsArrayList.remove(indexToBeModified);
                            amountsArrayList.add(indexToBeModified, amount);*/
                        }
                        else{
                            no_of_persons++;

                            editor.putString(no_of_persons + "", name);
                            editor.putInt("amount" + no_of_persons + "", amount);
                            editor.commit();
                            /*namesArrayList.add(index + 1, name);
                            amountsArrayList.add(index, amount);*/            //because index is static

//                            listView.setAdapter(customAdapter);
                        }

//                        EditTextPreference myPrefText = (EditTextPreference) super.findPreference(name);
//                        myPrefText.setText(amount + "");
                        Toast.makeText(MainActivity.this, name + " added!!" + "  Amount is " + amount, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();                       //work is done so dismiss
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();                        //cancel the operation
                    }
                });

                builder.show();

                //TODO: graphs showing the amount borrowed and amount lend
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
