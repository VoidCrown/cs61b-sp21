package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  does at a high level.
 *  .gitlet structure:
 * .Gitlet
 *     |--objects // store objects in gitlet
 *         |--commits (folder)
 *         |--blobs (folder)
 *     |--refs // store branch info
 *         |--heads // store reference to heads of branches
 *             |--master (file) // refer to current branch, it's hash code
 *             |--other head file...
 *     |--HEAD (file) //store hash code of current pointer
 *     |--stage (file)
 *
 *  @author Mr.T
 */
public class Repository {

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The objects directory. */
    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");
    /** The commits directory. */
    public static final File COMMITS_DIR = join(OBJECTS_DIR, "commits");
    /** The blobs directory. */
    public static final File BLOBS_DIR = join(OBJECTS_DIR, "blobs");

    /** The refs directory. */
    public static final File REFS_DIR = join(GITLET_DIR, "refs");
    /** The heads directory. */
    public static final File HEADS_DIR = join(REFS_DIR, "heads");
    /** The HEAD file */
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    /** The stage area file */
    public static final File STAGE = join(GITLET_DIR, "stage");

//    /** The addition stage directory. */
//    public static final File ADDITION_STAGE_DIR = join(GITLET_DIR, "additionStage");
//    /** The removal stage directory. */
//    public static final File REMOVAL_STAGE_DIR = join(GITLET_DIR, "removalStage");


    // helper methods
    /** Initialize the directory structure. */
    private static void setupPersistence() {
        GITLET_DIR.mkdir();
        OBJECTS_DIR.mkdir();
        COMMITS_DIR.mkdir();
        BLOBS_DIR.mkdir();
        REFS_DIR.mkdir();
        HEADS_DIR.mkdir();
//        ADDITION_STAGE_DIR.mkdir();
//        REMOVAL_STAGE_DIR.mkdir();
    }

    // methods for commands
    /** Implement init command.
     * Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit:
     * a commit that contains no files and has the commit message initial commit (just like that, with no punctuation).
     * It will have a single branch: master, which initially points to this initial commit,
     * and master will be the current branch.
     * The timestamp for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970
     * in whatever format you choose for dates (this is called “The (Unix) Epoch”, represented internally by the time 0.)
     * Since the initial commit in all repositories created by Gitlet will have exactly the same content,
     * it follows that all repositories will automatically share this commit (they will all have the same UID)
     * and all commits in all repositories will trace back to it. */
    public static void initialize() {
        // failure cases
        if (GITLET_DIR.exists()) {
            throw error("A Gitlet version-control system already exists in the current directory.");
        }

        // initialize directory structure
        setupPersistence();

        // create the first commit and store it
        Commit commit = new Commit("initial commit", null, null);
        String id = commit.getID();
        File c_objdir = join(COMMITS_DIR, id.substring(0, 1));
        File c_obj = join(COMMITS_DIR, id.substring(0, 1), id.substring(2));
        c_objdir.mkdirs();
        commit.saveToFile(c_obj);

        // create and initialize necessary files: HEAD, master and stage.
        File master = join(HEADS_DIR, "master");
        Stage stage = new Stage();
        writeContents(HEAD, id);
        writeContents(master, id);
        writeObject(STAGE, stage);

    }

    /** Implement add command.
     * Adds a copy of the file as it currently exists to the staging area (see the description of the commit command).
     * For this reason, adding a file is also called staging the file for addition.
     * Staging an already-staged file overwrites the previous entry in the staging area with the new contents.
     * The staging area should be somewhere in .gitlet.
     * If the current working version of the file is identical to the version in the current commit,
     * do not stage it to be added, and remove it from the staging area if it is already there
     * (as can happen when a file is changed, added, and then changed back to it’s original version).
     * The file will no longer be staged for removal (see gitlet rm), if it was at the time of the command.
     *  */
    public static void add(String filename) {
        // If a user inputs a command that requires being in an initialized Gitlet working directory,
        // but is not in such a directory.
        if (!GITLET_DIR.exists()) {
            throw error("Not in an initialized Gitlet directory.");
        }

        File addedFile = join(CWD, filename);

        // The file does not exist.
        if (!addedFile.exists()) {
            throw error("File does not exist.");
        }

        // 1. Create blob
        // 2. Add blob to objects and add its reference to additionStage if there is no same blob or blob is different.
        // 3. Remove blob from additionStage if the blob is same.
        Blob blob = new Blob(readContents(addedFile));
        String id = blob.getID();
        File b_objdir = join(BLOBS_DIR, id.substring(0, 1));
        File b_obj = join(BLOBS_DIR, id.substring(0, 1), id.substring(2));
        Stage stage = readObject(STAGE, Stage.class);
        HashMap<String, String> additionStage = stage.getAdditionStage();

        if (additionStage.containsKey(filename) && additionStage.containsValue(id)) {
            // the blob is same
            // remove blob from additionStage
            additionStage.replace(filename, id);
            stage.saveToFile(STAGE);
        } else {
            // blob is different
            // create or overwrite blob file to .gitlet/objects/blobs directory
            b_objdir.mkdirs();
            blob.saveToFile(b_obj);

            // add or overwrite blob to additionStage
            additionStage.put(filename, id);
            stage.saveToFile(STAGE);
        }
    }

    /** Implement commit message.
     * Saves a snapshot of tracked files in the current commit and staging area so they can be restored at a later time,
     * creating a new commit.
     * By default, each commit’s snapshot of files will be exactly the same as its parent commit’s snapshot of files;
     * it will keep versions of files exactly as they are, and not update them.
     * A commit will only update the contents of files it is tracking that have been staged for addition at the time of commit,
     * in which case the commit will now include the version of the file that was staged
     * instead of the version it got from its parent.
     * A commit will save and start tracking any files that were staged for addition but weren’t tracked by its parent.
     * Finally, files tracked in the current commit may be untracked in the new commit
     * as a result being staged for removal by the rm command.
     * */
    public static void commit(String message) {
        // get current commit
        String str_currentCommit = readContentsAsString(HEAD);
        File c_obj = join(COMMITS_DIR, str_currentCommit.substring(0, 1), str_currentCommit.substring(2));
        Commit currentCommit = readObject(c_obj, Commit.class);

        // default snapshot
        Commit newCommit = new Commit(message, currentCommit);

        // update commit
        HashMap<String, String> files = newCommit.getFiles();
        Stage stage = readObject(STAGE, Stage.class);
        HashMap<String, String> additionStage = stage.getAdditionStage();
        HashMap<String, String> removalStage = stage.getRemovalStageStage();

        if (additionStage.isEmpty() && removalStage.isEmpty()) {
            throw error("No changes added to the commit.");
        }
        files.putAll(additionStage);
        for (Map.Entry<String, String> entry : removalStage.entrySet()) {
            files.remove(entry.getKey(), entry.getValue());
        }

        // clear and save stage
        additionStage.clear();
        removalStage.clear();
        stage.saveToFile(STAGE);

        // save commit
        String id = newCommit.getID();
        File new_c_objdir = join(COMMITS_DIR, id.substring(0, 1));
        File new_c_obj = join(COMMITS_DIR, id.substring(0, 1), id.substring(2));
        new_c_objdir.mkdirs();
        newCommit.saveToFile(new_c_obj);

        // update HEAD
        writeContents(HEAD, id);
    }

    /** Implement rm command.
     * Unstage the file if it is currently staged for addition.
     * If the file is tracked in the current commit,
     * stage it for removal and remove the file from the working directory
     * if the user has not already done so (do not remove it unless it is tracked in the current commit).
     * */
    public static void rm(String filename) {
        Stage stage = readObject(STAGE, Stage.class);
        HashMap<String, String> additionStage = stage.getAdditionStage();
        HashMap<String, String> removalStage = stage.getRemovalStageStage();
        String commitID = readContentsAsString(HEAD);
        File c_obj = join(COMMITS_DIR, commitID.substring(0, 1), commitID.substring(2));
        Commit commit = readObject(c_obj, Commit.class);
        HashMap<String, String> files = commit.getFiles();
        if (!additionStage.containsKey(filename)) {
            // unstaged and untracked
            if (!files.containsKey(filename)) {
                throw error("No reason to remove the file.");
            } else {
                // unstaged but tracked
                // stage for removal and remove it from cwd
                File removedFile = join(CWD, filename);
                Blob blob = new Blob(readContents(removedFile));
                String id = blob.getID();
                removalStage.put(filename, id);
                removedFile.delete();
                stage.saveToFile(STAGE);
            }
        } else {
            // staged, remove from stage
            additionStage.remove(filename);
            stage.saveToFile(STAGE);
        }
    }

}
