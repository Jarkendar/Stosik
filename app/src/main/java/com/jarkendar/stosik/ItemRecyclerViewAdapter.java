package com.jarkendar.stosik;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jarkendar.stosik.ItemFragment.OnListFragmentInteractionListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {

    private List<Task> tasks;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public ItemRecyclerViewAdapter(List<Task> items, OnListFragmentInteractionListener listener, Context context) {
        tasks = items;
        mListener = listener;
        this.context = context;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = tasks.get(position);
        holder.mTitle.setText(tasks.get(position).getTitle());
        holder.mPriority.setText(tasks.get(position).getPriority()+"");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        holder.mDate.setText(simpleDateFormat.format(tasks.get(position).getEndDate()));
        holder.mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click task = "+position, Toast.LENGTH_SHORT).show();
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitle;
        public final TextView mPriority;
        public final TextView mDate;
        public final Button mCancelButton;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.textView_title);
            mPriority = (TextView) view.findViewById(R.id.textView_priority);
            mDate = (TextView) view.findViewById(R.id.textView_end_date);
            mCancelButton = (Button) view.findViewById(R.id.button_end);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mItem=" + mItem +
                    '}';
        }
    }
}
