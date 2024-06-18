package com.rappytv.globaltags.util;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage.Purpose;
import net.labymod.api.labyconnect.TokenStorage.Token;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.Notification.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Util {

    public static void notify(String title, String text) {
        Notification.builder()
            .title(net.labymod.api.client.component.Component.text(title))
            .text(net.labymod.api.client.component.Component.text(text))
            .type(Type.SOCIAL)
            .buildAndPush();
    }

    @NotNull
    public static Component translateColorCodes(String string) {
        if(string == null) string = "";
        return LegacyComponentSerializer
            .legacyAmpersand()
            .deserialize(string);
    }

    public static void clearCache() {
        TagCache.clear();
        TagCache.resolve(Laby.labyAPI().getUniqueId());
    }

    public static @Nullable String getSessionToken() {
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if(session == null) return null;

        Token token = session.tokenStorage().getToken(
            Purpose.JWT,
            session.self().getUniqueId()
        );

        if(token == null || token.isExpired()) return null;

        return token.getToken();
    }
}
