package com.jishin.exercise3jishin;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editConfirmPass;
    private Button btnSignUp;

    public static String TAG = SignUpActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editEmail = (EditText) findViewById(R.id.edt_email);
        editUsername = (EditText) findViewById(R.id.edt_username);
        editPassword = (EditText) findViewById(R.id.edt_password);
        editConfirmPass = (EditText) findViewById(R.id.edt_confirmPassword);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidEmail(editEmail.getEditableText().toString()) || editUsername.getEditableText().toString().isEmpty()
                        || editPassword.getEditableText().toString().isEmpty() || !editConfirmPass.getEditableText().toString().equals(editPassword.getEditableText().toString())
                        || editConfirmPass.getEditableText().toString().isEmpty() || !isConnected() || !isValidPass(editPassword.getEditableText().toString())){
                    String warning = "";
                    if(!isValidEmail(editEmail.getEditableText().toString())){
                        warning += "Invalid Email address\n";
                    }
                    if(editUsername.getEditableText().toString().isEmpty()){
                        warning += "Invalid Username\n";
                    }
                    if(editPassword.getEditableText().toString().isEmpty() || !isValidPass(editPassword.getEditableText().toString())){
                        warning += "Invalid Password\n";
                    }
                    if(!editConfirmPass.getEditableText().toString().equals(editPassword.getEditableText().toString()) || editConfirmPass.getEditableText().toString().isEmpty()){
                        warning += "Invalid Confirm password\n";
                    }
                    if(!isConnected())
                    {
                        warning += "Your device cannot connect to the internet";
                    }
                    Toast.makeText(SignUpActivity.this, warning, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Your device connected to the internet", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, UserInfoActivity.class);
                    intent.putExtra("username", editUsername.getEditableText().toString());
                    intent.putExtra("email", editEmail.getEditableText().toString());
                    intent.putExtra("pass", editPassword.getEditableText().toString());
                    startActivity(intent);
                }
            }
        });

    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    private boolean isValidPass(String pass){
        if(pass.length() <6){
            return false;
        }
        return true;
    }
}
