package com.g.firestoretest;

import android.app.Notification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    ArrayList<Test> tests = new ArrayList<>();

    public TestAdapter(ArrayList<Test> tests) {
        this.tests = tests;
    }

    @NonNull
    @Override
    public TestAdapter.TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_number,parent,false);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TestAdapter.TestHolder holder, int position) {
            Test currenttest = tests.get(position);
            holder.textViewTestName.setText(currenttest.getTestName());
            final String testno = currenttest.getTestName().trim();

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        MCSAFragmentDirections.ActionMCSAFragmentToMCSATestFragment action = MCSAFragmentDirections.actionMCSAFragmentToMCSATestFragment(testno);
                        action.setTestno(testno);
                        Navigation.findNavController(view).navigate(action);



                }
            });


    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public class TestHolder extends RecyclerView.ViewHolder {

        TextView textViewTestName;
        LinearLayout linearLayout;

        public TestHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout =itemView.findViewById(R.id.linear_layout);
            textViewTestName = itemView.findViewById(R.id.text_view_test_name);
        }
    }
}
