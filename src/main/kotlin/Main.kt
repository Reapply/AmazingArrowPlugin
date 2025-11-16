package me.inspect

import gg.flyte.twilight.twilight
import me.inspect.listener.ArrowListener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        var twilight = twilight(this)

        ArrowListener(this)

        println("HELLO WORLD")
    }

    override fun onDisable() {}
}
