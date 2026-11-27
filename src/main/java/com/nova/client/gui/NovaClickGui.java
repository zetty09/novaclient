package com.nova.client.gui;

import com.nova.client.NovaClient;
import com.nova.client.module.NovaModule;
import com.nova.client.util.NovaConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NovaClickGui extends GuiScreen {
    private final List<GuiButton> moduleButtons = new ArrayList<GuiButton>();
    private int accent;

    @Override
    public void initGui() {
        buttonList.clear();
        moduleButtons.clear();
        accent = NovaConfig.getColor();
        int y = 48;
        int id = 0;
        for (NovaModule module : NovaClient.MODULES.all()) {
            GuiButton b = new GuiButton(id++, width / 2 - 100, y, 200, 20, label(module));
            buttonList.add(b);
            moduleButtons.add(b);
            y += 24;
        }
        buttonList.add(new GuiButton(100, width / 2 - 100, y + 5, 98, 20, "TXT Creator"));
        buttonList.add(new GuiButton(101, width / 2 + 2, y + 5, 98, 20, "Accent: next"));
        buttonList.add(new GuiButton(102, width / 2 - 100, y + 30, 200, 20, "Close"));
    }

    private String label(NovaModule m) {
        return m.getName() + ": " + (m.isEnabled() ? "ON" : "OFF");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id >= 0 && button.id < NovaClient.MODULES.all().size()) {
            List<NovaModule> list = new ArrayList<NovaModule>(NovaClient.MODULES.all());
            list.get(button.id).toggle();
            button.displayString = label(list.get(button.id));
        } else if (button.id == 100) {
            mc.displayGuiScreen(new NovaTxtEditor());
        } else if (button.id == 101) {
            int[] colors = {0x6633FF, 0x2588FF, 0xFF4444, 0x22BB66, 0x00CCCC, 0xFF66AA, 0xFF9900, 0xFFFFFF};
            int next = 0;
            for (int i = 0; i < colors.length; i++) if (colors[i] == accent) next = (i + 1) % colors.length;
            accent = colors[next];
            NovaConfig.put("accent", String.valueOf(accent));
        } else if (button.id == 102) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(width / 2 - 125, 22, width / 2 + 125, height - 20, 0xDD101321);
        drawCenteredString(fontRendererObj, "NOVA CLIENT", width / 2, 29, 0xFFFFFFFF);
        drawCenteredString(fontRendererObj, "Right Shift - Toggle GUI", width / 2, height - 14, 0xFFAAAAAA);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
