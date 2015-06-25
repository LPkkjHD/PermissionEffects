package de.freemine.permissioneffects.Listener;

import de.freemine.permissioneffects.Main;
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
    private Main main;

    public Mainlistener(Main main) {
        this.main = main;
    }
    /*
    pe.use #use the plugin
    pe.<effect> gives the player the effect with amplifier 1 and allows him for further stronger amplifiers
    pe.<effect>.<amplifier> #gives the effect with the given lvl to the player
     */

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

        for (PotionEffectTypes effect : possibleEffects) {
            main.getLogger().info("checking for pe." + effect);

            if (player.hasPermission("pe." + effect.toString().toLowerCase())) {

                //check wether the player has a defined amplifier
                for (Integer integer : strengh) {
                    main.getLogger().info("checking integer: " + integer);
                    if (player.hasPermission("pe." + effect.toString().toLowerCase() + "." + integer.toString())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect.toString()), 100000000, integer -1, false, false));
                    }
                }

                //if not sets the default value
                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect.toString()), 1000000000, 0, false, false));
            }
        }
    }
}
