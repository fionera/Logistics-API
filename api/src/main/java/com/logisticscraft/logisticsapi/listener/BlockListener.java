package com.logisticscraft.logisticsapi.listener;

import com.logisticscraft.logisticsapi.block.LogisticBlockCache;
import com.logisticscraft.logisticsapi.service.PluginService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BlockListener implements Listener {

    @Inject
    private PluginService pluginService;

    @Inject
    private LogisticBlockCache blockCache;

    private HashSet<UUID> doubleRightClickCoolDown = new HashSet<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        blockCache.getCachedLogisticBlockAt(event.getBlock().getLocation()).ifPresent(block -> {
            block.onPlayerBreak(event);
            if (!event.isCancelled()) {
                blockCache.unloadLogisticBlock(event.getBlock().getLocation(), false);
            }
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        blockCache.getCachedLogisticBlockAt(event.getClickedBlock().getLocation()).ifPresent(block -> {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (doubleRightClickCoolDown.contains(event.getPlayer().getUniqueId())) {
                    return;
                }
                block.onRightClick(event);
                doubleRightClickCoolDown.add(event.getPlayer().getUniqueId());
                pluginService.runTaskLater(() -> {
                    doubleRightClickCoolDown.remove(event.getPlayer().getUniqueId());
                }, 1);
            } else {
                block.onLeftClick(event);
            }
        });
    }
}
