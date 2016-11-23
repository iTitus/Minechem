package minechem.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.apparatus.tier1.centrifuge.CentrifugeBlock;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleBlock;
import minechem.apparatus.tier1.electrolysis.ElectrolysisBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.blocks.BlockLight;
import minechem.blocks.BlockRedstone;

public class BlockRegistry {

	public static OpticalMicroscopeBlock opticalMicroscope;
	public static ElectrolysisBlock electrolysisBlock;
	public static ElectricCrucibleBlock electricCrucibleBlock;
	public static CentrifugeBlock centrifugeBlock;
	public static BlockLight blockLight;
	public static BlockRedstone blockRedstone;

	public static void init() {
		// opticalMicroscope = register(new OpticalMicroscopeBlock());
		// GameRegistry.registerTileEntity(OpticalMicroscopeTileEntity.class,
		// Compendium.Naming.opticalMicroscope + "TileEntity");
		//
		// electricCrucibleBlock = register(new ElectricCrucibleBlock());
		// GameRegistry.registerTileEntity(ElectricCrucibleTileEntity.class,
		// Compendium.Naming.electricCrucible + "TileEntity");

		centrifugeBlock = register(new CentrifugeBlock(), CentrifugeTileEntity.class);

		// electrolysisBlock = register(new ElectrolysisBlock());
		// GameRegistry.registerTileEntity(ElectrolysisTileEntity.class,
		// Compendium.Naming.electrolysis + "TileEntity");

		blockLight = register(new BlockLight(), false);

		blockRedstone = register(new BlockRedstone(), false);
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
