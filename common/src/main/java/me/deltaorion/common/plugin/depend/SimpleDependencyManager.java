package me.deltaorion.common.plugin.depend;

import com.google.common.base.MoreObjects;
import me.deltaorion.common.plugin.EPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SimpleDependencyManager implements DependencyManager {

    private final Map<String,Dependency> dependencies;
    private final EPlugin master;

    public SimpleDependencyManager(@NotNull EPlugin master) {
        Objects.requireNonNull(master);
        this.dependencies = new HashMap<>();
        this.master = master;
    }

    /**
     * Registers a plugin dependency. A dependency has useful methods such as getting the plugin and checking whether
     * it is enabled or not. Dependencies are identified by the plugin name which is the name used by the plugin loader
     * to identify a plugin. One can use {@link Dependency#isActive()}
     *
     * For example if a plugin were to depend on 'TownyAdvanced - https://github.com/TownyAdvanced/Towny' The plugin name
     * would be Towny.
     *
     * @param name The name used by the plugin loader to identify the dependency
     * @param required Whether the dependency is essential for this plugin or not
     *
     */
    public void registerDependency(@NotNull String name, boolean required) {
        if(hasDependency(name))
            throw new IllegalArgumentException("The dependency '"+name+"' has already been registered!");

        Dependency dependency = new Dependency(master,name,required);
        dependency.check();

        this.dependencies.put(name.toUpperCase(),dependency);
    }

    /**
     * Gets a plugin dependency object.
     *
     * Use {@link Dependency#isActive()} to check if the dependency is active
     *  CAST {@link Dependency#getDependency()}  to get the actual plugin.
     *
     * @param name The name of the dependency
     * @return a dependency object.
     */

    @Nullable
    public Dependency getDependency(@NotNull String name) {
        return dependencies.get(name.toUpperCase());
    }

    public boolean hasDependency(@NotNull String name) {
        return dependencies.containsKey(name.toUpperCase());
    }

    @NotNull
    public Set<String> getDependencies() {
        return Collections.unmodifiableSet(dependencies.keySet());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Registry",dependencies).toString();
    }
}
