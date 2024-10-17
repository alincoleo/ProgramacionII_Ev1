package cl.alincoleo.android.ensayoprueba

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.alincoleo.android.ensayoprueba.modelo.ItemMenu
import cl.alincoleo.android.ensayoprueba.modelo.ItemMesa


class MainActivity : AppCompatActivity() {
    //objetos del menu
    val ramen = ItemMenu("Ramen",6000)
    val yakisoba = ItemMenu("Yakisoba",8000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etCanRamen = findViewById<EditText>(R.id.etCanRamen)


        val textWatcher: TextWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no se usa
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no se usa
            }

            override fun afterTextChanged(p0: Editable?) {
                //enviar total ramen
                calcularSubtotalRamen()
            }
        }
        etCanRamen.addTextChangedListener(textWatcher)
    }
    fun calcularSubtotalRamen(){
        val etCanRamen = findViewById<EditText>(R.id.etCanRamen)
        val cantidadnumeroRamen = etCanRamen.text.toString().toIntOrNull()?:0
        val itemActual = ItemMesa(ramen,cantidadnumeroRamen)
        //tvTotalRamen
        findViewById<TextView>(R.id.tvTotalRamen).text= itemActual.calcularSubtotal().toString()
    }
}