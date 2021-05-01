package ch.virtbad.serint.server.game.actions;

import ch.virtbad.serint.server.local.Time;
import lombok.Getter;

/**
 * @author Virt
 */
public abstract class Action {
    @Getter
    private float expired;

    public Action(float delay){
        expired = Time.getSeconds() + delay;
    }

    public abstract void execute();
}
