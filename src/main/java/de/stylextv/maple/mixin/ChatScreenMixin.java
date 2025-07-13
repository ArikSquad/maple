package de.stylextv.maple.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.stylextv.maple.event.EventBus;
import de.stylextv.maple.event.events.ClientChatEvent;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

	@Inject(method = "sendMessage", at = @At("HEAD"), cancellable = true)
	private void sendChatMessage(String chatText, boolean addToHistory, CallbackInfo ci) {
		ClientChatEvent event = new ClientChatEvent(chatText);
		EventBus.onEvent(event);
		if (event.isCanceled()) ci.cancel();
	}
}