package keystrokesmod.client.mixin.mixins;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.combat.aura.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Final
    @Shadow
    private Minecraft mc;
    @Shadow
    private float equippedProgress;

    @Shadow
    protected abstract void transformFirstPersonItem(float equipProgress, float swingProgress);

    @Shadow
    protected abstract void func_178101_a(float angle, float angleY);

    @Shadow
    protected abstract void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress);

    @Shadow
    private float prevEquippedProgress;

    @Shadow
    protected abstract void func_178109_a(AbstractClientPlayer clientPlayer);

    @Shadow
    private ItemStack itemToRender;

    @Shadow
    protected abstract void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks);

    @Shadow
    protected abstract void func_178104_a(AbstractClientPlayer clientPlayer, float partialTicks);


    @Shadow
    protected abstract void func_178098_a(float partialTicks, AbstractClientPlayer clientPlayer);

    @Shadow
    protected abstract void func_178105_d(float swingProgress);

    @Shadow
    public abstract void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform);

    @Shadow
    protected abstract void func_178095_a(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress);


    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemModelForEntity(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;)V"))
    public void renderItem(EntityLivingBase entity, ItemStack item, ItemCameraTransforms.TransformType transformType, CallbackInfo ci) {
        try {
            if (!(item.getItem() instanceof ItemSword)) return;
            if (!(entity instanceof EntityPlayer)) return;
            if (!(((EntityPlayer) entity).getItemInUseCount() > 0)) return;
            if (!(item.getItemUseAction() == EnumAction.BLOCK)) return;
            if (transformType != ItemCameraTransforms.TransformType.THIRD_PERSON) return;
            GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(-0.04F, -0.04F, 0.0F);
        } catch (Exception e) {
        }
    }
    private void doSwordBlockAnimation() {
        GlStateManager.translate(-0.5F, 0.4F, -0.1F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    /**
     * @author Cosmic
     * @reason fake autoblock
     */

    @Overwrite
    public void renderItemInFirstPerson(float partialTicks) {
        try {
            float f = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
            EntityPlayerSP player = this.mc.thePlayer;
            float f1 = player.getSwingProgress(partialTicks);
            float f2 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
            float f3 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks;
            this.func_178101_a(f2, f3);
            this.func_178109_a(player);
            this.func_178110_a(player, partialTicks);
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            if (this.itemToRender != null) {
                if (this.itemToRender.getItem() instanceof ItemMap) {
                    this.renderItemMap(player, f2, f, f1);
                } else if (player.getItemInUseCount() > 0 || (itemToRender.getItem() instanceof ItemSword && (KillAura.blockMode.getMode() == KillAura.BlockMode.Fake || KillAura.blockMode.getMode() == KillAura.BlockMode.Legit) && KillAura.getTraget() != null && Raven.moduleManager.getModuleByClazz(KillAura.class).isEnabled())) {
                    //else if (player.getItemInUseCount() > 0) {
                    EnumAction action = this.itemToRender.getItemUseAction();
                    switch (action) {
                        case NONE:
                            this.transformFirstPersonItem(f, 0.0F);
                            break;
                        case EAT:
                        case DRINK:
                            this.func_178104_a(player, partialTicks);
                            this.transformFirstPersonItem(f, f1);
                            break;
                        case BLOCK:
                            this.transformFirstPersonItem(f, f1);
                            this.doSwordBlockAnimation(); //better 1.7 animation from sk1ers old animations mod
                            break;
                        case BOW:
                            this.transformFirstPersonItem(f, f1);
                            this.func_178098_a(partialTicks, player);
                    }
                } else {
                    this.func_178105_d(f1);
                    this.transformFirstPersonItem(f, f1);
                }

                this.renderItem(player, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            } else if (!player.isInvisible()) {
                this.func_178095_a(player, f, f1);
            }

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        } catch (Exception e) {
        }
    }

}
