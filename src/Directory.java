import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Directory implements Serializable {
    private String name;
    private Directory parent;
    private List<SimFile> files = new ArrayList<>();
    private List<Directory> directories = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public boolean createFile(String fileName) {
        if (getFile(fileName) != null) return false;
        files.add(new SimFile(fileName));
        return true;
    }

    public boolean deleteFile(String fileName) {
        SimFile f = getFile(fileName);
        if (f != null) {
            files.remove(f);
            return true;
        }
        return false;
    }

    public boolean renameFile(String oldName, String newName) {
        SimFile f = getFile(oldName);
        if (f != null && getFile(newName) == null) {
            f.setName(newName);
            return true;
        }
        return false;
    }

    public SimFile getFile(String fileName) {
        for (SimFile f : files) {
            if (f.getName().equals(fileName)) return f;
        }
        return null;
    }

    public boolean createDirectory(String dirName) {
        if (getDirectory(dirName) != null) return false;
        Directory newDir = new Directory(dirName, this);
        directories.add(newDir);
        return true;
    }

    public boolean deleteDirectory(String dirName) {
        Directory d = getDirectory(dirName);
        if (d != null && d.isEmpty()) {
            directories.remove(d);
            return true;
        }
        return false;
    }

    public boolean renameDirectory(String oldName, String newName) {
        Directory d = getDirectory(oldName);
        if (d != null && getDirectory(newName) == null) {
            d.setName(newName);
            return true;
        }
        return false;
    }

    public Directory getDirectory(String dirName) {
        for (Directory d : directories) {
            if (d.getName().equals(dirName)) return d;
        }
        return null;
    }

    public boolean isEmpty() {
        return files.isEmpty() && directories.isEmpty();
    }

    public List<SimFile> getFiles() {
        return files;
    }

    public List<Directory> getDirectories() {
        return directories;
    }
}
