package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;


public class Disabler extends Module {
    public static ComboSetting mode;
    private int packetPlayers = 0;

    public enum Mode {
        Vulcan, NCPTp
    }

    public Disabler() {
        super("Disabler", ModuleCategory.world);
        this.registerSetting(mode = new ComboSetting("Mode", Mode.Vulcan));
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        switch ((Mode) mode.getMode()) {
            case Vulcan:
                // autoblock - doesnt work
                if (e.isOutgoing()) {
                    if (e.getPacket() instanceof C17PacketCustomPayload) {
                        e.setCancelled(true);
                    }
                    // strafe
                    if (e.getPacket() instanceof C03PacketPlayer) {
                        packetPlayers++;

                        if (packetPlayers >= 6) {
                            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK,
                                    new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), EnumFacing.DOWN));
                        } else if (packetPlayers == 4) {
                            mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK,
                                    BlockPos.ORIGIN, EnumFacing.DOWN));
                        }
                    }

                }
                break;
            case NCPTp:
                if (e.isOutgoing()) {
                    if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                        mc.thePlayer.addChatMessage(new ChatComponentText("Cancelled C08"));
                        e.setCancelled(true);
                    }
                }
        }
    }
}