package me.dolphin2410.gamelib.abiity

import org.bukkit.event.Event

interface Ability {
    var isGlobalAbility: Boolean
    fun handleEvent(e: Event)
    fun onInit()
}