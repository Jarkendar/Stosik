package com.jarkendar.stosik;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Observer;

public class AddTaskDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private EditText titleEdit, priorityEdit, endDateEdit;
    private Button addButton, cancelButton;
    private Observer observer;

    public AddTaskDialog(Context context, Observer observer) {
        super(context);
        this.context = context;
        this.observer = observer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_task_layout_dialog);
        titleEdit = (EditText) findViewById(R.id.editText_title);
        priorityEdit = (EditText) findViewById(R.id.editText_priority);
        endDateEdit = (EditText) findViewById(R.id.editText_end_date);
        addButton = (Button) findViewById(R.id.button_add);
        cancelButton = (Button) findViewById(R.id.button_cancel);
        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add:{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                DatabaseLackey databaseLackey = new DatabaseLackey(context);
                try {
                    databaseLackey.insertTask(databaseLackey.getWritableDatabase(), new Task(titleEdit.getText().toString(), Integer.parseInt(priorityEdit.getText().toString()), simpleDateFormat.parse(endDateEdit.getText().toString().trim())));
                    observer.update(null, null);
                } catch (ParseException | NumberFormatException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        dismiss();
    }
}
