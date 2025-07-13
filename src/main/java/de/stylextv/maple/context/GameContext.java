package de.stylextv.maple.context;

import java.io.File;

import de.stylextv.maple.mixin.MinecraftClientInvoker;
import de.stylextv.maple.mixin.RenderTickCounterDynamicAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.math.Vec3d;

public class GameContext {
	
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	
	public static MinecraftClient client() {
		return CLIENT;
	}
	
	public static MinecraftClientInvoker clientInvoker() {
		return (MinecraftClientInvoker) CLIENT;
	}
	
	public static File directory() {
		return CLIENT.runDirectory;
	}
	
	public static GameOptions options() {
		return CLIENT.options;
	}
	
	public static ClientPlayerInteractionManager interactionManager() {
		return CLIENT.interactionManager;
	}
	
	public static ChatHud chatHud() {
		return inGameHud().getChatHud();
	}
	
	public static InGameHud inGameHud() {
		return CLIENT.inGameHud;
	}
	
	public static TextRenderer textRenderer() {
		return CLIENT.textRenderer;
	}
	
	public static float tickDelta() {
		return CLIENT.getRenderTickCounter().getFixedDeltaTicks(); // todo: check if this is correct
	}

	public static float lastFrameDuration() {
		RenderTickCounterDynamicAccessor rtc =
				(RenderTickCounterDynamicAccessor) CLIENT.getRenderTickCounter();
		long lastTime = rtc.getLastTimeMillis();
		// calculate how much time has passed since the last tick, then scale by 0.05f (1/20)
		return (System.currentTimeMillis() - lastTime) * 0.05f;
	}
	
	public static Vec3d cameraPosition() {
		Camera camera = CLIENT.gameRenderer.getCamera();
		
		return camera.getPos();
	}
	
	public static boolean isIngame() {
		if(!WorldContext.isInWorld()) return false;
		
		if(!isInSinglePlayer() || isOpenToLan()) return true;
		
		return !CLIENT.isPaused();
	}
	
	public static boolean isOpenToLan() {
		IntegratedServer server = CLIENT.getServer();
		
		if(server == null) return false;
		
		return server.isRemote();
	}
	
	public static boolean isInSinglePlayer() {
		return CLIENT.isInSingleplayer();
	}
	
}
