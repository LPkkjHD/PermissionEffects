package de.freemine.permissioneffects.Listener;

import de.freemine.permissioneffects.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LPkkjHD
 */
public class Mainlistener implements Listener {
    private Main main;

    public Mainlistener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        addEffects(event.getPlayer());
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        main.getServer().getScheduler().scheduleSyncDelayedTask(main.plugin, new Runnable() {
            public void run() {
               addEffects(player);
            }
        }, 5L);
    }

    private void addEffects(Player player) {
        ArrayList<PotionEffectTypes> possibleEffects = new ArrayList<PotionEffectTypes>();
        Collections.addAll(possibleEffects,  PotionEffectTypes.values());

        ArrayList<Integer> strength = new ArrayList<Integer>();
        strength.add(1);
        strength.add(2);
        strength.add(3);
        strength.add(4);

        if (!player.hasPermission("pe.bypass") || !player.isOp()) {
            for (PotionEffectTypes effect : possibleEffects) {
                if (player.hasPermission("pe." + effect.toString().toLowerCase())) {
                    for (Integer integer : strength) {
                        if (player.hasPermission("pe." + effect.toString().toLowerCase() + "." + integer.toString())) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect.toString()), 100000000, integer -1, false, false));
                        }
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect.toString()), 1000000000, 0, false, false));
                }
            }
        }
    }
}
