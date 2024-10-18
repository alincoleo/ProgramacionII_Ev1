package cl.alincoleo.android.ensayoprueba

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.alincoleo.android.ensayoprueba.modelo.CuentaMesa
import cl.alincoleo.android.ensayoprueba.modelo.ItemMenu
import cl.alincoleo.android.ensayoprueba.modelo.ItemMesa
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    // objetos en private para que solo existan en esta clase
    private val ramen = ItemMenu("Ramen",6000)
    private val yakisoba = ItemMenu("Yakisoba",8000)
    private val mesaActual = CuentaMesa(1)
    //aquí creamos los accesos a la aplicaccion tal de no necesitar crearlas constantemente
    private var etCanRamen: EditText? = null
    private var etCanYakisoba: EditText? = null
    private var swPropina : Switch? = null
    //aquí creamos los objetos de los textos a modificar
    private var tvTotalRamen : TextView? = null
    private var tvTotalYakisoba : TextView? = null
    private var tvTotalSinPropina : TextView? = null
    private var tvPropina : TextView? = null
    private var tvTotalAPagar : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //aquí asignamos los botones de la pantalla
        etCanRamen = findViewById<EditText>(R.id.etCanRamen)
        etCanYakisoba = findViewById<EditText>(R.id.etCanYakisoba)
        swPropina = findViewById<Switch>(R.id.switchPropina)
        //aquí los textos a editar
        tvTotalRamen = findViewById<TextView>(R.id.tvTotalRamen)
        tvTotalYakisoba = findViewById<TextView>(R.id.tvTotalYakisoba)
        tvTotalSinPropina = findViewById<TextView>(R.id.tvTotalSinPropina)
        tvPropina = findViewById<TextView>(R.id.tvPropina)
        tvTotalAPagar = findViewById<TextView>(R.id.tvTotalApagar)

        val textWatcher: TextWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no se usa
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //no se usa
            }

            override fun afterTextChanged(p0: Editable?) {
                //enviar a calcular total
                calcularTotales()
            }
        }
        etCanRamen?.addTextChangedListener(textWatcher)
        etCanYakisoba?.addTextChangedListener(textWatcher)
        swPropina?.setOnCheckedChangeListener { compoundButton, b -> calcularTotales() }
    }
    fun calcularTotales(){
        //si no limpio la lista empieza a llenarse de nuevo
        mesaActual.items.clear()
        val cantidadnumeroRamen = etCanRamen?.text.toString().toIntOrNull()?:0
        val itemRamen = ItemMesa(ramen,cantidadnumeroRamen)
        //objeto que permite formatear, se establecen las monedas de chile, sin decimales
        val formatoMoneda= NumberFormat.getCurrencyInstance(Locale("es","CL")).apply { maximumFractionDigits=0 }
        //tvTotalRamen
        tvTotalRamen?.text= formatoMoneda.format(itemRamen.calcularSubtotal())

        val cantidadnumeroYakisoba = etCanYakisoba?.text.toString().toIntOrNull()?:0
        val itemYakisoba = ItemMesa(yakisoba,cantidadnumeroYakisoba)
        tvTotalYakisoba?.text= formatoMoneda.format(itemYakisoba.calcularSubtotal())

        mesaActual.agregarItem(itemRamen)
        mesaActual.agregarItem(itemYakisoba)
        val valorTotalSinPropina = mesaActual.calcularTotalSinPropina()
        var propina = 0
        var valorTotalConPropina = valorTotalSinPropina
        tvTotalSinPropina?.text= "Comida ${formatoMoneda.format(valorTotalSinPropina)}"
        if(swPropina?.isChecked == true){
            propina = mesaActual.calcularPropina().roundToInt()
            valorTotalConPropina= mesaActual.calcularTotalConPropina().roundToInt()
        }
        tvPropina?.text= "Propina ${formatoMoneda.format(propina)}"
        tvTotalAPagar?.text= "Total ${formatoMoneda.format(valorTotalConPropina)}"
    }
}