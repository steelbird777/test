package com.example.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise5 extends AppCompatActivity {
    private TextView registernum;
    private EditText nameText;
    private EditText registerText;
    private Spinner spinnerDept;
    private Button insertBtn;
    private Button deleteBtn;
    private Button viewBtn;
    private Button viewAllBtn;
    private Button updateBtn;
    private String selectedItem;
    private String idView;
    private DatabaseHelper db;
    private int isInserted = 0;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise5);

        nameText = findViewById(R.id.nameText);
        registerText = findViewById(R.id.registernoText);
        spinnerDept = findViewById(R.id.spinnerDept);
        insertBtn = findViewById(R.id.insertBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        updateBtn = findViewById(R.id.updateBtn);
        viewBtn = findViewById(R.id.viewBtn);
        viewAllBtn = findViewById(R.id.viewAllBtn);
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Exercise5.this, Exercise6.class);
                    startActivity(intent);
            }
        });
                registernum = findViewById(R.id.registerno);

        db = new DatabaseHelper(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(adapter);

        spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameText.getText().toString();
                String registerno = registerText.getText().toString();
                if(!name.isEmpty() && !registerno.isEmpty() && !selectedItem.isEmpty()) {
                    isInserted = db.addDetails(name, registerno, selectedItem);
                    if (isInserted == 1) {
                        showMessage("Success", "Inserted Successfully!");
                    } else {
                        showMessage("Error", "Cannot Insert Data!");
                    }
                }else{
                    showMessage("Error", "Please Fill All the Fields!");
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog();
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewDialog();
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showViewAllDialog();
            }
        });
    }

    private void showUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_dialog, null);
        final EditText idText = dialogView.findViewById(R.id.edit_text_id);
        final EditText nameText = dialogView.findViewById(R.id.edit_text_name);
        final EditText registerText = dialogView.findViewById(R.id.edit_text_regis);
        final EditText deptText = dialogView.findViewById(R.id.edit_text_dept);
        builder.setView(dialogView)
                .setTitle("Update Details")
                .setMessage("Enter the following Details." +
                        "For unchanged details leave it blank.")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idStr = idText.getText().toString();
                        String name = nameText.getText().toString();
                        String registerno = registerText.getText().toString();
                        String dept = deptText.getText().toString();
                        if (!idStr.isEmpty()) {
                            int id = Integer.parseInt(idStr);
                            db.updateDetails(id, name, registerno, dept);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showViewAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.viewall_dialog, null);
        LinearLayout container = dialogView.findViewById(R.id.container);

        Cursor result = db.viewAllDetails();
        if (result.moveToFirst()) {
            do {
                View itemView = inflater.inflate(R.layout.item_view, container, false);

                TextView idText = itemView.findViewById(R.id.idView);
                TextView nameText = itemView.findViewById(R.id.nameView);
                TextView registerText = itemView.findViewById(R.id.regisView);
                TextView deptText = itemView.findViewById(R.id.deptView);

                idText.setText("ID: " + result.getString(0));
                nameText.setText("Name: " + result.getString(1));
                registerText.setText("Register Number: " + result.getString(2));
                deptText.setText("Department: " + result.getString(3));

                container.addView(itemView);
            } while (result.moveToNext());
        }
        result.close();

        builder.setView(dialogView)
                .setTitle("Details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void showDetailsDialog(String idView){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.view_dialog, null);
        final TextView idText = dialogView.findViewById(R.id.idView);
        final TextView nameText = dialogView.findViewById(R.id.nameView);
        final TextView registerText = dialogView.findViewById(R.id.regisView);
        final TextView deptText = dialogView.findViewById(R.id.deptView);

        Cursor result = db.viewDetails(idView);
        if(result.getCount() == 0){
            showMessage("Error", "No data found");
            return;
        }

        while(result.moveToNext()){
            idText.setText("ID: " + result.getString(0));
            nameText.setText("Name: " + result.getString(1));
            registerText.setText("Reegister Number: " + result.getString(2));
            deptText.setText("Department: " + result.getString(3));
        }

        builder.setView(dialogView)
                .setTitle("Details")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            int id = Integer.parseInt(idView);
                            db.viewDetails(idView);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void showViewDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_dialog,null);
        final EditText editText = dialogView.findViewById(R.id.edit_text_id);
        builder.setView(dialogView)
                .setTitle("View Details")
                .setMessage("Enter the ID to view:")
                .setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idView = editText.getText().toString();
                        if(!idView.isEmpty()) {
                            showDetailsDialog(idView);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.delete_dialog, null);
        final EditText editText = dialogView.findViewById(R.id.edit_text_id);
        builder.setView(dialogView)
                .setTitle("Delete Details")
                .setMessage("Enter the ID to delete:")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String idStr = editText.getText().toString();
                        if (!idStr.isEmpty()) {
                            int id = Integer.parseInt(idStr);
                            db.deleteDetails(id);
                        } else {
                            Toast.makeText(Exercise5.this, "Please enter a valid ID", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
