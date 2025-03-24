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
    private String message;
    /** The timestamp of this Commit. */
    private Date timestamp;
    /** The parent commit of this Commit. */
    private String parent;
    /** The second parent of this Commit, used for merge command. */
    private String secondParent;
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
        return sha1((Object) serialize(this));
    }

    public HashMap<String, String> getFiles() {
        return files;
    }

    public void saveToFile(File file) {
        writeObject(file, this);
    }


}
