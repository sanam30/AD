package loh.calvin.imanagead;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText Email;
    private EditText Password;
    private Button Register;

    String userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        UIsettings();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String userEmail = Email.getText().toString().trim();
                    String userPassword = Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUserData();
                                Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterPage.this, LoginPage.class));
                            }
                            else {
                                Toast.makeText(RegisterPage.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void UIsettings(){
        Email = (EditText)findViewById(R.id.Register_Email);
        Password = (EditText)findViewById(R.id.Register_Password);
        Register = (Button)findViewById(R.id.RegisterButton2);
    }

    private Boolean validate(){
        Boolean result = false;

        userEmail = Email.getText().toString();
        userPassword = Password.getText().toString();

        if (userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
        }
        else {
            return true;
        }
        return result;
    }

    private void sendUserData(){
        DatabaseReference ref = firebaseDatabase.getReference().child("Admin").child(firebaseAuth.getCurrentUser().getUid());
        UserProfile userProfile = new UserProfile(userEmail, userPassword);
        ref.setValue(userProfile);

    }
}
