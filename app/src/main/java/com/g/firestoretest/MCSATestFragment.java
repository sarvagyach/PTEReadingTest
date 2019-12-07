package com.g.firestoretest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MCSATestFragment extends Fragment {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView textViewPassage, textViewMainQuestion, textViewAnswer, textViewSolution, textViewQNo;
    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    MaterialButton btnSolution, btnNext;
    ScrollView scrollView;
    String answer;
    int qNo;
    private int focus_pos = 1;
    int score;



    ArrayList<MCSA> mcsaArrayList = new ArrayList<>();

    public MCSATestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mcsatest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String testno = MCSATestFragmentArgs.fromBundle(getArguments()).getTestno();

        textViewQNo = view.findViewById(R.id.text_view_qno);
        textViewPassage = view.findViewById(R.id.text_view_passage);
        textViewMainQuestion = view.findViewById(R.id.text_view_mainquestion);
        textViewAnswer = view.findViewById(R.id.text_view_answer);
        radioGroup = view.findViewById(R.id.radio_group);
        radioButton1 = view.findViewById(R.id.radio_button1);
        radioButton2 = view.findViewById(R.id.radio_button2);
        radioButton3 = view.findViewById(R.id.radio_button3);
        radioButton4 = view.findViewById(R.id.radio_button4);

        scrollView = view.findViewById(R.id.scroll_view);

        textViewSolution = view.findViewById(R.id.text_view_solution);

        btnSolution = view.findViewById(R.id.btn_solution);
        btnNext = view.findViewById(R.id.btn_next);

        score = 0;
        qNo = 1;

        db.collection("MCSA").whereEqualTo("testNm",testno).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();

                                for (int i = 0; i < documentSnapshots.size(); i++) {

                                    mcsaArrayList.add(documentSnapshots.get(i).toObject(MCSA.class));

                                }

                                setQuestion();

                            }
                        }
                    }
                });


        btnSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int selectedRadioID = radioGroup.getCheckedRadioButtonId();

                if (selectedRadioID != -1) {
                    textViewAnswer.setVisibility((textViewAnswer.getVisibility() == view.VISIBLE) ? view.GONE : view.VISIBLE);
                    textViewSolution.setVisibility((textViewSolution.getVisibility() == View.VISIBLE) ? view.GONE : view.VISIBLE);
                    textViewSolution.setText("" + answer);
                }else{
                    textViewSolution.setVisibility(View.VISIBLE);
                    textViewSolution.setText("Please choose one option!");
                    textViewSolution.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textViewSolution.setVisibility(View.GONE);
                            textViewSolution.setText("Please choose one option!");
                        }
                    }, 1000);
                }
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (focus_pos == 1) {
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 2;
                        } else {
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 1;
                        }


                    }
                });
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int selectedRadioID = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioID != -1) {
                    checkAnswer();
                    if(qNo<mcsaArrayList.size()){
                        changeQuestion();
                        textViewSolution.setVisibility(View.GONE);
                        textViewAnswer.setVisibility(View.GONE);
                        textViewSolution.setText("");
                    }else{
                        MCSATestFragmentDirections.ActionMCSATestFragmentToMCSATestResultFragment action = MCSATestFragmentDirections.actionMCSATestFragmentToMCSATestResultFragment();
                        action.setScore(score);
                        Navigation.findNavController(view).navigate(action);

                    }

                }else{
                    textViewSolution.setVisibility(View.VISIBLE);
                    textViewSolution.setText("Please choose one option!");
                    textViewSolution.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textViewSolution.setVisibility(View.GONE);
                            textViewSolution.setText("Please choose one option!");
                        }
                    }, 1000);
                }
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (focus_pos == 1) {
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 2;
                        } else {
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 1;
                        }


                    }
                });
            }
        });



    }

            public void setQuestion(){
            if(mcsaArrayList.size()>0) {
                MCSA firstMCSA = mcsaArrayList.get(0);
                textViewQNo.setText(String.valueOf(qNo));
                textViewPassage.setText(firstMCSA.getPassage());
                textViewMainQuestion.setText(firstMCSA.getMainQuestion());
                radioButton1.setText(firstMCSA.getOption1());
                radioButton2.setText(firstMCSA.getOption2());
                radioButton3.setText(firstMCSA.getOption3());
                radioButton4.setText(firstMCSA.getOption4());
                answer = firstMCSA.getAnswer();
            }
            }

    public  void checkAnswer(){
        int selectedRadioID = radioGroup.getCheckedRadioButtonId();
        String choosedAnswer = "";

        switch (selectedRadioID){
            case (R.id.radio_button1):
                choosedAnswer = radioButton1.getText().toString();
                break;

            case (R.id.radio_button2):
                choosedAnswer = radioButton2.getText().toString();
                break;

            case (R.id.radio_button3):
                choosedAnswer = radioButton3.getText().toString();
                break;

            case (R.id.radio_button4):
                choosedAnswer = radioButton4.getText().toString();
                break;
        }
        if(choosedAnswer.equals(answer)){
            score = score + 15;
        }
    }


    public  void changeQuestion(){
        MCSA firstMCSA = mcsaArrayList.get(qNo);
        textViewQNo.setText(String.valueOf(qNo+1));
        textViewPassage.setText(firstMCSA.getPassage());
        textViewMainQuestion.setText(firstMCSA.getMainQuestion());
        radioButton1.setText(firstMCSA.getOption1());
        radioButton2.setText(firstMCSA.getOption2());
        radioButton3.setText(firstMCSA.getOption3());
        radioButton4.setText(firstMCSA.getOption4());
        answer = firstMCSA.getAnswer();
        qNo++;
        radioGroup.clearCheck();

    }


}


