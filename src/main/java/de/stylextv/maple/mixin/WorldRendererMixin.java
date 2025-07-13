package de.stylextv.maple.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.util.Handle;
import net.minecraft.util.profiler.Profiler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.RenderWorldEvent;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@Final
	@Shadow
	private BufferBuilderStorage bufferBuilders;

	@Inject(
			method = "method_62214", // this took me years to understand
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/render/BufferBuilderStorage;getEntityVertexConsumers()Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;",
					shift = At.Shift.AFTER
			)
	)
	private void render(Fog fog, RenderTickCounter renderTickCounter, Camera camera, Profiler profiler, Matrix4f matrix4f, Matrix4f matrix4f2, Handle handle, Handle handle2, boolean bl, Frustum frustum, Handle handle3, Handle handle4, CallbackInfo ci) {
		EventBus.onEvent(new RenderWorldEvent(RenderWorldEvent.Type.ENTITIES, new MatrixStack(), this.bufferBuilders.getEntityVertexConsumers()));
	}
	
}
