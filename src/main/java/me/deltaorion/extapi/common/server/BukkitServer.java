package me.deltaorion.extapi.common.server;
import me.deltaorion.extapi.common.entity.sender.BukkitSenderInfo;
import me.deltaorion.extapi.common.entity.sender.Sender;
import me.deltaorion.extapi.common.entity.sender.SimpleSender;
import me.deltaorion.extapi.common.plugin.BukkitPluginWrapper;
import me.deltaorion.extapi.common.plugin.EPlugin;
import me.deltaorion.extapi.common.version.MinecraftVersion;
import me.deltaorion.extapi.common.version.VersionFactory;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BukkitServer implements EServer {

    private final Server server;
    private final MinecraftVersion minecraftVersion;
    public final static int MILLIS_PER_TICK = 50;

    public BukkitServer(Server server) {
        this.server = server;
        this.minecraftVersion = VersionFactory.parse(server.getBukkitVersion());
    }

    @Override
    public MinecraftVersion getVersion() {
        return this.minecraftVersion;
    }

    @Override
    public String getBrand() {
        return server.getName();
    }

    @Override
    public List<UUID> getOnlinePlayers() {
        List<UUID> players = new ArrayList<>();
        for(Player player : server.getOnlinePlayers()) {
            players.add(player.getUniqueId());
        }
        return players;
    }

    @Override
    public List<Sender> getOnlineSenders() {
        List<Sender> senders = new ArrayList<>();
        for(Player player : server.getOnlinePlayers()) {
            senders.add(new SimpleSender(new BukkitSenderInfo(this,player)));
        }
        return senders;
    }

    @Override
    public Sender getConsoleSender() {
        return new SimpleSender(new BukkitSenderInfo(this, server.getConsoleSender()));
    }

    @Override
    public int getMaxPlayer() {
        return server.getMaxPlayers();
    }

    @Override
    public boolean isPlayerOnline(UUID uuid) {
        if(uuid.equals(EServer.CONSOLE_UUID))
            return false;

        return server.getPlayer(uuid).isOnline();
    }

    @Override
    public EPlugin getPlugin(String name) {
        return new BukkitPluginWrapper(server.getPluginManager().getPlugin(name));
    }

    @Override
    public boolean isPluginEnabled(String name) {
        return getServer().getPluginManager().isPluginEnabled(name);
    }

    @Override
    public Object getServerObject() {
        return server;
    }

    @Override
    public String translateColorCodes(String raw) {
        return ChatColor.translateAlternateColorCodes('&',raw);
    }

    public Server getServer() {
        return this.server;
    }

}
