package cl.alincoleo.android.ensayoprueba.modelo

class CuentaMesa(val mesa:Int) {
    val items = mutableListOf<ItemMesa>()
    var aceptarPropina = true
    //propina 10%

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val agregandoItem = ItemMesa(itemMenu, cantidad)
        items.add(agregandoItem)
    }
    //puede que no se use
    fun agregarItem(itemMesa: ItemMesa){
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina():Int{
        var total = 0
        for (actual in items) {
            total += actual.calcularSubtotal()
        }
        return  total
    }
    fun calcularPropina(): Double {
        return 0.1*calcularTotalSinPropina()
    }
    fun calcularTotalConPropina():Double{
        return calcularPropina()+calcularTotalSinPropina()
    }
}