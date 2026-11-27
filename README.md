# NOVA CLIENT 1.8.9

NOVA is delivered as a **client-side Forge 1.8.9 mod**, not as a standalone Minecraft launcher.

## Included in this starter project
- Forge 1.8.9 mod entry point
- Toggleable NOVA HUD
- Keystrokes display (WASD, Space, mouse buttons)
- FPS, coordinates and CPS display
- NOVA Click GUI opened with Right Shift
- Basic module toggles and color theme selection
- Local JSON-like properties configuration
- Built-in TXT editor opened from the Click GUI
- TXT file open/save and basic text editing
- Rich-text `.nova` format is reserved for a later implementation; ordinary `.txt` remains plain text

## Requirements
- JDK 8 (ForgeGradle 2.1 is legacy and may not work with newer Java versions)
- Gradle 2.7-compatible environment / ForgeGradle 2.1
- Internet access for the first dependency download

## Build
On Windows, install JDK 8 and run:
```bat
gradlew.bat setupDecompWorkspace
gradlew.bat build
```
The resulting mod JAR should be in `build/libs/`.

This environment did not compile or run the project. Treat the JAR as unverified until you build and test it locally.

## Install
1. Install Minecraft Forge 1.8.9.
2. Build the project.
3. Copy the generated JAR from `build/libs/` into `.minecraft/mods/`.
4. Start the Forge 1.8.9 profile.
5. Press Right Shift to open NOVA GUI.

## Controls
- Right Shift: open/close NOVA Click GUI
- In the GUI: toggle HUD / Keystrokes / FPS / Coordinates / CPS
- TXT Creator: open from the Tools category
- TXT editor: Ctrl+S saves, Ctrl+A selects all, Ctrl+V pastes, Ctrl+C copies

## Notes
- FPS display reports the game's current FPS; it does not guarantee an FPS increase.
- The performance module only reduces selected visual effects. It cannot safely promise a fixed FPS gain.
- This is a starter implementation. Features such as armor/potion panels, ping, full theme editor, formatted `.nova` documents, gradients, and custom inventory/sky rendering are not implemented yet.
