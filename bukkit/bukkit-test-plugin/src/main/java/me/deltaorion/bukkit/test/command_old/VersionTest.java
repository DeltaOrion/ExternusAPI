package me.deltaorion.bukkit.test.command_old;

import me.deltaorion.common.APIPermissions;
import me.deltaorion.common.command.sent.MessageErrors;
import me.deltaorion.common.plugin.version.VersionFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class VersionTest implements CommandExecutor {

    private final Plugin plugin;

    public VersionTest(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission(APIPermissions.COMMAND)) {
            sender.sendMessage(MessageErrors.NO_PERMISSION().toString());
            return true;
        }
        sender.sendMessage(plugin.getServer().getVersion());
        sender.sendMessage(plugin.getServer().getBukkitVersion());
        sender.sendMessage(VersionFactory.parse(plugin.getServer().getBukkitVersion()).toString());
        return true;
    }
}
