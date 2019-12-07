package com.g.firestoretest;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WFITBTestFragment extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MaterialButton btnSolution, btnNext;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    TextView textViewAnswer, textViewSolution;
    TextView textViewQno, textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    String answer1, answer2, answer3, answer4, answer5, answer6;
    String a1,a2,a3,a4,a5,b1,b2,b3,b4,b5,c1,c2,c3,c4,c5,d1,d2,d3,d4,d5,e1,e2,e3,e4,e5,f1,f2,f3,f4,f5;
    ScrollView scrollView;
    private int focus_pos = 1;
    float score;
    int qNo;

    ArrayList<WFITB> wfitbArrayList = new ArrayList<>();


    public WFITBTestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wfitbtest, container, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = view.findViewById(R.id.scroll_view);

        textViewQno = view.findViewById(R.id.text_view_qno);
        textView1 = view.findViewById(R.id.text_view_1);
        textView2 = view.findViewById(R.id.text_view_2);
        textView3 = view.findViewById(R.id.text_view_3);
        textView4 = view.findViewById(R.id.text_view_4);
        textView5 = view.findViewById(R.id.text_view_5);
        textView6 = view.findViewById(R.id.text_view_6);
        textView7 = view.findViewById(R.id.text_view_7);

        spinner1 = view.findViewById(R.id.spinner_1);
        spinner2 = view.findViewById(R.id.spinner_2);
        spinner3 = view.findViewById(R.id.spinner_3);
        spinner4 = view.findViewById(R.id.spinner_4);
        spinner5 = view.findViewById(R.id.spinner_5);
        spinner6 = view.findViewById(R.id.spinner_6);


        textViewAnswer = view.findViewById(R.id.text_view_answer);
        textViewSolution = view.findViewById(R.id.text_view_solution);

        btnSolution = view.findViewById(R.id.btn_solution);
        btnNext = view.findViewById(R.id.btn_next);

        score = 0.00f;
        qNo = 1;

        String testno = WFITBTestFragmentArgs.fromBundle(getArguments()).getTestno();

        db.collection("WFITB").whereEqualTo("testNm", testno).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            for (int i = 0; i < documentSnapshots.size(); i++) {
                                wfitbArrayList.add(documentSnapshots.get(i).toObject(WFITB.class));
                            }
                            setQuestion();
                        }
                    }
                });

        btnSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                textViewAnswer.setVisibility((textViewAnswer.getVisibility() == view.VISIBLE) ? view.GONE : view.VISIBLE);
                textViewSolution.setVisibility((textViewSolution.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
                textViewSolution.setText("1. " + answer1 + "\n2. " + answer2 + " \n3. " + answer3 + "\n4. " + answer4 + "\n5. " + answer5 + "\n6. " + answer6 );

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
            public void onClick(View view) {
                checkAnswer();
                if (qNo<wfitbArrayList.size()){
                    textView5.setVisibility(View.GONE);
                    textView6.setVisibility(View.GONE);
                    textView7.setVisibility(View.GONE);
                    spinner6.setVisibility(View.GONE);
                    spinner5.setVisibility(View.GONE);
                    textViewAnswer.setVisibility(View.GONE);
                    textViewSolution.setVisibility(View.GONE);
                    changeQuestion();

                }else{
                    WFITBTestFragmentDirections.ActionWFITBTestFragmentToWFITBTestResultFragment action = WFITBTestFragmentDirections.actionWFITBTestFragmentToWFITBTestResultFragment();
                    action.setScore(score);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });

    }

    public void setQuestion() {
        if (wfitbArrayList.size() > 0) {
            textViewQno.setText(String.valueOf(qNo));
            textView1.setText(wfitbArrayList.get(0).getT1());
            textView2.setText(wfitbArrayList.get(0).getT2());
            textView3.setText(wfitbArrayList.get(0).getT3());
            textView4.setText(wfitbArrayList.get(0).getT4());
            textView5.setText(wfitbArrayList.get(0).getT5());
            textView6.setText(wfitbArrayList.get(0).getT6());
            textView7.setText(wfitbArrayList.get(0).getT7());
            answer1 = wfitbArrayList.get(0).getAnswer1();
            answer2 = wfitbArrayList.get(0).getAnswer2();
            answer3 = wfitbArrayList.get(0).getAnswer3();
            answer4 = wfitbArrayList.get(0).getAnswer4();
            answer5 = wfitbArrayList.get(0).getAnswer5();
            answer6 = wfitbArrayList.get(0).getAnswer6();
            a1 = wfitbArrayList.get(0).getA1();
            a2 = wfitbArrayList.get(0).getA2();
            a3 = wfitbArrayList.get(0).getA3();
            a4 = wfitbArrayList.get(0).getA4();
            a5 = wfitbArrayList.get(0).getA5();
            b1 = wfitbArrayList.get(0).getB1();
            b2 = wfitbArrayList.get(0).getB2();
            b3 = wfitbArrayList.get(0).getB3();
            b4 = wfitbArrayList.get(0).getB4();
            b5 = wfitbArrayList.get(0).getB5();
            c1 = wfitbArrayList.get(0).getC1();
            c2 = wfitbArrayList.get(0).getC2();
            c3 = wfitbArrayList.get(0).getC3();
            c4 = wfitbArrayList.get(0).getC4();
            c5 = wfitbArrayList.get(0).getC5();
            d1 = wfitbArrayList.get(0).getD1();
            d2 = wfitbArrayList.get(0).getD2();
            d3 = wfitbArrayList.get(0).getD3();
            d4 = wfitbArrayList.get(0).getD4();
            d5 = wfitbArrayList.get(0).getD5();
            e1 = wfitbArrayList.get(0).getE1();
            e2 = wfitbArrayList.get(0).getE2();
            e3 = wfitbArrayList.get(0).getE3();
            e4 = wfitbArrayList.get(0).getE4();
            e5 = wfitbArrayList.get(0).getE5();
            f1 = wfitbArrayList.get(0).getF1();
            f2 = wfitbArrayList.get(0).getF2();
            f3 = wfitbArrayList.get(0).getF3();
            f4 = wfitbArrayList.get(0).getF4();
            f5 = wfitbArrayList.get(0).getF5();

            ArrayList option1 = new ArrayList();
            option1.add(a1);
            option1.add(a2);
            option1.add(a3);
            option1.add(a4);
            option1.add(a5);
            final ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option1);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(adapter1);

            ArrayList option2 = new ArrayList();
            option2.add(b1);
            option2.add(b2);
            option2.add(b3);
            option2.add(b4);
            option2.add(b5);
            final ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option2);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter2);

            ArrayList option3 = new ArrayList();
            option3.add(c1);
            option3.add(c2);
            option3.add(c3);
            option3.add(c4);
            option3.add(c5);
            final ArrayAdapter adapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option3);
            adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner3.setAdapter(adapter3);

            ArrayList option4 = new ArrayList();
            option4.add(d1);
            option4.add(d2);
            option4.add(d3);
            option4.add(d4);
            option4.add(d5);
            final ArrayAdapter adapter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option4);
            adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner4.setAdapter(adapter4);

            if (textView5!=null){
                textView5.setVisibility(View.VISIBLE);
            }
            if (e1!=null){
                spinner5.setVisibility(View.VISIBLE);
                ArrayList option5 = new ArrayList();
                option5.add(e1);
                option5.add(e2);
                option5.add(e3);
                option5.add(e4);
                option5.add(e5);
                final ArrayAdapter adapter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option5);
                adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner5.setAdapter(adapter5);
            }

            if (textView6!=null){
                textView6.setVisibility(View.VISIBLE);
            }
            if (f1!=null){
                spinner6.setVisibility(View.VISIBLE);
                ArrayList option6 = new ArrayList();
                option6.add(f1);
                option6.add(f2);
                option6.add(f3);
                option6.add(f4);
                option6.add(f5);
                final ArrayAdapter adapter6 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option6);
                adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner6.setAdapter(adapter6);
            }

            if (textView7!=null){
                textView7.setVisibility(View.VISIBLE);
            }

        }
    }


    public void checkAnswer(){
        String opt1 = spinner1.getSelectedItem().toString();
        String opt2 = spinner2.getSelectedItem().toString();
        String opt3 = spinner3.getSelectedItem().toString();
        String opt4 = spinner4.getSelectedItem().toString();

        float result = 0;
        if ((e1 == null)&&(f1==null)){
            if (opt1.equals(answer1)){
                result = result + 3.75f;
            }
            if (opt2.equals(answer2)){
                result = result + 3.75f;
            }
            if (opt3.equals(answer3)){
                result = result + 3.75f;
            }
            if (opt4.equals(answer4)){
                result = result + 3.75f;
            }
        }

        if ((e1!=null)&&(f1==null)){
            String opt5 = spinner5.getSelectedItem().toString();
            if (opt1.equals(answer1)){
                result = result + 3.00f;
            }
            if (opt2.equals(answer2)){
                result = result + 3.00f;
            }
            if (opt3.equals(answer3)){
                result = result + 3.00f;
            }
            if (opt4.equals(answer4)){
                result = result + 3.00f;
            }
            if (opt5.equals(answer5)){
                result = result + 3.00f;
            }
        }

        if ((e1!=null)&&(f1!=null)){
            String opt5 = spinner5.getSelectedItem().toString();
            String opt6 = spinner6.getSelectedItem().toString();

            if (opt1.equals(answer1)){
                result = result + 2.50f;
            }
            if (opt2.equals(answer2)){
                result = result + 2.50f;
            }
            if (opt3.equals(answer3)){
                result = result + 2.50f;
            }
            if (opt4.equals(answer4)){
                result = result + 2.50f;
            }
            if (opt5.equals(answer5)){
                result = result + 2.50f;
            }
            if (opt6.equals(answer6)){
                result = result + 2.50f;
            }
        }
        score = score + result;
    }


    public void changeQuestion(){
        textViewQno.setText(String.valueOf(qNo+1));
        textView1.setText(wfitbArrayList.get(qNo).getT1());
        textView2.setText(wfitbArrayList.get(qNo).getT2());
        textView3.setText(wfitbArrayList.get(qNo).getT3());
        textView4.setText(wfitbArrayList.get(qNo).getT4());
        textView5.setText(wfitbArrayList.get(qNo).getT5());
        textView6.setText(wfitbArrayList.get(qNo).getT6());
        textView7.setText(wfitbArrayList.get(qNo).getT7());
        answer1 = wfitbArrayList.get(qNo).getAnswer1();
        answer2 = wfitbArrayList.get(qNo).getAnswer2();
        answer3 = wfitbArrayList.get(qNo).getAnswer3();
        answer4 = wfitbArrayList.get(qNo).getAnswer4();
        answer5 = wfitbArrayList.get(qNo).getAnswer5();
        answer6 = wfitbArrayList.get(qNo).getAnswer6();
        a1 = wfitbArrayList.get(qNo).getA1();
        a2 = wfitbArrayList.get(qNo).getA2();
        a3 = wfitbArrayList.get(qNo).getA3();
        a4 = wfitbArrayList.get(qNo).getA4();
        a5 = wfitbArrayList.get(qNo).getA5();
        b1 = wfitbArrayList.get(qNo).getB1();
        b2 = wfitbArrayList.get(qNo).getB2();
        b3 = wfitbArrayList.get(qNo).getB3();
        b4 = wfitbArrayList.get(qNo).getB4();
        b5 = wfitbArrayList.get(qNo).getB5();
        c1 = wfitbArrayList.get(qNo).getC1();
        c2 = wfitbArrayList.get(qNo).getC2();
        c3 = wfitbArrayList.get(qNo).getC3();
        c4 = wfitbArrayList.get(qNo).getC4();
        c5 = wfitbArrayList.get(qNo).getC5();
        d1 = wfitbArrayList.get(qNo).getD1();
        d2 = wfitbArrayList.get(qNo).getD2();
        d3 = wfitbArrayList.get(qNo).getD3();
        d4 = wfitbArrayList.get(qNo).getD4();
        d5 = wfitbArrayList.get(qNo).getD5();
        e1 = wfitbArrayList.get(qNo).getE1();
        e2 = wfitbArrayList.get(qNo).getE2();
        e3 = wfitbArrayList.get(qNo).getE3();
        e4 = wfitbArrayList.get(qNo).getE4();
        e5 = wfitbArrayList.get(qNo).getE5();
        f1 = wfitbArrayList.get(qNo).getF1();
        f2 = wfitbArrayList.get(qNo).getF2();
        f3 = wfitbArrayList.get(qNo).getF3();
        f4 = wfitbArrayList.get(qNo).getF4();
        f5 = wfitbArrayList.get(qNo).getF5();

        ArrayList option1 = new ArrayList();
        option1.add(a1);
        option1.add(a2);
        option1.add(a3);
        option1.add(a4);
        option1.add(a5);
        final ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayList option2 = new ArrayList();
        option2.add(b1);
        option2.add(b2);
        option2.add(b3);
        option2.add(b4);
        option2.add(b5);
        final ArrayAdapter adapter2 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        ArrayList option3 = new ArrayList();
        option3.add(c1);
        option3.add(c2);
        option3.add(c3);
        option3.add(c4);
        option3.add(c5);
        final ArrayAdapter adapter3 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);

        ArrayList option4 = new ArrayList();
        option4.add(d1);
        option4.add(d2);
        option4.add(d3);
        option4.add(d4);
        option4.add(d5);
        final ArrayAdapter adapter4 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter4);

        if (textView5!=null){
            textView5.setVisibility(View.VISIBLE);
        }
        if (e1!=null){
            spinner5.setVisibility(View.VISIBLE);
            ArrayList option5 = new ArrayList();
            option5.add(e1);
            option5.add(e2);
            option5.add(e3);
            option5.add(e4);
            option5.add(e5);
            final ArrayAdapter adapter5 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option5);
            adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner5.setAdapter(adapter5);
        }

        if (textView6!=null){
            textView6.setVisibility(View.VISIBLE);
        }
        if (f1!=null){
            spinner6.setVisibility(View.VISIBLE);
            ArrayList option6 = new ArrayList();
            option6.add(f1);
            option6.add(f2);
            option6.add(f3);
            option6.add(f4);
            option6.add(f5);
            final ArrayAdapter adapter6 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, option6);
            adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner6.setAdapter(adapter6);
        }

        if (textView7!=null){
            textView7.setVisibility(View.VISIBLE);
        }
        qNo++;

    }

}

