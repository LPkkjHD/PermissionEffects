package de.freemine.permissioneffects;

import de.freemine.permissioneffects.Listener.PotionEffectTypes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LPkkjHD
 */
public class command implements CommandExecutor {
    private Main main;

    public command(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("PermissionEffects")) {
            if (sender.hasPermission("pe.admin")) {
                if (args.length == 0) {
                    sender.sendMessage(header("Permission Effects"));
                    sender.sendMessage("§a/pe reload          §2#Reloads the Effects, if permissions were changed");
                    sender.sendMessage(footer());
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        try {
                            ReloadPermissionEffects();
                            sender.sendMessage("§8§l[§7P§6E§8§l] §aReload complete");
                        } catch (Exception e) {
                            sender.sendMessage("§4ERROR: §cfailed to reload the PermissionEffects");
                            e.printStackTrace();
                        }
                    }
                } else {
                    sender.sendMessage("§cYou just gave me too many arguments");
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private void ReloadPermissionEffects() {
        ArrayList<PotionEffectTypes> possibleEffects = new ArrayList<PotionEffectTypes>();
        Collections.addAll(possibleEffects, PotionEffectTypes.values());

        ArrayList<Integer> strengh = new ArrayList<Integer>();
        strengh.add(1);
        strengh.add(2);
        strengh.add(3);
        strengh.add(4);

        //Clearing all effects
        for (Player player : main.getServer().getOnlinePlayers()) {
            System.out.println("Using Player:" + player);
            for (PotionEffectTypes types : possibleEffects) {
                System.out.println("Checking for:" + types.toString());
                player.removePotionEffect(PotionEffectType.getByName(types.toString()));
            }
        }

        System.out.println("##########Clearing Finished############");

        //setting the new Values
        for (Player player : main.getServer().getOnlinePlayers()) {
            System.out.println("Checking for: " + player);
            for (PotionEffectTypes effect : possibleEffects) {
                System.out.println("Checking for: " + effect.toString());
                if (player.hasPermission("pe." + effect.toString().toLowerCase())) {
                    //check wether the player has a defined amplifier
                    for (Integer integer : strengh) {
                        System.out.println("Checking for: " + integer);
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

    private String header(String s) {
        return "§b█████████§9[ §3" + s + " §9]§b██████████";
    }

    private String footer() {
        return "§b█████████████████████████████████████";
    }
}
