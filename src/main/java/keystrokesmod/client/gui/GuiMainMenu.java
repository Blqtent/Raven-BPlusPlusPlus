package keystrokesmod.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiMainMenu extends net.minecraft.client.gui.GuiMainMenu {

    @SubscribeEvent
    public void initGui(InitGuiEvent event) {
        super.initGui();

        // Add or modify buttons as needed
        int buttonWidth = 200;
        int buttonHeight = 20;
        int buttonX = this.width / 2 - buttonWidth / 2;
        int buttonY = this.height / 4 + 72;
        this.buttonList.add(new GuiButton(0, buttonX, buttonY, buttonWidth, buttonHeight, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(1, buttonX, buttonY + buttonHeight + 4, buttonWidth, buttonHeight, I18n.format("menu.multiplayer")));
        this.buttonList.add(new GuiButton(2, buttonX, buttonY + (buttonHeight + 4) * 2, buttonWidth, buttonHeight, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(3, buttonX, buttonY + (buttonHeight + 4) * 3, buttonWidth, buttonHeight, I18n.format("menu.quit")));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // Handle button clicks
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        } else if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        } else if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        } else if (button.id == 3) {
            mc.shutdown();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Draw your custom background or modify the rendering if needed
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        drawString(fontRendererObj, "Ravenb+++", width / 4, height / 4 - 40, 0xFFFFFF);
        GL11.glPopMatrix();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}