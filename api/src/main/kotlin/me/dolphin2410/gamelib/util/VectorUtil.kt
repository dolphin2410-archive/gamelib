package me.dolphin2410.gamelib.util

import org.bukkit.util.Vector

object VectorUtil {
    fun Vector.perpendicular(): Vector {
        return Vector(z, y, -x)
    }

    fun Vector.position(index: Int): Vector {
        val normalized = clone().normalize()
        return clone().add(normalized.multiply(index))
    }
}