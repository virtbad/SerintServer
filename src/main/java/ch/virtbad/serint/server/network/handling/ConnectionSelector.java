package ch.virtbad.serint.server.network.handling;

import lombok.Getter;

import java.util.List;

/**
 * @author Virt
 */
public class ConnectionSelector {
    public static final int INCLUDED = 0;
    public static final int EXCLUDED = 1;

    public static ConnectionSelector include(int ...clients){
        return new ConnectionSelector(INCLUDED, new int[0], clients);
    }

    public static ConnectionSelector exclude(int ...clients){
        return new ConnectionSelector(EXCLUDED, clients, new int[0]);
    }

    @Getter
    private int mode;
    @Getter
    private int[] excluded;
    @Getter
    private int[] included;

    public ConnectionSelector(int mode, int[] excluded, int[] included) {
        this.mode = mode;
        this.excluded = excluded;
        this.included = included;
    }
}
