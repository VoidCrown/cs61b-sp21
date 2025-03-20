# Gitlet Design Document

**Name**: Mr.T

## Classes and Data Structures

### Commit

A commit will consist of a log message, timestamp, a mapping of file names to blob references, 
a parent reference, and (for merges) a second parent reference.

#### Instance variables

* String message
* Date Timestamp: Using Date type to store some time info.
* String parent: Reference to parent commit.
* String secondParent: Using for merge commands. Reference to another parent commit.
* List&lt;Array> files: Stores all the references to blobs.

Parent and secondParent are pointers to commit, 
and using String type here because Serialize will encode all the objects it points.
To save time and space, it uses String type to store pointer.

For pointers to blobs storage, it uses ArrayList here because its searching method 
is used more frequently than adding or deleting methods.

#### Methods



### Blob

#### Instance variables

1. Field 1
2. Field 2

#### Methods



## Algorithms

### .Gitlet folder structure:

```agsl
.Gitlet
    |--objects // store objects in gitlet
        |--commits (folder)
        |--blobs (folder)
    |--refs // store branch info
        |--heads // store reference to heads of branches
            |--master (file) // refer to current branch
            |--other head file...
    |--HEAD (file) //store hash code of current pointer
    |--stage (file)
```

### Commands

#### init


## Persistence

