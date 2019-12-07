package com.g.firestoretest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WFITBAdapter extends RecyclerView.Adapter<WFITBAdapter.WFITBHolder> {
    ArrayList<Test> tests = new ArrayList<>();

    public WFITBAdapter(ArrayList<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public WFITBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_number,parent,false);
        return new WFITBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WFITBHolder holder, int position) {
        Test currenttest = tests.get(position);
        holder.textViewTestName.setText(currenttest.getTestName());
        final String testno = currenttest.getTestName().trim();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WFITBFragmentDirections.ActionWFITBFragmentToWFITBTestFragment action = WFITBFragmentDirections.actionWFITBFragmentToWFITBTestFragment(testno);
                action.setTestno(testno);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class WFITBHolder extends RecyclerView.ViewHolder {
        TextView textViewTestName;
        LinearLayout linearLayout;
        public WFITBHolder(@NonNull View itemView) {
            super(itemView);
            textViewTestName = itemView.findViewById(R.id.text_view_test_name);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
