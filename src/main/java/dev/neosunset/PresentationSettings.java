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

    private PresentationSettings() {
    }

    public static OptionInstance<Boolean> option(Minecraft client) {
        if (option == null) {
            Path config = client.gameDirectory.toPath().resolve("config/neosunset-presentation.txt");
            option = OptionInstance.createBoolean("neosunset.options.presentation",
                    OptionInstance.cachedConstantTooltip(
                            Component.translatable("neosunset.options.presentation.tooltip")),
                    load(config), value -> save(config, value));
        }
        return option;
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

    private static boolean load(Path config) {
        try (var input = Files.newInputStream(config)) {
            return Arrays.equals(input.readNBytes(ENABLED.length + 1), ENABLED);
        } catch (NoSuchFileException ignored) {
            return false;
        } catch (IOException error) {
            LOGGER.log(System.Logger.Level.WARNING, "Could not read NeoSunset settings", error);
            return false;
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
