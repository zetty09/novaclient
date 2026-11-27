package com.nova.client.module;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleManager {
    private final Map<String, NovaModule> modules = new LinkedHashMap<String, NovaModule>();

    public void registerDefaults() {
        add(new NovaModule("HUD", true));
        add(new NovaModule("Keystrokes", true));
        add(new NovaModule("FPS Counter", true));
        add(new NovaModule("Coordinates", true));
        add(new NovaModule("CPS Counter", true));
        add(new NovaModule("Minimal Effects", false));
    }

    public void add(NovaModule module) { modules.put(module.getName(), module); }
    public NovaModule get(String name) { return modules.get(name); }
    public Collection<NovaModule> all() { return modules.values(); }
}
