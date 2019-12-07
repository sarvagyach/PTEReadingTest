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

public class MCMAAdapter extends RecyclerView.Adapter<MCMAAdapter.MCMAHolder> {
    ArrayList<Test> tests = new ArrayList<>();

    public MCMAAdapter(ArrayList<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public MCMAAdapter.MCMAHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_number,parent,false);
        return new MCMAAdapter.MCMAHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MCMAAdapter.MCMAHolder holder, int position) {
        Test currenttest = tests.get(position);
        holder.textViewTestName.setText(currenttest.getTestName());
        final String testno = currenttest.getTestName().trim();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MCMAFragmentDirections.ActionMCMAFragmentToMCMATestFragment2 action = MCMAFragmentDirections.actionMCMAFragmentToMCMATestFragment2(testno);
                action.setTestno(testno);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class MCMAHolder extends RecyclerView.ViewHolder {
        TextView textViewTestName;
        LinearLayout linearLayout;
        public MCMAHolder(@NonNull View itemView) {
            super(itemView);


            linearLayout =itemView.findViewById(R.id.linear_layout);
            textViewTestName = itemView.findViewById(R.id.text_view_test_name);
        }
    }
}
