package com.apps.dafz.learntocook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.apps.dafz.learntocook.helpers.JsonFile;
import com.apps.dafz.learntocook.helpers.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Quiz extends MainActivity {

    private TextView mTitle;
    private TextView mQ;
    private RadioGroup mButtonGroup;
    private RadioButton mAns1;
    private RadioButton mAns2;
    private RadioButton mAns3;
    private TextView mResult;
    private TextView mReason;

    private ArrayList<Question> questions = new ArrayList<Question>();

    private Button mNextButton;

    private int currentQuestion = 0;
    private int nextQuestion = 1;
    private int correctCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(7).setChecked(true);

        JSONArray json = new JsonFile(this, "quiz.json").toArray();

        for(int i = 0; i <= json.length(); i++) {
            try {
                questions.add(new Question(json.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("Quiz Array:", questions.get(2).getTitle());

        mTitle = (TextView) findViewById(R.id.Title);
        mQ = (TextView) findViewById(R.id.Q);
        mAns1 = (RadioButton) findViewById(R.id.Ans1);
        mAns2 = (RadioButton) findViewById(R.id.Ans2);
        mAns3 = (RadioButton) findViewById(R.id.Ans3);
        mResult = (TextView) findViewById(R.id.Result);
        mReason = (TextView) findViewById(R.id.Reason);

        mButtonGroup = (RadioGroup) findViewById(R.id.buttonGroup);
        mNextButton = (Button) findViewById(R.id.nextButton);

        setQuestion(currentQuestion);

        setupListeners();
    }

    public void setQuestion(int position) {
        mTitle.setText(questions.get(position).getTitle());
        mQ.setText(questions.get(position).getQ());
        mAns1.setText(questions.get(position).getAns1());
        mAns2.setText(questions.get(position).getAns2());
        mAns3.setText(questions.get(position).getAns3());

        mButtonGroup.clearCheck();
        mAns1.setEnabled(true);
        mAns2.setEnabled(true);
        mAns3.setEnabled(true);
        mResult.setText(" ");
        mReason.setText(" ");

        nextQuestion = currentQuestion + 1;

    }

    public void setupListeners() {
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentQuestion == 9) {
                    Intent intent = new Intent(Quiz.this, QuizComplete.class);
                    intent.putExtra("CorrectCount", Integer.toString(correctCount));
                    startActivity(intent);
                } else {
                    currentQuestion += 1;
                    setQuestion(currentQuestion);
                }
            }
        });

        mButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(checkedId);

                if (currentQuestion != nextQuestion) {
                    if (radioButton != null) {
                        Question question = questions.get(currentQuestion);

                        String a = radioButton.getText().toString();
                        String b = question.getCorrect();

                        boolean pass = Objects.equals(a, b);

                        Log.d("PASSED QUESTION", Boolean.toString(pass));

                        if (pass) {
                            mResult.setText("Correct!!");

                            correctCount += 1;
                        } else {
                            mResult.setText("WRONG!! :(");
                        }

                        mReason.setText(question.getReason());
                        Log.d("CorrectCount", Integer.toString(correctCount));
                    }
                }

                mAns1.setEnabled(false);
                mAns2.setEnabled(false);
                mAns3.setEnabled(false);
            }

        });
    }



}
