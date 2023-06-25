package keystrokesmod.client.module.modules.world;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.UpdateEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.ComboSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import static keystrokesmod.client.utils.Utils.Player.isPlayerInGame;
import java.util.ArrayList;

//Taken straight from Quantum coz i don't wanna code the same thing twice and also im lazy
// By Cosmic-SC
public class AntiBot extends Module {
    private static final ComboSetting<MODES> mode = new ComboSetting<>("Mode", MODES.ChecksOnly);
    private final TickSetting remove = new TickSetting("Remove Bots", false);
    private static final TickSetting tab = new TickSetting("TabList Check", false);
    private static final TickSetting name = new TickSetting("Invalid Check", false);
    private static final TickSetting sound = new TickSetting("Sound Check",true);
    public static ArrayList<Entity> bots = new ArrayList<>();

    public AntiBot() {
        super("AntiBot", ModuleCategory.world);
        this.registerSetting(tab);
        this.registerSetting(name);
        this.registerSetting(sound);
        this.registerSetting(remove);
        this.registerSetting(mode);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (!isPlayerInGame()) return;
        try {
            switch (mode.getMode()) {
                case Hypixel:
                    for (Entity entity : mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityPlayer) {
                            if (entity != mc.thePlayer && !((EntityPlayer) entity).isSpectator()) {
                                if (bot((EntityPlayer) entity)) {
                                    if (remove.isToggled()) {
                                        mc.theWorld.removeEntity(entity);
                                    }
                                    bots.add(entity);
                                }
                            } else {
                                bots.remove(entity);
                            }
                        }
                    }
                    break;
                case Advanced:
                    mc.theWorld.playerEntities.forEach(player -> {
                        if (player != mc.thePlayer) {
                            if (mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 200) {
                                bots.remove(player);
                            }

                            if (player.ticksExisted < 5 || player.isInvisible() || mc.thePlayer.getDistanceSq(player.posX, mc.thePlayer.posY, player.posZ) > 100 * 100) {
                                if (!bots.contains(player)) {
                                    if (remove.isToggled()) {
                                        mc.theWorld.removeEntity(player);
                                    }
                                    bots.add(player);
                                }
                            }
                        }
                    });
                    break;
                case ChecksOnly:
                    for (final Entity entity : mc.theWorld.loadedEntityList) {
                        if (entity instanceof EntityPlayer && entity != mc.thePlayer) {
                            if ((dupelicateInTab((EntityPlayer) entity) && tab.isToggled()) || (invalidName(entity) && name.isToggled())) {
                                if (sound.isToggled() && entity.doesEntityNotTriggerPressurePlate()) {
                                    if (remove.isToggled()) {
                                        mc.theWorld.removeEntity(entity);
                                    }
                                }
                            }
                        }
                    }
            }
        } catch (Exception e) {

        }
    }


    public void guiButtonToggled(Setting s){
        if (s == mode){
            bots.clear();
        }
    }

    public static boolean bot(EntityPlayer entity) {
        try {
            if (Raven.moduleManager.getModuleByClazz(AntiBot.class).isEnabled()) {
                if (mode.getMode() == MODES.ChecksOnly) {
                    return (dupelicateInTab(entity) && tab.isToggled()) || (invalidName(entity) && name.isToggled());
                } else {
                    return bots.contains(entity);
                }
            } else {
                return false;
            }
        } catch(Exception e){return false;}
    }

    @Override
    public void onDisable() {
        if (!isPlayerInGame()) return;
        bots.clear();
    }

    public enum MODES {
        Hypixel,
        Advanced,
        ChecksOnly
    }


    static boolean dupelicateInTab(final EntityPlayer entity) {
        return mc.getNetHandler().getPlayerInfoMap().stream().filter((player) -> player != null && entity != null && player.getDisplayName() != null && entity.getDisplayName() != null && player.getDisplayName().getUnformattedText().equals(entity.getDisplayName().getUnformattedText())).count() > 0;
    }

    static boolean invalidName(final Entity e) {
        return e.getName().contains("-") || e.getName().contains("/") || e.getName().contains("_") || e.getName().contains("NPC-") || e.getName().contains("|") || e.getName().contains("<") || e.getName().contains(">") || e.getName().contains("\u0e22\u0e07") || e.getName().equals("");
    }
}
