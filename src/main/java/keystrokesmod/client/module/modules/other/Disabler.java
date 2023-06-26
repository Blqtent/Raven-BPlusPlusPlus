package keystrokesmod.client.module.modules.other;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.EnumChatFormatting;

import java.util.LinkedList;

public class Disabler extends Module {
    public static DescriptionSetting warning, mmcSafeWarning1, mmcSafeWarning2;
    public static ComboSetting mode;
    public static DoubleSliderSetting mmcSafeDelay;
    public static

    LinkedList<Packet<?>> mmcPackets = new LinkedList<>();
    boolean mmc;

    public Disabler() {
        super("Disabler", ModuleCategory.other);

        this.registerSetting(mode = new ComboSetting("Mode", Mode.None));

    }

    @Override
    public void onEnable() {
        mmcPackets.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        switch ((Mode) mode.getMode()) {
        case None:
            if (e.isOutgoing() && !mmc) {

            }
            break;
        }
    }

    public enum Mode {
        None
    }
}
