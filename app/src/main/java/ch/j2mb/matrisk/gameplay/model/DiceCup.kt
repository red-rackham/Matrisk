package ch.j2mb.matrisk.gameplay.model

import kotlin.random.Random.Default.nextInt

/*
* This class is a random number generator for dice-throws.
* Call the shake functions with two parameters which are the number of dices for player A and player B.
* It returns an 2D IntArray with the results in the IntArray dicesA and dicesB
*/

class diceCup {

    fun shake(a:Int, b:Int): Array<IntArray> {
        var dicesA: IntArray = intArrayOf(a)
        var dicesB: IntArray = intArrayOf(b)


        for(i in 0 until a) {
            dicesA[i] = nextInt(1, 7)

        }
        for(i in 0 until b) {
            dicesB[i] = nextInt(1, 7)

        }

        return  arrayOf(dicesA, dicesB)
    }
}