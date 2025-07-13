package de.stylextv.maple.util;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class RegistryUtil {
	
	public static Block getBlock(String id) {
		Identifier i = idFromString(id);
		
		Optional<Block> o = Optional.ofNullable(Registries.BLOCK.get(i));
		
		return o.orElse(null);
	}
	
	public static EntityType<?> getEntityType(String id) {
		Identifier i = idFromString(id);
		
		Optional<EntityType<?>> o = Optional.ofNullable(Registries.ENTITY_TYPE.get(i));
		
		return o.orElse(null);
	}
	
	private static Identifier idFromString(String s) {
		return Identifier.tryParse(s);
	}

}
