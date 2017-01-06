package minechem.common.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.minecraft.item.ItemStack;

import minechem.common.chemical.ChemicalBase;
import minechem.common.chemical.process.ChemicalProcess;
import minechem.common.chemical.process.ChemicalProcessComboType;
import minechem.common.chemical.process.ChemicalProcessType;
import minechem.common.util.collections.ItemStackMap;

public class ChemicalProcessRegistry {
	private static ChemicalProcessRegistry instance;
	private ItemStackMap<Map<ChemicalProcessType, Set<ChemicalProcess>>> itemProcessMap;
	private Map<ChemicalProcessType, String> processTypes;
	private Map<String, ChemicalProcessType> processNames;

	private ChemicalProcessRegistry() {
		itemProcessMap = new ItemStackMap<Map<ChemicalProcessType, Set<ChemicalProcess>>>();
		processNames = new TreeMap<String, ChemicalProcessType>();
		processTypes = new HashMap<ChemicalProcessType, String>();
	}

	public static ChemicalProcessRegistry getInstance() {
		if (instance == null) {
			instance = new ChemicalProcessRegistry();
		}
		return instance;
	}

	/**
	 * Add a {@link minechem.common.chemical.process.ChemicalProcessType} with given name to the registry
	 *
	 * @param name name for the process
	 * @return the generated ChemicalProcessType, null if the given name already exists, is null or is empty
	 */
	public ChemicalProcessType addProcess(String name) {
		if (name != null && !name.isEmpty()) {
			ChemicalProcessType result = new ChemicalProcessType(name);
			if (processTypes.get(result) == null && processNames.get(name) == null) {
				processTypes.put(result, name.toLowerCase());
				processNames.put(name.toLowerCase(), result);
				return result;
			}
		}
		return null;
	}

	/**
	 * Add a {@link minechem.common.chemical.process.ChemicalProcessComboType} with given name to the registry A child process will be created when the name does not exist in the registry
	 *
	 * @param childTypes childProcesses
	 * @return the generated ChemicalProcessComboType, null if the given set already exists, is null or less then two entry's
	 */
	public ChemicalProcessComboType addProcess(ChemicalProcessType... childTypes) {
		if (childTypes != null) {
			List<ChemicalProcessType> list = Arrays.asList(childTypes);
			list.removeAll(Arrays.asList(new ChemicalProcessType[]
					{
							null
					})); // no nulls allowed
			if (list.size() > 1) {
				Collections.sort(list);
				ChemicalProcessComboType result = new ChemicalProcessComboType(list.toArray(new ChemicalProcessType[list.size()]));
				if (processTypes.get(result) == null && processNames.get(result.getName()) == null) {
					processTypes.put(result, result.getName().toLowerCase());
					processNames.put(result.getName().toLowerCase(), result);
					return result;
				}
			}
		}
		return null;
	}

	/**
	 * Get the {@link minechem.common.chemical.process.ChemicalProcessType} for the given process name
	 *
	 * @param name the process name
	 * @return the requested ChemicalProcessType or null if nothing is registered under that name
	 */
	public ChemicalProcessType getProcess(String name) {
		return processNames.get(name.toLowerCase());
	}

	/**
	 * Add a {@link minechem.common.chemical.process.ChemicalProcess} to an {@link net.minecraft.item.ItemStack}
	 *
	 * @param stack the itemStack
	 * @param process   the process
	 */
	public void addItemProcess(ItemStack stack, ChemicalProcess process) {
		Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes = itemProcessMap.get(stack);
		if (chemicalTypes == null) {
			chemicalTypes = new HashMap<ChemicalProcessType, Set<ChemicalProcess>>();
		}
		Set<ChemicalProcess> processes = chemicalTypes.get(process.getType());
		if (processes == null) {
			processes = new HashSet<ChemicalProcess>();
		}
		processes.add(process);
		chemicalTypes.put(process.getType(), processes);
		itemProcessMap.put(stack, chemicalTypes);
	}

	/**
	 * Get the output of given {@link net.minecraft.item.ItemStack} using given {@link minechem.common.chemical.process.ChemicalProcessType} and the given level
	 *
	 * @param stack   the input ItemStack
	 * @param processType the ChemicalProcessType
	 * @param level       the level of the process
	 * @return the output in an array
	 */
	public ChemicalBase[] getOutput(ItemStack stack, ChemicalProcessType processType, int level) {
		if (stack.isEmpty()) {
			return ChemicalProcess.empty;
		}
		Map<ChemicalProcessType, Set<ChemicalProcess>> chemicalTypes = itemProcessMap.get(stack);
		Set<ChemicalProcess> processes = chemicalTypes.get(processType);
		if (processType == null) {
			return ChemicalProcess.empty;
		}
		List<ChemicalBase> output = new ArrayList<ChemicalBase>();
		for (ChemicalProcess process : processes) {
			output.addAll(Arrays.asList(process.getOutput(processType, level)));
		}
		return output.toArray(new ChemicalBase[output.size()]);
	}
}
