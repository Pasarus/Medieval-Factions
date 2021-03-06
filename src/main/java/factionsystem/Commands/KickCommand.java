package factionsystem.Commands;

import factionsystem.Objects.Faction;
import factionsystem.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.getPlayersPowerRecord;
import static factionsystem.Subsystems.UtilitySubsystem.sendAllPlayersInFactionMessage;

public class KickCommand {

    Main main = null;

    public KickCommand(Main plugin) {
        main = plugin;
    }

    public boolean kickPlayer(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 1) {
                boolean owner = false;
                for (Faction faction : main.factions) {
                    if (faction.isOwner(player.getName()) || faction.isOfficer(player.getName())) {
                        owner = true;
                        if (faction.isMember(args[1])) {
                            if (!(args[1].equalsIgnoreCase(player.getName()))) {
                                if (!(args[1].equalsIgnoreCase(faction.getOwner()))) {

                                    if (faction.isOfficer(args[1])) {
                                        faction.removeOfficer(args[1]);
                                    }

                                    main.playersInFactionChat.remove(args[1]);

                                    faction.removeMember(args[1], getPlayersPowerRecord(player.getName(), main.playerPowerRecords).getPowerLevel());
                                    try {
                                        sendAllPlayersInFactionMessage(faction, ChatColor.RED + args[1] + " has been kicked from " + faction.getName());
                                    } catch (Exception ignored) {

                                    }
                                    try {
                                        Player target = Bukkit.getServer().getPlayer(args[1]);
                                        target.sendMessage(ChatColor.RED + "You have been kicked from your faction by " + player.getName() + ".");
                                    } catch (Exception ignored) {

                                    }
                                    return true;
                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "You can't kick the owner.");
                                    return false;
                                }

                            }
                            else {
                                player.sendMessage(ChatColor.RED + "You can't kick yourself.");
                                return false;
                            }

                        }
                    }
                }

                if (!owner) {
                    player.sendMessage(ChatColor.RED + "You need to be the owner of a faction to use this command.");
                    return false;
                }

            } else {
                player.sendMessage(ChatColor.RED + "Usage: /mf kick (player-name)");
                return false;
            }
        }
        return false;
    }

}
