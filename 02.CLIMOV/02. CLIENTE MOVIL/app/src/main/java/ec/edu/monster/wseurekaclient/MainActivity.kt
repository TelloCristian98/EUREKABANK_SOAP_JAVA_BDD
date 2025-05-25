package ec.edu.monster.wseurekaclient

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity // Asegúrate de importar AppCompatActivity aquí
import ec.edu.monster.servicio.DepósitoActivity
import ec.edu.monster.servicio.MovimientoActivity
import ec.edu.monster.servicio.RetiroActivity
import ec.edu.monster.servicio.TransferenciaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<Button>(R.id.btnConsultaMovimiento).setOnClickListener {
            val intent = Intent(this, MovimientoActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnDeposito).setOnClickListener {
            val intent = Intent(this, DepósitoActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnRetiro).setOnClickListener {
            val intent = Intent(this, RetiroActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnTransferencia).setOnClickListener {
            val intent = Intent(this, TransferenciaActivity::class.java)
            startActivity(intent)
        }
    }
}
