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

public class DepósitoActivity extends AppCompatActivity {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final String TAG = "DepositoActivity";

    private EditText editTextNumeroCuenta;
    private EditText editTextImporte;
    private Button buttonDepositar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);

        // Vincular elementos de la interfaz de usuario
        editTextNumeroCuenta = findViewById(R.id.editTextNumeroCuenta);
        editTextImporte = findViewById(R.id.editTextImporte);
        buttonDepositar = findViewById(R.id.buttonDepositar);

        // Configurar el botón de depósito
        buttonDepositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los campos
                String numeroCuenta = editTextNumeroCuenta.getText().toString().trim();
                String importeString = editTextImporte.getText().toString().trim();

                if (numeroCuenta.isEmpty() || importeString.isEmpty()) {
                    Toast.makeText(DepósitoActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Convertir el importe a BigDecimal
                        BigDecimal importe = new BigDecimal(importeString);

                        // Registrar el movimiento de depósito
                        registrarDeposito(numeroCuenta, importe);
                    } catch (NumberFormatException e) {
                        Toast.makeText(DepósitoActivity.this, "Importe inválido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void registrarDeposito(final String numeroCuenta, final BigDecimal importe) {
        // Usar un hilo en segundo plano para registrar el depósito en el servicio web
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    // Llamar al servicio web para registrar el movimiento de depósito
                    boolean resultado = SoapClient.regDeposito(numeroCuenta, importe.doubleValue());

                    if (resultado) {
                        Log.d(TAG, "Depósito registrado exitosamente");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mostrarDialogoExito();
                            }
                        });
                    } else {
                        Log.d(TAG, "Depósito no registrado, mostrando diálogo de error");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mostrarDialogoError();
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Error al registrar el depósito: " + e.getMessage(), e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mostrarDialogoError();
                        }
                    });
                }
                return null;
            }
        };

        // Ejecutar la tarea en el hilo de trabajo
        Future<Void> future = executorService.submit(callable);
        try {
            // Esperar a que el proceso termine
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error al ejecutar la tarea: " + e.getMessage(), e);
        }
    }

    private void mostrarDialogoExito() {
        new AlertDialog.Builder(DepósitoActivity.this)
                .setTitle("Depósito Registrado")
                .setMessage("El depósito ha sido registrado correctamente.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();  // Finalizar la actividad
                    }
                })
                .show();
    }

    private void mostrarDialogoError() {
        new AlertDialog.Builder(DepósitoActivity.this)
                .setTitle("Error")
                .setMessage("Hubo un error al registrar el depósito. Intente nuevamente.")
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();  // Liberar los recursos del hilo
    }
}
