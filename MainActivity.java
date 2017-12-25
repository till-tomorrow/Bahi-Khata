package com.example.palakjain.bahikhata;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String name;
    int amount;                         //TODO: currently, only integers are accepted....MODIFICATION NEEDED
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        sharedpreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add new person");
                View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_new_member_alert, (ViewGroup) findViewById(android.R.id.content), false);
                // Set up the input
                final EditText etName = (EditText) viewInflated.findViewById(R.id.etName);
                final EditText etAmount = (EditText) viewInflated.findViewById(R.id.etAmount);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();                       //work is done so dismiss
                        name = etName.getText().toString();
                        amount = Integer.parseInt(etAmount.getText().toString());
                        //shared preferences commit
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        //if that person already exists, then modify his/her record in the map instead of adding im/her again
                        //TODO: this has to be MODIFIED because option for value incrementation/decrementation should be there with the amount on list
                        //TODO: Auto-complete of name feature has to be added
                        //TODO: Fix the issue ---- app crashes if name alrady exists in the shared preferences

                        if (sharedpreferences.contains(name)) {
                            amount += Integer.parseInt(sharedpreferences.getString(name + "Amount", ""));
                        }
                        editor.putString(name, name);
                        editor.putInt(name + "Amount", amount);
                        editor.commit();

                        Toast.makeText(MainActivity.this, "Person added!" + "Amount is " + amount, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();                        //cancel the operation
                    }
                });

                builder.show();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
