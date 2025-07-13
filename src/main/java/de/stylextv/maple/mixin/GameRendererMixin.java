package de.stylextv.maple.mixin;

import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.TickEvent;
import net.minecraft.client.render.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	
	@Inject(method = "render", at = @At("HEAD"))
	private void render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		EventBus.onEvent(new TickEvent(TickEvent.Type.RENDER));
	}
	
}
