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

public class TransferenciaActivity extends AppCompatActivity {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String TAG = "TransferenciaActivity";

    private EditText editTextCuentaOrigen;
    private EditText editTextCuentaDestino;
    private EditText editTextImporte;
    private Button buttonTransferir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        editTextCuentaOrigen = findViewById(R.id.editTextCuentaOrigen);
        editTextCuentaDestino = findViewById(R.id.editTextCuentaDestino);
        editTextImporte = findViewById(R.id.editTextImporte);
        buttonTransferir = findViewById(R.id.buttonTransferir);

        buttonTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cuentaOrigen = editTextCuentaOrigen.getText().toString().trim();
                String cuentaDestino = editTextCuentaDestino.getText().toString().trim();
                String importeString = editTextImporte.getText().toString().trim();

                if (cuentaOrigen.isEmpty() || cuentaDestino.isEmpty() || importeString.isEmpty()) {
                    Toast.makeText(TransferenciaActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        BigDecimal importe = new BigDecimal(importeString);
                        registrarTransferencia(cuentaOrigen, cuentaDestino, importe);
                    } catch (NumberFormatException e) {
                        Toast.makeText(TransferenciaActivity.this, "Importe inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registrarTransferencia(final String cuentaOrigen, final String cuentaDestino, final BigDecimal importe) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    boolean resultado = SoapClient.regTransferencia(cuentaOrigen, cuentaDestino, importe.doubleValue());
                    if (resultado) {
                        runOnUiThread(() -> mostrarDialogoExito());
                    } else {
                        runOnUiThread(() -> mostrarDialogoError());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error al registrar la transferencia: " + e.getMessage(), e);
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
        new AlertDialog.Builder(TransferenciaActivity.this)
                .setTitle("Transferencia Registrada")
                .setMessage("La transferencia ha sido registrada correctamente.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .show();
    }

    private void mostrarDialogoError() {
        new AlertDialog.Builder(TransferenciaActivity.this)
                .setTitle("Error")
                .setMessage("Hubo un error al registrar la transferencia. Intente nuevamente.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
