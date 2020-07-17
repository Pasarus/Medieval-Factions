package factionsystem.EventHandlers;

import factionsystem.Main;
import factionsystem.Objects.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;

import static factionsystem.Subsystems.UtilitySubsystem.getPlayersFaction;
import static factionsystem.Subsystems.UtilitySubsystem.isInFaction;

public class ProjectileHitEventHandler {

    Main main = null;

    public ProjectileHitEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(ProjectileHitEvent event) {

        // if between two players
        if (event.getEntity() instanceof Player && event.getHitEntity() instanceof Player) {

            Player attacker = (Player) event.getEntity();
            Player victim = (Player) event.getHitEntity();

            Faction attackersFaction = getPlayersFaction(attacker.getName(), main.factions);
            Faction victimsFaction = getPlayersFaction(victim.getName(), main.factions);

            // if attacker and victim are both in a faction
            if (isInFaction(attacker.getName(), main.factions) && isInFaction(victim.getName(), main.factions)) {
                // if attacker and victim are part of the same faction
                if (attackersFaction.getName().equalsIgnoreCase(victimsFaction.getName())) {

                    // TODO: cancel event

                    attacker.sendMessage(ChatColor.RED + "You can't attack another player if you are part of the same faction.");
                    return;
                }

                // if attacker's faction and victim's faction are not at war
                if (!attackersFaction.isEnemy(victimsFaction.getName()) &&
                        !victimsFaction.isEnemy(attackersFaction.getName())) {

                    // TODO: cancel event

                    attacker.sendMessage(ChatColor.RED + "You can't attack another player if your factions aren't at war.");
                }
            }

        }

    }

}
