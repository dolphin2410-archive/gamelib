package me.dolphin2410.gamelib

import io.github.monun.invfx.openFrame
import me.dolphin2410.gamelib.core.TunnelImpl
import me.dolphin2410.gamelib.game.GameLoaderImpl
import me.dolphin2410.gamelib.game.NoConfigException
import me.dolphin2410.gamelib.warp.WarpItemBuilderImpl
import org.bukkit.Bukkit
import org.bukkit.WorldCreator
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import java.net.URLClassLoader
import java.nio.file.Files

class GameLibCore: JavaPlugin(), Listener {
    private var classLoaders: ArrayList<URLClassLoader>? = ArrayList()

    override fun onEnable() {
        GameLibAPI.builder = {
            WarpItemBuilderImpl()
        }
        GameLibAPI.plugin = this
        GameLibAPI.gameLoader = GameLoaderImpl()
        GameLibAPI.tunnel = TunnelImpl()
        GameLibAPI.tunnel.warpItem.initInventory()

        Files.createDirectories(dataFolder.toPath())

        for(file in dataFolder.listFiles() ?: arrayOf()) {
            val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()), javaClass.classLoader)
            classLoaders!!.add(classLoader)
            val yaml = classLoader.getResource("addon.yml") ?: throw NoConfigException("No addon.yml found in the jar's resource list")
            val config = YamlConfiguration()
            config.loadFromString(yaml.readText())
            val clazz = classLoader.loadClass(config.getString("config.main"))
            val game = GameLibAPI.gameLoader.loadGame(clazz)
            GameLibAPI.tunnel.warpItem.registerGame(game)
        }

        server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        val iter = classLoaders!!.iterator()
        while (iter.hasNext()) {
            val classLoader = iter.next()
            iter.remove()
            classLoader.close()
        }
        classLoaders = null
        GameLibAPI.tunnel.warpItem.games.forEach {
            it.onDisable()
        }
        System.gc()
    }

    @EventHandler
    fun playerJoin(e: PlayerJoinEvent) {
        // e.player.inventory.clear()
        e.player.inventory.setItem(0, GameLibAPI.tunnel.warpItem.item)
    }

    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
        if (e.item == GameLibAPI.tunnel.warpItem.item) {
            e.player.openFrame(GameLibAPI.tunnel.warpItem.inventory)
        }
    }
}