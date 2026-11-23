package dev.neosunset.mixin;

import com.mojang.renderpearl.backend.vulkan.VulkanGpuSurface;
import dev.neosunset.PresentRate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VulkanGpuSurface.class)
abstract class VulkanGpuSurfaceMixin {
    @Inject(method = "present()V", at = @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lorg/lwjgl/vulkan/KHRSwapchain;vkQueuePresentKHR(Lorg/lwjgl/vulkan/VkQueue;Lorg/lwjgl/vulkan/VkPresentInfoKHR;)I"))
    private void neosunset$countPresentAttempt(CallbackInfo callback) {
        PresentRate.CLIENT.recordAttempt();
    }
}
