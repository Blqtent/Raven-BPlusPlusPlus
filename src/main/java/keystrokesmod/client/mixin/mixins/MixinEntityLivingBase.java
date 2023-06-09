package keystrokesmod.client.mixin.mixins;

import keystrokesmod.client.event.impl.JumpEvent;
import keystrokesmod.client.main.Raven;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(priority = 1005,value = EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Shadow
    protected abstract float getJumpUpwardsMotion();

    @Shadow
    public abstract PotionEffect getActivePotionEffect(Potion potionIn);

    @Shadow
    public abstract boolean isPotionActive(Potion potionIn);
    /**
     * @author CosmicSC
     * @reason JumpEvent
     */
    @Overwrite
    protected void jump() {
        final JumpEvent e = new JumpEvent(this.rotationYaw, this.getJumpUpwardsMotion());
        Raven.eventBus.post(e);

        if (e.isCancelled()) return;

        this.motionY = e.getMotion();
        if (this.isPotionActive(Potion.jump)) {
            this.motionY += ((float) (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting()) {
            float f = e.getYaw() * 0.017453292F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }

        this.isAirBorne = true;
        //net.minecraftforge.common.ForgeHooks.onLivingJump(this.entity);
    }

}
