package factionsystem.EventHandlers;

import factionsystem.Main;
import factionsystem.Objects.PlayerPowerRecord;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventHandler {

    Main main = null;

    public PlayerJoinEventHandler(Main plugin) {
        main = plugin;
    }

    public void handle(PlayerJoinEvent event) {
        if (!main.utilities.hasPowerRecord(event.getPlayer().getName())) {
            PlayerPowerRecord newRecord = new PlayerPowerRecord(event.getPlayer().getName(), main.getConfig().getInt("initialPowerLevel"), main.getConfig().getInt("maxPowerLevel"));
            main.playerPowerRecords.add(newRecord);
        }
    }
}
