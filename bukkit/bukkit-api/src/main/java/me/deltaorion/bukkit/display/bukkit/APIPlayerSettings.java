package me.deltaorion.bukkit.display.bukkit;

import me.deltaorion.bukkit.display.actionbar.ActionBarFactories;
import me.deltaorion.bukkit.display.actionbar.ActionBarFactory;
import me.deltaorion.bukkit.display.bossbar.BossBarRendererFactory;
import me.deltaorion.bukkit.display.scoreboard.ScoreboardFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class APIPlayerSettings {

    @NotNull private ActionBarFactory actionBarFactory;
    @NotNull private ScoreboardFactory scoreboardFactory;
    @NotNull private BossBarRendererFactory bossBarRendererFactory;

    public APIPlayerSettings() {
        this.actionBarFactory = ActionBarFactories.SCHEDULE_FROM_VERSION();
        this.scoreboardFactory = ScoreboardFactory.WRAPPER;
        this.bossBarRendererFactory = BossBarRendererFactory.FROM_VERSION();
    }

    public APIPlayerSettings setActionBarFactory(@NotNull ActionBarFactory factory) {
        this.actionBarFactory = Objects.requireNonNull(factory);
        return this;
    }

    @NotNull
    public ActionBarFactory getActionBarFactory() {
        return actionBarFactory;
    }

    public APIPlayerSettings setScoreboardFactory(@NotNull ScoreboardFactory factory) {
        this.scoreboardFactory = Objects.requireNonNull(factory);
        return this;
    }

    @NotNull
    public ScoreboardFactory getScoreboardFactory() {
        return scoreboardFactory;
    }

    @NotNull
    public BossBarRendererFactory getBossBarRendererFactory() {
        return bossBarRendererFactory;
    }

    public APIPlayerSettings setBossBarRendererFactory(@NotNull BossBarRendererFactory bossBarRendererFactory) {
        this.bossBarRendererFactory = bossBarRendererFactory;
        return this;
    }
}
