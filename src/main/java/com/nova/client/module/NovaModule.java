package com.nova.client.module;

public class NovaModule {
    private final String name;
    private boolean enabled;

    public NovaModule(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }
    public void toggle() { enabled = !enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}
