package me.dolphin2410.gamelib.core

import me.dolphin2410.gamelib.api.Tunnel
import me.dolphin2410.gamelib.warp.WarpItem
import me.dolphin2410.gamelib.warp.WarpItemBuilder

class TunnelImpl: Tunnel {
    override var warpItem: WarpItem = WarpItemBuilder.new().build()
        set(value) {
            value.initInventory()
            field = value
        }
}