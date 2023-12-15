package ru.gb.lesson3.hw;

import java.io.*;
import java.util.UUID;

/** @noinspection ResultOfMethodCallIgnored*/
public class PackUnpack {
    /** @noinspection UnnecessaryToStringCall*/
    public static String pack(Serializable object) throws IOException {
        String fileName=object.getClass() + "_" + UUID.randomUUID().toString();
        File dataFile=new File(fileName);
        if (!dataFile.exists()) {
            dataFile.createNewFile();
        } else {
            dataFile.delete();
            dataFile.createNewFile();
        }

        ObjectOutputStream stream=new ObjectOutputStream(new FileOutputStream(dataFile));
        stream.writeObject(object);
        stream.close();
        return fileName;
    }

    public static Serializable unpack(String fileName) throws IOException, ClassNotFoundException {
        File dataFile=new File(fileName);
        if (!dataFile.exists()) {
            return null;
        } else {
            ObjectInputStream stream=new ObjectInputStream(new FileInputStream(dataFile));
            Serializable object=(Serializable) stream.readObject();
            stream.close();
            dataFile.delete();
            return (object);
        }

    }
}