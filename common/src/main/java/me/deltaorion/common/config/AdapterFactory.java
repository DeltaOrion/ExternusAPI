package me.deltaorion.common.config;

import org.jetbrains.annotations.NotNull;

/**
 * An adapter factory is a factory class that is used to create fresh instances of {@link ConfigAdapter}.
 *
 * Sub Classes include
 *   - {@link me.deltaorion.common.config.yaml.YamlAdapter}
 *   - {@link me.deltaorion.common.config.properties.PropertiesAdapter}
 *
 */
public interface AdapterFactory {

    public ConfigAdapter getNew(@NotNull ConfigSection adapterFor);

    public String getFileExtension();
}
