package me.inspect.nms

import net.minecraft.world.entity.projectile.Arrow
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import kotlin.math.cos
import kotlin.math.sin

class CustomCircleArrow(
    level: Level,
    shooter: LivingEntity,
    pickup: ItemStack
) : Arrow(level, shooter, pickup, null) {

    private var t = 0.0
    private val radius = 1.5
    private val speed = 0.35

    override fun tick() {
        super.tick()

        if (this.isInGround) return

        t += 0.25
        val dx = cos(t) * radius
        val dz = sin(t) * radius

        this.setDeltaMovement(dx * speed, this.deltaMovement.y, dz * speed)
        this.hasImpulse = true
    }
}

