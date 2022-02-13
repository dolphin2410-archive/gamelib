package me.dolphin2410.sample.arena

import me.dolphin2410.gamelib.GameLibAPI
import me.dolphin2410.gamelib.land.Arena
import me.dolphin2410.sample.SampleGame
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.title
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.DisplaySlot
import java.time.Duration
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class GamingArena: Arena(Bukkit.getWorld("world")!!), Listener {
    init {
        maxPlayers = 2
    }

    val players = ArrayList<UUID>()

    val saved = HashMap<UUID, Array<ItemStack?>?>()

    override fun queueEnd(players: List<Player>) {
        Bukkit.getServer().pluginManager.registerEvents(this, GameLibAPI.plugin)
        this.players.addAll(players.map { it.uniqueId })
        var counter = 10
        object: BukkitRunnable() {
            override fun run() {
                players.forEach {
                    it.showTitle(title(text("$counter"), text("")))
                }
                counter--
                if (counter == 0) {
                    arenaQueue.counter = 0
                    players.forEach {
                        it.showTitle(title(text("Game Start"), text(""), Title.Times.of(Duration.ofSeconds(1), Duration.ofSeconds(2), Duration.ofSeconds(1))))
                        createBoard(it).runTaskTimer(GameLibAPI.plugin, 0, 20)
                        it.teleport(world.spawnLocation)
                    }
                    cancel()
                }
            }
        }.runTaskTimer(GameLibAPI.plugin, 0, 20)
    }

    var leftTime = 5400 // 90 min
    var pvpTime = 120 // 2 min

    fun createBoard(player: Player): BukkitRunnable {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective = scoreboard.registerNewObjective("plboard", "dummy", text("Gom UHC"))
        objective.displaySlot = DisplaySlot.SIDEBAR
        objective.getScore("=-=-=-=-=-=-=-=").score = 6
        objective.getScore("Time Left").score = 5

        val teamPvp = scoreboard.registerNewTeam("leftTime")
        teamPvp.addEntry("${ChatColor.GREEN}")
        teamPvp.prefix(text("${leftTime / 60}m"))
        objective.getScore("${ChatColor.GREEN}").score = 4

        objective.getScore("Pvp Time Left").score = 3
        val teamFull = scoreboard.registerNewTeam("pvpLeftTime")
        teamFull.addEntry("${ChatColor.RED}")
        teamFull.prefix(text("${pvpTime / 60}m"))
        objective.getScore("${ChatColor.RED}").score = 2

        player.scoreboard = scoreboard

        return object: BukkitRunnable() {
            override fun run() {
                if (leftTime > 60) {
                    scoreboard.getTeam("leftTime")!!.prefix(text("${leftTime / 60}"))
                } else {
                    scoreboard.getTeam("leftTime")!!.prefix(text("${leftTime}s"))
                }
                if (pvpTime > 60) {
                    scoreboard.getTeam("pvpLeftTime")!!.prefix(text("${pvpTime / 60}"))
                } else {
                    scoreboard.getTeam("pvpLeftTime")!!.prefix(text("${pvpTime}s"))
                }
                leftTime--
                if (pvpTime > 0) {
                    pvpTime--
                }
                if (pvpTime == 0) {
                    SampleGame.pvp = true
                    Bukkit.getOnlinePlayers().forEach {
                        it.sendActionBar(text("Pvp Allowed", NamedTextColor.RED))
                        object: BukkitRunnable() {
                            override fun run() {
                                it.sendActionBar(text(""))
                            }
                        }.runTaskLater(GameLibAPI.plugin, 100)
                    }
                }
                if (leftTime == 0) {
                    cancel()
                    Bukkit.getOnlinePlayers().forEach {
                        it.showTitle(title(text("Time for PVP"), text("teleport to center...")))
                    }
                }
            }
        }
    }

    fun rejoin(player: Player) {
        player.inventory.clear()
        player.inventory.contents = saved[player.uniqueId]
        saved.remove(player.uniqueId)
    }

    @EventHandler
    fun onQuit(e: PlayerQuitEvent) {
        if (players.contains(e.player.uniqueId)) {
            saved[e.player.uniqueId] = e.player.inventory.contents
        }
    }
}