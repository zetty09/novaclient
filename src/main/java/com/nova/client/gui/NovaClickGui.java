
package com.nova.client.gui;

import com.nova.client.NovaClient;
import com.nova.client.module.NovaModule;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NovaClickGui extends GuiScreen {

    @Override
    public void initGui() {
        buttonList.clear();

        int y = 48;
        int id = 0;

        for (NovaModule module : NovaClient.MODULES.all()) {
            buttonList.add(new GuiButton(
                id++,
                width / 2 - 100,
                y,
                200,
                20,
                label(module)
            ));
            y += 24;
        }

        buttonList.add(new GuiButton(
            102, width / 2 - 100, y + 10, 200, 20, "Close"
        ));
    }

    private String label(NovaModule module) {
        return module.getName() + ": " +
            (module.isEnabled() ? "ON" : "OFF");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        List<NovaModule> modules =
            new ArrayList<NovaModule>(NovaClient.MODULES.all());

        if (button.id >= 0 && button.id < modules.size()) {
            NovaModule module = modules.get(button.id);
            module.toggle();
            button.displayString = label(module);
        } else if (button.id == 102) {
            mc.displayGuiScreen(null);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        drawRect(
            width / 2 - 125, 22,
            width / 2 + 125, height - 20,
            0xDD101321
        );

        drawCenteredString(
            fontRendererObj,
            "NOVA CLIENT",
            width / 2,
            29,
            0xFFFFFFFF
        );

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
