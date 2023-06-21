package keystrokesmod.client.module.modules.other;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.ComboSetting;

public class ClientSpoof extends Module {
    private static final ComboSetting<Brand> mode = new ComboSetting<>("Brand", Brand.Lunar);
    public ClientSpoof() {
        super("ClientSpoof", ModuleCategory.other);
        this.registerSettings(
                mode
        );
        withEnabled(true);
    }

    public static String getClientName() {
        if (mode.getMode().equals(Brand.Lunar)) {
            return "lunarclient:3yewuhd";
        } else if (mode.getMode().equals(Brand.Feather)) {
            return "Feather";
        } else if (mode.getMode().equals(Brand.BPlusPlusPlus)) {
            return "Raven BPlusPlusPlus";
        } else if (mode.getMode().equals(Brand.LabyMod)) {
            return "LMC";
        } else {
            return "fml,forge";
        }
    }

    public enum Brand {
        Lunar,
        Feather,
        LabyMod,
        BPlusPlusPlus
    }
}
