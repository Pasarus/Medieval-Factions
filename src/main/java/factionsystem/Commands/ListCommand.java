package factionsystem.Commands;

import factionsystem.Objects.Faction;
import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

import static factionsystem.Subsystems.UtilitySubsystem.getChunksClaimedByFaction;

public class ListCommand {

    Main main = null;

    public ListCommand(Main plugin) {
        main = plugin;
    }

    public boolean listFactions(CommandSender sender) {
        // if there aren't any factions
        if (main.factions.size() == 0) {
            sender.sendMessage(ChatColor.AQUA + "There are currently no factions.");
        }
        // factions exist, list them
        else {
            sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + " == Factions" + " == ");
            listFactionsWithFormatting(sender);
        }
        return true;
    }

    public void listFactionsWithFormatting(CommandSender sender) {
//        sender.sendMessage(ChatColor.AQUA + String.format("%-20s %10s %12s %10s", "Name", "Power", "Population", "Land"));
        for (Faction faction : main.utilities.getFactionsSortedByPower()) {
            sender.sendMessage(ChatColor.AQUA + String.format("%-30s %12s %12s %12s", faction.getName(), "power: " + faction.getCumulativePowerLevel(), "pop: " + faction.getPopulation(), "land: " + getChunksClaimedByFaction(faction.getName(), main.claimedChunks)));
        }
    }
}
