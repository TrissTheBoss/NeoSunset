package dev.neosunset.mixin;

import com.mojang.renderpearl.api.device.GpuSurface.Configuration;
import com.mojang.renderpearl.api.device.GpuSurface.PresentMode;
import com.mojang.renderpearl.backend.vulkan.VulkanGpuSurface;
import dev.neosunset.PresentRate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VulkanGpuSurface.class)
abstract class VulkanGpuSurfaceMixin {
    @Unique private static final System.Logger NEOSUNSET_LOGGER = System.getLogger("NeoSunset");
    @Unique private PresentMode neosunset$lastLoggedMode;

    @Inject(method = "configure(Lcom/mojang/renderpearl/api/device/GpuSurface$Configuration;)V", at = @At("TAIL"))
    private void neosunset$logPresentMode(Configuration configuration, CallbackInfo callback) {
        PresentMode mode = configuration.presentMode();
        if (mode != neosunset$lastLoggedMode) {
            neosunset$lastLoggedMode = mode;
            NEOSUNSET_LOGGER.log(System.Logger.Level.INFO, "Vulkan swapchain present mode: {0}", mode);
        }
    }
    @Inject(method = "present()V", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lorg/lwjgl/vulkan/KHRSwapchain;vkQueuePresentKHR(Lorg/lwjgl/vulkan/VkQueue;Lorg/lwjgl/vulkan/VkPresentInfoKHR;)I"))
    private void neosunset$countPresentAttempt(CallbackInfo callback) {
        PresentRate.CLIENT.recordAttempt();
    }
}
