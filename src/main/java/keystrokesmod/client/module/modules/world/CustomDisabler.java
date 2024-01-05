package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.network.play.client.*;

public class CustomDisabler extends Module {
    public static TickSetting c00,c02,c03,c04,c05,c06,c07,c08,c09, c0a,c0b,c0c,c0d,c0e,c0f,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19;
    public CustomDisabler() {
        super("CustomDisabler", ModuleCategory.world);
        // custom packets disabler
        this.registerSetting(c00 = new TickSetting("C00PacketKeepAlive", false));
        this.registerSetting(c02 = new TickSetting("C02PacketUseEntity", false));
        this.registerSetting(c03 = new TickSetting("C03PacketPlayer", false));
        this.registerSetting(c04 = new TickSetting("C04PacketPlayerPosition", false));
        this.registerSetting(c05 = new TickSetting("C05PacketPlayerLook", false));
        this.registerSetting(c06 = new TickSetting("C06PacketPlayerPosLook", false));
        this.registerSetting(c07 = new TickSetting("C07PacketPlayerDigging", false));
        this.registerSetting(c08 = new TickSetting("C08PlayerBlockPlacement", false));
        this.registerSetting(c09 = new TickSetting("C09PacketHeldItemChange", false));
        this.registerSetting(c0a = new TickSetting("C0APacketAnimation", false));
        this.registerSetting(c0b = new TickSetting("C0BPacketEntityAction", false));
        this.registerSetting(c0c = new TickSetting("C0CPacketInput", false));
        this.registerSetting(c0d = new TickSetting("C0DPacketCloseWindow", false));
        this.registerSetting(c0e = new TickSetting("C0EPacketClickWindow", false));
        this.registerSetting(c0f = new TickSetting("C0FConfirmTransaction", false));
        this.registerSetting(c10 = new TickSetting("C10PCreativeInventory", false));
        this.registerSetting(c11 = new TickSetting("C11PacketEnchantItem", false));
        this.registerSetting(c12 = new TickSetting("C12PacketUpdateSign", false));
        this.registerSetting(c13 = new TickSetting("C13PacketPlayerAbilities", false));
        this.registerSetting(c14 = new TickSetting("C14PacketTabComplete", false));
        this.registerSetting(c15 = new TickSetting("C15PacketClientSettings", false));
        this.registerSetting(c16 = new TickSetting("C16PacketClientStatus", false));
        this.registerSetting(c17 = new TickSetting("C17PacketCustomPayload", false));
        this.registerSetting(c18 = new TickSetting("C18PacketSpectate", false));
        this.registerSetting(c19 = new TickSetting("C19PacketPackStatus", false));
        // pingspoof soon
    }
    @Subscribe
    public void onPacket(PacketEvent e) {
        // packets start
        if(c00.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C00PacketKeepAlive)
             e.setCancelled(true);
        }
        if(c02.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C02PacketUseEntity)
                e.setCancelled(true);
        }
        if(c03.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C03PacketPlayer)
                e.setCancelled(true);
        }
        if(c04.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition)
                e.setCancelled(true);
        }
        if(c05.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook)
                e.setCancelled(true);
        }
        if(c06.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook)
                e.setCancelled(true);
        }
        if(c07.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C07PacketPlayerDigging)
                e.setCancelled(true);
        }
        if(c08.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C08PacketPlayerBlockPlacement)
                e.setCancelled(true);
        }
        if(c09.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C09PacketHeldItemChange)
                e.setCancelled(true);
        }
        if(c0a.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C0APacketAnimation)
                e.setCancelled(true);
        }
        if(c0b.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C08PacketPlayerBlockPlacement)
                e.setCancelled(true);
        }
        if(c0c.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C0CPacketInput)
                e.setCancelled(true);
        }
        if(c0d.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C0DPacketCloseWindow)
                e.setCancelled(true);
        }
        if(c0e.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C0EPacketClickWindow)
                e.setCancelled(true);
        }
        if(c0f.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C0FPacketConfirmTransaction)
                e.setCancelled(true);
        }
        if(c10.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C10PacketCreativeInventoryAction)
                e.setCancelled(true);
        }
        if(c11.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C11PacketEnchantItem)
                e.setCancelled(true);
        }
        if(c12.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C12PacketUpdateSign)
                e.setCancelled(true);
        }
        if(c13.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C13PacketPlayerAbilities)
                e.setCancelled(true);
        }
        if(c14.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C14PacketTabComplete)
                e.setCancelled(true);
        }
        if(c15.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C15PacketClientSettings)
                e.setCancelled(true);
        }
        if(c16.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C16PacketClientStatus)
                e.setCancelled(true);
        }
        if(c17.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C17PacketCustomPayload)
                e.setCancelled(true);
        }
        if(c18.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C18PacketSpectate)
                e.setCancelled(true);
        }
        if(c19.isToggled()  && e.isOutgoing())
        {
            if(e.getPacket() instanceof C19PacketResourcePackStatus)
                e.setCancelled(true);
        }
        // packets end
    }
}