package dev.neosunset.mixin;

import com.mojang.renderpearl.api.device.GpuSurface.Configuration;
import com.mojang.renderpearl.api.device.GpuSurface.PresentMode;
import com.mojang.renderpearl.backend.vulkan.VulkanGpuSurface;
import dev.neosunset.PresentRate;
import dev.neosunset.PresentationSettings;
import dev.neosunset.ProfileCompatibility;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(VulkanGpuSurface.class)
abstract class VulkanGpuSurfaceMixin {
    @Unique private static final Logger NEOSUNSET_LOGGER = LoggerFactory.getLogger("NeoSunset");
    @Unique private String neosunset$lastModeDiagnostic;

    @Shadow public abstract Collection<PresentMode> supportedPresentModes();

    @Inject(method = "configure(Lcom/mojang/renderpearl/api/device/GpuSurface$Configuration;)V", at = @At("TAIL"))
    private void neosunset$logPresentMode(Configuration configuration, CallbackInfo callback) {
        Minecraft client = Minecraft.getInstance();
        String diagnostic = configuration.presentMode() + "; supported=" + supportedPresentModes();
        if (client != null && client.options != null && client.getWindow() != null) {
            var tracker = client.getFramerateLimitTracker();
            diagnostic += "; eligible=" + PresentationSettings.eligible(client)
                    + ", enabled=" + PresentationSettings.option(client).get()
                    + ", verifiedProfile=" + ProfileCompatibility.isVerified()
                    + ", focused=" + client.getWindow().isFocused()
                    + ", vsync=" + client.options.enableVsync().get()
                    + ", selectedCap=" + client.options.framerateLimit().get()
                    + ", effectiveCap=" + (tracker == null ? "unavailable" : Integer.toString(tracker.getFramerateLimit()));
        }
        if (!diagnostic.equals(neosunset$lastModeDiagnostic)) {
            neosunset$lastModeDiagnostic = diagnostic;
            NEOSUNSET_LOGGER.info("Vulkan swapchain present mode: {}", diagnostic);
        }
    }

    @Inject(method = "present()V", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lorg/lwjgl/vulkan/KHRSwapchain;vkQueuePresentKHR(Lorg/lwjgl/vulkan/VkQueue;Lorg/lwjgl/vulkan/VkPresentInfoKHR;)I"))
    private void neosunset$countPresentAttempt(CallbackInfo callback) {
        PresentRate.CLIENT.recordAttempt();
    }
}
