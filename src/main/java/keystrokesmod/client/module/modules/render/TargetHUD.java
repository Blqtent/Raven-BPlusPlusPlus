package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TargetHUD extends Module {
    public static TickSetting editPosition;
    public static int height, width;
    public static FontRenderer fr;
    ScaledResolution sr;
    public TargetHUD() {
        super("Target HUD", ModuleCategory.render);
        sr = new ScaledResolution(Minecraft.getMinecraft());
        height = sr.getScaledHeight();
        width = sr.getScaledWidth();
        fr = mc.fontRendererObj;
    }

    @SubscribeEvent
    public void r(RenderGameOverlayEvent ev) {
        if (ev.type != RenderGameOverlayEvent.ElementType.CROSSHAIRS || !Utils.Player.isPlayerInGame()) return;
        if (mc.currentScreen != null || mc.gameSettings.showDebugInfo) {
            return;
        }
        height = sr.getScaledHeight();
        width = sr.getScaledWidth();
        Gui.drawRect(width - 10, 0,width, height, 0x90000000);
        Gui.drawRect((int)(width * 0.65), (int)(height * 0.65), (int)(width * 0.75), (int)(height * 0.75),0xffff4500);
    }
}
