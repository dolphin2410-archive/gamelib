package me.dolphin2410.gamelib.game

interface GameLoader {
    fun loadGame(clazz: Class<out Any>): Game
}