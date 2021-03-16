package ch.virtbad.serint.server.game.map;

import lombok.Getter;

@Getter
public class Map {

    private int height;
    private int width;
    private String name;

    private Tile[] tiles;
    private LightSource[] lights;
    private Action[] actions;
    private Cosmetic[] cosmetics;

    @Getter
    public static class Tile {
        private int x;
        private int y;
        private TileType type;

        public static enum TileType {
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

        public static class RGB {
            private float r;
            private float g;
            private float b;
        }
    }

    @Getter
    public static class Action {
        private int x;
        private int y;
        private ActionType type;

        public static enum ActionType {
            ITEM,
            SPAWN
        }
    }

    @Getter
    public static class Cosmetic {
        private int x;
        private int y;
        private CosmeticType type;

        public static enum CosmeticType {
            COBWEB,
            CRACK
        }
    }


}
