package com.ehaqui.ehcore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ehaqui.ehcore.api.util.storage.DataKey;
import com.ehaqui.ehcore.api.util.storage.Storage;
import com.ehaqui.ehcore.api.util.storage.YamlStorage;
import com.google.common.collect.Lists;

public class Settings
{
    public static final boolean debug                      = true;
    
    
    public static String        pluginName                 = "[EhCore]";
    public static String        pluginPrefix               = "EhCore ";
    public static String        prefixPermission           = "ehcore";

    public static boolean       denyBreakHoldingSpawner    = true;
    public static boolean       ignoreFakeBreakEvents      = true;

    public static short         defaultEntityID            = 0;

    public static boolean       useVanishNoPacket          = false;
    public static boolean       useSimpleVanish            = false;

    public static boolean       playSoundOnTeleport        = false;

    public static boolean       restrictions_can_dropitems = false;
    public static boolean       restrictions_can_pvp       = false;
    public static String        motd                       = "..:: EhAqui ::.. Instável devido                               Ataques!!! :(";
    
    
    public static String PREFIX_PERMISSION = "ehcore";
    
    private final Storage config;
    private final DataKey root;

    public Settings(File folder) {
        config = new YamlStorage(new File(folder, "config.yml"), "EhCore Configuration");
        root = config.getKey("");

        config.load();
        for (Setting setting : Setting.values()) {
            if (!root.keyExists(setting.path)) {
                setting.setAtKey(root);
            } else
                setting.loadFromKey(root);
        }

        save();
    }

    public void reload() {
        config.load();
        for (Setting setting : Setting.values())
            if (root.keyExists(setting.path))
                setting.loadFromKey(root);

        save();
    }

    public void save() {
        config.save();
    }

    public enum Setting {
        CHAT_PREFIX("npc.chat.prefix", "[<npc>]: "),
        DATABASE_DRIVER("storage.database.driver", ""),
        DATABASE_PASSWORD("storage.database.password", ""),
        DATABASE_URL("storage.database.url", ""),
        DATABASE_USERNAME("storage.database.username", ""),
        DEBUG_MODE("general.debug-mode", false),
        DEFAULT_LOOK_CLOSE("npc.default.look-close.enabled", false),
        DEFAULT_LOOK_CLOSE_RANGE("npc.default.look-close.range", 5),
        DEFAULT_NPC_LIMIT("npc.limits.default-limit", 10),
        DEFAULT_PATHFINDING_RANGE("npc.default.pathfinding.range", 25F),
        DEFAULT_RANDOM_TALKER("npc.default.random-talker", true),
        DEFAULT_REALISTIC_LOOKING("npc.default.realistic-looking", false),
        DEFAULT_STATIONARY_TICKS("npc.default.stationary-ticks", -1),
        DEFAULT_TALK_CLOSE("npc.default.talk-close.enabled", false),
        DEFAULT_TALK_CLOSE_RANGE("npc.default.talk-close.range", 5),
        DEFAULT_TEXT("npc.default.text.0", "Hi, I'm <npc>!") {
            @Override
            public void loadFromKey(DataKey root) {
                List<String> list = new ArrayList<String>();
                for (DataKey key : root.getRelative("npc.default.text").getSubKeys())
                    list.add(key.getString(""));
                value = list;
            }
        },
        HIGHLIGHT_COLOUR("general.color-scheme.message-highlight", "<e>"),
        LOCALE("general.translation.locale", ""),
        MAX_NPC_LIMIT_CHECKS("npc.limits.max-permission-checks", 100),
        MAX_SPEED("npc.limits.max-speed", 100),
        MESSAGE_COLOUR("general.color-scheme.message", "<f>"),
        NPC_COST("economy.npc.cost", 100D),
        QUICK_SELECT("npc.selection.quick-select", false),
        REMOVE_PLAYERS_FROM_PLAYER_LIST("npc.player.remove-from-list", true),
        SAVE_TASK_DELAY("storage.save-task.delay", 20 * 60 * 60),
        SELECTION_MESSAGE("npc.selection.message", "<b>You selected <a><npc><b>!"),
        SERVER_OWNS_NPCS("npc.server-ownership", false),
        STORAGE_FILE("storage.file", "saves.yml"),
        STORAGE_TYPE("storage.type", "yaml"),
        SUBPLUGIN_FOLDER("subplugins.folder", "plugins"),
        TALK_CLOSE_MAXIMUM_COOLDOWN("npc.text.max-talk-cooldown", 60),
        TALK_CLOSE_MINIMUM_COOLDOWN("npc.text.min-talk-cooldown", 30),
        TALK_ITEM("npc.text.talk-item", "340");

        protected String path;
        protected Object value;

        Setting(String path, Object value) {
            this.path = path;
            this.value = value;
        }

        public boolean asBoolean() {
            return (Boolean) value;
        }

        public double asDouble() {
            return ((Number) value).doubleValue();
        }

        public float asFloat() {
            return ((Number) value).floatValue();
        }

        public int asInt() {
            if (value instanceof String) {
                return Integer.parseInt(value.toString());
            }
            return ((Number) value).intValue();
        }

        @SuppressWarnings("unchecked")
        public List<String> asList() {
            if (!(value instanceof List)) {
                value = Lists.newArrayList(value);
            }
            return (List<String>) value;
        }

        public long asLong() {
            return ((Number) value).longValue();
        }

        public String asString() {
            return value.toString();
        }

        protected void loadFromKey(DataKey root) {
            value = root.getRaw(path);
        }

        protected void setAtKey(DataKey root) {
            root.setRaw(path, value);
        }
    }

}