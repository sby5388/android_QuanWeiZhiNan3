package com.android.alarmmanager2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @author Administrator  on 2019/4/3.
 */
public class O0o {
    private static final String FILE_NAME = "men.ser";

    List<Man> getMan() throws Exception {
        File file = new File(FILE_NAME);
        InputStream inputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        List<Man> manList = (List<Man>) objectInputStream.readObject();
        objectInputStream.close();
        inputStream.close();
        return manList;

    }

    void write(List<Man> men) throws Exception {
        File file = new File(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(men);
        objectOutputStream.close();
        System.out.println("保存成功");
    }
}
