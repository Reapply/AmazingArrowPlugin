package me.inspect.listener

import gg.flyte.twilight.event.event
import me.inspect.nms.CustomCircleArrow
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftLivingEntity
import org.bukkit.entity.Arrow
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.plugin.Plugin

class ArrowListener(plugin: Plugin) {

    init {
        event<EntityShootBowEvent> {
            val shooter = entity
            val proj = projectile
            if (proj !is Arrow) return@event

            isCancelled = true

            val world = (shooter.world as CraftWorld).handle
            val nmsShooter = (shooter as CraftLivingEntity).handle

            val custom = CustomCircleArrow(
                world,
                nmsShooter,
                ItemStack(Items.ARROW)
            )

            custom.setPos(
                shooter.location.x,
                shooter.location.y + 1.5,
                shooter.location.z
            )

            custom.setRot(
                shooter.location.yaw,
                shooter.location.pitch
            )

            world.addFreshEntity(custom)
        }
    }
}
