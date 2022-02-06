package me.dolphin2410.sample

import me.dolphin2410.gamelib.land.Arena
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class GamingArena: Arena(Bukkit.getWorld("world")!!) {
    init {
        maxPlayers = 2
    }

    override fun queueEnd(players: List<Player>) {
        players.forEach {
            it.teleport(world.spawnLocation)
        }
    }
}