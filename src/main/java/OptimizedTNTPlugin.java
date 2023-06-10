import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class OptimizedTNTPlugin extends JavaPlugin implements Listener, CommandExecutor {

    private boolean optimizedTNTEnabled = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("optimizedTNT").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Cleanup code if needed
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    optimizedTNTEnabled = true;
                    sender.sendMessage("Optimized TNT enabled.");
                    return true;
                } else if (args[0].equalsIgnoreCase("off")) {
                    optimizedTNTEnabled = false;
                    sender.sendMessage("Optimized TNT disabled.");
                    return true;
                }
            }
            sender.sendMessage("Invalid argument. Usage: /optimizedTNT <on/off>");
            return true;
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed && optimizedTNTEnabled) {
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            Block block = tnt.getLocation().getBlock();

            if (block.getType() == Material.TNT || block.isLiquid()) {
                event.blockList().clear(); // Remove all blocks from the explosion
            }
        }
    }
}
