package com.g.firestoretest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FITBAdapter extends RecyclerView.Adapter<FITBAdapter.FITBHolder> {

    ArrayList<Test> tests = new ArrayList<>();

    public FITBAdapter(ArrayList<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public FITBAdapter.FITBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_number,parent,false);
        return new FITBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FITBAdapter.FITBHolder holder, int position) {
        Test currenttest = tests.get(position);
        holder.textViewTestName.setText(currenttest.getTestName());
        final String testno = currenttest.getTestName().trim();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FITBFragmentDirections.ActionFITBFragmentToFITBTestFragment action = FITBFragmentDirections.actionFITBFragmentToFITBTestFragment(testno);
               action.getTestno();
               Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class FITBHolder extends RecyclerView.ViewHolder {

        TextView textViewTestName;
        LinearLayout linearLayout;
        public FITBHolder(@NonNull View itemView) {
            super(itemView);

            textViewTestName = itemView.findViewById(R.id.text_view_test_name);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
