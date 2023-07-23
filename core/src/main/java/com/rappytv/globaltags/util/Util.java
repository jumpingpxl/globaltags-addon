package com.rappytv.globaltags.util;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.Session;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.Notification.Type;
import net.labymod.api.util.I18n;
import org.jetbrains.annotations.Nullable;

public class Util {

    public static void notify(String title, String text, boolean isError) {
        notify(
            title,
            text,
            Icon.texture(ResourceLocation.create(
                "globaltags",
                "textures/" + (isError ? "error" : "success") + ".png"
            ))
        );
    }

    public static void notify(String title, String text, Icon icon) {
        Notification.Builder builder = Notification.builder()
            .title(Component.text(title))
            .text(Component.text(text))
            .icon(icon)
            .type(Type.ADVANCEMENT);
        Laby.labyAPI().notificationController().push(builder.build());
    }

    public static boolean clearCache(boolean notify) {
        if(TagCache.isEmpty()) {
            if(notify) Util.notify(
                I18n.translate("globaltags.notifications.error"),
                I18n.translate("globaltags.notifications.cacheEmpty"),
                null
            );
            return false;
        }
        TagCache.clear();
        return true;
    }

    public static @Nullable String getSessionToken() {
        Session session = Laby.labyAPI().minecraft().sessionAccessor().getSession();
        if(session == null) return null;

        return session.getAccessToken();
    }
}
