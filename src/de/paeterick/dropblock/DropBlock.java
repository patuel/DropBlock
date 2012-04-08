package de.paeterick.dropblock;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DropBlock extends JavaPlugin implements Listener {
	private Logger log = Logger.getLogger("Minecraft");
	private ArrayList<CraftItem> watchlist = new ArrayList<CraftItem>();
	private ArrayList<String> enabledList = new ArrayList<String>();

	public void onEnable() {

		log.info("Dropblock by Erquu. :)");
		log.info("Dropblock has been enabled!");

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);

		getServer().getScheduler().scheduleSyncRepeatingTask(this,
				new Runnable() {
					@Override
					public void run() {
						update();
					}
				}, 1L, 1L);
	}

	public void onDisable() {
		watchlist.clear();
		enabledList.clear();

		log.info("Dropblock has been disabled!");
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Item item = event.getItemDrop();
		if (enabledList.contains(event.getPlayer().getDisplayName())
				&& item.getItemStack().getAmount() == 1) {
			watchlist.add((CraftItem) item);
		}
	}

	public void update() {
		CraftItem item = null;
		Location loc = null;
		World world = null;
		for (int i = 0; i < watchlist.size(); i++) {
			item = watchlist.get(i);
			loc = item.getLocation();
			world = item.getWorld();

			Block touchingBlock = getSolidTouchingBlock(world, item, loc);

			if (touchingBlock == null)
				continue;

			CraftBlock b = (CraftBlock) world.getBlockAt(loc.getBlockX(),
					loc.getBlockY(), loc.getBlockZ());
			int id = item.getItemStack().getTypeId();

			if (Misc.isPlacableBlock(id)) {
				if (!Misc.isReplacableBlock(b.getTypeId()))
					continue;

				boolean removeItem = true;
				boolean removeFromWatchList = true;

				if (id == 331) {
					b.setType(Material.REDSTONE_WIRE);
				} else if (id == 354) {
					b.setType(Material.CAKE_BLOCK);
				} else if (id == 259) {
					b.setType(Material.FIRE);
				} else if (id == 326) {
					b.setType(Material.WATER);
				} else if (id == 327) {
					b.setType(Material.LAVA);
				} else if (Misc.isAttachable(id)) {
					if (b.getFace(touchingBlock) == BlockFace.WEST) {
						if ((id == 63) || (id == 68) || (id == 323))
							b.setType(Material.WALL_SIGN);
						else {
							b.setTypeId(id);
						}
						if ((id == 69) || (id == 77) || (id == 50)
								|| (id == 75) || (id == 76))
							b.setData((byte) 4);
						else
							b.setData((byte) 2);
					} else if (b.getFace(touchingBlock) == BlockFace.EAST) {
						if ((id == 63) || (id == 68) || (id == 323))
							b.setType(Material.WALL_SIGN);
						else {
							b.setTypeId(id);
						}
						b.setData((byte) 3);
					} else if (b.getFace(touchingBlock) == BlockFace.SOUTH) {
						if ((id == 63) || (id == 68) || (id == 323))
							b.setType(Material.WALL_SIGN);
						else {
							b.setTypeId(id);
						}
						if ((id == 69) || (id == 77) || (id == 50)
								|| (id == 75) || (id == 76))
							b.setData((byte) 2);
						else
							b.setData((byte) 4);
					} else if (b.getFace(touchingBlock) == BlockFace.NORTH) {
						if ((id == 63) || (id == 68) || (id == 323))
							b.setType(Material.WALL_SIGN);
						else {
							b.setTypeId(id);
						}
						if ((id == 69) || (id == 77) || (id == 50)
								|| (id == 75) || (id == 76))
							b.setData((byte) 1);
						else {
							b.setData((byte) 5);
						}
					} else if (b.getFace(touchingBlock) == BlockFace.DOWN) {
						if ((id == 63) || (id == 68) || (id == 323)) {
							b.setType(Material.SIGN_POST);
							b.setData((byte) 4);
						} else if ((id == 69) || (id == 50) || (id == 75)
								|| (id == 76)) {
							b.setTypeId(id);
							b.setData((byte) 5);
						} else {
							removeItem = false;
							removeFromWatchList = false;
						}
					} else {
						removeItem = false;
						removeFromWatchList = false;
					}
				} else if ((id == 295) || (id == 59)) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					if (beneath.getType() == Material.SOIL)
						b.setType(Material.CROPS);
					else
						removeItem = false;
				} else if (id == 37) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					if ((beneath.getType() == Material.GRASS)
							|| (beneath.getType() == Material.DIRT))
						b.setType(Material.YELLOW_FLOWER);
					else
						removeItem = false;
				} else if (id == 38) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					if ((beneath.getType() == Material.GRASS)
							|| (beneath.getType() == Material.DIRT))
						b.setType(Material.RED_ROSE);
					else
						removeItem = false;
				} else if (id == 39) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					if ((beneath.getType() == Material.GRASS)
							|| (beneath.getType() == Material.DIRT))
						b.setType(Material.BROWN_MUSHROOM);
					else
						removeItem = false;
				} else if (id == 40) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					if ((beneath.getType() == Material.GRASS)
							|| (beneath.getType() == Material.DIRT))
						b.setType(Material.RED_MUSHROOM);
					else
						removeItem = false;
				} else if (id == 81) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					Block right = world.getBlockAt(b.getX() + 1, b.getY(),
							b.getZ());
					Block left = world.getBlockAt(b.getX() - 1, b.getY(),
							b.getZ());
					Block forward = world.getBlockAt(b.getX(), b.getY(),
							b.getZ() + 1);
					Block backward = world.getBlockAt(b.getX(), b.getY(),
							b.getZ() - 1);

					if ((beneath.getType() == Material.SAND)
							&& (Misc.isReplacableBlock(right.getTypeId()))
							&& (Misc.isReplacableBlock(left.getTypeId()))
							&& (Misc.isReplacableBlock(forward.getTypeId()))
							&& (Misc.isReplacableBlock(backward.getTypeId()))) {
						b.setType(Material.CACTUS);
					} else
						removeItem = false;
				} else if ((id == 338) || (id == 83)) {
					Block beneath = world.getBlockAt(b.getX(), b.getY() - 1,
							b.getZ());
					Block right = world.getBlockAt(beneath.getX() + 1,
							beneath.getY(), beneath.getZ());
					Block left = world.getBlockAt(beneath.getX() - 1,
							beneath.getY(), beneath.getZ());
					Block forward = world.getBlockAt(beneath.getX(),
							beneath.getY(), beneath.getZ() + 1);
					Block backward = world.getBlockAt(beneath.getX(),
							beneath.getY(), beneath.getZ() - 1);

					if (((beneath.getType() == Material.DIRT) || (beneath
							.getType() == Material.GRASS))
							&& ((right.getTypeId() == 8)
									|| (right.getTypeId() == 9)
									|| (left.getTypeId() == 8)
									|| (left.getTypeId() == 9)
									|| (forward.getTypeId() == 8)
									|| (forward.getTypeId() == 9)
									|| (backward.getTypeId() == 8) || (backward
									.getTypeId() == 9))) {
						b.setType(Material.SUGAR_CANE_BLOCK);
					} else
						removeItem = false;
				} else if (id == 324) {
					b.setType(Material.WOODEN_DOOR);
					b = (CraftBlock) world.getBlockAt(b.getX(), b.getY() + 1,
							b.getZ());
					b.setType(Material.WOODEN_DOOR);
					b.setData((byte) 8);
				} else if (id == 330) {
					b.setType(Material.IRON_DOOR_BLOCK);
					b = (CraftBlock) world.getBlockAt(b.getX(), b.getY() + 1,
							b.getZ());
					b.setType(Material.IRON_DOOR_BLOCK);
					b.setData((byte) 8);
				} else {
					b.setTypeId(id);
					b.setData(new Short(item.getItemStack().getDurability())
							.byteValue());
				}

				if (removeFromWatchList) {
					watchlist.remove(i);
				}
				if (!removeItem)
					continue;
				item.getHandle().getBukkitEntity().remove();
			} else if (b.getTypeId() == 6) {
				if ((id != 351) || (item.getItemStack().getDurability() != 15))
					continue;
				b.setType(Material.AIR);
				TreeType tr = Misc.randomTreeType();

				if (!world.generateTree(b.getLocation(), tr)) {
					b.setType(Material.SAPLING);
				}
				watchlist.remove(i);
				item.getHandle().getBukkitEntity().remove();
			} else {
				watchlist.remove(i);
			}
		}
	}

	public Block getSolidTouchingBlock(World world, Item item, Location loc) {

		double sesitivity = Config.getDistance();

		Location[] sb = new Location[6];
		sb[0] = Misc.add(loc, sesitivity, 0.0D, 0.0D);
		sb[1] = Misc.add(loc, 0.0D, sesitivity, 0.0D);
		sb[2] = Misc.add(loc, 0.0D, 0.0D, sesitivity);
		sb[3] = Misc.substract(loc, sesitivity, 0.0D, 0.0D);
		sb[4] = Misc.substract(loc, 0.0D, sesitivity, 0.0D);
		sb[5] = Misc.substract(loc, 0.0D, 0.0D, sesitivity);

		for (Location l : sb) {
			Block b = world.getBlockAt(l.getBlockX(), l.getBlockY(),
					l.getBlockZ());
			if (!Misc.canPassThrough(b.getTypeId())) {
				return b;
			}
		}
		return null;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (sender.hasPermission("op")) {

		}
		if ((sender instanceof Player)) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("db")) {
				if (args.length > 0) {
					boolean state = false;
					String playerName = player.getDisplayName();

					if (args[0].equalsIgnoreCase("on")){
						state = true;
						if (!enabledList.contains(playerName)) {
							enabledList.add(playerName);
						}
					}
					else if (args[0].equalsIgnoreCase("off")) {
						state = false;
						if (enabledList.contains(playerName)) {
							enabledList.remove(playerName);
						}
					}

					player.sendMessage("DropBlock is now "
							+ (state ? "enabled" : "disabled"));

				} else {
					return false;
				}
			}
		}
		return true;
	}
}
