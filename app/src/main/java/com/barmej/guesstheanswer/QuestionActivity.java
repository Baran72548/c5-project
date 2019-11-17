package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuestionActivity extends AppCompatActivity {

    private String[] QUESTIONS;

    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false };

    private String[] ANSWERS_DETAILS;

    private TextView mTextViewQuestions;
    private String mCurrentQuestion, mCurrentQuestionDetail;
    private boolean mCurrentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        String appLang = sharedPreferences.getString("app_lang", "");
        //Use device's default language if there is no saved language.
        if (!appLang.equals(""))
            LocaleHelper.setLocale(this, appLang);

        setContentView(R.layout.activity_main);
        mTextViewQuestions = findViewById(R.id.text_view_question);
        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS = getResources().getStringArray(R.array.answers_details);
        showNewQuestion();
    }

    //Add menu to action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuChangeLang){
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    //This method will create an alert dialog and assign our menu to it.
    private void showLanguageDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String language = "ar";
                        switch (i) {
                            case (0):
                                language = "ar";
                                break;
                            case (1):
                                language = "en";
                                break;
                            case (2):
                                language = "fr";
                                break;
                        }
                        //Call savelanguage() method in case the user exit the application.
                        saveLanguage(language);
                        LocaleHelper.setLocale(QuestionActivity.this, language);
                        //Use Intent to be sure selected language is the main language of app.
                        //FLAG_ACTIVITY_CLEAR_TOP will delete all previous activities.
                        //FLAG_ACTIVITY_NEW_TASK will add the new activity.
                        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }

    //This method will be called after choosing the language to save it as the chosen language of user.
    private void saveLanguage(String lang){
        SharedPreferences sharedPreferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("app_lang", lang);
        editor.apply();
    }

    private void showNewQuestion() {
        Random random = new Random();
        int randomQuestionIndex = random.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomQuestionIndex];
        mCurrentAnswer = ANSWERS[randomQuestionIndex];
        mCurrentQuestionDetail = ANSWERS_DETAILS[randomQuestionIndex];
        mTextViewQuestions.setText(mCurrentQuestion);
    }

    public void onChangeQuestionClicked(View view) {
        showNewQuestion();
    }

    public void onTrueClicked(View view) {
        if (mCurrentAnswer == true){
            Toast.makeText(this, "إجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "إجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
            intent.putExtra("question_answer", mCurrentQuestionDetail);
            startActivity(intent);
        }
    }

    public void onFalseClicked(View view) {
        if (mCurrentAnswer == false){
            Toast.makeText(this, "إجابة صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        } else {
            Toast.makeText(this, "إجابة خاطئة", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
            intent.putExtra("question_answer", mCurrentQuestionDetail);
            startActivity(intent);
        }
    }

    public void onShareQuestionClicked(View view) {
        Intent intent = new Intent(QuestionActivity.this, ShareActivity.class);
        intent.putExtra("question text extra", mCurrentQuestion);
        startActivity(intent);
    }
}
