package minechem.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.common.block.BlockCentrifuge;
import minechem.common.block.BlockCrucible;
import minechem.common.block.BlockElectrolysis;
import minechem.common.block.BlockLight;
import minechem.common.block.BlockMicroscope;
import minechem.common.block.BlockRedstone;
import minechem.common.tile.TileCentrifuge;
import minechem.common.tile.TileCrucible;
import minechem.common.tile.TileElectrolysis;
import minechem.common.tile.TileMicroscope;

public class BlockRegistry {

	public static BlockMicroscope opticalMicroscope;
	public static BlockElectrolysis electrolysis;
	public static BlockCrucible electricCrucible;
	public static BlockCentrifuge centrifuge;
	public static BlockLight light;
	public static BlockRedstone redstone;

	public static void init() {
		light = register(new BlockLight(), false);
		redstone = register(new BlockRedstone(), false);

		centrifuge = register(new BlockCentrifuge(), TileCentrifuge.class);
		electricCrucible = register(new BlockCrucible(), TileCrucible.class);
		electrolysis = register(new BlockElectrolysis(), TileElectrolysis.class);
		opticalMicroscope = register(new BlockMicroscope(), TileMicroscope.class);
	}

	private static <T extends Block> T register(T block, boolean registerItem) {
		GameRegistry.register(block);
		if (registerItem) {
			GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
		return block;
	}

	private static <T extends Block> T register(T block) {
		return register(block, true);
	}

	private static <T extends Block> T register(T block, Class<? extends TileEntity> tileEntityClass) {
		T block_ = register(block, true);
		ResourceLocation resourceLocation = block.getRegistryName();
		GameRegistry.registerTileEntity(tileEntityClass, resourceLocation.getResourceDomain() + ":tileentity." + resourceLocation.getResourcePath());
		return block_;
	}
}
