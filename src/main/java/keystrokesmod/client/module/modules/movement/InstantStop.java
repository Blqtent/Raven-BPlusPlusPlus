package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;

public class InstantStop extends Module {
    private int onGroundTicks;
    public InstantStop(){
        super("InstantStop",ModuleCategory.movement);
        this.registerSetting(new DescriptionSetting("Best With WTap"));
        withEnabled(true);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event){
        if (mc.thePlayer.onGround){
            onGroundTicks++;
        } else {
            onGroundTicks = 0;
        }

        if (mc.currentScreen != null) return;

        if (mc.thePlayer.movementInput.moveForward == 0F && mc.thePlayer.movementInput.moveStrafe == 0F && mc.thePlayer.onGround && onGroundTicks >= 5){
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
        }
    }
}