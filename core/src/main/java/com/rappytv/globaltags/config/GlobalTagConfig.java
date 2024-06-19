package com.rappytv.globaltags.config;

import com.rappytv.globaltags.api.ApiRequest;
import com.rappytv.globaltags.config.subconfig.TagSubConfig;
import com.rappytv.globaltags.util.Util;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget.ButtonSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.util.I18n;
import net.labymod.api.util.MethodOrder;

@ConfigName("settings")
@SpriteTexture("settings")
public class GlobalTagConfig extends AddonConfig {

    public GlobalTagConfig() {
        localizedResponses.addChangeListener((property, oldValue, newValue) ->
            ApiRequest.useLocalizedResponses(newValue)
        );
    }

    @SwitchSetting
    @SpriteSlot(size = 32)
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @IntroducedIn(namespace = "globaltags", value = "1.1.9")
    @SpriteSlot(size = 32, y = 2, x = 2)
    @SwitchSetting
    private final ConfigProperty<Boolean> localizedResponses = new ConfigProperty<>(true);

    @SettingSection("display")
    @SwitchSetting
    @SpriteSlot(size = 32, x = 1)
    private final ConfigProperty<Boolean> showOwnTag = new ConfigProperty<>(false);
    @SpriteSlot(size = 32, x = 2)
    @SliderSetting(min = 5, max = 10)
    private final ConfigProperty<Integer> tagSize = new ConfigProperty<>(10);
    @IntroducedIn(namespace = "globaltags", value = "1.1.7")
    @SpriteSlot(size = 32, y = 2, x = 1)
    @SwitchSetting
    private final ConfigProperty<Boolean> showBackground = new ConfigProperty<>(false);

    @SettingSection("tags")
    @SpriteSlot(size = 32, y = 1)
    private final TagSubConfig tags = new TagSubConfig();

    @MethodOrder(after = "tags")
    @ButtonSetting
    @SpriteSlot(size = 32, y = 2)
    public void clearCache(Setting setting) {
        Util.clearCache();
        Util.notify(
            I18n.translate("globaltags.notifications.success"),
            I18n.translate("globaltags.notifications.cacheCleared")
        );
    }

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public ConfigProperty<Boolean> showOwnTag() {
        return showOwnTag;
    }
    public ConfigProperty<Integer> tagSize() {
        return tagSize;
    }
    public ConfigProperty<Boolean> showBackground() {
        return showBackground;
    }
}
