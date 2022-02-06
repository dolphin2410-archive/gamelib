package me.dolphin2410.gamelib.game

import me.dolphin2410.gamelib.util.Icon
import org.bukkit.entity.Player

abstract class Game {
    open fun onInit() {

    }

    open fun onDisable() {

    }

    fun hasIcon() = ::icon.isInitialized

    abstract fun joinPlayer(player: Player)

    lateinit var name: String

    lateinit var icon: Icon

}