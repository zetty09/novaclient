package com.nova.client.hud;

import com.nova.client.NovaClient;
import com.nova.client.module.NovaModule;
import com.nova.client.util.NovaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class NovaHud {
    private final Minecraft mc = Minecraft.getMinecraft();
    private long leftSecond = System.currentTimeMillis();
    private int leftClicks, rightClicks, leftCps, rightCps;

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (!enabled("HUD")) return;
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;
        int accent = 0xFF000000 | NovaConfig.getColor();
        int y = 5;

        fr.drawStringWithShadow("NOVA", 5, y, accent); y += 12;

        if (enabled("FPS Counter")) {
            fr.drawStringWithShadow("FPS: " + Minecraft.getDebugFPS(), 5, y, 0xFFFFFF); y += 10;
        }
        if (enabled("Coordinates")) {
            fr.drawStringWithShadow(String.format("XYZ: %.1f / %.1f / %.1f",
                    mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), 5, y, 0xFFFFFF);
            y += 10;
        }
        if (enabled("CPS Counter")) {
            fr.drawStringWithShadow("CPS: " + leftCps + " | " + rightCps, 5, y, 0xFFFFFF);
        }
        if (enabled("Keystrokes")) drawKeys(sr, accent);

        if (enabled("Minimal Effects")) {
            // Deliberately limited to client-side particle preference; no game mechanics changed.
            mc.gameSettings.particleSetting = 2;
        }
    }

    private boolean enabled(String name) {
        NovaModule m = NovaClient.MODULES.get(name);
        return m != null && m.isEnabled();
    }

    private void drawKeys(ScaledResolution sr, int accent) {
        int x = 8, y = sr.getScaledHeight() - 66, size = 18, gap = 2;
        drawKey("W", x + size + gap, y, KeyboardState.w(), accent, size);
        drawKey("A", x, y + size + gap, KeyboardState.a(), accent, size);
        drawKey("S", x + size + gap, y + size + gap, KeyboardState.s(), accent, size);
        drawKey("D", x + (size + gap) * 2, y + size + gap, KeyboardState.d(), accent, size);
        drawKey("SPACE", x, y + (size + gap) * 2, KeyboardState.space(), accent, size * 3 + gap * 2);
        drawKey("LMB", x, y - size - gap, Mouse.isButtonDown(0), accent, size + 8);
        drawKey("RMB", x + size + 10, y - size - gap, Mouse.isButtonDown(1), accent, size + 8);
    }

    private void drawKey(String label, int x, int y, boolean pressed, int accent, int width) {
        int bg = pressed ? (0x99000000 | (accent & 0xFFFFFF)) : 0x990D1020;
        Gui.drawRect(x, y, x + width, y + 18, bg);
        int textWidth = mc.fontRendererObj.getStringWidth(label);
        mc.fontRendererObj.drawStringWithShadow(label, x + (width - textWidth) / 2, y + 5, 0xFFFFFF);
    }

    private static class KeyboardState {
        static boolean w() { return org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_W); }
        static boolean a() { return org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_A); }
        static boolean s() { return org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_S); }
        static boolean d() { return org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_D); }
        static boolean space() { return org.lwjgl.input.Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_SPACE); }
    }
}
