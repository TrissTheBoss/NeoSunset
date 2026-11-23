package dev.neosunset.mixin;

import com.mojang.blaze3d.platform.VideoMode;
import com.mojang.renderpearl.backend.opengl.GlSurface;
import dev.neosunset.DisplayChanges;
import dev.neosunset.FramePolicy;
import dev.neosunset.PresentationSettings;
import dev.neosunset.SwapCadence;
import net.minecraft.client.Minecraft;
import org.lwjgl.sdl.SDLVideo;
import org.lwjgl.system.MemoryStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GlSurface.class)
abstract class GlSurfaceMixin {
    @Shadow @Final private long windowHandle;
    @Unique private final SwapCadence neosunset$cadence = new SwapCadence();
    @Unique private boolean neosunset$wasEligible;
    @Unique private int neosunset$displayGeneration;
    @Unique private long neosunset$lastProbe;

    @Inject(method = "present", at = @At(value = "INVOKE",
            target = "Lorg/lwjgl/sdl/SDLVideo;SDL_GL_SwapWindow(J)Z"), cancellable = true)
    private void neosunset$swapWhenDue(CallbackInfo callback) {
        Minecraft client = Minecraft.getInstance();
        if (!PresentationSettings.eligible(client) || client.getWindow().handle() != windowHandle
                || !FramePolicy.isUnlimited(client.gameRenderer.gameRenderState().framerateLimit)) {
            neosunset$wasEligible = false;
            return;
        }

        long now = System.nanoTime();
        int generation = DisplayChanges.generation();
        if (!neosunset$wasEligible || generation != neosunset$displayGeneration
                || now - neosunset$lastProbe >= 250_000_000L) {
            boolean displayChanged = !neosunset$wasEligible || generation != neosunset$displayGeneration;
            neosunset$wasEligible = true;
            neosunset$displayGeneration = generation;
            neosunset$lastProbe = now;
            try (MemoryStack stack = MemoryStack.stackPush()) {
                var interval = stack.mallocInt(1);
                VideoMode mode = SDLVideo.SDL_GL_GetSwapInterval(interval) && interval.get(0) == 0
                        ? client.getWindow().getActiveVideoMode() : null;
                neosunset$cadence.refresh(mode == null ? 0 : mode.getRefreshRate(), displayChanged);
            }
        }

        // Vanilla already rendered, blitted and submitted this frame. Leave surface bookkeeping intact.
        if (!neosunset$cadence.shouldSwap(now)) {
            callback.cancel();
        }
    }
}
