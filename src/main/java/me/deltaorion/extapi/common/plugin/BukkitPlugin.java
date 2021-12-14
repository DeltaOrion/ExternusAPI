package me.deltaorion.extapi.common.plugin;

import me.deltaorion.extapi.common.depend.Dependency;
import me.deltaorion.extapi.common.entity.sender.Sender;
import me.deltaorion.extapi.common.logger.PluginLogger;
import me.deltaorion.extapi.common.scheduler.SchedulerAdapter;
import me.deltaorion.extapi.common.server.EServer;
import me.deltaorion.extapi.locale.translator.PluginTranslator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;

public class BukkitPlugin extends JavaPlugin implements EPlugin {

    private final BukkitPluginWrapper wrapper;

    public BukkitPlugin() {
        wrapper = new BukkitPluginWrapper(this);
    }

    @Override
    public EServer getEServer() {
        return wrapper.getEServer();
    }

    @Override
    public SchedulerAdapter getScheduler() {
        return wrapper.getScheduler();
    }

    @Override
    public Sender wrapSender(Object commandSender) {
        return wrapper.wrapSender(commandSender);
    }

    @Override
    public Path getDataDirectory() {
        return wrapper.getDataDirectory();
    }

    @Override
    public InputStream getResourceStream(String path) {
        return wrapper.getResourceStream(path);
    }

    @Override
    public URL getResourceURL(String path) {
        return wrapper.getResourceURL(path);
    }

    @Override
    public PluginLogger getPluginLogger() {
        return wrapper.getPluginLogger();
    }

    @Override
    public boolean isPluginEnabled() {
        return wrapper.isPluginEnabled();
    }

    @Override
    public Object getPluginObject() {
        return wrapper.getPlugin();
    }

    @Override
    public void disablePlugin() {
        wrapper.disablePlugin();
    }

    @Override
    public PluginTranslator getTranslator() {
        return wrapper.getTranslator();
    }

    @Override
    public void registerDependency(String name, boolean required) {
        wrapper.registerDependency(name,required);
    }


    @Override
    public Dependency getDependency(String name) {
        return wrapper.getDependency(name);
    }

    @Override
    public boolean hasDependency(String name) {
        return wrapper.hasDependency(name);
    }

    @Override
    public Set<String> getDependencies() {
        return wrapper.getDependencies();
    }

    public JavaPlugin getPlugin() {
        return wrapper.getPlugin();
    }
}
