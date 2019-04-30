package loh.calvin.imanagead;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    private EditText Password, Email;
    private Button Login, Register;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        UIsettings();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, RegisterPage.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Logging in... Please wait a moment");
                progressDialog.show();
                if(Email.getText().toString().isEmpty()||Password.getText().toString().isEmpty()){
                    Toast.makeText(LoginPage.this,"Please fill in the detail",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    validate(Email.getText().toString(), Password.getText().toString());
                }
            }
        });
    }

    private void UIsettings(){
        Email = (EditText)findViewById(R.id.Login_Email);
        Password = (EditText)findViewById(R.id.Login_Password);
        Login = (Button)findViewById(R.id.LoginButton);
        Register = (Button)findViewById(R.id.RegisterButton);
    }

    private void validate(String userEmail, String userPassword){
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this, MainPage.class));
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(LoginPage.this,"Login Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}
