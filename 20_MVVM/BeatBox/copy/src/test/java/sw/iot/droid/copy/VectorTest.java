package sw.iot.droid.copy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * @author admin  on 2019/1/25.
 */
public class VectorTest {
    @Test
    public void testVector(){
        List<Integer> vector = new ArrayList<>(10);
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        vector.add(5);
        vector.add(5);
        vector.add(5);
//        ((Vector<Integer>) vector).addElement();
        for (Integer number:vector){
            System.out.println(number);
        }
        System.out.println("------>>>>");
        Vector<Integer> temp = new Vector<>();
        temp.add(5);
        temp.add(2);
        temp.add(9);
        Stack<Integer> stack = new Stack<>();
        vector.removeAll(temp);
        for (Integer number:vector){
            System.out.println(number);
        }
    }
}
