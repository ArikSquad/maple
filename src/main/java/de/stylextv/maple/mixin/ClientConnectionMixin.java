package de.stylextv.maple.mixin;

import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.PacketEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.listener.PacketListener;

@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
	
	@Inject(method = "channelRead0", at = @At("TAIL"))
	private <T extends PacketListener> void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo info) {
		ClientConnection connection = (ClientConnection) (Object) this;
		
		NetworkSide side = connection.getSide();
		
		if(side != NetworkSide.CLIENTBOUND) return;
		
		EventBus.onEvent(new PacketEvent(PacketEvent.Type.RECEIVED, packet));
	}
	
	@Inject(method = "sendImmediately", at = @At("HEAD"))
	private void sendImmediately(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
		ClientConnection connection = (ClientConnection) (Object) this;
		
		NetworkSide side = connection.getSide();
		
		if(side != NetworkSide.CLIENTBOUND) return;
		
		EventBus.onEvent(new PacketEvent(PacketEvent.Type.SENT, packet));
	}
	
}
