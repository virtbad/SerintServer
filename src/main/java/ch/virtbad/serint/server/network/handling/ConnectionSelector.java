package ch.virtbad.serint.server.network.handling;

import lombok.Getter;

/**
 * This class is used to select a few clients that are connected to the server
 * @author Virt
 */
public class ConnectionSelector {
    static final int INCLUDED = 0;
    static final int EXCLUDED = 1;

    /**
     * Creates a selector that includes a set of clients
     * @param clients clients to include
     * @return created selector
     */
    public static ConnectionSelector include(int ...clients){
        return new ConnectionSelector(INCLUDED, new int[0], clients);
    }

    /**
     * Creates a selector that excludes a set of clients
     * @param clients clients to exclude
     * @return created selector
     */
    public static ConnectionSelector exclude(int ...clients){
        return new ConnectionSelector(EXCLUDED, clients, new int[0]);
    }

    @Getter
    private int mode;
    @Getter
    private int[] excluded;
    @Getter
    private int[] included;

    /**
     * Creates a selector
     * @param mode mode of the selector (include or exclude)
     * @param excluded included clients
     * @param included excluded clients
     */
    private ConnectionSelector(int mode, int[] excluded, int[] included) {
        this.mode = mode;
        this.excluded = excluded;
        this.included = included;
    }
}
