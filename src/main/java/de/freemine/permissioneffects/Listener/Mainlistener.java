package de.freemine.permissioneffects.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LPkkjHD
 */
public class Mainlistener implements Listener {
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {
        ArrayList<PotionEffectTypes> possibleEffects = new ArrayList<PotionEffectTypes>();
        Collections.addAll(possibleEffects,  PotionEffectTypes.values());

        ArrayList<Integer> strengh = new ArrayList<Integer>();
        strengh.add(1);
        strengh.add(2);
        strengh.add(3);
        strengh.add(4);

        Player player = event.getPlayer();

        if (!player.hasPermission("pe.bypass") || !player.isOp()) {
            for (PotionEffectTypes effect : possibleEffects) {
                if (player.hasPermission("pe." + effect.toString().toLowerCase())) {
                    for (Integer integer : strengh) {
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
