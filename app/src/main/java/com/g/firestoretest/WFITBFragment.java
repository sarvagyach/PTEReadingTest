package com.g.firestoretest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WFITBFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Test> testArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    WFITBAdapter adapter;
    ScrollView scrollView;
    ProgressBar progressBar;

    public WFITBFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wfitb, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = view.findViewById(R.id.scroll_view);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);



        db.collection("WFITBtest").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();

                            testArrayList.clear();
                            for (int i = 0; i < documentSnapshots.size(); i++) {

                                testArrayList.add(documentSnapshots.get(i).toObject(Test.class));

                            }
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        adapter = new WFITBAdapter(testArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
