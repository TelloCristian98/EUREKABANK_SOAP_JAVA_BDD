package ec.edu.monster.servicio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.modelo.MovimientoAdapter;
import ec.edu.monster.ws.SoapClient;
import ec.edu.monster.wseurekaclient.R;

public class MovimientoActivity extends AppCompatActivity {

    private static final String TAG = "MovimientoActivity";

    private EditText editTextNumeroCuenta;
    private Button buttonConsultar, buttonBack;
    private RecyclerView recyclerViewMovimientos;
    private TextView textViewError;
    private MovimientoAdapter movimientoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_movimiento);

        editTextNumeroCuenta = findViewById(R.id.editTextNumeroCuenta);
        buttonConsultar = findViewById(R.id.buttonConsultar);
        buttonBack = findViewById(R.id.buttonBack);
        recyclerViewMovimientos = findViewById(R.id.recyclerViewMovimientos);
        textViewError = findViewById(R.id.textViewError);

        // Configurar RecyclerView
        movimientoAdapter = new MovimientoAdapter(new ArrayList<>());
        recyclerViewMovimientos.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMovimientos.setAdapter(movimientoAdapter);

        // Configurar botón Consultar
        buttonConsultar.setOnClickListener(v -> {
            String numeroCuenta = editTextNumeroCuenta.getText().toString().trim();
            if (!numeroCuenta.isEmpty()) {
                new ConsultarMovimientosTask().execute(numeroCuenta);
            } else {
                textViewError.setText("Por favor, ingresa un número de cuenta.");
                textViewError.setVisibility(View.VISIBLE);
            }
        });

        // Configurar botón Volver
        buttonBack.setOnClickListener(v -> finish());
    }

    private class ConsultarMovimientosTask extends AsyncTask<String, Void, List<Movimiento>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewError.setVisibility(View.GONE); // Ocultar errores previos
            textViewError.setText("");
        }

        @Override
        protected List<Movimiento> doInBackground(String... params) {
            String numeroCuenta = params[0];
            try {
                return SoapClient.traerMovimientos(numeroCuenta);
            } catch (Exception e) {
                Log.e(TAG, "Error al consultar movimientos", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movimiento> movimientos) {
            if (movimientos != null && !movimientos.isEmpty()) {
                movimientoAdapter.updateMovimientos(movimientos);
            } else {
                textViewError.setText("No se encontraron movimientos para esta cuenta.");
                textViewError.setVisibility(View.VISIBLE);
            }
        }
    }
}
