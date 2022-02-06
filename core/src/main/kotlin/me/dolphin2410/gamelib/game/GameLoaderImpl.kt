package me.dolphin2410.gamelib.game

class GameLoaderImpl: GameLoader {
    override fun loadGame(clazz: Class<out Any>): Game {
        return clazz.getDeclaredConstructor().newInstance() as Game
    }
}