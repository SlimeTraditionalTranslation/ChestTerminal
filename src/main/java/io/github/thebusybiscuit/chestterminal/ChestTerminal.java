package io.github.thebusybiscuit.chestterminal;

//import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.chestterminal.items.AccessTerminal;
import io.github.thebusybiscuit.chestterminal.items.ExportBus;
import io.github.thebusybiscuit.chestterminal.items.ImportBus;
import io.github.thebusybiscuit.chestterminal.items.MilkyQuartz;
import io.github.thebusybiscuit.chestterminal.items.WirelessTerminal;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class ChestTerminal extends JavaPlugin implements Listener, SlimefunAddon {
	
	@Override
	public void onEnable() {
		
		// Setting up bStats
		//new Metrics(this, 5503);

		SlimefunItemStack milkyQuartz = new SlimefunItemStack("MILKY_QUARTZ", Material.QUARTZ, "&f乳色石英");
		SlimefunItemStack ctPanel = new SlimefunItemStack("CT_PANEL", "7a44ff3a5f49c69cab676bad8d98a063fa78cfa61916fdef3e267557fec18283", "&3CT照明面板", "&7製作零件");
		
		SlimefunItemStack chestTerminal = new SlimefunItemStack("CHEST_TERMINAL", "7a44ff3a5f49c69cab676bad8d98a063fa78cfa61916fdef3e267557fec18283", "&3CT 存取終端", "&7如果此方塊連接至", "&7物流網路, 它將允許你使用遠端", "&7互動任何物品", "&7物流節點的頻道設定成箱子終端頻道");
		SlimefunItemStack importBus = new SlimefunItemStack("CT_IMPORT_BUS", "113db2e7e72ea4432eefbd6e58a85eaa2423f83e642ca41abc6a9317757b889", "&3CT 輸入接口", "&7如果此方塊連接至", "&7物流網路, 它將會拉任何物品來自", "&7附加到的庫存並將其放置", "&7進入CT網絡頻道");
		SlimefunItemStack exportBus = new SlimefunItemStack("CT_EXPORT_BUS", "113db2e7e72ea4432eefbd6e58a85eaa2423f83e642ca41abc6a9317757b889", "&3CT 輸出接口", "&7如果此方塊連接至", "&7物流網路, 它將會拉任何物品來自", "&7CT網絡頻道並將其放置", "&7附加到庫存中");
		
		SlimefunItemStack wirelessTerminal16 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_16", Material.ITEM_FRAME, "&3CT 無限存取終端 &b(16)", "&8\u21E8 &7連結至: &c無處", "&8\u21E8 &7範圍: &e16 格", "&c&o&8\u21E8 &e\u26A1 &70 / 10 J", "", "&7如果此模塊鏈接到接入終端", "&7它將能夠遠程訪問該終端", "", "&7&e右鍵點擊訪問終端 &7鏈接", "&7&e右鍵&7來打開連結的終端");
		SlimefunItemStack wirelessTerminal64 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_64", Material.ITEM_FRAME, "&3CT 無限存取終端 &b(64)", "&8\u21E8 &7連結至: &c無處", "&8\u21E8 &7範圍: &e64 格", "&c&o&8\u21E8 &e\u26A1 &70 / 25 J", "", "&7如果此模塊鏈接到接入終端", "&7它將能夠遠程訪問該終端", "", "&7&e右鍵點擊訪問終端 &7鏈接", "&7&e右鍵&7來打開連結的終端");
		SlimefunItemStack wirelessTerminal128 = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_128", Material.ITEM_FRAME, "&3CT 無限存取終端 &b(128)", "&8\u21E8 &7連結至: &c無處", "&8\u21E8 &7範圍: &e128 格", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7如果此模塊鏈接到接入終端", "&7它將能夠遠程訪問該終端", "", "&7&e右鍵點擊訪問終端 &7鏈接", "&7&e右鍵&7來打開連結的終端");
		SlimefunItemStack wirelessTerminalTransdimensional = new SlimefunItemStack("CT_WIRELESS_ACCESS_TERMINAL_TRANSDIMENSIONAL", Material.ITEM_FRAME, "&3CT 無限存取終端 &b(跨維度)", "&8\u21E8 &7連結至: &c無處", "&8\u21E8 &7範圍: &e無限", "&c&o&8\u21E8 &e\u26A1 &70 / 50 J", "", "&7如果此模塊鏈接到接入終端", "&7它將能夠遠程訪問該終端", "", "&7&e右鍵點擊訪問終端 &7鏈接", "&7&e右鍵&7來打開連結的終端");
		
		Category category = new Category(new NamespacedKey(this, "chest_terminal"), new CustomItem(chestTerminal, "&5箱子終端", "", "&a> 點擊開啟"));
		
		new SlimefunItem(category, milkyQuartz, RecipeType.GEO_MINER, 
		new ItemStack[0])
		.register(this);
		
		new SlimefunItem(category, ctPanel, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, milkyQuartz, SlimefunItems.BLISTERING_INGOT_3, milkyQuartz})
		.register(this);
		
		new AccessTerminal(category, chestTerminal, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_3, milkyQuartz, SlimefunItems.POWER_CRYSTAL, ctPanel, SlimefunItems.POWER_CRYSTAL, SlimefunItems.PLASTIC_SHEET, SlimefunItems.ENERGY_REGULATOR, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new ImportBus(category, importBus, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {SlimefunItems.REDSTONE_ALLOY, SlimefunItems.POWER_CRYSTAL, SlimefunItems.REDSTONE_ALLOY, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.CARGO_INPUT_NODE, SlimefunItems.HARDENED_METAL_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.CARGO_MOTOR, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new ExportBus(category, exportBus, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {null, SlimefunItems.DAMASCUS_STEEL_INGOT, null, SlimefunItems.ALUMINUM_BRONZE_INGOT, importBus, SlimefunItems.ALUMINUM_BRONZE_INGOT, SlimefunItems.PLASTIC_SHEET, SlimefunItems.GOLD_10K, SlimefunItems.PLASTIC_SHEET})
		.register(this);
		
		new WirelessTerminal(category, wirelessTerminal16, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, chestTerminal, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 16;
			}

            @Override
			public float getMaxItemCharge(ItemStack item) {
			    return 10;
			}
			
		}.register(this);
		
		new WirelessTerminal(category, wirelessTerminal64, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal16, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 64;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 25;
            }
			
		}.register(this);
		
		new WirelessTerminal(category, wirelessTerminal128, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_2, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal64, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return 128;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 50;
            }
			
		}.register(this);
		
		new WirelessTerminal(category, wirelessTerminalTransdimensional, RecipeType.ENHANCED_CRAFTING_TABLE,
		new ItemStack[] {milkyQuartz, SlimefunItems.GPS_TRANSMITTER_4, milkyQuartz, SlimefunItems.COBALT_INGOT, wirelessTerminal128, SlimefunItems.COBALT_INGOT, SlimefunItems.BATTERY, SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.BATTERY}) {

			@Override
			public int getRange() {
				return -1;
			}

            @Override
            public float getMaxItemCharge(ItemStack item) {
                return 50;
            }
			
		}.register(this);
		
		new MilkyQuartz(this, milkyQuartz).register();
	}

	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}

	@Override
	public String getBugTrackerURL() {
		return "https://github.com/xMikux/ChestTerminal/issues";
	}
}
