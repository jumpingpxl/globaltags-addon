package com.rappytv.globaltags.activities;

import com.rappytv.globaltags.GlobalTagAddon;
import com.rappytv.globaltags.activities.widgets.TagHistoryEntryWidget;
import com.rappytv.globaltags.api.GlobalTagAPI;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;

import java.util.UUID;

@Link("list.lss")
@AutoActivity
public class TagHistoryActivity extends SimpleActivity {

    private final GlobalTagAPI api;
    private final UUID uuid;
    private final String username;

    public TagHistoryActivity(GlobalTagAPI api, UUID uuid, String username) {
        this.api = api;
        this.uuid = uuid;
        this.username = username;
    }

    @Override
    public void initialize(Parent parent) {
        super.initialize(parent);
        api.getApiHandler().getTagHistory(uuid, (response) -> Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
            if(document.getChild("window") != null) return;
            System.out.println(response.data());
            if(!response.successful()) {
                Laby.references().chatExecutor().displayClientMessage(
                    TextComponent.builder()
                        .append(GlobalTagAddon.prefix)
                        .append(Component.text(response.error(), NamedTextColor.RED))
                        .build()
                );
                Laby.labyAPI().minecraft().minecraftWindow().displayScreen((ScreenInstance) null);
                return;
            }
            FlexibleContentWidget windowWidget = new FlexibleContentWidget().addId("window");
            HorizontalListWidget profileWrapper = new HorizontalListWidget().addId("header");
            IconWidget headWidget = new IconWidget(Icon.head(this.uuid)).addId("head");
            ComponentWidget titleWidget = ComponentWidget.i18n("globaltags.context.tag_history.title", this.username).addId("username");
            VerticalListWidget<TagHistoryEntryWidget> notes = new VerticalListWidget<>().addId("item-list");
            for(int i = 0; i < response.data().size(); i++) {
                notes.addChild(new TagHistoryEntryWidget(i + 1, api, response.data().get(i)));
            }

            profileWrapper.addEntryInitialized(headWidget);
            profileWrapper.addEntryInitialized(titleWidget);

            windowWidget.addContentInitialized(profileWrapper);
            windowWidget.addContentInitialized(new ScrollWidget(notes));
            this.document.addChildInitialized(windowWidget);
        }));
    }
}
