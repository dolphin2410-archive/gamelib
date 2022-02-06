package me.dolphin2410.gamelib.land

import org.bukkit.entity.Player

class ArenaQueue(var maxPlayers: Int, val endQueue: (List<Player>)->Unit) {
    var counter = 0
    val list = ArrayList<Player>()

    fun incrementCounter(player: Player) {
        counter++
        list.add(player)
        if (counter == maxPlayers) {
            endQueue(list)
        }
    }

    fun decrementCounter() {
        if (counter > 0) {
            counter--
        }
    }
}