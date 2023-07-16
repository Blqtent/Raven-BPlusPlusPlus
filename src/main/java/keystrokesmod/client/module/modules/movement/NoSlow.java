package keystrokesmod.client.module.modules.movement;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class NoSlow extends Module {
    public static DescriptionSetting a;
    public static DescriptionSetting c;
    public static SliderSetting b;
    public static TickSetting noReset, swordonly;

    public NoSlow() {
        super("NoSlow", ModuleCategory.movement);
        this.registerSetting(a = new DescriptionSetting("Default is 80% motion reduction."));
        this.registerSetting(c = new DescriptionSetting("Use 'No Reset' on Hypixel."));
        this.registerSetting(b = new SliderSetting("Slow %", 80.0D, 0.0D, 80.0D, 1.0D));
        this.registerSetting(noReset = new TickSetting("No Reset", false));
        this.registerSetting(swordonly = new TickSetting("Sword only", false));
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (noReset.isToggled() || (swordonly.isToggled() && !Utils.Player.isPlayerHoldingWeapon())) {
            if (e.getPacket() instanceof S30PacketWindowItems) {
                if (mc.thePlayer.isUsingItem()) {
                    e.cancel();
                }
            }
        }
    }

}
