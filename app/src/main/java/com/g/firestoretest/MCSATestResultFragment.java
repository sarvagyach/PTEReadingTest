package com.g.firestoretest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MCSATestResultFragment extends Fragment {

    TextView textViewScore, textViewMainScore;
    MaterialButton btnReturnToMenu;

    public MCSATestResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mcsatest_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewScore = view.findViewById(R.id.text_view_score);
        btnReturnToMenu = view.findViewById(R.id.btn_menu);
        textViewMainScore = view.findViewById(R.id.text_view_mainscore);

        final int score = MCSATestResultFragmentArgs.fromBundle(getArguments()).getScore();

        textViewScore.setText(String.valueOf(score) + " Out of 90");
        textViewMainScore.setText(String.valueOf(score));

        btnReturnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = MCSATestResultFragmentDirections.actionMCSATestResultFragmentToStartFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

    }
}
