package ch.j2mb.matrisk.gameplay.model

class Country(val name:String, val xPosition:Int, val yPosition:Int ) {
    var owner:Player? = null
    var troops:Int = 0

    //For extended functionality to play with continent capitals
    //var capital:Boolean = false


}