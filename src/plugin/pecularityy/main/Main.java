package plugin.pecularityy.main;

import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.acl.Permission;

/*
 * Created by ShadyCarpet
 * PrefixPlugin created in 9/4/2017
 * All work belongs to ShadyCarpet
 */
public class Main extends JavaPlugin {
    PluginManager pm = Bukkit.getPluginManager();
    public static Chat chatsetup = null;

    @Override
    public void onEnable() {


        getCommand("prefix").setExecutor(new PrefixCommand(this));
        pm.registerEvents(new PrefixCommand(this), this);

        getLogger().info("Prefix plugin created by -- Pecularityy Enabled!");
        saveDefaultConfig();



        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            System.out.println("PrefixPlugin |  Vault was detected on startup.");
        } else {
            System.out.println("PrefixPlugin |  Vault was not detected on startup Please ensure it started properly or you have another permissions plugin.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        setupChatSystem();
    }

    private boolean setupChatSystem()
    {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chatsetup = (Chat)rsp.getProvider();
        return chatsetup != null;
    }


    }




