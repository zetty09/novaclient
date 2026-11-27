package com.nova.client;

import com.nova.client.gui.NovaClickGui;
import com.nova.client.hud.NovaHud;
import com.nova.client.module.ModuleManager;
import com.nova.client.util.NovaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = NovaClient.MODID, name = NovaClient.NAME, version = NovaClient.VERSION, clientSideOnly = true)
public class NovaClient {
    public static final String MODID = "novaclient";
    public static final String NAME = "NOVA CLIENT";
    public static final String VERSION = "1.0.0";

    public static KeyBinding openGui;
    public static final ModuleManager MODULES = new ModuleManager();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        NovaConfig.load();
        MODULES.registerDefaults();
        openGui = new KeyBinding("Open NOVA GUI", Keyboard.KEY_RSHIFT, "NOVA CLIENT");
        ClientRegistry.registerKeyBinding(openGui);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new NovaHud());
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (openGui != null && openGui.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new NovaClickGui());
        }
    }
}
