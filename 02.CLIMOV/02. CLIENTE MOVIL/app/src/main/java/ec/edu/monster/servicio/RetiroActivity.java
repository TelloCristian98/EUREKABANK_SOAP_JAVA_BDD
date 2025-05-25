package ec.edu.monster.servicio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.monster.ws.SoapClient;
import ec.edu.monster.wseurekaclient.R;

public class RetiroActivity extends AppCompatActivity {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String TAG = "RetiroActivity";

    private EditText editTextNumeroCuenta;
    private EditText editTextImporte;
    private Button buttonRetirar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);

        editTextNumeroCuenta = findViewById(R.id.editTextNumeroCuenta);
        editTextImporte = findViewById(R.id.editTextImporte);
        buttonRetirar = findViewById(R.id.buttonRetirar);

        buttonRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroCuenta = editTextNumeroCuenta.getText().toString().trim();
                String importeString = editTextImporte.getText().toString().trim();

                if (numeroCuenta.isEmpty() || importeString.isEmpty()) {
                    Toast.makeText(RetiroActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        BigDecimal importe = new BigDecimal(importeString);
                        registrarRetiro(numeroCuenta, importe);
                    } catch (NumberFormatException e) {
                        Toast.makeText(RetiroActivity.this, "Importe inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registrarRetiro(final String numeroCuenta, final BigDecimal importe) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    boolean resultado = SoapClient.regRetiro(numeroCuenta, importe.doubleValue());
                    if (resultado) {
                        runOnUiThread(() -> mostrarDialogoExito());
                    } else {
                        runOnUiThread(() -> mostrarDialogoError());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al registrar el retiro: " + e.getMessage(), e);
                    runOnUiThread(() -> mostrarDialogoError());
                }
                return null;
            }
        };

        Future<Void> future = executorService.submit(callable);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error al ejecutar la tarea: " + e.getMessage(), e);
        }
    }

    private void mostrarDialogoExito() {
        new AlertDialog.Builder(RetiroActivity.this)
                .setTitle("Retiro Registrado")
                .setMessage("El retiro ha sido registrado correctamente.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }

    private void mostrarDialogoError() {
        new AlertDialog.Builder(RetiroActivity.this)
                .setTitle("Error")
                .setMessage("Hubo un error al registrar el retiro. Intente nuevamente.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
