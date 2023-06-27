package keystrokesmod.client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import static keystrokesmod.client.utils.Utils.Player.isPlayerInGame;

public class AutoWeapon extends Module {

    public AutoWeapon() {
        super("AutoWeapon",ModuleCategory.combat);
        this.registerSetting(new DescriptionSetting("AutoWeapon."));
    }

    /*@Subscribe
    public void onAttack(ForgeEvent e){
        if (e.getEvent() instanceof AttackEntityEvent) {
            if (isPlayerInGame()) {
                hotkeyToSword();
            }
        }
    }
    private void hotkeyToSword() {
        if (mc.thePlayer.inventory.currentItem != Utils.Player.getMaxDamageSlot()) {
            mc.thePlayer.inventory.currentItem = Utils.Player.getMaxDamageSlot();
        }
    }*/
}
