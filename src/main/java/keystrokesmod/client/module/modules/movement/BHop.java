package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.MoveUtil;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

import static keystrokesmod.client.utils.MoveUtil.getSpeed;
import static keystrokesmod.client.utils.MoveUtil.strafe;
import static keystrokesmod.client.utils.Utils.Player.isMoving;

public class BHop extends Module {
    public static SliderSetting a;
    private boolean wasTimer = false;
    private ComboSetting<mode> hopMode;

    public BHop() {
        super("BHop", ModuleCategory.movement);
        this.registerSetting(a = new SliderSetting("Speed", 2.0D, 1.0D, 15.0D, 0.2D));
        this.registerSetting(hopMode = new ComboSetting<>("Mode", mode.Legit));
    }
    public void onDisable() {
        mc.thePlayer.setSprinting(true);
        mc.timer.timerSpeed = 1f;
    }
    public void guiUpdate() {
        a.hideComponent(hopMode.getMode().equals(mode.Blatant));
    }

    @Subscribe
    public void onUpate(UpdateEvent e) {
        switch (hopMode.getMode()) {
            case Blatant:
                Module fly = Raven.moduleManager.getModuleByClazz(Fly.class);
                if (fly != null && !fly.isEnabled() && isMoving() && !mc.thePlayer.isInWater()) {
                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), false);
                    mc.thePlayer.noClip = true;
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                    }

                    mc.thePlayer.setSprinting(true);
                    double spd = 0.0025D * a.getInput();
                    double m = (float) (Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ) + spd);
                    Utils.Player.bop(m);
                }
                break;

            case Legit:
                mc.thePlayer.setSprinting(false);
                if (isMoving()) {
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                     }
                }
                break;
            case Vulcan: {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    MoveUtil.strafe(0.50);
                }
            }
            break;
        }
    }
    public enum mode {
        Blatant, Legit, Vulcan
    }
}