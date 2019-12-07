package com.g.firestoretest;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
public class FITBTestFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MaterialButton btnSolution,  btnNext;
    TextView choice1, choice2, choice3, choice4, choice5, choice6, option1, option2, option3, option4, option5, option6, option7, option8, option9;
    String answer1, answer2, answer3, answer4, answer5, answer6;
    TextView textView1, textView2, textView3, textView4,textView5,textView6,textView7;
    TextView textViewAnswer, textViewSolution;
    TextView qNumber;
    ScrollView scrollView;
    private int focus_pos = 1;
    int qNo = 1;
    float score;

    ArrayList<FITB> fitbArrayList = new ArrayList<>();


    public FITBTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitbtest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String testno = FITBTestFragmentArgs.fromBundle(getArguments()).getTestno();

        textView1 = view.findViewById(R.id.text_view1);
        textView2 = view.findViewById(R.id.text_view2);
        textView3 = view.findViewById(R.id.text_view3);
        textView4 = view.findViewById(R.id.text_view4);
        textView5 = view.findViewById(R.id.text_view5);
        textView6 = view.findViewById(R.id.text_view_6);
        textView7 = view.findViewById(R.id.text_view_7);


        choice1 = view.findViewById(R.id.choice1);
        choice2 = view.findViewById(R.id.choice2);
        choice3 = view.findViewById(R.id.choice3);
        choice4 = view.findViewById(R.id.choice4);
        choice5 = view.findViewById(R.id.choice5);
        choice6 = view.findViewById(R.id.choice6);

        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        option5 = view.findViewById(R.id.option5);
        option6 = view.findViewById(R.id.option6);
        option7 = view.findViewById(R.id.option7);
        option8 = view.findViewById(R.id.option8);
        option9 = view.findViewById(R.id.option9);

        qNumber = view.findViewById(R.id.text_view_qno);

        textViewAnswer = view.findViewById(R.id.text_view_answer);
        textViewSolution = view.findViewById(R.id.text_view_solution);

        btnSolution = view.findViewById(R.id.btn_solution);
        btnNext = view.findViewById(R.id.btn_next);

        scrollView = view.findViewById(R.id.scroll_view);

        score = 0.00f;

        db.collection("FITB").whereEqualTo("testNm", testno).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            for (int i=0; i< documentSnapshots.size();i++){
                                fitbArrayList.add(documentSnapshots.get(i).toObject(FITB.class));
                            }
                            setQuestion();
                        }
                    }
                });

        option1.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option2.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option3.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option4.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option5.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option6.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option7.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option8.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());
        option9.setOnTouchListener(new FITBTestFragment.ChoiceTouchListener());

        choice1.setOnDragListener(new FITBTestFragment.ChoiceDragListener());
        choice2.setOnDragListener(new FITBTestFragment.ChoiceDragListener());
        choice3.setOnDragListener(new FITBTestFragment.ChoiceDragListener());
        choice4.setOnDragListener(new FITBTestFragment.ChoiceDragListener());
        choice5.setOnDragListener(new FITBTestFragment.ChoiceDragListener());
        choice6.setOnDragListener(new FITBTestFragment.ChoiceDragListener());

        btnSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                textViewAnswer.setVisibility((textViewAnswer.getVisibility() == view.VISIBLE) ? view.GONE : view.VISIBLE);
                textViewSolution.setVisibility((textViewSolution.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                textViewSolution.setText("1. "+answer1+"\n2. "+answer2+" \n3. "+answer3+"\n4. "+answer4+"\n5. "+answer5+"\n6. "+answer6);

                scrollView.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(focus_pos == 1){
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 2;
                        }else{
                            scrollView.fullScroll(view.FOCUS_DOWN);
                            focus_pos = 1;
                        }


                    }
                });
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
                int totalQuestion = fitbArrayList.size();
                if (qNo<totalQuestion){
                    textViewAnswer.setVisibility(View.GONE);
                    textViewSolution.setVisibility(View.GONE);
                    textView5.setVisibility(View.GONE);
                    textView6.setVisibility(View.GONE);
                    textView7.setVisibility(View.GONE);
                    choice5.setVisibility(View.GONE);
                    choice6.setVisibility(View.GONE);
                    option7.setVisibility(View.GONE);
                    option8.setVisibility(View.GONE);
                    option9.setVisibility(View.GONE);
                    choice1.setText("");
                    choice2.setText("");
                    choice3.setText("");
                    choice4.setText("");
                    choice5.setText("");
                    choice6.setText("");
                    changeQuestion();

                }else {
                    FITBTestFragmentDirections.ActionFITBTestFragmentToFITBTestResultFragment action = FITBTestFragmentDirections.actionFITBTestFragmentToFITBTestResultFragment();
                    action.setScore(score);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });


    }

    public void setQuestion(){
        if (fitbArrayList.size()>0){
            qNumber.setText(String.valueOf(qNo));
            textView1.setText(fitbArrayList.get(0).getT1());
            textView2.setText(fitbArrayList.get(0).getT2());
            textView3.setText(fitbArrayList.get(0).getT3());
            textView4.setText(fitbArrayList.get(0).getT4());
            textView5.setText(fitbArrayList.get(0).getT5());
            textView6.setText(fitbArrayList.get(0).getT6());
            textView7.setText(fitbArrayList.get(0).getT7());
            answer1 = fitbArrayList.get(0).getA1();
            answer2 = fitbArrayList.get(0).getA2();
            answer3 = fitbArrayList.get(0).getA3();
            answer4 = fitbArrayList.get(0).getA4();
            answer5 = fitbArrayList.get(0).getA5();
            answer6 = fitbArrayList.get(0).getA6();
            option1.setText(fitbArrayList.get(0).getO1());
            option2.setText(fitbArrayList.get(0).getO2());
            option3.setText(fitbArrayList.get(0).getO3());
            option4.setText(fitbArrayList.get(0).getO4());
            option5.setText(fitbArrayList.get(0).getO5());
            option6.setText(fitbArrayList.get(0).getO6());
            option7.setText(fitbArrayList.get(0).getO7());
            option8.setText(fitbArrayList.get(0).getO8());
            option9.setText(fitbArrayList.get(0).getO9());
            if(textView5!=null){
                textView5.setVisibility(View.VISIBLE);
            }
            if (textView6!=null){
                textView6.setVisibility(View.VISIBLE);
            }
            if (textView7!=null){
                textView7.setVisibility(View.VISIBLE);
            }
            if (answer5!=null){
                choice5.setVisibility(View.VISIBLE);
            }
            if (answer6!=null){
                choice6.setVisibility(View.VISIBLE);
            }
            if (option7!=null){
                option7.setVisibility(View.VISIBLE);
            }
            if (option8!=null){
                option8.setVisibility(View.VISIBLE);
            }
            if (option9!=null){
                option9.setVisibility(View.VISIBLE);
            }

        }
    }


    private final class ChoiceTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if( motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                ClipData data = ClipData.newPlainText("","");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;

            }else{
                return false;
            }

        }
    }

    private final class ChoiceDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {

            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    View view = (View) dragEvent.getLocalState();
                    TextView dropTarget = (TextView) v;
                    TextView dropped = (TextView) view;
                    dropTarget.setText(dropped.getText().toString());
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    break;

                default:
                    break;
            }
            return true;

        }
    }


    public void checkAnswer(){

        String blank1 = choice1.getText().toString().trim();
        String blank2 = choice2.getText().toString().trim();
        String blank3 = choice3.getText().toString().trim();
        String blank4 = choice4.getText().toString().trim();
        String blank5 = choice5.getText().toString().trim();
        String blank6 = choice6.getText().toString().trim();

        float result = 0.00f;
        if ((answer5==null) && (answer6==null)){
            if(blank1.equals(answer1)){
                result = result + 3.75f;
            }

            if(blank2.equals(answer2)){
                result = result + 3.75f;
            }

            if(blank3.equals(answer3)){
                result = result + 3.75f;
            }

            if(blank4.equals(answer4)){
                result = result + 3.75f;
            }
        }else if(answer5!=null && (answer6==null)){
            if(blank1.equals(answer1)){
                result = result + 3.00f;
            }

            if(blank2.equals(answer2)){
                result = result + 3.00f;
            }

            if(blank3.equals(answer3)){
                result = result + 3.00f;
            }

            if(blank4.equals(answer4)){
                result = result + 3.00f;
            }

            if(blank5.equals(answer5)){
                result = result + 3.00f;
            }
        }else{
            if(blank1.equals(answer1)){
                result = result + 2.5f;
            }

            if(blank2.equals(answer2)){
                result = result + 2.5f;
            }

            if(blank3.equals(answer3)){
                result = result + 2.5f;
            }

            if(blank4.equals(answer4)){
                result = result + 2.5f;
            }

            if(blank5.equals(answer5)){
                result = result + 2.5f;
            }

            if(blank6.equals(answer6)){
                result = result + 2.5f;
            }
        }
        score = score + result;
    }

    public void changeQuestion(){
        qNumber.setText(String.valueOf(qNo+1));
        textView1.setText(fitbArrayList.get(qNo).getT1());
        textView2.setText(fitbArrayList.get(qNo).getT2());
        textView3.setText(fitbArrayList.get(qNo).getT3());
        textView4.setText(fitbArrayList.get(qNo).getT4());
        textView5.setText(fitbArrayList.get(qNo).getT5());
        textView6.setText(fitbArrayList.get(qNo).getT6());
        textView7.setText(fitbArrayList.get(qNo).getT7());
        answer1 = fitbArrayList.get(qNo).getA1();
        answer2 = fitbArrayList.get(qNo).getA2();
        answer3 = fitbArrayList.get(qNo).getA3();
        answer4 = fitbArrayList.get(qNo).getA4();
        answer5 = fitbArrayList.get(qNo).getA5();
        answer6 = fitbArrayList.get(qNo).getA6();
        option1.setText(fitbArrayList.get(qNo).getO1());
        option2.setText(fitbArrayList.get(qNo).getO2());
        option3.setText(fitbArrayList.get(qNo).getO3());
        option4.setText(fitbArrayList.get(qNo).getO4());
        option5.setText(fitbArrayList.get(qNo).getO5());
        option6.setText(fitbArrayList.get(qNo).getO6());
        option7.setText(fitbArrayList.get(qNo).getO7());
        option8.setText(fitbArrayList.get(qNo).getO8());
        option9.setText(fitbArrayList.get(qNo).getO9());
        qNo++;
        if(textView5!=null){
            textView5.setVisibility(View.VISIBLE);
        }
        if (textView6!=null){
            textView6.setVisibility(View.VISIBLE);
        }
        if (textView7!=null){
            textView7.setVisibility(View.VISIBLE);
        }
        if (answer5!=null){
            choice5.setVisibility(View.VISIBLE);
        }
        if (answer6!=null){
            choice6.setVisibility(View.VISIBLE);
        }
        if (option7!=null){
            option7.setVisibility(View.VISIBLE);
        }
        if (option8!=null){
            option8.setVisibility(View.VISIBLE);
        }
        if (option9!=null){
            option9.setVisibility(View.VISIBLE);
        }

    }

}
