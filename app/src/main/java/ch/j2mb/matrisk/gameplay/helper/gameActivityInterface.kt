package ch.j2mb.matrisk.gameplay.helper


interface gameActivityInterface {
    fun getAttackFragment()
    fun getRelocationFragment()
    fun getReinforcementFragment()
    fun toastIt(bread: String)
    fun setReinforcement(countrySelected: String, troops: Int)
    fun attack(source: String, target: String, troops: Int)
    fun updateButtons()

}