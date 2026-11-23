package dev.neosunset.mixin;

import dev.neosunset.PresentRate;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
abstract class HudMixin {
    @Shadow @Final private Minecraft minecraft;
    @Shadow private boolean isHidden;
    @Unique private int neosunset$fps = -1;
    @Unique private long neosunset$presentRate = -1;
    @Unique private String neosunset$fpsText = "FPS: --";
    @Unique private String neosunset$presentText = "Present attempts/s: --";

    @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V",
            at = @At("TAIL"))
    private void neosunset$showFrameRates(GuiGraphicsExtractor graphics, DeltaTracker delta, CallbackInfo callback) {
        long rate = PresentRate.CLIENT.sample(System.nanoTime());
        if (isHidden) {
            return;
        }
        int fps = minecraft.getFps();
        if (fps != neosunset$fps) {
            neosunset$fps = fps;
            neosunset$fpsText = "FPS: " + fps;
        }
        if (rate != neosunset$presentRate) {
            neosunset$presentRate = rate;
            neosunset$presentText = rate < 0 ? "Present attempts/s: --" : "Present attempts/s: " + rate;
        }
        graphics.text(minecraft.font, neosunset$fpsText, 4, 4, 0xFFFFFFFF, true);
        graphics.text(minecraft.font, neosunset$presentText, 4, 14, 0xFFFFFFFF, true);
    }
}
