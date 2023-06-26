package keystrokesmod.client.utils;

import com.google.common.base.Predicates;
import keystrokesmod.client.module.modules.combat.KillAura;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;

import java.util.List;

import static keystrokesmod.client.utils.Utils.mc;

public class CombatUtils {
    public static boolean canTarget(Entity entity, boolean idk) {
        if (entity != null && entity != Minecraft.getMinecraft().thePlayer) {
            EntityLivingBase entityLivingBase = null;

            if (entity instanceof EntityLivingBase) {
                entityLivingBase = (EntityLivingBase) entity;
            }

            boolean isTeam = isTeam(Minecraft.getMinecraft().thePlayer, entity);
            boolean isVisible = (!entity.isInvisible());

            return !(entity instanceof EntityArmorStand) && isVisible
                    && (entity instanceof EntityPlayer && !isTeam && !idk || entity instanceof EntityAnimal
                            || entity instanceof EntityMob
                            || entity instanceof EntityLivingBase && entityLivingBase.isEntityAlive());
        } else {
            return false;
        }
    }

    public static boolean isTeam(EntityPlayer player, Entity entity) {
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).getTeam() != null && player.getTeam() != null) {
            Character entity_3 = entity.getDisplayName().getFormattedText().charAt(3);
            Character player_3 = player.getDisplayName().getFormattedText().charAt(3);
            Character entity_2 = entity.getDisplayName().getFormattedText().charAt(2);
            Character player_2 = player.getDisplayName().getFormattedText().charAt(2);
            boolean isTeam = false;
            if (entity_3.equals(player_3) && entity_2.equals(player_2)) {
                isTeam = true;
            } else {
                Character entity_1 = entity.getDisplayName().getFormattedText().charAt(1);
                Character player_1 = player.getDisplayName().getFormattedText().charAt(1);
                Character entity_0 = entity.getDisplayName().getFormattedText().charAt(0);
                Character player_0 = player.getDisplayName().getFormattedText().charAt(0);
                if (entity_1.equals(player_1) && Character.isDigit(0) && entity_0.equals(player_0)) {
                    isTeam = true;
                }
            }

            return isTeam;
        } else {
            return true;
        }
    }
    public static Entity raycastEntity(final double range, final IEntityFilter entityFilter) {
        return raycastEntity(range, KillAura.yaw, KillAura.pitch,
                entityFilter);
    }
    public static boolean canEntityBeSeen(Entity entityIn) {
        EntityPlayer p = mc.thePlayer;
        return mc.theWorld.rayTraceBlocks(new Vec3(p.posX, p.posY + (double) p.getEyeHeight(), p.posZ),
                new Vec3(entityIn.posX, entityIn.posY + (double) entityIn.getEyeHeight(), entityIn.posZ), false) == null;
    }
    private static Entity raycastEntity(final double range, final float yaw, final float pitch, final IEntityFilter entityFilter) {
        final Entity renderViewEntity = mc.getRenderViewEntity();

        if (renderViewEntity != null && mc.theWorld != null) {
            double blockReachDistance = range;
            final Vec3 eyePosition = renderViewEntity.getPositionEyes(1F);

            final float yawCos = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292F);

            final Vec3 entityLook = new Vec3(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            final Vec3 vector = eyePosition.addVector(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance);
            final List<Entity> entityList = mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance).expand(1D, 1D, 1D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));

            Entity pointedEntity = null;

            for (final Entity entity : entityList) {
                if (!entityFilter.canRaycast(entity))
                    continue;

                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);

                if (axisAlignedBB.isVecInside(eyePosition)) {
                    if (blockReachDistance >= 0.0D) {
                        pointedEntity = entity;
                        blockReachDistance = 0.0D;
                    }
                } else if (movingObjectPosition != null) {
                    final double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);

                    if (eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
                        if (entity == renderViewEntity.ridingEntity && !renderViewEntity.canRiderInteract()) {
                            if (blockReachDistance == 0.0D)
                                pointedEntity = entity;
                        } else {
                            pointedEntity = entity;
                            blockReachDistance = eyeDistance;
                        }
                    }
                }
            }

            return pointedEntity;
        }

        return null;
    }

    public interface IEntityFilter {
        boolean canRaycast(final Entity entity);
    }
}
