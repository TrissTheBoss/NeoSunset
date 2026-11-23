package dev.neosunset.mixin;

import com.mojang.renderpearl.backend.opengl.GlSurface;
import dev.neosunset.PresentationSettings;
import dev.neosunset.ProfileCompatibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Files;
import java.util.Arrays;

@Mixin(Minecraft.class)
abstract class ClientSmokeMixin {
    @Inject(method = "renderFrame", at = @At("TAIL"), require = 1)
    private void neosunset$verifyClient(boolean tick, CallbackInfo callback) throws Exception {
        Minecraft client = (Minecraft) (Object) this;
        Screen parent = client.gui.screen();
        if (client.gui.overlay() != null || parent == null) {
            return;
        }
        verify(Arrays.stream(GlSurface.class.getDeclaredMethods())
                .anyMatch(method -> method.getName().contains("neosunset$swapWhenDue")),
                "OpenGL swap hook did not apply");
        verify(Arrays.stream(Minecraft.class.getDeclaredMethods())
                .anyMatch(method -> method.getName().contains("neosunset$preferMailbox")),
                "Vulkan mode hook did not apply");

        VideoSettingsScreen screen = new VideoSettingsScreen(parent, client, client.options);
        client.gui.setScreen(screen);
        var option = PresentationSettings.option(client);
        verify(screen.children().stream().anyMatch(child ->
                        child instanceof OptionsList list && list.findOption(option) != null),
                "Video Settings did not show the option");
        verify(ProfileCompatibility.isVerified(), "A clean loader profile was not recognized");
        verify(!option.get(), "The setting must default to off");

        client.options.framerateLimit().set(120);
        client.options.enableVsync().set(false);
        option.set(true);
        verify(!PresentationSettings.eligible(client), "A finite limit must block presentation changes");
        var config = client.gameDirectory.toPath().resolve("config/neosunset-presentation.txt");
        verify(Files.readString(config).equals("true\n"), "Opt-in was not saved");
        option.set(false);
        verify(Files.readString(config).equals("false\n"), "Opt-out was not saved");
        System.out.println("NEOSUNSET_CLIENT_SMOKE_PASS");
        client.stop();
    }

    private static void verify(boolean condition, String detail) {
        if (!condition) {
            throw new IllegalStateException(detail);
        }
    }
}
