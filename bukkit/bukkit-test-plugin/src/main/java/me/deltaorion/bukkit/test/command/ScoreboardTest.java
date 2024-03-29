package me.deltaorion.bukkit.test.command;

import me.deltaorion.bukkit.display.bukkit.BukkitApiPlayer;
import me.deltaorion.bukkit.display.scoreboard.EScoreboard;
import me.deltaorion.bukkit.plugin.plugin.BukkitPlugin;
import me.deltaorion.bukkit.test.animation.ScoreboardAnimation;
import me.deltaorion.common.APIPermissions;
import me.deltaorion.common.animation.MinecraftAnimation;
import me.deltaorion.common.animation.MinecraftFrame;
import me.deltaorion.common.animation.RunningAnimation;
import me.deltaorion.common.animation.factory.AnimationFactories;
import me.deltaorion.common.command.CommandException;
import me.deltaorion.common.command.FunctionalCommand;
import me.deltaorion.common.command.sent.SentCommand;
import me.deltaorion.common.locale.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ScoreboardTest extends FunctionalCommand {

    @Nullable private RunningAnimation<Player> runningAnimation;
    @Nullable private MinecraftAnimation<String,Player> animation;
    private final BukkitPlugin plugin;
    private final String scoreboardName = "sb-1";

    public ScoreboardTest(BukkitPlugin plugin) {
        super(APIPermissions.COMMAND);
        this.plugin = plugin;
        registerArguments();
    }

    private void registerArguments() {
        registerArgument("cancel", (command) -> {
            if(runningAnimation!=null)
                runningAnimation.cancel();

            if(command.getSender().isConsole())
                return;

            Player player = plugin.getServer().getPlayer(command.getSender().getUniqueId());
            BukkitApiPlayer p = plugin.getBukkitPlayerManager().getPlayer(player);
            p.removeScoreboard();
        });

        registerArgument("pause", (command) -> {
            if(runningAnimation!=null)
                runningAnimation.pause();
        });

        registerArgument("play", command -> {
            if(runningAnimation!=null)
                runningAnimation.play();
        });

        registerArgument("visible",command -> {
            Player player = Bukkit.getPlayer(command.getSender().getUniqueId());
            EScoreboard scoreboard = plugin.getBukkitPlayerManager().getPlayer(player).getScoreboard();
            if(scoreboard==null)
                return;

            scoreboard.setVisible(!scoreboard.isVisible());
        });
    }

    @Override
    public void commandLogic(SentCommand command) throws CommandException {
        if (command.getSender().isConsole())
            return;

        Player player = Bukkit.getPlayer(command.getSender().getUniqueId());
        BukkitApiPlayer apiPlayer = plugin.getBukkitPlayerManager().getPlayer(player);
        final String title = command.getArgOrDefault(0, "Hello World").asString();

        if (apiPlayer.getScoreboard() == null)
            createScoreboard(apiPlayer, title);

        if (!apiPlayer.getScoreboard().getName().equals(scoreboardName))
            createScoreboard(apiPlayer, title);

        if (this.runningAnimation != null)
            this.runningAnimation.cancel();

        createAnimation(title);

    }

    private void createAnimation(String title) {
        animation = new MinecraftAnimation<>(plugin,
                AnimationFactories.SCHEDULE_ASYNC(),
                new ScoreboardAnimation(scoreboardName,plugin));

        String altColor = ChatColor.YELLOW + "" + ChatColor.BOLD;
        animation.addFrame(new MinecraftFrame<>(ChatColor.WHITE + "" + ChatColor.BOLD + title,400));
        for(int i=0;i<title.length();i++) {
            String splitA = title.substring(0,i);
            String letter = title.substring(i,i+1);
            String splitB = title.substring(i+1);
            String entry = ChatColor.WHITE + "" + ChatColor.BOLD + splitA + altColor + letter + ChatColor.WHITE + ChatColor.BOLD + "" + splitB;
            animation.addFrame(new MinecraftFrame<>(entry,400));
        }
        animation.addFrame(new MinecraftFrame<>(altColor + title,400));
        this.runningAnimation = animation.start(() -> new ArrayList<>(plugin.getServer().getOnlinePlayers()));
    }

    private void createScoreboard(BukkitApiPlayer player, String title) {
        EScoreboard scoreboard = player.setScoreboard(scoreboardName,4);
        assert scoreboard != null;
        scoreboard.setTitle(ChatColor.WHITE + "" + ChatColor.BOLD + title);
        scoreboard.setLine(ChatColor.GRAY + "Test Server", 0);
        scoreboard.setLine(ChatColor.WHITE + "abcdefghijklmnopqrstuvwxyz32", 1);
        scoreboard.setLine(Message.valueOfTranslatable("hello-arg"),2,null,player.getName());
        double tps;
        try {
            tps = plugin.getServer().getTPS()[0];
        } catch(NoSuchMethodError e) {
            tps = Math.random();
        }
        scoreboard.setLine(Message.valueOf(ChatColor.GOLD + "TPS: " + ChatColor.WHITE + "{0}"), 3, "TPS", tps);
    }
}
