package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.CoolDown;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

public class VulcantBHop extends Module {
    private Minecraft mc = Minecraft.getMinecraft();
    
    public VulcantBHop() {
        super("Vulcan BHop (Credit goes to Blqtent)", ModuleCategory.movement);
    }
    // Subscribe to https://www.youtube.com/channel/UCa1M9UnJX7IGJMbmAmLbDFQ for epic BHop
    @Subscribe
    public void onTick(TickEvent e) {
        if (wasTimer) {
            mc.timer.timerSpeed = 1.00f;
            wasTimer = false;
        }
        if (Math.abs(Module.mc.thePlayer.movementInput.moveStrafe) < 0.1f) {
            mc.thePlayer.jumpMovementFactor = 0.026499f;
        } else {
            mc.thePlayer.jumpMovementFactor = 0.0244f;
        }
        mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);

        if (getSpeed() < 0.215f && !mc.thePlayer.onGround) {
            strafe(0.215f);
        }
        if (mc.thePlayer.onGround && isMoving()) {
            mc.gameSettings.keyBindJump.pressed = false;
            mc.thePlayer.jump();
            if (!mc.thePlayer.isAirBorne) {
                return;
            }
            mc.timer.timerSpeed = 1.25f;
            wasTimer = true;
            strafe();
            if (getSpeed() < 0.5f) {
                strafe(0.4849f);
            }
        } else if (!isMoving()) {
            mc.timer.timerSpeed = 1.00f;
        }        
    }

    public static float getPlayerDirection() {
        // start with our current yaw
        float yaw = mc.thePlayer.rotationYaw;
        float strafe = 45;
        // add 180 to the yaw to strafe backwards
        if(mc.thePlayer.moveForward < 0){
            // invert our strafe to -45
            strafe = -45;
            yaw += 180;
        }
        if (mc.thePlayer.moveStrafing > 0) {
            // subtract 45 to strafe left forward
            yaw -= strafe;
            // subtract an additional 45 if we do not press W in order to get to -90
            if (mc.thePlayer.moveForward == 0) {
                yaw -= 45;
            }
        } else if (mc.thePlayer.moveStrafing < 0) {
            // add 45 to strafe right forward
            yaw += strafe;
            // add 45 if we do not press W in order to get to 90
            if (mc.thePlayer.moveForward == 0) {
                yaw += 45;
            }
        }
        return yaw;
    }
    
    public void strafe(final float speed) {
        if (!isMoving()) return;

        final double yaw = getDirection();

        mc.thePlayer.motionX = -MathHelper.sin((float) yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos((float) yaw) * speed;
    }
    
    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }
    
}
