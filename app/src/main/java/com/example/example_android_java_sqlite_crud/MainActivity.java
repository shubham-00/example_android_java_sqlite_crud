package com.example.example_android_java_sqlite_crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    Button addButton, viewButton, updateButton, deleteButton;
    EditText name, email, show, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        name = findViewById(R.id.nameInput);
        email = findViewById(R.id.emailInput);
        show = findViewById(R.id.showInput);
        id = findViewById(R.id.idInput);

        addData();
        viewData();
        updateData();
        deleteData();
    }

    public void addData() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                String emailText = email.getText().toString();
                String showText = show.getText().toString();

                boolean insertData = db.addData(nameText, emailText, showText);

                if (insertData) {
                    Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewData() {
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor data = db.viewData();
                if (data.getCount() == 0) {
                    display("Error!", "No data to display!");
                }

                StringBuffer buffer = new StringBuffer();
                while(data.moveToNext()) {
                    buffer.append("ID " + data.getString(0) + "\n");
                    buffer.append("Name " + data.getString(1) + "\n");
                    buffer.append("Email " + data.getString(2) + "\n");
                    buffer.append("Show " + data.getString(3) + "\n");
                }
                display("Users", buffer.toString());
            }
        });
    }

    public void updateData() {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idLength = id.getText().toString().length();
                if (idLength > 0) {
                    boolean operation = db.updateData(id.getText().toString(), name.getText().toString(), email.getText().toString(), show.getText().toString());
                    if (operation) {
                        Toast.makeText(MainActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Unable to update!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void deleteData() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idLength = id.getText().toString().length();
                if (idLength > 0) {
                    int operation = db.deleteData(id.getText().toString());
                    if (operation > 0) {
                        Toast.makeText(MainActivity.this, "Delete successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Unable to delete!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Enter ID!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void display (String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}