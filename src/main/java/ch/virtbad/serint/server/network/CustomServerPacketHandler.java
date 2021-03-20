package ch.virtbad.serint.server.network;

import ch.virt.pseudopackets.handlers.ServerPacketHandler;
import ch.virt.pseudopackets.packets.Packet;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author VirtCode
 * @version 1.0
 */
@Slf4j
public abstract class CustomServerPacketHandler extends ServerPacketHandler {

    public abstract void connected(UUID client);

    public abstract void disconnected(UUID client);

    public void handle(Packet packet, UUID client){
        try {
            Method method = this.getClass().getMethod("handle", packet.getClass(), UUID.class);
            method.invoke(this, packet, client);
        }  catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t != null) t.printStackTrace();

            log.warn("Failed handling Packet {}", packet.getClass().getName());
        } catch (Exception e){
            log.warn("Failed to handle Packet {}", packet.getClass().getName());

        }
    }
}
