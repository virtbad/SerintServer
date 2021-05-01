package ch.virtbad.serint.server.local;

import ch.virtbad.serint.server.game.Game;
import ch.virtbad.serint.server.network.Communications;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class CommandLineInterface implements Runnable {

    private final Communications com;
    private final Game game;

    public CommandLineInterface(Communications com, Game game){
        this.com = com;
        this.game = game;
    }

    public void start(){
        Thread input = new Thread(this, "input");
        input.start();
    }

    private void processCommand(String command){
        if (command.equals("test")) log.info("Command interface is online!");
        else if (command.equals("start")){
            log.info("Starting game!");


        }else if (command.equals("exit")){
            log.info("Doing hard exit!");
            com.kickEveryone("Server Closed!");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        log.info("Started console input");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            if (command.equals("cancel")) break;

            processCommand(command);

        }

        log.info("Exited console input");
    }
}
