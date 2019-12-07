package com.g.firestoretest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MCMATestFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView textViewSolution1, textViewSolution2, textViewSolution3, textViewSolution4, textViewSolution5, textViewAnswer, passage, mainQuestion, qNumber;
    MaterialButton btnSolution, btnNext;
    CheckBox checkBox1,checkBox2,checkBox3, checkBox4, checkBox5;
    int result;
    int qNo = 1;
    boolean answer1, answer2, answer3, answer4, answer5;
    ScrollView scrollView;
    private int focus_pos = 1;
    int score;
    int totalQuestion;
    int questionResult;
    int correctNo;

    ArrayList<MCMA> mcmaArrayList = new ArrayList<>();


    public MCMATestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mcmatest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String testno = MCMATestFragmentArgs.fromBundle(getArguments()).getTestno();

        passage = view.findViewById(R.id.text_view_passage);
        mainQuestion = view.findViewById(R.id.text_view_mainquestion);
        qNumber = view.findViewById(R.id.text_view_qno);

        checkBox1 = view.findViewById(R.id.check_box1);
        checkBox2 = view.findViewById(R.id.check_box2);
        checkBox3 = view.findViewById(R.id.check_box3);
        checkBox4 = view.findViewById(R.id.check_box4);
        checkBox5 = view.findViewById(R.id.check_box5);
        btnSolution = view.findViewById(R.id.btn_solution);
        btnNext = view.findViewById(R.id.btn_next);
        textViewAnswer = view.findViewById(R.id.text_view_answer);
        textViewSolution1 = view.findViewById(R.id.text_view_solution1);
        textViewSolution2 = view.findViewById(R.id.text_view_solution2);
        textViewSolution3 = view.findViewById(R.id.text_view_solution3);
        textViewSolution4 = view.findViewById(R.id.text_view_solution4);
        textViewSolution5 = view.findViewById(R.id.text_view_solution5);

        scrollView = view.findViewById(R.id.scroll_view);
        score = 0;

        db.collection("MCMA").whereEqualTo("testNm",testno)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            for (int i=0; i<documentSnapshots.size();i++){
                                mcmaArrayList.add(documentSnapshots.get(i).toObject(MCMA.class));
                            }

                            setQuestion();
                        }
                    }
                });

        btnSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox5.isChecked()){

                    textViewAnswer.setVisibility((textViewAnswer.getVisibility() == view.VISIBLE) ? view.GONE : view.VISIBLE);
                    getAnswer();

                }else{
                    textViewSolution1.setVisibility(View.VISIBLE);
                    textViewSolution1.setText("Please choose one option!");
                    textViewSolution1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textViewSolution1.setVisibility(View.GONE);
                            textViewSolution1.setText("Please choose one option!");
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
                checkAnswer();
                totalQuestion = mcmaArrayList.size();
                if(qNo<totalQuestion){
                    if(checkBox1.isChecked()||checkBox2.isChecked()||checkBox3.isChecked()||checkBox4.isChecked()||checkBox5.isChecked()) {
                        changeQuestion();
                        textViewAnswer.setVisibility(View.GONE);
                        textViewSolution1.setVisibility(View.GONE);
                        textViewSolution2.setVisibility(View.GONE);
                        textViewSolution3.setVisibility(View.GONE);
                        textViewSolution4.setVisibility(View.GONE);
                        textViewSolution5.setVisibility(View.GONE);
                        textViewSolution1.setText("");
                        textViewSolution2.setText("");
                        textViewSolution3.setText("");
                        textViewSolution4.setText("");
                        textViewSolution5.setText("");
                    }else{
                        textViewSolution1.setVisibility(View.VISIBLE);
                        textViewSolution1.setText("Please choose one option!");
                        textViewSolution1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textViewSolution1.setVisibility(View.GONE);
                                textViewSolution1.setText("Please choose one option!");
                            }
                        }, 1000);
                    }
                }else {
                  MCMATestFragmentDirections.ActionMCMATestFragment2ToMCMATestResultFragment action = MCMATestFragmentDirections.actionMCMATestFragment2ToMCMATestResultFragment();
                  action.setScore(score);
                  Navigation.findNavController(view).navigate(action);
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
        if (mcmaArrayList.size()>0){
            qNumber.setText(String.valueOf(qNo));
            passage.setText(mcmaArrayList.get(0).getPassage());
            mainQuestion.setText(mcmaArrayList.get(0).getMainQuestion());
            checkBox1.setText(mcmaArrayList.get(0).getOption1());
            checkBox2.setText(mcmaArrayList.get(0).getOption2());
            checkBox3.setText(mcmaArrayList.get(0).getOption3());
            checkBox4.setText(mcmaArrayList.get(0).getOption4());
            checkBox5.setText(mcmaArrayList.get(0).getOption5());
            answer1 = mcmaArrayList.get(0).isAnswer1();
            answer2 = mcmaArrayList.get(0).isAnswer2();
            answer3 = mcmaArrayList.get(0).isAnswer3();
            answer4 = mcmaArrayList.get(0).isAnswer4();
            answer5 = mcmaArrayList.get(0).isAnswer5();
        }
    }

    public  void getAnswer(){
        if(answer1==true){
            String ans1 = checkBox1.getText().toString().trim();
            textViewSolution1.setText(ans1+"");
            textViewSolution1.setVisibility((textViewSolution1.getVisibility()==View.VISIBLE)? View.GONE: View.VISIBLE);
        }

        if (answer2==true){
            String ans2 = checkBox2.getText().toString().trim();
            textViewSolution2.setText(ans2+"");
            textViewSolution2.setVisibility((textViewSolution2.getVisibility()==View.VISIBLE)? View.GONE: View.VISIBLE);
        }

        if (answer3==true){
            String ans3 = checkBox3.getText().toString().trim();
            textViewSolution3.setText(ans3+"");
            textViewSolution3.setVisibility((textViewSolution3.getVisibility()==View.VISIBLE)? View.GONE: View.VISIBLE);
        }

        if (answer4==true){
            String ans4 = checkBox4.getText().toString().trim();
            textViewSolution4.setText(ans4+"");
            textViewSolution4.setVisibility((textViewSolution4.getVisibility()==View.VISIBLE)? View.GONE: View.VISIBLE);
        }
        if (answer5==true){
            String ans5 = checkBox5.getText().toString().trim();
            textViewSolution5.setText(ans5+"");
            textViewSolution5.setVisibility((textViewSolution5.getVisibility()==View.VISIBLE)? View.GONE: View.VISIBLE);
        }

    }


    public void checkAnswer(){
        questionResult = 0;
        correctNo = 0;
        if (checkBox1.isChecked()&& answer1==true){
            questionResult=questionResult + 1;
        }else if (checkBox1.isChecked()&& answer1==false){
            questionResult=questionResult - 1;
        }

        if (checkBox2.isChecked()&& answer2==true){
            questionResult=questionResult + 1;
        }else if (checkBox2.isChecked()&& answer2==false){
            questionResult=questionResult - 1;
        }

        if (checkBox3.isChecked()&& answer3==true){
            questionResult= questionResult + 1;
        }else if (checkBox3.isChecked()&& answer3==false){
            questionResult=questionResult - 1;
        }

        if (checkBox4.isChecked()&& answer4==true){
            questionResult= questionResult+ 1;
        }else if (checkBox4.isChecked()&& answer4==false){
            questionResult=questionResult - 1;
        }

        if (checkBox5.isChecked()&& answer5==true){
            questionResult= questionResult+ 1;
        }else if (checkBox5.isChecked()&& answer5==false){
            questionResult=questionResult - 1;
        }

        if (answer1 == true){
            correctNo++;
        }
        if (answer2 == true){
            correctNo++;
        }
        if (answer3 == true){
            correctNo++;
        }
        if (answer4 == true){
            correctNo++;
        }
        if (answer5 == true){
            correctNo++;
        }

        result = (18/correctNo)*questionResult;
        if(result > 0){
            score = score + result;
        }else{
            score = score;
        }


    }


    public void changeQuestion(){
        qNumber.setText(String.valueOf(qNo+1));
        passage.setText(mcmaArrayList.get(qNo).getPassage());
        mainQuestion.setText(mcmaArrayList.get(qNo).getMainQuestion());
        checkBox1.setText(mcmaArrayList.get(qNo).getOption1());
        checkBox2.setText(mcmaArrayList.get(qNo).getOption2());
        checkBox3.setText(mcmaArrayList.get(qNo).getOption3());
        checkBox4.setText(mcmaArrayList.get(qNo).getOption4());
        checkBox5.setText(mcmaArrayList.get(qNo).getOption5());
        answer1 = mcmaArrayList.get(qNo).isAnswer1();
        answer2 = mcmaArrayList.get(qNo).isAnswer2();
        answer3 = mcmaArrayList.get(qNo).isAnswer3();
        answer4 = mcmaArrayList.get(qNo).isAnswer4();
        answer5 = mcmaArrayList.get(qNo).isAnswer5();
        qNo++;

        if (checkBox1.isChecked()){
            checkBox1.toggle();
        }
        if (checkBox2.isChecked()){
            checkBox2.toggle();
        }
        if (checkBox3.isChecked()){
            checkBox3.toggle();
        }
        if (checkBox4.isChecked()){
            checkBox4.toggle();
        }
        if (checkBox5.isChecked()){
            checkBox5.toggle();
        }


    }



}
