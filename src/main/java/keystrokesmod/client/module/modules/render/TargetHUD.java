package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.combat.KillAura;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class TargetHUD extends Module {
    public TargetHUD() {
        super("Target HUD", ModuleCategory.render);
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
    }

    @Subscribe
    public void onRender2D(Render2DEvent e) {
    }
}
