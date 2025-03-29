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
                Repository.checkInitialize();
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
                Repository.checkInitialize();
                Repository.commit(args[1]);
                break;
            case "rm" :
                // handle the `rm [filename]` command
                if (args.length != 2) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.rm(args[1]);
                break;
            case "log":
                // handle the `log` command
                if (args.length > 1) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.log();
                break;
            case "global-log":
                // handle the `global-log` command
                if (args.length > 1) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.globalLog();
                break;
            case "find":
                // handle the `find [commit message]` command
                if (args.length != 2) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.find(args[1]);
                break;
            case "status":
                // handle the `status` command
                if (args.length > 1) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.status();
                break;
            case "checkout":
                if (args.length == 2) {
                    // handle the `checkout [branch name]` command
                    Repository.checkInitialize();
                    Repository.checkoutBranch(args[1]);
                } else if (args.length == 3) {
                    // handle the `checkout -- [file name]` command
                    Repository.checkInitialize();
                    Repository.checkoutFile(null, args[2]);
                } else if (args.length == 4) {
                    // handle the `checkout [commit id] -- [file name]` command
                    Repository.checkInitialize();
                    Repository.checkoutFile(args[1], args[3]);
                } else {
                    throw error("Incorrect operands.");
                }
                break;
            case "branch":
                // handle the `branch [branch name]` command
                if (args.length > 2) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                // handle the `rm-branch [branch name]` command
                if (args.length > 2) {
                    throw error("Incorrect operands.");
                }
                Repository.checkInitialize();
                Repository.rmBranch(args[1]);
                break;
            default:
                throw error("No command with that name exists.");
        }
    }
}
