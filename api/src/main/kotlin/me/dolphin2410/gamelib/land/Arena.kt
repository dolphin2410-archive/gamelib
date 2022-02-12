package me.dolphin2410.gamelib.land

import org.bukkit.World
import org.bukkit.entity.Player

abstract class Arena(val world: World) {
    val isFull: Boolean
        get() = arenaQueue.counter == maxPlayers

    var maxPlayers = 20
        set(value) {
            arenaQueue.maxPlayers = value
            field = value
        }

    val arenaQueue = ArenaQueue(maxPlayers) {
        queueEnd(it)
    }

    /**
     * ALWAYS CALL `super.queue()`
     */
    open fun queue(player: Player) {
        arenaQueue.incrementCounter(player)
    }

    abstract fun queueEnd(players: List<Player>)
}