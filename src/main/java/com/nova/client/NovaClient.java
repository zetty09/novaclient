
package com.nova.client;

import com.nova.client.gui.NovaClickGui;
import com.nova.client.module.ModuleManager;
import com.nova.client.util.NovaConfig;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

public class NovaClient implements ClientModInitializer {

    public static final String MODID = "novaclient";
    public static final String NAME = "NOVA CLIENT";
    public static final String VERSION = "1.0.0";

    public static KeyBinding openGui;
    public static final ModuleManager MODULES = new ModuleManager();

    @Override
    public void onInitializeClient() {
        NovaConfig.load();
        MODULES.registerDefaults();

        openGui = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                "key.novaclient.opengui",
                Keyboard.KEY_RSHIFT,
                "category.novaclient"
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGui.isPressed()) {
                Minecraft.getMinecraft().displayGuiScreen(
                    new NovaClickGui()
                );
            }
        });
    }
}
