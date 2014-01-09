package game;

import org.apache.commons.cli.*;

public class GameCommandLineParser {
    public static void parseCommandLine(String[] args) throws ParseException {
        CommandLineParser parser= new BasicParser();
        Options options = new Options( );

        for (int i = 1; i <= 5; ++i){
            options.addOption("p"+i, "police", true, "Input police id");
        }
        options.addOption("t", "thief", true, "Input thief id" );
        CommandLine commandLine = parser.parse( options, args );

        for (int i = 1 ; i <= 5; ++i){
            if (commandLine.hasOption("p"+i)){
                GameRoleConfig.getInstance().addPolicePlayerID(commandLine.getOptionValue("p"+i));
            }
        }

        if (commandLine.hasOption('t')){
            GameRoleConfig.getInstance().addThiefPlayerID(commandLine.getOptionValue('t'));
        }
    }
}
