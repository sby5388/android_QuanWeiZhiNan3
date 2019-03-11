package sw.iot.droid.copy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author admin  on 2019/1/25.
 */
public class CollectionTest {
    @Test
    public void test() {
        int count = 20;
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            workers.add(new Worker("worker" + i));
        }
        List<Worker> workerLinkedList = new LinkedList<>(workers);
        //如果是这种的移除，得从最后面开始往前移动
        for (int i = count - 1; i > 0; i--) {
            if (i % 4 == 0) {
                System.out.println(workerLinkedList.remove(i).name);
            }
        }

        workers = new ArrayList<>(workerLinkedList);
        System.out.println("^^^^^^^^^");
        for (Worker worker : workers) {
            System.out.println(worker.name);
        }


    }

    private class Worker {
        UUID id;
        String name;

        Worker(String name) {
            this.id = UUID.randomUUID();
            this.name = name;
        }
    }

}
