package factionsystem.Commands;

import factionsystem.Objects.Faction;
import factionsystem.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static factionsystem.Subsystems.UtilitySubsystem.*;

public class MakePeaceCommand {

    Main main = null;

    public MakePeaceCommand(Main plugin) {
        main = plugin;
    }

    public void makePeace(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (isInFaction(player.getName(), main.factions)) {
                Faction playersFaction = getPlayersFaction(player.getName(), main.factions);

                if (playersFaction.isOwner(player.getName()) || playersFaction.isOfficer(player.getName())) {

                    // player is able to do this command

                    if (args.length > 1) {
                        String targetFactionName = createStringFromFirstArgOnwards(args);
                        Faction targetFaction = getFaction(targetFactionName, main.factions);

                        if (!playersFaction.getName().equalsIgnoreCase(targetFactionName)) {

                            if (targetFaction != null) {

                                if (!playersFaction.isTruceRequested(targetFactionName)) {
                                    // if not already requested

                                    if (playersFaction.isEnemy(targetFactionName)) {

                                        playersFaction.requestTruce(targetFactionName);
                                        player.sendMessage(ChatColor.GREEN + "Attempted to make peace with " + targetFactionName);

                                        sendAllPlayersInFactionMessage(targetFaction,ChatColor.GREEN + "" + playersFaction.getName() + " has attempted to make peace with " + targetFactionName + "!");

                                        if (playersFaction.isTruceRequested(targetFactionName) && targetFaction.isTruceRequested(playersFaction.getName())) {
                                            // remove requests in case war breaks out again and they need to make peace aagain
                                            playersFaction.removeRequestedTruce(targetFactionName);
                                            targetFaction.removeRequestedTruce(playersFaction.getName());

                                            // make peace between factions
                                            playersFaction.removeEnemy(targetFactionName);
                                            getFaction(targetFactionName, main.factions).removeEnemy(playersFaction.getName());
                                            sendAllPlayersInFactionMessage(playersFaction, ChatColor.GREEN + "Your faction is now at peace with " + targetFactionName + "!");
                                            sendAllPlayersInFactionMessage(targetFaction, ChatColor.GREEN + "Your faction is now at peace with " + playersFaction.getName() + "!");
                                        }
                                    }
                                    else {
                                        player.sendMessage(ChatColor.RED + "That faction is not your enemy!");
                                    }

                                }
                                else {
                                    player.sendMessage(ChatColor.RED + "You've already requested peace with this faction!");
                                }

                            }
                            else {
                                player.sendMessage(ChatColor.RED + "That faction wasn't found!");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "You can't make peace with your own faction!");
                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Usage: /mf makepeace (faction-name)");
                    }

                }
                else {
                    player.sendMessage(ChatColor.RED + "You need to be the owner of a faction or an officer of a faction to use this command.");
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "You need to be in a faction to use this command.");
            }
        }
    }
}
