package dev.neosunset;

import com.mojang.blaze3d.platform.FramerateLimitTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public final class PresentationSettings {
    private static final System.Logger LOGGER = System.getLogger("NeoSunset");
    private static final byte[] ENABLED = "true\n".getBytes(StandardCharsets.US_ASCII);
    private static OptionInstance<Boolean> option;
    private static OptionInstance<Boolean> overlayOption;

    private PresentationSettings() {
    }

    public static OptionInstance<Boolean> option(Minecraft client) {
        if (option == null) {
            option = createOption(client, "presentation", false);
        }
        return option;
    }

    public static OptionInstance<Boolean> overlayOption(Minecraft client) {
        if (overlayOption == null) {
            overlayOption = createOption(client, "overlay", true);
        }
        return overlayOption;
    }

    private static OptionInstance<Boolean> createOption(Minecraft client, String name, boolean defaultValue) {
        Path config = client.gameDirectory.toPath().resolve("config/neosunset-" + name + ".txt");
        String key = "neosunset.options." + name;
        return OptionInstance.createBoolean(key,
                OptionInstance.cachedConstantTooltip(Component.translatable(key + ".tooltip")),
                load(config, defaultValue), value -> save(config, value));
    }

    public static boolean eligible(Minecraft client) {
        if (client == null || client.options == null || !option(client).get()
                || client.options.enableVsync().get()) {
            return false;
        }
        FramerateLimitTracker tracker = client.getFramerateLimitTracker();
        return tracker != null && FramePolicy.eligible(true, ProfileCompatibility.isVerified(),
                client.getWindow().isFocused(), false, client.options.framerateLimit().get(),
                tracker.getFramerateLimit());
    }

    private static boolean load(Path config, boolean defaultValue) {
        try (var input = Files.newInputStream(config)) {
            return Arrays.equals(input.readNBytes(ENABLED.length + 1), ENABLED);
        } catch (NoSuchFileException ignored) {
            return defaultValue;
        } catch (IOException error) {
            LOGGER.log(System.Logger.Level.WARNING, "Could not read NeoSunset settings", error);
            return defaultValue;
        }
    }

    private static void save(Path config, boolean enabled) {
        try {
            Files.createDirectories(config.getParent());
            Path pending = Files.createTempFile(config.getParent(), "neosunset-", ".tmp");
            try {
                Files.writeString(pending, enabled ? "true\n" : "false\n", StandardCharsets.US_ASCII);
                try {
                    Files.move(pending, config, StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.ATOMIC_MOVE);
                } catch (AtomicMoveNotSupportedException ignored) {
                    Files.move(pending, config, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
                Files.deleteIfExists(pending);
            }
        } catch (IOException error) {
            LOGGER.log(System.Logger.Level.WARNING, "Could not save NeoSunset settings", error);
        }
    }
}
