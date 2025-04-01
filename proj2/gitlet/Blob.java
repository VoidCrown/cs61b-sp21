package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

/** Represents a gitlet blob object.
 *  A Blob variable represents a file in the system.
 *
 *  @author Mr.T
 */
public class Blob implements Serializable {

    private byte[] contents;
    private final int size;

    public Blob(byte[] contents) {
        this.contents = contents;
        this.size = contents.length;
    }

    /** get the sha1 hash value using this instance variable. */
    public String getID() {
        return sha1((Object) contents);
    }

    public void saveToFile(File file) {
        writeContents(file, (Object) contents);
    }

    public byte[] getContents() {
        return contents;
    }

}
