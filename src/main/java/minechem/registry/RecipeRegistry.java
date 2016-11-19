package minechem.registry;

import net.minecraftforge.fml.common.registry.GameRegistry;

import minechem.recipes.AugmentRecipe;
import minechem.recipes.WrapperRecipe;

public class RecipeRegistry {

	private static final RecipeRegistry recipes = new RecipeRegistry();

	public static RecipeRegistry getInstance() {
		return recipes;
	}

	public void init() {
		GameRegistry.addRecipe(new AugmentRecipe());
		GameRegistry.addRecipe(new WrapperRecipe());
	}

}
