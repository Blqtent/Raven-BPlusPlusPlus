package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.client.Targets;
import keystrokesmod.client.module.modules.combat.KillAura;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;

public class Sprint extends Module {
    public static TickSetting a, ignoreBlindness, noka,multiDir;


    public Sprint() {
        super("Sprint", ModuleCategory.movement);
        this.registerSetting(ignoreBlindness = new TickSetting("Ignore Blindness", true));
        this.registerSetting(noka = new TickSetting("No Sprint On Killaura", false));
        this.registerSetting(multiDir = new TickSetting("Multi Direction",false));
        a = new TickSetting("OmniSprint", false);
        this.registerSetting(a);
    }

    @Subscribe
    public void p(UpdateEvent e) {
        if (Utils.Player.isPlayerInGame()) {
            if (Raven.moduleManager.getModuleByClazz(InvMove.class).isEnabled() && mc.currentScreen != null) return;
            if (noka.isToggled() && Raven.moduleManager.getModuleByClazz(KillAura.class).isEnabled() && Targets.getTarget() != null) {
                mc.thePlayer.setSprinting(false);
                KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
            } else {
                if (mc.inGameHasFocus) {
                    EntityPlayerSP p = mc.thePlayer;
                    if (a.isToggled()) {
                        if (Utils.Player.isMoving() && p.getFoodStats().getFoodLevel() > 6) {
                            p.setSprinting(true);
                        }
                    } else {
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
                    }
                }
            }
        }
    }

}
