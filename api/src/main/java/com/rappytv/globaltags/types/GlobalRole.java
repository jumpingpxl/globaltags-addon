package com.rappytv.globaltags.types;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public enum GlobalRole {
    ADMIN("purple"),
    DEVELOPER("aqua"),
    MODERATOR("orange"),
    SUPPORTER("green");

    private final Icon icon;

    GlobalRole(String color) {
        this.icon = Icon.texture(ResourceLocation.create(
            "globaltags",
            "textures/icons/roles/" + color + ".png"
        ));
    }

    @NotNull
    public Icon getIcon() {
        return icon;
    }
}
