package plugin.pecularityy.main;

import org.bukkit.Bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import java.util.ArrayList;
import java.util.UUID;

/*
 * Created by ShadyCarpet
 * PrefixPlugin created in 9/4/2017
 * All work belongs to ShadyCarpet
 */
public class PrefixCommand implements CommandExecutor, Listener {

    public Main plugin;


    public PrefixCommand(Main instance) {
        plugin = instance;
    }

    public static ArrayList<UUID> newPrefix = new ArrayList();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
       if (command.getName().equalsIgnoreCase("prefix")) {
           Player p = (Player) commandSender;
            if (args.length == 0) {
                if (!p.hasPermission("PREFIX.setPREFIX")) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") +  plugin.getConfig().getString("Messages.Permission_Error")));
                    return true;
                }


                 p.sendMessage(ChatColor.translateAlternateColorCodes('&',   plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Set_Prefix")));
                newPrefix.add(p.getUniqueId());
                return true;
            }
            if (args.length >= 1) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Usage_Error")));
                return true;

            }

        }
        return false;
    }


    @EventHandler
    public void OnDone(PlayerChatEvent e) {

        Player p = e.getPlayer();
        if(newPrefix.contains(p.getUniqueId())) {
        int limitedcharacters = plugin.getConfig().getInt("prefix_length");
        final Player target = Bukkit.getServer().getPlayer(p.getDisplayName());


                        e.setCancelled(true);


                if(e.getMessage().contains("cancel")) {
                   newPrefix.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Cancel_Message")));
                        return;

                }

                if(e.getMessage().contains("reset")) {
                    newPrefix.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Reset_Message")));
                    Main.chatsetup.setPlayerPrefix(p, null);
                 return;
                 }
            ArrayList<String> bannedwords = (ArrayList)plugin.getConfig().getStringList("bannedwords");
                for(String badwords : bannedwords) {

                    badwords = badwords.toString();

                    if((e.getMessage().contains(badwords))) {
                        e.setCancelled(true);
                        newPrefix.remove(p.getUniqueId());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Banned_Words")));
                        return;
                    }

                }

                 if(e.getMessage().length() >= limitedcharacters) {
                     newPrefix.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Prefix_To_Long")));
                     return;
                 }
                 if(e.getMessage().length() <= limitedcharacters) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Messages.Prefix") + plugin.getConfig().getString("Messages.Prefix_Set_To").replace("{newprefix}", e.getMessage())));
                    Main.chatsetup.setPlayerPrefix(p, e.getMessage());
                    newPrefix.remove(p.getUniqueId());
                    return;

                 }

                }
                e.setCancelled(false);





            }




    }
