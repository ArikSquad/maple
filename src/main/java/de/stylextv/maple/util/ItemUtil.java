package de.stylextv.maple.util;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;

public class ItemUtil {
	
	public static float getToolScore(ItemStack stack, BlockState state) {
		float score = getDigSpeed(stack, state);
		
		if(stack.isDamageable() && score <= 1) {
			int cost = 1;
			try {
				cost = stack.get(DataComponentTypes.REPAIR_COST);
			} catch (NullPointerException ignored)  {

			}

			score = -cost - 1;
		}
		
		return score;
	}

	public static float getDigSpeed(ItemStack stack, BlockState state) {
		float f = stack.getMiningSpeedMultiplier(state);

		if (f <= 1) return f;

		ItemEnchantmentsComponent comp = stack.getEnchantments();
		int level = 0;
		if (comp != null) {
			for (var entry : comp.getEnchantments()) {
				if (entry.getKey().get().getRegistry().equals(net.minecraft.util.Identifier.of("minecraft:efficiency"))) {
					level = EnchantmentHelper.getLevel(entry, stack);
					break;
				}
			}
		}

		if (level > 0) f += level * level + 1;

		return f;
	}
}
