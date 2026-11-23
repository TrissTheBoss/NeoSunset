package dev.neosunset.mixin;

import com.mojang.blaze3d.platform.Window;
import dev.neosunset.DisplayChanges;
import org.lwjgl.sdl.SDL_Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.lwjgl.sdl.SDLEvents.*;

@Mixin(Window.class)
abstract class WindowMixin {
    @Inject(method = "handleEvent", at = @At("TAIL"))
    private void neosunset$onWindowEvent(SDL_Event event, CallbackInfo callback) {
        switch (event.type()) {
            case SDL_EVENT_WINDOW_DISPLAY_CHANGED, SDL_EVENT_WINDOW_PIXEL_SIZE_CHANGED,
                 SDL_EVENT_WINDOW_FOCUS_GAINED, SDL_EVENT_WINDOW_FOCUS_LOST,
                 SDL_EVENT_WINDOW_ENTER_FULLSCREEN, SDL_EVENT_WINDOW_LEAVE_FULLSCREEN ->
                    DisplayChanges.invalidate();
            default -> {
            }
        }
    }
}
