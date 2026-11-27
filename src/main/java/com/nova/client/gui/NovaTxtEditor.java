package com.nova.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class NovaTxtEditor extends GuiScreen {
    private GuiTextField editor;
    private String fileName = "notes.txt";
    private String status = "New document";
    private File directory;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        directory = new File(mc.mcDataDir, "NOVA/documents");
        if (!directory.exists()) directory.mkdirs();
        editor = new GuiTextField(0, fontRendererObj, 20, 55, width - 40, height - 100);
        editor.setMaxStringLength(32767);
        editor.setFocused(true);
        buttonList.clear();
        buttonList.add(new GuiButton(1, 20, 25, 70, 20, "Save"));
        buttonList.add(new GuiButton(2, 95, 25, 70, 20, "Open"));
        buttonList.add(new GuiButton(3, 170, 25, 70, 20, "New"));
        buttonList.add(new GuiButton(4, width - 90, 25, 70, 20, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) saveFile();
        if (button.id == 2) openDefault();
        if (button.id == 3) { editor.setText(""); fileName = "notes.txt"; status = "New document"; }
        if (button.id == 4) mc.displayGuiScreen(new NovaClickGui());
    }

    private void saveFile() {
        File target = new File(directory, fileName);
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(target), StandardCharsets.UTF_8)) {
            writer.write(editor.getText());
            status = "Saved: " + target.getName();
        } catch (IOException e) { status = "Save failed: " + e.getMessage(); }
    }

    private void openDefault() {
        File target = new File(directory, fileName);
        if (!target.isFile()) { status = "No notes.txt found"; return; }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(target), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (sb.length() > 0) sb.append('\n');
                sb.append(line);
            }
            editor.setText(sb.toString());
            status = "Opened: " + target.getName();
        } catch (IOException e) { status = "Open failed: " + e.getMessage(); }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            Keyboard.enableRepeatEvents(false);
            mc.displayGuiScreen(new NovaClickGui());
            return;
        }
        if (isCtrlKeyDown()) {
            if (keyCode == Keyboard.KEY_S) { saveFile(); return; }
            if (keyCode == Keyboard.KEY_A) { editor.setCursorPosition(editor.getText().length()); editor.setSelectionPos(0); return; }
            if (keyCode == Keyboard.KEY_C) { setClipboardString(editor.getSelectedText()); return; }
            if (keyCode == Keyboard.KEY_V) { editor.writeText(getClipboardString()); return; }
            if (keyCode == Keyboard.KEY_X) { setClipboardString(editor.getSelectedText()); editor.writeText(""); return; }
        }
        editor.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        editor.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() { editor.updateCursorCounter(); }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawRect(10, 10, width - 10, height - 10, 0xEE101321);
        drawCenteredString(fontRendererObj, "NOVA TXT CREATOR", width / 2, 12, 0xFF9977FF);
        editor.drawTextBox();
        drawString(fontRendererObj, status, 20, height - 30, 0xFFBBBBBB);
        drawString(fontRendererObj, "Characters: " + editor.getText().length(), width - 130, height - 30, 0xFFBBBBBB);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() { Keyboard.enableRepeatEvents(false); }

    @Override
    public boolean doesGuiPauseGame() { return false; }
}
