package comp2402a3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class represents an interval that is closed on its left and open on its
 * right.
 *
 * @author morin
 *
 * @param <T> the type of the endpoints of the interval (e.g., Integer, Double,
 * String,...)
 */
public class Interval<T extends Comparable<T>> implements Comparable<Interval<T>> {

    /**
     * This interval's left endpoint
     */
    private final T a;
    /**
     * This interval's right endpoint
     */
    private final T b;

    /**
     * Create a new interval that represents [ia,ib)
     *
     * @param ia the left endpoint of the interval
     * @param ib the right endpoint of the interval
     */
    public Interval(T ia, T ib) {
        a = ia;
        b = ib;
    }

    /**
     * @return This interval's left endpoint
     */
    public T getA() {
        return a;
    }

    /**
     * @return This interval's right endpoint
     */
    public T getB() {
        return b;
    }

    /**
     * Convert this interval to a string
     */
    @Override
    public String toString() {
        return "[" + a.toString() + "," + b.toString() + ")";
    }

    /**
     * Two intervals are considered equal if their left and right endpoints are
     * the same. Warning: This doesn't agree with the output from compareTo(),
     * which return 0 if two intervals overlap.
     * @param o
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public boolean equals(Object o) {
        return (o instanceof Interval) && ((Interval<T>) o).a.equals(a)
                && ((Interval<T>) o).b.equals(b);
    }

    /**
     * We override equals, so we also override hashCode(). This uses the method
     * discussed in the notes.
     */
    @Override
    public int hashCode() {
        return a.hashCode() * 0xa0f3727d + b.hashCode();
    }

    /**
     * Return true if this interval contains the value x
     *
     * @param x the value to test
     * @return a.compareTo(x) <= 0 && x.compareTo(b)
     */
    public boolean contains(T x) {
        return a.compareTo(x) <= 0 && x.compareTo(b) < 0;
    }

    /**
     * Compare two intervals. This is not what you're used to. If this and i
     * overlap, this method it returns 0, otherwise it determines whether this
     * comes before i or after
     *
     * One should never use this to sort a set of intervals when it is possible
     * that two intervals in the set could overlap.
     *
     * On the other hand, if you use this to store a set of disjoint intervals
     * in, for example, a SortedSet s, then one can find the interval (a,b) that
     * contains a query value x. See the main() method for an example of how to
     * do this
     *
     * @param i
     * @return A negative value if this is entirely to the left of i, a positive
     * value if this is entirely to the right of i, and 0 if this and i overlap.
     */
    @Override
    public int compareTo(Interval<T> i) {
        if (a.compareTo(i.a) == 0 && b.compareTo(i.b) == 0) {
            return 0; // identical intervals
        }
        if (b.compareTo(i.a) <= 0 && a.compareTo(i.a) < 0) {
            return -1; // this comes before i
        }
        if (a.compareTo(i.b) >= 0 && a.compareTo(i.a) > 0) {
            return 1;  // this comes after i
        }
        return 0; // intervals overlap but are not identical
    }

    /**
     * A simple test driver
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Interval<Integer>> l = new LinkedList<>();
        Random r = new Random();
        // create a randomly permuted set of intervals
        for (int i = 0; i < 10; i++) {
            l.add(r.nextInt(l.size() + 1), new Interval<>(5 * i, 5 * i + 3));
        }
        System.out.println(l);
        Collections.sort(l);
        System.out.println(l);
        SortedSet<Interval<Integer>> s = new TreeSet<>();
        s.addAll(l);
        for (int i = 0; i < 50; i++) {
            // This illustrates how to search for the interval containing a
            // query value i within a SortedSet of disjoint intervals.
            SortedSet<Interval<Integer>> ts = s.tailSet(new Interval<>(i, i)); // Find stuff >= [i,i)
            if (!ts.isEmpty()) {
                Interval<Integer> u = ts.first(); // if it's there, it's in
                // the first interval
                if (u.contains(i)) {
                    System.out.print(i + ",");
                }
            }
        }
    }
}
