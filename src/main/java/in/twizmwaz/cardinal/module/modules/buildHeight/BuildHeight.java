package in.twizmwaz.cardinal.module.modules.buildHeight;

import in.twizmwaz.cardinal.module.Module;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class BuildHeight implements Module {

    private final JavaPlugin plugin;
    private final int height;

    protected BuildHeight(JavaPlugin plugin, int height) {
        this.plugin = plugin;
        this.height = height;

    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getY() >= height) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + " You have reached the maximum build height! " + ChatColor.GRAY + "(" + height + " blocks)");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getY() >= height) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + " You have reached the maximum build height! " + ChatColor.GRAY + "(" + height + " blocks)");
        }
    }

    @EventHandler
    public void onEmptyBucket(PlayerBucketEmptyEvent event) {
        Block toFill = event.getBlockClicked().getRelative(event.getBlockFace());
        if (toFill.getY() >= height) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + " You have reached the maximum build height! " + ChatColor.GRAY + "(" + height + " blocks)");
        }
    }

    @EventHandler
    public void onFillBucket(PlayerBucketFillEvent event) {
        Block toEmpty = event.getBlockClicked().getRelative(event.getBlockFace());
        if (toEmpty.getY() >= height) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.YELLOW + "\u26A0" + ChatColor.RED + " You have reached the maximum build height! " + ChatColor.GRAY + "(" + height + " blocks)");
        }
    }

    @EventHandler
    public void onStructureGrow(StructureGrowEvent event) {
        Set<BlockState> blocksAboveHeight = new HashSet<>();
        for (BlockState blockState : event.getBlocks()) {
            if (blockState.getY() >= height) {
                blocksAboveHeight.add(blockState);
            }
        }
        for (BlockState blockState : blocksAboveHeight) {
            event.getBlocks().remove(blockState);
        }
    }

    @EventHandler
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (block.getY() >= height) {
                event.setCancelled(true);
            }
        }

    }
}
