package keystrokesmod.client.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.google.common.eventbus.EventBus;

import keystrokesmod.client.clickgui.kv.KvCompactGui;
import keystrokesmod.client.clickgui.raven.ClickGui;
import keystrokesmod.client.command.CommandManager;
import keystrokesmod.client.config.ConfigManager;
import keystrokesmod.client.event.forge.ForgeEventListener;
import keystrokesmod.client.module.ModuleManager;
import keystrokesmod.client.notifications.NotificationRenderer;
import keystrokesmod.client.utils.DebugInfoRenderer;
import keystrokesmod.client.utils.MouseManager;
import keystrokesmod.client.utils.PingChecker;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import keystrokesmod.client.utils.font.FontUtil;
import keystrokesmod.client.utils.version.VersionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Raven {

	public static boolean debugger;
    public static final VersionManager versionManager = new VersionManager();
    public static CommandManager commandManager;
    public static final String sourceLocation = "https://github.com/StephenIsTaken/Raven-BPlusPlusPlus";
    public static final String downloadLocation = "https://github.com/StephenIsTaken/Raven-BPlusPlusPlus/tree/main/build/libs";
    public static final String discord = "https://discord.gg/ZJJRC9mBWk";
    public static String[] updateText = {
            "Your version of Raven B+++ (" + versionManager.getClientVersion().toString() + ") is outdated!",
            "Enter the command update into client CommandLine to open the download page",
            "or just enable the update module to get a message in chat.", "",
            "Newest version: " + versionManager.getLatestVersion().toString() };
    public static ConfigManager configManager;
    public static ClientConfig clientConfig;

    public static final ModuleManager moduleManager = new ModuleManager();

    public static ClickGui clickGui;
    public static KvCompactGui kvCompactGui;
    // public static TabGui tabGui;

    private static final ScheduledExecutorService ex = Executors.newScheduledThreadPool(2);

    public static ResourceLocation mResourceLocation;

    public static final String osName, osArch;
    public static final List<Object> registered = new ArrayList<Object>();
    public static final EventBus eventBus = new EventBus(); // use this
    public static final Minecraft mc = Minecraft.getMinecraft();

    static {
        osName = System.getProperty("os.name").toLowerCase();
        osArch = System.getProperty("os.arch").toLowerCase();
    }

    public static void init() {
        register(new Raven());
        register(new DebugInfoRenderer());
        register(new MouseManager());
        register(new PingChecker());
        register(new ForgeEventListener());
        eventBus.register(NotificationRenderer.notificationRenderer);

        FontUtil.bootstrap();

        Runtime.getRuntime().addShutdownHook(new Thread(ex::shutdown));

        mResourceLocation = RenderUtils.getResourcePath("/assets/keystrokesmod/raven.png");

        commandManager = new CommandManager();
        clickGui = new ClickGui();
        kvCompactGui = new KvCompactGui();
        configManager = new ConfigManager();
        clientConfig = new ClientConfig();
        clientConfig.applyConfig();

        ex.execute(() -> {
            try {
                LaunchTracker.registerLaunch();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (Utils.Player.isPlayerInGame()) {
            String msg = event.message.getUnformattedText();

            if (msg.startsWith("Your new API key is")) {
                Utils.URLS.hypixelApiKey = msg.replace("Your new API key is ", "");
                Utils.Player.sendMessageToSelf("&aSet api key to " + Utils.URLS.hypixelApiKey + "!");
                clientConfig.saveConfig();
            }
        }
    }

    public static void register(Object obj) {
        registered.add(obj);
        MinecraftForge.EVENT_BUS.register(obj);
    }

    public static ScheduledExecutorService getExecutor() {
        return ex;
    }
}