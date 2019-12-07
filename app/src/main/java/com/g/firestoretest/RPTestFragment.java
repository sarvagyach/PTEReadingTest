package com.g.firestoretest;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
public class RPTestFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MaterialButton btnSolution, btnNext;
    TextView textViewSolution, textViewPerquestion, textViewAnswer;
    TextView textViewOption1, textViewOption2, textViewOption3, textViewOption4, textViewOption5, textViewQno;
    EditText e1, e2, e3, e4, e5;
    ArrayList<RP> rpArrayList = new ArrayList<>();
    String answer1,answer2,answer3, answer4, answer5;
    int qNo;
    int perquestion;
    String texta, textb, textc,textd, texte;
    String ans1, ans2, ans3, ans4, ans5;
    ScrollView scrollView;
    private int focus_pos = 1;
    float result;
    float score;


    public RPTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rptest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String testno = RPTestFragmentArgs.fromBundle(getArguments()).getTestno();

        textViewQno = view.findViewById(R.id.text_view_qno);
        textViewOption1 = view.findViewById(R.id.text_view_option1);
        textViewOption2 = view.findViewById(R.id.text_view_option2);
        textViewOption3 = view.findViewById(R.id.text_view_option3);
        textViewOption4 = view.findViewById(R.id.text_view_option4);
        textViewOption5 = view.findViewById(R.id.text_view_option5);
        textViewSolution = view.findViewById(R.id.text_view_solution);
        textViewAnswer = view.findViewById(R.id.text_view_answer);
        textViewPerquestion = view.findViewById(R.id.text_view_perquestion);

        scrollView = view.findViewById(R.id.scroll_view);

        e1 = view.findViewById(R.id.edit_text1);
        e2 = view.findViewById(R.id.edit_text2);
        e3 = view.findViewById(R.id.edit_text3);
        e4 = view.findViewById(R.id.edit_text4);
        e5 = view.findViewById(R.id.edit_text5);

        btnSolution = view.findViewById(R.id.btn_solution);
        btnNext = view.findViewById(R.id.btn_next);

        e1.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});
        e2.setFilters(new InputFilter[]{new InputFilterMinMax("1", "5")});
        e3.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
        e4.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});
        e5.setFilters(new InputFilter[]{new InputFilterMinMax("1","5")});

        score = 0.00f;
        qNo = 1;


        db.collection("RP").whereEqualTo("testNm",testno).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();

                                for (int i = 0; i < documentSnapshots.size(); i++) {

                                    rpArrayList.add(documentSnapshots.get(i).toObject(RP.class));

                                }

                                setQuestion();

                            }
                        }
                });

        btnSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                texta = e1.getText().toString().trim();
                textb = e2.getText().toString().trim();
                textc = e3.getText().toString().trim();
                textd = e4.getText().toString().trim();
                texte = e5.getText().toString().trim();

                if (answer5!=null){

                    if(texta.isEmpty() || textb.isEmpty() || textc.isEmpty() || textd.isEmpty() || texte.isEmpty()){

                        textViewSolution.setVisibility(View.VISIBLE);
                        textViewSolution.setText("Please fill all the blanks!");
                        textViewSolution.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textViewSolution.setVisibility(View.GONE);
                            }
                        }, 1000);

                    }else {
                        getAnswer1();
                        getOption1();
                        textViewAnswer.setVisibility((textViewAnswer.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                        textViewSolution.setVisibility((textViewSolution.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                        textViewSolution.setText("" + ans1 + "\n" + ans2 + "\n" + ans3 + "\n" +ans4+ "\n" +ans5);
                        textViewPerquestion.setText("Your Score in this question is \n" + perquestion + " out of 4");
                    }

                }else{

                    if(texta.isEmpty() || textb.isEmpty() || textc.isEmpty() || textd.isEmpty()){

                        textViewSolution.setVisibility(View.VISIBLE);
                        textViewSolution.setText("Please fill all the blanks!");
                        textViewSolution.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                textViewSolution.setVisibility(View.GONE);
                            }
                        }, 1000);

                    }else {
                        getAnswer2();
                        getOption2();
                        textViewAnswer.setVisibility((textViewAnswer.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                        textViewSolution.setVisibility((textViewSolution.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                        textViewSolution.setText("" + ans1 + "\n" + ans2 + "\n" + ans3 + "\n" +ans4);
                        textViewPerquestion.setText("Your Score in this question is \n" + perquestion + " out of 3");
                    }


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

                texta = e1.getText().toString().trim();
                textb = e2.getText().toString().trim();
                textc = e3.getText().toString().trim();
                textd = e4.getText().toString().trim();
                texte = e5.getText().toString().trim();

                if(answer5!=null){
                    getAnswer1();
                }else{
                    getAnswer2();
                }
                    score = score + result;

                if(qNo<rpArrayList.size()){

                    if (answer5!=null){

                        if(texta.isEmpty() || textb.isEmpty() || textc.isEmpty() || textd.isEmpty() || texte.isEmpty()){

                            textViewSolution.setVisibility(View.VISIBLE);
                            textViewSolution.setText("Please fill all the blanks!");
                            textViewSolution.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textViewSolution.setVisibility(View.GONE);
                                }
                            }, 1000);

                        }else {
                            textViewOption5.setVisibility(View.GONE);
                            e5.setVisibility(View.GONE);
                            changeQuestion();
                            textViewAnswer.setVisibility(View.GONE);
                            textViewSolution.setVisibility(View.GONE);
                            e1.setText("");
                            e2.setText("");
                            e3.setText("");
                            e4.setText("");
                            e5.setText("");
                            textViewPerquestion.setText("");

                        }

                    }else{

                        if(texta.isEmpty() || textb.isEmpty() || textc.isEmpty() || textd.isEmpty()){

                            textViewSolution.setVisibility(View.VISIBLE);
                            textViewSolution.setText("Please fill all the blanks!");
                            textViewSolution.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    textViewSolution.setVisibility(View.GONE);
                                }
                            }, 1000);

                        }else {
                            textViewOption5.setVisibility(View.GONE);
                            e5.setVisibility(View.GONE);
                            changeQuestion();
                            textViewAnswer.setVisibility(View.GONE);
                            textViewSolution.setVisibility(View.GONE);
                            e1.setText("");
                            e2.setText("");
                            e3.setText("");
                            e4.setText("");
                            e5.setText("");
                            textViewPerquestion.setText("");
                        }


                    }
                }else {
                    if (answer5!=null){
                        getAnswer1();
                        RPTestFragmentDirections.ActionRPTestFragmentToRPTestResultFragment action = RPTestFragmentDirections.actionRPTestFragmentToRPTestResultFragment();
                        action.setScore(score);
                        Navigation.findNavController(view).navigate(action);
                    }else {
                        getAnswer2();
                        RPTestFragmentDirections.ActionRPTestFragmentToRPTestResultFragment action = RPTestFragmentDirections.actionRPTestFragmentToRPTestResultFragment();
                        action.setScore(score);
                        Navigation.findNavController(view).navigate(action);
                    }

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
        if (rpArrayList.size()>0){
            textViewQno.setText(String.valueOf(qNo));
            textViewOption1.setText(rpArrayList.get(0).getOption1());
            textViewOption2.setText(rpArrayList.get(0).getOption2());
            textViewOption3.setText(rpArrayList.get(0).getOption3());
            textViewOption4.setText(rpArrayList.get(0).getOption4());
            textViewOption5.setText(rpArrayList.get(0).getOption5());
            answer1 = rpArrayList.get(0).getAnswer1();
            answer2 = rpArrayList.get(0).getAnswer2();
            answer3 = rpArrayList.get(0).getAnswer3();
            answer4 = rpArrayList.get(0).getAnswer4();
            answer5 = rpArrayList.get(0).getAnswer5();
            if (answer5!=null){
                textViewOption5.setVisibility(View.VISIBLE);
                e5.setVisibility(View.VISIBLE);
            }
        }
    }


    public void changeQuestion(){
        textViewQno.setText(String.valueOf(qNo+1));
        textViewOption1.setText(rpArrayList.get(qNo).getOption1());
        textViewOption2.setText(rpArrayList.get(qNo).getOption2());
        textViewOption3.setText(rpArrayList.get(qNo).getOption3());
        textViewOption4.setText(rpArrayList.get(qNo).getOption4());
        textViewOption5.setText(rpArrayList.get(qNo).getOption5());
        answer1 = rpArrayList.get(qNo).getAnswer1();
        answer2 = rpArrayList.get(qNo).getAnswer2();
        answer3 = rpArrayList.get(qNo).getAnswer3();
        answer4 = rpArrayList.get(qNo).getAnswer4();
        answer5 = rpArrayList.get(qNo).getAnswer5();
        if (answer5!=null){
            textViewOption5.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
        }
        qNo++;
    }


    public void getAnswer1(){
            perquestion = 0;
            result = 0.00f;
            if ((answer1.equals(texta)&&answer2.equals(textb))||(answer2.equals(texta)&&answer3.equals(textb))||(answer3.equals(texta)&&answer4.equals(textb))||answer4.equals(texta)&&answer5.equals(textb)){
                perquestion = perquestion + 1;
                result = result + 3.75f;
            }
            if ((answer1.equals(textb)&&answer2.equals(textc))||(answer2.equals(textb)&&answer3.equals(textc))||(answer3.equals(textb)&&answer4.equals(textc))||answer4.equals(textb)&&answer5.equals(textc)){
                perquestion = perquestion + 1;
                result = result + 3.75f;
            }
            if ((answer1.equals(textc)&&answer2.equals(textd))||(answer2.equals(textc)&&answer3.equals(textd))||(answer3.equals(textc)&&answer4.equals(textd))||answer4.equals(textc)&&answer5.equals(textd)){
                perquestion = perquestion + 1;
                result = result + 3.75f;
            }
            if ((answer1.equals(textd)&&answer2.equals(texte))||(answer2.equals(textd)&&answer3.equals(texte))||(answer3.equals(textd)&&answer4.equals(texte))||answer4.equals(textd)&&answer5.equals(texte)){
                perquestion = perquestion + 1;
                result = result + 3.75f;
            }


    }

    public void getAnswer2(){
        perquestion = 0;
        result = 0.00f;
        if ((answer1.equals(texta)&&answer2.equals(textb))||(answer2.equals(texta)&&answer3.equals(textb))||(answer3.equals(texta)&&answer4.equals(textb))){
            perquestion = perquestion + 1;
            result = result + 5.00f;
        }
        if ((answer1.equals(textb)&&answer2.equals(textc))||(answer2.equals(textb)&&answer3.equals(textc))||(answer3.equals(textb)&&answer4.equals(textc))){
            perquestion = perquestion + 1;
            result = result + 5.00f;
        }
        if ((answer1.equals(textc)&&answer2.equals(textd))||(answer2.equals(textc)&&answer3.equals(textd))||(answer3.equals(textc)&&answer4.equals(textd))){
            perquestion = perquestion + 1;
            result = result + 5.00f;
        }

    }


    public void getOption1(){
        if (answer1.equals("1")){
            ans1 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("2")){
            ans2 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("3")){
            ans3 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("4")){
            ans4 = textViewOption1.getText().toString().trim();
        }else{
            ans5 = textViewOption1.getText().toString().trim();
        }

        if (answer2.equals("1")){
            ans1 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("2")){
            ans2 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("3")){
            ans3 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("4")){
            ans4 = textViewOption2.getText().toString().trim();
        }else{
            ans5 = textViewOption2.getText().toString().trim();
        }

        if (answer3.equals("1")){
            ans1 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("2")){
            ans2 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("3")){
            ans3 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("4")){
            ans4 = textViewOption3.getText().toString().trim();
        }else{
            ans5 = textViewOption3.getText().toString().trim();
        }

        if (answer4.equals("1")){
            ans1 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("2")){
            ans2 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("3")){
            ans3 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("4")){
            ans4 = textViewOption4.getText().toString().trim();
        }else{
            ans5 = textViewOption4.getText().toString().trim();
        }

        if (answer5.equals("1")){
            ans1 = textViewOption5.getText().toString().trim();
        }else if (answer5.equals("2")){
            ans2 = textViewOption5.getText().toString().trim();
        }else if (answer5.equals("3")){
            ans3 = textViewOption5.getText().toString().trim();
        }else if (answer5.equals("4")){
            ans4 = textViewOption5.getText().toString().trim();
        }else {
            ans5 = textViewOption5.getText().toString().trim();
        }
    }

    public void getOption2(){
        if (answer1.equals("1")){
            ans1 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("2")){
            ans2 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("3")){
            ans3 = textViewOption1.getText().toString().trim();
        }else if (answer1.equals("4")){
            ans4 = textViewOption1.getText().toString().trim();
        }

        if (answer2.equals("1")){
            ans1 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("2")){
            ans2 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("3")){
            ans3 = textViewOption2.getText().toString().trim();
        }else if (answer2.equals("4")){
            ans4 = textViewOption2.getText().toString().trim();
        }

        if (answer3.equals("1")){
            ans1 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("2")){
            ans2 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("3")){
            ans3 = textViewOption3.getText().toString().trim();
        }else if (answer3.equals("4")){
            ans4 = textViewOption3.getText().toString().trim();
        }

        if (answer4.equals("1")){
            ans1 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("2")){
            ans2 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("3")){
            ans3 = textViewOption4.getText().toString().trim();
        }else if (answer4.equals("4")){
            ans4 = textViewOption4.getText().toString().trim();
        }

    }

}



