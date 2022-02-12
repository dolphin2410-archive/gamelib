package me.dolphin2410.sample

import io.github.monun.invfx.openFrame
import io.github.monun.kommand.kommand
import me.dolphin2410.gamelib.GameLibAPI

object GameCommands {
    fun register() {
        GameLibAPI.plugin.kommand {
            register("gomuhc") {
                requires { isPlayer }
                then("help") {
                    executes {
                        player.openFrame(Help.mainFrame(player))
                    }
                }

                then("rejoin") {
                    executes {
                        SampleGame.running.forEach {
                            if (it.players.contains(player.uniqueId)) {
                                if (it.saved.containsKey(player.uniqueId)) {
                                    it.rejoin(player)
                                } else {
                                    player.sendMessage("You are already playing that game")
                                }
                                return@forEach
                            }
                        }
                        player.sendMessage("You aren't playing any games")
                    }
                }
            }
        }
    }
}