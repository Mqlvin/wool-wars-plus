package me.mqlvin.wwp.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/*
    sorta TODO: Could fix game crashing on invalid config, although most people who edit the config manually probably will have some brains.
 */

public class WWPConfig {
    private static final File configFile = new File("./config/woolwars_plus.json");


    private boolean showItemNametags;
    private boolean showNametagDistance;

    private Position playerCountPos;
    private boolean showPlayerCount;

    public WWPConfig() {
        if (!configFile.exists()) {
            showItemNametags = true;
            showNametagDistance = false;
            playerCountPos = Position.TOP_MIDDLE;
            showPlayerCount = true;

            saveConfig();
        }

        loadConfig();
    }

    public void saveConfig() {
        JsonObject obj = new JsonObject();
        obj.addProperty("showHol", showItemNametags);
        obj.addProperty("showHolDistance", showNametagDistance);
        obj.addProperty("showSideCount", showPlayerCount);
        obj.addProperty("sideCountPos", playerCountPos.toString());

        try {
            FileUtils.writeStringToFile(configFile, obj.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadConfig() {
        if (configFile.exists()) {
            try {
                JsonObject obj = new JsonParser().parse(FileUtils.readFileToString(configFile)).getAsJsonObject();
                showItemNametags = obj.get("showHol").getAsBoolean();
                showNametagDistance = obj.get("showHolDistance").getAsBoolean();
                showPlayerCount = obj.get("showSideCount").getAsBoolean();
                playerCountPos = Position.valueOf(obj.get("sideCountPos").getAsString());
            } catch (Exception defaultValues) {
                showItemNametags = true;
                showNametagDistance = false;
                playerCountPos = Position.TOP_MIDDLE;
                showPlayerCount = true;
            }
        }
    }


    public boolean showItemNametags() {
        return showItemNametags;
    }

    public boolean showNametagDistance() {
        return showNametagDistance;
    }

    public boolean showPlayerCount() {
        return this.showPlayerCount;
    }

    public Position getPlayerCountPos() {
        return this.playerCountPos;
    }

    public void toggleShowItemNametags() {
        this.showItemNametags = !this.showItemNametags;
    }

    public void toggleShowNametagDistance() {
        this.showNametagDistance = !this.showNametagDistance;
    }

    public void toggleShowPlayerCount() {
        this.showPlayerCount = !this.showPlayerCount;
    }

    public void scrollPlayerCountPos() {
        this.playerCountPos = this.playerCountPos.next();
    }


    public enum Position {
        TOP_LEFT("Top Left"),
        TOP_RIGHT("Top Right"),
        BOTTOM_LEFT("Bottom Left"),
        BOTTOM_RIGHT("Bottom Right"),
        TOP_MIDDLE("Top Middle"),
        UNDER_CROSSHAIR("Under Crosshair");

        private static final Position[] positions = values();
        private final String formattedName;

        Position(String formattedName) {
            this.formattedName = formattedName;
        }

        public String getFormattedName() {
            return formattedName;
        }

        public Position next() {
            return positions[(ordinal() + 1) % positions.length];
        }
    }
}
