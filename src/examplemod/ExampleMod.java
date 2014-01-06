package examplemod;

import org.apache.commons.lang3.ObjectUtils;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import openmods.api.IProxy;
import openmods.config.RegisterBlock;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import examplemod.common.block.BlockWith24Rotations;
import examplemod.common.block.BlockWith4Rotations;
import examplemod.common.block.BlockWith6Rotations;
import examplemod.common.tileentity.TileEntityWith24Rotations;
import examplemod.common.tileentity.TileEntityWith4Rotations;
import examplemod.common.tileentity.TileEntityWith6Rotations;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(serverSideRequired = true, clientSideRequired = true, channels = { ModInfo.ID })
public class ExampleMod {

	@Instance(value = "ExampleMod")
	public static ExampleMod instance;

	@SidedProxy(clientSide = ModInfo.PROXY_CLIENT, serverSide = ModInfo.PROXY_SERVER)
	public static IProxy proxy;

	public static int renderId;

	public static class Blocks {

		@RegisterBlock(name = "block4rotations", tileEntity = TileEntityWith4Rotations.class)
		public static BlockWith4Rotations block4Rotations;

		@RegisterBlock(name = "block6rotations", tileEntity = TileEntityWith6Rotations.class)
		public static BlockWith6Rotations block6Rotations;

		@RegisterBlock(name = "block24rotations", tileEntity = TileEntityWith24Rotations.class)
		public static BlockWith24Rotations block24Rotations;

	}

	public static class Items {

	}

	public static CreativeTabs tabExampleMod = new CreativeTabs("tabExampleMod") {
		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(ObjectUtils.firstNonNull(Item.appleGold, Item.fishRaw), 1, 0);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());
		Config.readConfig(configFile);
		if (configFile.hasChanged()) {
			configFile.save();
		}
		Config.register();

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent evt) {
		proxy.init();
		proxy.registerRenderInformation();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {

	}
}