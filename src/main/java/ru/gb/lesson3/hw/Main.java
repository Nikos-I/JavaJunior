/**
 * Написать класс с двумя методами:
 * 1. принимает объекты, имплементирующие интерфейс serializable, и сохраняющие их в файл. Название файл - class.getName() + "_" + UUID.randomUUID().toString()
 * 2. принимает строку вида class.getName() + "_" + UUID.randomUUID().toString() и загружает объект из файла и удаляет этот файл.
 *
 * Что делать в ситуациях, когда файла нет или в нем лежит некорректные данные - подумать самостоятельно.
 */

package ru.gb.lesson3.hw;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/** @noinspection SimplifyStreamApiCallChains*/
public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<String> fileList = new ArrayList<>();
        ArrayList<Object> sourceList = new ArrayList<>();
        ArrayList<Object> targetList = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            sourceList.add(new TestObject());
        }
        //упаковка
        sourceList.stream().forEach(o -> {
            try {
                System.out.println("Упаковка объекта: ");
                System.out.println(o.toString());
                fileList.add(PackUnpack.pack((Serializable)o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //распаковка
        for (String file : fileList) {
            try {
                System.out.println("Распаковка объекта: ");
                System.out.println(file);
                targetList.add(PackUnpack.unpack(file));
                System.out.println(targetList.get(targetList.size() - 1).toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
