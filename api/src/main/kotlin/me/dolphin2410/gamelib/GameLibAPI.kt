package me.dolphin2410.gamelib

import me.dolphin2410.gamelib.api.Tunnel
import me.dolphin2410.gamelib.game.GameLoader
import me.dolphin2410.gamelib.warp.WarpItemBuilder
import org.bukkit.plugin.java.JavaPlugin

object GameLibAPI {
    lateinit var builder: () -> WarpItemBuilder
    lateinit var tunnel: Tunnel
    lateinit var gameLoader: GameLoader
    lateinit var plugin: JavaPlugin
}