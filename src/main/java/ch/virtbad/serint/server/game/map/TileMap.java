package ch.virtbad.serint.server.game.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This class stores basic map information
 */
@Getter
public class TileMap {

    private int height;
    private int width;
    private String name;

    private Tile[] tiles;
    private LightSource[] lights;
    private Action[] actions;
    private Cosmetic[] cosmetics;

    /**
     * Creates a map with all of its components.
     * (and yes i do not describe the parameters)
     */
    private TileMap(int height, int width, String name, Tile[] tiles, LightSource[] lights, Action[] actions, Cosmetic[] cosmetics) {
        this.height = height;
        this.width = width;
        this.name = name;
        this.tiles = tiles;
        this.lights = lights;
        this.actions = actions;
        this.cosmetics = cosmetics;
    }

    /**
     * Selects a random action location
     * @param type type of the location to select
     * @return selected action location
     */
    public Action selectRandomAction(Action.ActionType type){
        int count = 0;

        for (Action action : actions) {
            if (action.type == type) count++;
        }

        int selected = (int) Math.floor(Math.random() * count);
        count = 0;

        for (Action action : actions) {
            if (action.type == type){
                if (count == selected) return action;
                count++;
            }
        }

        return new Action(0, 0, type);
    }

    /**
     * Creates a map without the action parameter
     * @return created map
     */
    public TileMap createWithoutAction(){
        return new TileMap(height, width, name, tiles, lights, null, cosmetics);
    }

    @Getter
    public static class Tile {
        private int x;
        private int y;
        private TileType type;

        public enum TileType {
            GRAVEL,
            GRASS,
            BRICK,
            STONE,
        }
    }

    @Getter
    public static class LightSource {
        private float x;
        private float y;
        private float intensity;
        private RGB color;

        @Getter
        public static class RGB {
            private int r;
            private int g;
            private int b;
        }
    }

    @Getter @AllArgsConstructor
    public static class Action {
        private int x;
        private int y;
        private ActionType type;

        public enum ActionType {
            ITEM,
            SPAWN
        }
    }

    @Getter
    public static class Cosmetic {
        private int x;
        private int y;
        private CosmeticType type;

        public enum CosmeticType {
            COBWEB,
            CRACK
        }
    }


}
