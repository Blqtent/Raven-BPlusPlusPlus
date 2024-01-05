package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.PacketEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.KillAura;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("Target HUD", ModuleCategory.render);
    }
    Entity target = KillAura.getTraget();
    @Subscribe
    public void onEnable(PacketEvent pe) {
        if (pe.getPacket() instanceof AttackEntityEvent) {
            ScaledResolution sr = new ScaledResolution(mc);
            FontUtil.normal.drawCenteredSmoothString(target.getName(), (int) (sr.getScaledWidth()/2f+8), (int) (sr.getScaledHeight()/2f-4), 0x99808080);
        }
    }
}
