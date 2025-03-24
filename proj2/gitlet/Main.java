package gitlet;

import java.io.IOException;

import static gitlet.Utils.error;
import static java.lang.System.exit;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Mr.T
 */

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args){
        if (args.length == 0) {
            throw error("Please enter a command.");
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // handle the `init` command
                if (args.length > 1) {
                    throw error("Incorrect operands.");
                }
                Repository.initialize();
                break;
            case "add":
                // handle the `add [filename]` command
                if (args.length != 2) {
                    throw error("Incorrect operands.");
                }
                Repository.add(args[1]);
                break;
            case "commit":
                // handle the `commit [message]` command
                if (args.length > 2) {
                    throw error("Incorrect operands.");
                }
                if (args.length == 1) {
                    throw error("Please enter a commit message.");
                }
                Repository.commit(args[1]);
                break;
            case "rm" :
                // handle the `rm [filename]` command
                if (args.length != 2) {
                    throw error("Incorrect operands.");
                }
                Repository.rm(args[1]);
                break;

            default:
                throw error("No command with that name exists.");
        }
    }
}
