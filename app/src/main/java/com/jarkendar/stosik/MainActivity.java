package com.jarkendar.stosik;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, Observer {

    private ItemFragment itemFragment;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_activity);
        if (savedInstanceState == null) {
            itemFragment = ItemFragment.newInstance(1);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, itemFragment)
                    .commitNow();
        }
        this.context = getApplication();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddTaskDialog(MainActivity.this, MainActivity.this).show();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Task item) {
        itemFragment.refreshAdapter();
    }

    @Override
    public void update(Observable observable, Object o) {
        onListFragmentInteraction(null);
    }
}
