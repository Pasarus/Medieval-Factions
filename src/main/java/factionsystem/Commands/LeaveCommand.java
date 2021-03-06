package factionsystem.Commands;

import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class LeaveCommand {

    Main main = null;

    public LeaveCommand(Main plugin) {
        main = plugin;
    }

    public boolean leaveFaction(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getName(), main.factions)) {
                for (int i = 0; i < main.factions.size(); i++) {
                    if (main.factions.get(i).isMember(player.getName())) {
                        if (main.factions.get(i).isOwner(player.getName())) {
                            // is faction empty?
                            if (main.factions.get(i).getPopulation() == 1) {
                                // able to leave

                                if (main.factions.get(i).isOfficer(player.getName())) {
                                    main.factions.get(i).removeOfficer(player.getName());
                                }

                                main.playersInFactionChat.remove(player.getName());

                                // delete file associated with faction
                                System.out.println("Attempting to delete file plugins/MedievalFactions/" + main.factions.get(i).getName() + ".txt");
                                try {
                                    File fileToDelete = new File("plugins/MedievalFactions/" + main.factions.get(i).getName() + ".txt");
                                    if (fileToDelete.delete()) {
                                        System.out.println("Success. File deleted.");
                                    }
                                    else {
                                        System.out.println("There was a problem deleting the file.");
                                    }
                                } catch(Exception e) {
                                    System.out.println("An error has occurred during file deletion.");
                                }

                                // remove claimed land objects associated with this faction
                                removeAllClaimedChunks(main.factions.get(i).getName(), main.claimedChunks);

                                main.factions.get(i).removeMember(player.getName(), getPlayersPowerRecord(player.getName(), main.playerPowerRecords).getPowerLevel());
                                main.factions.remove(i);
                                player.sendMessage(ChatColor.AQUA + "You left your faction. It was deleted since no one else was a member.");

                                return true;
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "Sorry! You must transfer ownership or kick everyone in your faction to leave.");
                                return false;
                            }
                        }
                        else {
                            // able to leave

                            if (main.playersInFactionChat.contains(player.getName())) {
                                main.playersInFactionChat.remove(player.getName());
                            }

                            main.factions.get(i).removeMember(player.getName(), getPlayersPowerRecord(player.getName(), main.playerPowerRecords).getPowerLevel());
                            player.sendMessage(ChatColor.AQUA + "You left your faction.");
                            try {
                                sendAllPlayersInFactionMessage(main.factions.get(i), ChatColor.GREEN + player.getName() + " has left " + main.factions.get(i).getName());
                            } catch (Exception ignored) {

                            }
                            return true;
                        }
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "You need to be in a faction to use this command.");
            }
        }
        return false;
    }
}
