package me.deltaorion.bungee.plugin.server;

import com.google.common.base.MoreObjects;
import me.deltaorion.bungee.plugin.plugin.BungeePluginWrapper;
import me.deltaorion.bungee.plugin.sender.BungeeSenderInfo;
import me.deltaorion.common.plugin.EPlugin;
import me.deltaorion.common.plugin.sender.Sender;
import me.deltaorion.common.plugin.sender.SenderFactory;
import me.deltaorion.common.plugin.sender.SimpleSender;
import me.deltaorion.common.plugin.EServer;
import me.deltaorion.common.plugin.version.MinecraftVersion;
import me.deltaorion.common.plugin.version.VersionFactory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BungeeServer implements EServer {

    @NotNull private final ProxyServer proxyServer;
    @NotNull private final MinecraftVersion minecraftVersion;
    @NotNull private volatile SenderFactory senderFactory;

    public BungeeServer(@NotNull ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
        this.minecraftVersion = Objects.requireNonNull(VersionFactory.parse(proxyServer.getVersion()),String.format("Cannot parse proxy version in format '%s'",proxyServer.getVersion()));
        this.senderFactory = getBungee;
    }

    @Override
    public MinecraftVersion getServerVersion() {
        return minecraftVersion;
    }

    @Override
    public String getServerBrand() {
        return proxyServer.getName();
    }

    @Override
    public List<UUID> getOnlinePlayers() {
        List<UUID> players = new ArrayList<>();
        for(ProxiedPlayer player : proxyServer.getPlayers()) {
            players.add(player.getUniqueId());
        }

        return Collections.unmodifiableList(players);
    }

    @Override
    public List<Sender> getOnlineSenders() {
        List<Sender> players = new ArrayList<>();
        for(ProxiedPlayer player : proxyServer.getPlayers()) {
            players.add(wrapSender(player));
        }
        return Collections.unmodifiableList(players);
    }

    @Override
    public Sender getConsoleSender() {
        return wrapSender(proxyServer.getConsole());
    }

    @Override
    public int getMaxPlayer() {
        return proxyServer.getConfig().getPlayerLimit();
    }


    @Override
    public boolean isPlayerOnline(@NotNull UUID uuid) {
        return proxyServer.getPlayer(uuid) != null;
    }

    @Override
    public EPlugin getPlugin(@NotNull String name) {
        Objects.requireNonNull(name);
        Plugin plugin = proxyServer.getPluginManager().getPlugin(name);
        if(plugin==null)
            return null;

        return new BungeePluginWrapper(plugin);
    }

    @Nullable
    @Override
    public Object getPluginObject(@NotNull String name) {
        return proxyServer.getPluginManager().getPlugin(name);
    }

    @Override
    public boolean isPluginEnabled(@NotNull String name) {
        Objects.requireNonNull(name);
        return proxyServer.getPluginManager().getPlugin(name) != null;
    }

    @Override
    public String translateColorCodes(@NotNull String raw) {
        Objects.requireNonNull(raw);
        return ChatColor.translateAlternateColorCodes('&',raw);
    }

    @Override
    public String getServerName() {
        return proxyServer.getName();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name",proxyServer.getName())
                .add("minecraft version",minecraftVersion)
                .add("server version",proxyServer.getVersion()).toString();

    }

    @NotNull @Override
    public Sender wrapSender(@NotNull Object commandSender) {
        return senderFactory.get(commandSender,minecraftVersion);
    }

    @Override
    public void setSenderFactory(@NotNull SenderFactory factory) {
        this.senderFactory = Objects.requireNonNull(factory);
    }

    private final SenderFactory getBungee = new SenderFactory() {
        @NotNull
        @Override
        public Sender get(@NotNull Object commandSender, @NotNull MinecraftVersion version) {
            if(!(commandSender instanceof CommandSender))
                throw new IllegalArgumentException("Command Sender must be a net.md5 command sender");

            return new SimpleSender(new BungeeSenderInfo((CommandSender) commandSender,proxyServer,BungeeServer.this));
        }
    };
}
