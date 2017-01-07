package minechem.common.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import java.util.List;

import com.google.common.collect.Lists;

import minechem.common.Compendium;
import minechem.common.block.BlockBase;
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
import minechem.common.util.LogHelper;

@ObjectHolder(Compendium.Naming.id)
@Mod.EventBusSubscriber(modid = Compendium.Naming.id)
public class BlockRegistry {

	static final List<BlockBase> registeredBlocks = Lists.newArrayList();

	@ObjectHolder(Compendium.Naming.opticalMicroscope)
	public static final BlockMicroscope opticalMicroscope = null;
	@ObjectHolder(Compendium.Naming.electrolysis)
	public static final BlockElectrolysis electrolysis = null;
	@ObjectHolder(Compendium.Naming.electricCrucible)
	public static final BlockCrucible electricCrucible = null;
	@ObjectHolder(Compendium.Naming.centrifuge)
	public static final BlockCentrifuge centrifuge = null;
	@ObjectHolder(Compendium.Naming.light)
	public static final BlockLight light = null;
	@ObjectHolder(Compendium.Naming.redstone)
	public static final BlockRedstone redstone = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		LogHelper.debug("Registering Blocks...");
		
		register(new BlockLight());
		register(new BlockRedstone());

		register(new BlockCentrifuge(), TileCentrifuge.class);
		register(new BlockCrucible(), TileCrucible.class);
		register(new BlockElectrolysis(), TileElectrolysis.class);
		register(new BlockMicroscope(), TileMicroscope.class);
		
		event.getRegistry().registerAll(registeredBlocks.toArray(new Block[registeredBlocks.size()]));
	}

	private static <T extends BlockBase> T register(T block) {
		registeredBlocks.add(block);
		Item itemBlock = block.getItemBlock();
		if(itemBlock != null) {
			ItemRegistry.registeredItems.add(itemBlock);
		}
		return block;
	}

	private static <T extends BlockBase> T register(T block, Class<? extends TileEntity> tileEntityClass) {
		T block_ = register(block);
		ResourceLocation resourceLocation = block.getRegistryName();
		GameRegistry.registerTileEntity(tileEntityClass, resourceLocation.getResourceDomain() + ":tileentity." + resourceLocation.getResourcePath());
		return block_;
	}
}
