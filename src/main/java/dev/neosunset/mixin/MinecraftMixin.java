package dev.neosunset.mixin;

import com.mojang.renderpearl.api.device.GpuSurface;
import com.mojang.renderpearl.api.device.GpuSurface.PresentMode;
import dev.neosunset.PresentationSettings;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
abstract class MinecraftMixin {
    @Shadow @Final private GpuSurface windowSurface;
    @Unique private boolean neosunset$useMailbox;

    @Inject(method = "renderFrame", at = @At("HEAD"))
    private void neosunset$trackMailboxEligibility(boolean tick, CallbackInfo callback) {
        Minecraft client = (Minecraft) (Object) this;
        if (windowSurface == null || windowSurface.isAcquired()) {
            return;
        }
        boolean useMailbox = PresentationSettings.eligible(client)
                && windowSurface.supportedPresentModes().contains(PresentMode.MAILBOX);
        if (useMailbox != neosunset$useMailbox) {
            neosunset$useMailbox = useMailbox;
            client.invalidateSurfaceConfiguration();
        }
    }

    @ModifyArg(method = "renderFrame", at = @At(value = "INVOKE",
            target = "Lcom/mojang/renderpearl/api/device/GpuSurface;configure(Lcom/mojang/renderpearl/api/device/GpuSurface$Configuration;)V"))
    private GpuSurface.Configuration neosunset$preferMailbox(GpuSurface.Configuration configuration) {
        if (neosunset$useMailbox && configuration.presentMode() == PresentMode.IMMEDIATE) {
            return new GpuSurface.Configuration(configuration.width(), configuration.height(), PresentMode.MAILBOX);
        }
        return configuration;
    }
}
