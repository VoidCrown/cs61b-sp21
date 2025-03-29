package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import gitlet.Utils.*;

/** Represents a gitlet stage area.
 * Include additionStage and removalStage.
 *
 *  @author Mr.T
 */
public class Stage implements Serializable {

    // Stage structure: <filename, blob hashcode>
    private HashMap<String, String> additionStage;
    private HashMap<String, String> removalStage;

    public Stage() {
        additionStage = new HashMap<>();
        removalStage = new HashMap<>();
    }

    public void saveToFile(File stage) {
        Utils.writeObject(stage, this);
    }

    public HashMap<String, String> getAdditionStage() {
        return additionStage;
    }

    public HashMap<String, String> getRemovalStageStage() {
        return removalStage;
    }


//    public void addToAdd(String filename, String hashcode) {
//        additionStage.put(filename, hashcode);
//    }
//
//    public void addToRm(String filename, String hashcode) {
//        removalStage.put(filename, hashcode);
//    }
//
//    public boolean findAdd(String filename) {
//        return additionStage.containsKey(filename);
//    }
//
//    public boolean findRm(String filename) {
//        return removalStage.containsKey(filename);
//    }
//
//    public void clear() {
//        additionStage.clear();
//        removalStage.clear();
//    }



}
