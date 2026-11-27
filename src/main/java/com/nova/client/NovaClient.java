
package com.nova.client;

import com.nova.client.gui.NovaClickGui;
import com.nova.client.module.ModuleManager;
import com.nova.client.util.NovaConfig;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class NovaClient implements ClientModInitializer {

    public static final String MODID = "novaclient";
    public static final String NAME = "NOVA CLIENT";
    public static final String VERSION = "1.0.0";

    public static final ModuleManager MODULES = new ModuleManager();

    private boolean rightShiftWasDown = false;

    @Override
    public void onInitializeClient() {
        NovaConfig.load();
        MODULES.registerDefaults();
    }

    // Wywoływane z mixina podczas ticku klienta.
    public void onClientTick() {
        Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null) {
            rightShiftWasDown = false;
            return;
        }

        boolean down = Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);

        if (down && !rightShiftWasDown && mc.currentScreen == null) {
            mc.displayGuiScreen(new NovaClickGui());
        }

        rightShiftWasDown = down;
    }
}
