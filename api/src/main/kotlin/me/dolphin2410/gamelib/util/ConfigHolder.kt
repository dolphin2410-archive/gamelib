package me.dolphin2410.gamelib.util

class ConfigHolder<K, V> {
    val map = HashMap<K, V>()

    fun set(key: K, value: V) {
        map[key] = value
    }

    fun get(key: K): V {
        return getOrNull(key)!!
    }

    fun getOrNull(key: K): V? {
        return map[key]
    }

    fun has(key: K): Boolean {
        return map.containsKey(key)
    }
}