package me.deltaorion.common.test.unit;

import me.deltaorion.common.config.file.ConfigLoader;
import me.deltaorion.common.config.file.FileConfigLoader;
import me.deltaorion.common.config.properties.PropertiesAdapter;
import me.deltaorion.common.plugin.ApiPlugin;
import me.deltaorion.common.test.generic.McTest;
import me.deltaorion.common.test.generic.MinecraftTest;
import me.deltaorion.common.test.shared.LocalizationTest;

public class LocaleTest implements MinecraftTest {

    private final ApiPlugin plugin;
    private final LocalizationTest test;

    public LocaleTest(ApiPlugin plugin) {
        this.plugin = plugin;
        this.test = new LocalizationTest();
        pullTestLocales();
    }

    private void pullTestLocales() {
        ConfigLoader configFr = new FileConfigLoader(new PropertiesAdapter(), getClass().getClassLoader(), plugin.getDataDirectory(), "translations/fr.properties");
        ConfigLoader configPT = new FileConfigLoader(new PropertiesAdapter(), getClass().getClassLoader(), plugin.getDataDirectory(), "translations/en_PT.properties");
        configPT.saveDefaultConfig();
        configFr.saveDefaultConfig();
        plugin.getTranslator().reload();
    }

    @McTest
    public void testTranslationManager() {
        test.testTranslationManager();
    }

    @McTest
    public void testMessage() {
        test.testMessage();
    }

    @Override
    public String getName() {
        return "Locale Test";
    }
}
