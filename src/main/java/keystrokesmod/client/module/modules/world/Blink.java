package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.utils.Utils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.LinkedList;

public class Blink extends Module {
    public static

    LinkedList<Packet<?>> beforeblink = new LinkedList<>();
    public Blink() {
        super("Blink", ModuleCategory.world);
    }

    @Override
    public void onEnable() {
        beforeblink.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (e.isOutgoing()) {
            if (e.getPacket() instanceof C03PacketPlayer) { //movement shit
                e.setCancelled(true);
            }
            if (e.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition
                    || e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook
                    || e.getPacket() instanceof C08PacketPlayerBlockPlacement
                    || e.getPacket() instanceof C0APacketAnimation
                    || e.getPacket() instanceof C08PacketPlayerBlockPlacement
                    || e.getPacket() instanceof C02PacketUseEntity
                    || e.getPacket() instanceof C0FPacketConfirmTransaction) {
                beforeblink.add(e.getPacket());
                e.setCancelled(true);
            }
        }
    }
    public void onDisable()
    {
        while(!beforeblink.isEmpty())
            mc.thePlayer.sendQueue.addToSendQueue(beforeblink.poll());
    }
}
