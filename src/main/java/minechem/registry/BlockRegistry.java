package minechem.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.apparatus.tier1.centrifuge.CentrifugeBlock;
import minechem.apparatus.tier1.centrifuge.CentrifugeTileEntity;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleBlock;
import minechem.apparatus.tier1.electricCrucible.ElectricCrucibleTileEntity;
import minechem.apparatus.tier1.electrolysis.ElectrolysisBlock;
import minechem.apparatus.tier1.electrolysis.ElectrolysisTileEntity;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeBlock;
import minechem.apparatus.tier1.opticalMicroscope.OpticalMicroscopeTileEntity;
import minechem.blocks.BlockLight;
import minechem.blocks.BlockRedstone;

public class BlockRegistry {

	public static OpticalMicroscopeBlock opticalMicroscope;
	public static ElectrolysisBlock electrolysis;
	public static ElectricCrucibleBlock electricCrucible;
	public static CentrifugeBlock centrifuge;
	public static BlockLight light;
	public static BlockRedstone redstone;

	public static void init() {
		light = register(new BlockLight(), false);
		redstone = register(new BlockRedstone(), false);

		centrifuge = register(new CentrifugeBlock(), CentrifugeTileEntity.class);
		electricCrucible = register(new ElectricCrucibleBlock(), ElectricCrucibleTileEntity.class);
		electrolysis = register(new ElectrolysisBlock(), ElectrolysisTileEntity.class);
		opticalMicroscope = register(new OpticalMicroscopeBlock(), OpticalMicroscopeTileEntity.class);
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
