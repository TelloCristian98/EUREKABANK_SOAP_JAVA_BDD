package ec.edu.monster.servicio;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ec.edu.monster.wseurekaclient.MainActivity;
import ec.edu.monster.wseurekaclient.R;

public class LoginActivity  extends AppCompatActivity {
    private EditText editTextUsuario;
    private EditText editTextPassword;
    private Button buttonLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsuario = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            if (usuario.equals("Monster") && password.equals("Monster9")) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
