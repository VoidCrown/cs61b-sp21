package gitlet;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import static gitlet.Utils.*;
import java.io.Serializable;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author Mr.T
 */
public class Commit implements Serializable{

    /** The message of this Commit. */
    private final String message;
    /** The timestamp of this Commit. */
    private final Date timestamp;
    /** The parent commit of this Commit. */
    private final String parent;
    /** The second parent of this Commit, used for merge command. */
    private final String secondParent;
    /** The files of this Commit. Structure: <file name, hash code of blob> */
    private HashMap<String, String> files = new HashMap<>();

    public Commit(String message, String parent, String secondParent) {
        this.message = message;
        this.parent = parent;
        this.secondParent = secondParent;
        if (message.equals("initial commit")) {
            timestamp = new Date(0);
        } else {
            timestamp = new Date();
        }
    }

    public Commit(String message, Commit parent) {
        this.message = message;
        this.parent = parent.getID();
        this.secondParent = null;
        timestamp = new Date();
        files = new HashMap<>(parent.getFiles());
    }

    /** get the sha1 hash value using this instance variable. */
    public String getID() {
        String variables = message + timestamp.toString() + parent + secondParent + files.toString();
        return sha1(variables);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getParent() {
        return parent;
    }

    public String getSecondParent() {
        return secondParent;
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void saveToFile(File file) {
        writeObject(file, this);
    }


}
