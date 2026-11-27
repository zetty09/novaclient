
package com.nova.client.util;

import net.minecraft.client.Minecraft;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class NovaConfig {

    private static final Properties DATA = new Properties();

    private NovaConfig() {}

    private static File file() {
        return new File(
            Minecraft.getMinecraft().mcDataDir,
            "config/novaclient.properties"
        );
    }

    public static void load() {
        File f = file();

        if (!f.isFile()) {
            return;
        }

        try (FileInputStream in = new FileInputStream(f)) {
            DATA.load(in);
        } catch (IOException e) {
            System.err.println("[NOVA] Nie udało się wczytać konfiguracji.");
            e.printStackTrace();
        }
    }

    public static String get(String key, String fallback) {
        return DATA.getProperty(key, fallback);
    }

    public static void put(String key, String value) {
        DATA.setProperty(key, value);
        save();
    }

    public static int getColor() {
        try {
            return Integer.parseInt(get("accent", "1122867"));
        } catch (NumberFormatException e) {
            return 0x6633FF;
        }
    }

    public static void save() {
        File f = file();
        File parent = f.getParentFile();

        if (!parent.exists() && !parent.mkdirs()) {
            System.err.println("[NOVA] Nie udało się utworzyć folderu config.");
            return;
        }

        try (FileOutputStream out = new FileOutputStream(f)) {
            DATA.store(out, "NOVA CLIENT settings");
        } catch (IOException e) {
            System.err.println("[NOVA] Nie udało się zapisać konfiguracji.");
            e.printStackTrace();
        }
    }
}
