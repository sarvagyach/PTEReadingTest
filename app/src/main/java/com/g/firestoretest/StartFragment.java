package com.g.firestoretest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    ConstraintLayout layoutStart, layoutRun;
    TextView textViewOne, textViewTwo, textViewThree, textViewFour, textViewFive;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewOne = view.findViewById(R.id.text_view_one);
        textViewTwo = view.findViewById(R.id.text_view_two);
        textViewThree = view.findViewById(R.id.text_view_three);
        textViewFour = view.findViewById(R.id.text_view_four);
        textViewFive = view.findViewById(R.id.text_view_five);


        layoutStart = view.findViewById(R.id.layout_start);
        layoutRun = view.findViewById(R.id.layout_run);
        layoutStart.postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutStart.setVisibility(View.GONE);
                layoutRun.setVisibility(View.VISIBLE);

            }
        },500);


        textViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavDirections action = StartFragmentDirections.actionStartFragmentToMCSAFragment();
                Navigation.findNavController(view).navigate(action);

            }
        });

        textViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = StartFragmentDirections.actionStartFragmentToMCMAFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        textViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = StartFragmentDirections.actionStartFragmentToRPFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        textViewFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = StartFragmentDirections.actionStartFragmentToFITBFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        textViewFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = StartFragmentDirections.actionStartFragmentToWFITBFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

}
