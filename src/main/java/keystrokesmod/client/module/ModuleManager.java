package keystrokesmod.client.module;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module.ModuleCategory;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.modules.client.FPSSpoofer;
import keystrokesmod.client.module.modules.client.FakeHud;
import keystrokesmod.client.module.modules.client.GuiModule;
import keystrokesmod.client.module.modules.client.SelfDestruct;
import keystrokesmod.client.module.modules.client.Targets;
import keystrokesmod.client.module.modules.client.Terminal;
import keystrokesmod.client.module.modules.client.UpdateCheck;
import keystrokesmod.client.module.modules.combat.*;
import keystrokesmod.client.module.modules.config.ConfigSettings;
import keystrokesmod.client.module.modules.minigames.BedwarsOverlay;
import keystrokesmod.client.module.modules.minigames.BridgeInfo;
import keystrokesmod.client.module.modules.minigames.DuelsStats;
import keystrokesmod.client.module.modules.minigames.MurderMystery;
import keystrokesmod.client.module.modules.minigames.SumoFences;
import keystrokesmod.client.module.modules.movement.*;
import keystrokesmod.client.module.modules.other.Disabler;
import keystrokesmod.client.module.modules.other.FakeChat;
import keystrokesmod.client.module.modules.other.MiddleClick;
import keystrokesmod.client.module.modules.other.NameHider;
import keystrokesmod.client.module.modules.other.Spin;
import keystrokesmod.client.module.modules.other.WaterBucket;
import keystrokesmod.client.module.modules.player.AutoArmour;
import keystrokesmod.client.module.modules.player.AutoJump;
import keystrokesmod.client.module.modules.player.AutoPlace;
import keystrokesmod.client.module.modules.player.AutoTool;
import keystrokesmod.client.module.modules.player.BedAura;
import keystrokesmod.client.module.modules.player.BridgeAssist;
import keystrokesmod.client.module.modules.player.ChestStealer;
import keystrokesmod.client.module.modules.player.FastPlace;
import keystrokesmod.client.module.modules.player.Freecam;
import keystrokesmod.client.module.modules.player.NoFall;
import keystrokesmod.client.module.modules.player.Parkour;
import keystrokesmod.client.module.modules.player.RightClicker;
import keystrokesmod.client.module.modules.player.SafeWalk;
import keystrokesmod.client.module.modules.render.*;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.modules.world.ChatLogger;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.FontRenderer;

public class ModuleManager {
    private List<Module> modules = new ArrayList<>();

    public static boolean initialized;
    public GuiModuleManager guiModuleManager;

    public ModuleManager() {
        System.out.println(ModuleCategory.values());
        if(initialized)
            return;
        this.guiModuleManager = new GuiModuleManager();
        addModule(new ChestStealer());
        addModule(new AutoArmour());
        addModule(new LeftClicker());
        addModule(new RightClicker());
        addModule(new AimAssist());
        addModule(new ClickAssist());
        addModule(new Criticals());
        addModule(new DelayRemover());
        addModule(new HitBox());
        addModule(new Reach());
        addModule(new Velocity());
        addModule(new Boost());
        addModule(new Fly());
        addModule(new InstantStop());
        addModule(new InvMove());
        addModule(new KeepSprint());
        addModule(new NoSlow());
        addModule(new Sprint());
        addModule(new StopMotion());
        addModule(new LegitSpeed());
        addModule(new Timer());
        addModule(new VClip());
        addModule(new AutoJump());
        addModule(new AutoPlace());
        addModule(new BedAura());
        addModule(new FastPlace());
        addModule(new Freecam());
        addModule(new NoFall());
        addModule(new SafeWalk());
        addModule(new AntiBot());
        addModule(new AntiShuffle());
        addModule(new Chams());
        addModule(new ChestESP());
        addModule(new Nametags());
        addModule(new PlayerESP());
        addModule(new Tracers());
        addModule(new HUD());
        addModule(new BridgeInfo());
        addModule(new DuelsStats());
        addModule(new MurderMystery());
        addModule(new SumoFences());
        addModule(new SlyPort());
        addModule(new FakeChat());
        addModule(new NameHider());
        addModule(new WaterBucket());
        addModule(new Terminal());
        addModule(new GuiModule());
        addModule(new SelfDestruct());
        addModule(new ChatLogger());
        addModule(new BridgeAssist());
        addModule(new Fullbright());
        addModule(new UpdateCheck());
        addModule(new AutoHeader());
        addModule(new BHop());
        addModule(new AutoTool());
        addModule(new WTap());
        addModule(new BlockHit());
        addModule(new STap());
        addModule(new AutoWeapon());
        addModule(new BedwarsOverlay());
        addModule(new FPSSpoofer());
        addModule(new AutoBlock());
        addModule(new MiddleClick());
        addModule(new Projectiles());
        addModule(new FakeHud());
        addModule(new ConfigSettings());
        addModule(new Parkour());
        addModule(new Disabler());
        addModule(new JumpReset());
        addModule(new KillAura());
        addModule(new Spin());
        addModule(new AutoGHead());
        addModule(new AutoSoup());
        addModule(new Targets());
        addModule(new TargetHUD());
        initialized = true;
    }

    public void addModule(Module m) {
        modules.add(m);
    }

    public void removeModuleByName(String s) {
        Module m = getModuleByName(s);
        modules.remove(m);
    }


    public Module getModuleByName(String name) {
        if (!initialized)
            return null;

        for (Module module : modules)
			if (module.getName().replaceAll(" ", "").equalsIgnoreCase(name) || module.getName().equalsIgnoreCase(name))
                return module;
        return null;
    }

    public Module getModuleByClazz(Class<? extends Module> c) {
        if (!initialized)
            return null;

        for (Module module : modules)
			if (module.getClass().equals(c))
                return module;
        return null;
    }

    public List<Module> getModules() {
        ArrayList<Module> allModules = new ArrayList<>(modules);
        try {
            allModules.addAll(Raven.configManager.configModuleManager.getConfigModules());
        } catch (NullPointerException ignored) {
        }
        try {
            allModules.addAll(guiModuleManager.getModules());
        } catch (NullPointerException ignored) {
        }
        return allModules;
    }

    public List<Module> getConfigModules() {
        List<Module> modulesOfC = new ArrayList<>();

        for (Module mod : getModules())
			if (!mod.isClientConfig())
				modulesOfC.add(mod);

        return modulesOfC;
    }

    public List<Module> getClientConfigModules() {
        List<Module> modulesOfCC = new ArrayList<>();

        for (Module mod : getModules())
			if (mod.isClientConfig())
				modulesOfCC.add(mod);

        return modulesOfCC;
    }

    public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
        ArrayList<Module> modulesOfCat = new ArrayList<>();

        for (Module mod : getModules())
			if (mod.moduleCategory().equals(categ))
				modulesOfCat.add(mod);

        return modulesOfCat;
    }

    public void sort() {
        modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())
                - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
    }

    public int numberOfModules() {
        return modules.size();
    }

    public void sortLongShort() {
        modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
    }

    public void sortShortLong() {
        modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())
                - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
    }

    public int getLongestActiveModule(FontRenderer fr) {
        int length = 0;
        for (Module mod : modules)
			if (mod.isEnabled())
				if (fr.getStringWidth(mod.getName()) > length)
					length = fr.getStringWidth(mod.getName());
        return length;
    }

    public int getBoxHeight(FontRenderer fr, int margin) {
        int length = 0;
        for (Module mod : modules)
			if (mod.isEnabled())
				length += fr.FONT_HEIGHT + margin;
        return length;
    }

}
