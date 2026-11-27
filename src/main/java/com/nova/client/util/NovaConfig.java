package com.nova.client.util;

import com.nova.client.NovaClient;
import net.minecraft.client.Minecraft;
import java.io.*;
import java.util.Properties;

public final class NovaConfig {
    private static final Properties DATA = new Properties();
    private static File file() {
        return new File(Minecraft.getMinecraft().mcDataDir, "config/novaclient.properties");
    }
    private NovaConfig() {}

    public static void load() {
        File f = file();
        if (!f.exists()) return;
        try (FileInputStream in = new FileInputStream(f)) { DATA.load(in); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public static String get(String key, String fallback) {
        return DATA.getProperty(key, fallback);
    }

    public static void put(String key, String value) {
        DATA.setProperty(key, value);
        save();
    }

    public static int getColor() {
        try { return Integer.parseInt(get("accent", "1122867")); }
        catch (NumberFormatException e) { return 0x6633FF; }
    }

    public static void save() {
        File f = file();
        File parent = f.getParentFile();
        if (!parent.exists()) parent.mkdirs();
        try (FileOutputStream out = new FileOutputStream(f)) {
            DATA.store(out, "NOVA CLIENT settings");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
