package day07;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class Directory {
    private HashMap<String, Directory> subDirectories = new HashMap<>();
    private HashMap<String, Long> files = new HashMap<>();
    private long totalSize = 0;

    public static long totalSizeUnter100000Count = 0;

    public Directory getAndAddSubdirectory(String name) {
        Directory existing = subDirectories.get(name);
        if (existing == null) {
            existing = new Directory();
            subDirectories.put(name, existing);
        }
        return existing;
    }

    public void addFile(String name, long size) {
        files.put(name, size);
    }

    public void print(int indent) {
        if (totalSize < 100000) {
            totalSizeUnter100000Count += totalSize;
        }
        String indentString = " ".repeat(indent);
        for (Entry<String, Directory> e : subDirectories.entrySet()) {
            System.out.println(indentString + "dir " + e.getKey() + " (" + e.getValue().totalSize + ")");
            e.getValue().print(indent + 2);
        }
        for (Entry<String, Long> e : files.entrySet()) {
            System.out.println(indentString + "file " + e.getKey() + ": " + e.getValue());
        }
    }

    public void addTotalSize(long size) {
        totalSize += size;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public Collection<Directory> getSubdirectories() {
        return subDirectories.values();
    }
}
