package keystrokesmod.client.module.modules.player;

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
    boolean mmc;

    public Blink() {
        super("Blink", ModuleCategory.other);
    }

    @Override
    public void onEnable() {
        beforeblink.clear();
    }

    @Subscribe
    public void onPacket(PacketEvent e) {
        if (e.isOutgoing()) {
            if (e.getPacket() instanceof C08PacketPlayerBlockPlacement) {
                beforeblink.add(e.getPacket());
                e.setCancelled(true);
            }
            if (e.getPacket() instanceof C03PacketPlayer) {
                beforeblink.add(e.getPacket());
                e.setCancelled(true);
            }
            if (e.getPacket() instanceof C02PacketUseEntity) {
                beforeblink.add(e.getPacket());
                e.setCancelled(true);
            }
            if (e.getPacket() instanceof C0APacketAnimation) {
                beforeblink.add(e.getPacket());
                e.setCancelled(true);
            }
        }
    }
    public void onDisable()
    {
            mc.thePlayer.sendQueue.addToSendQueue(beforeblink.poll());
    }
}
