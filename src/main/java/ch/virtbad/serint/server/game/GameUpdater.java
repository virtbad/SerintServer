package ch.virtbad.serint.server.game;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Time;

/**
 * @author Virt
 */
@Slf4j
public class GameUpdater {

    private long lastTick;

    private long frameDelta;

    @Getter
    private int tickSpeed;

    private Game game;

    public GameUpdater(Game game, int tickSpeed){
        this.game = game;

        this.tickSpeed = tickSpeed;
        this.frameDelta = (long) (1e+9 / tickSpeed);
    }

    public void call(){
        long time = System.nanoTime();

        if (time - lastTick > frameDelta){
            if (time - lastTick > frameDelta * 2) log.info("Running {} ticks behind!", (time - lastTick) / frameDelta);
            lastTick = time;

            game.update();
        }
    }

}
