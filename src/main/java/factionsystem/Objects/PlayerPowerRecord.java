package factionsystem.Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PlayerPowerRecord {

    // saved
    private String playerName = "";
    private int powerLevel = 0;

    // temporary
    int maxPower = 0;

    public PlayerPowerRecord(String nameOfPlayer, int initial, int max) {
        playerName = nameOfPlayer;
        powerLevel = initial;
        maxPower = max;
    }
    public PlayerPowerRecord(int max) { // server constructor for loading
        maxPower = max;
    }

    public void setPlayerName(String newName) {
        playerName = newName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean increasePower() {
        if (powerLevel < maxPower) {
            powerLevel++;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean decreasePower() {
        if (powerLevel > 0) {
            powerLevel--;
            return true;
        }
        else {
            return false;
        }
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int newPower) {
        powerLevel = newPower;
    }

    public void save() {
        try {
            File saveFolder = new File("./plugins/MedievalFactions/player-power-records/");
            if (!saveFolder.exists()) {
                saveFolder.mkdir();
            }
            File saveFile = new File("./plugins/MedievalFactions/player-power-records/" + playerName + ".txt");
            if (saveFile.createNewFile()) {
                System.out.println("Save file for player power record associated with  " + playerName + " created.");
            } else {
                System.out.println("Save file for player power record associated with  " + playerName + " already exists. Altering.");
            }

            FileWriter saveWriter = new FileWriter("./plugins/MedievalFactions/player-power-records/" + playerName + ".txt");

            // actual saving takes place here
            saveWriter.write(playerName + "\n");
            saveWriter.write(powerLevel + "\n");

            saveWriter.close();

            System.out.println("Successfully saved player power record associated with " + playerName + ".");

        } catch (IOException e) {
            System.out.println("An error occurred saving the player power record associated with " + playerName);
        }
    }

    public void load(String filename) {
        try {
            File loadFile = new File("./plugins/MedievalFactions/player-power-records/" + filename);
            Scanner loadReader = new Scanner(loadFile);

            // actual loading
            if (loadReader.hasNextLine()) {
                playerName = loadReader.nextLine();
            }
            if (loadReader.hasNextLine()) {
                powerLevel = Integer.parseInt(loadReader.nextLine());
            }

            loadReader.close();

            System.out.println("Player power record for " + playerName + " successfully loaded.");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred loading the file " + filename + ".");
        }
    }

    public void increasePowerByTenPercent() {
        if (powerLevel + (powerLevel * 0.10) < maxPower) {
            powerLevel = (int) (powerLevel + (powerLevel * 0.10));
        }
        else {
            powerLevel = maxPower;
        }
    }

    public void decreasePowerByTenPercent() {
        if (powerLevel - (powerLevel * 0.10) > 0) {
            powerLevel = (int) (powerLevel - (powerLevel * 0.10));
        }
        else {
            decreasePower();
        }
    }
}
