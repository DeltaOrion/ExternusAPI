package me.deltaorion.bukkit.display.bossbar;

import me.deltaorion.bukkit.plugin.UnsupportedVersionException;
import me.deltaorion.bukkit.plugin.plugin.BukkitAPIDepends;
import me.deltaorion.bukkit.plugin.plugin.BukkitPlugin;
import me.deltaorion.common.plugin.depend.MissingDependencyException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EntityBossBarRenderer implements BossBarRenderer {

    @NotNull private final Player player;
    private static final int ENTITY_DISTANCE = 32;
    @NotNull private final FakeWither wither;

    public EntityBossBarRenderer(@NotNull BukkitPlugin plugin, @NotNull Player player) {
        if(!plugin.getDependency(BukkitAPIDepends.PROTOCOL_LIB).isActive())
            throw new MissingDependencyException("Cannot make a BossBar as '"+BukkitAPIDepends.PROTOCOL_LIB+"' is not installed!");
        this.player = player;
        Location initialLocation = makeLocation();
        this.wither = new FakeWither(this.player,initialLocation);
    }

    private int getHealth(float progress) {
        return (int) Math.max(FakeWither.MIN_HEALTH,FakeWither.MAX_HEALTH*progress);
    }

    @NotNull
    private Location makeLocation() {
        Location base = player.getLocation();
        return base.getDirection().multiply(ENTITY_DISTANCE).add(base.toVector()).toLocation(player.getWorld());
    }

    @Override
    public void setMessage(@NotNull String render) {
        this.wither.setCustomName(render);
    }

    @Override
    public void setProgress(float progress) {
        this.wither.setHealth(getHealth(progress));
    }

    @Override
    public void setVisible(boolean visible) {
        if(visible) {
            this.wither.spawn();
        } else {
            this.wither.destroy();
        }
    }

    @Override
    public void update() {
        if(!this.wither.isSpawned())
            return;

        wither.teleport(makeLocation());
    }

    @Override
    public void setColor(@NotNull BarColor color) {
        handleUnsupported("Set Color");
    }

    @Override
    public void setStyle(@NotNull BarStyle style) {
        handleUnsupported("Set Style");
    }

    @Override
    public void setCreateFog(boolean createFog) {
        handleUnsupported("Create Fog");
    }

    @Override
    public void setDarkenSky(boolean darkenSky) {
        handleUnsupported("Darken Sky");
    }

    @Override
    public void setPlayMusic(boolean playMusic) {
        handleUnsupported("Play Music");
    }

    private void handleUnsupported(String action) {
        throw new UnsupportedVersionException("Cannot perform the action '"+action+"' as a Entity BossBar Renderer is being used!");
    }
}
