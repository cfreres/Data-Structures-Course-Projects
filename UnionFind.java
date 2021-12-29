/** Union-Find data structure based on the UF.java code of
 *  Robert Sedgewick and Kevin Wayne, available via
 *  <a href="https://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i>.
 *  This data structure uses union by rank and path compression giving
 *  an extremely slowly growing amortized cost (average cost) per operation.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;

public class UnionFind<E> {
  private HashMap<E,E> parent = null;
  private HashMap<E,Integer> rank = null;
  private int count = 0;

  public UnionFind(List<E> list) {
    parent = new HashMap<E,E>();
    rank = new HashMap<E,Integer>();
    count = list.size();
    for ( E e : list ) {
      parent.put(e,e); // e's parent is e
      rank.put(e,0);
    }
  }

  public UnionFind(Iterator<E> iter) {
    parent = new HashMap<E,E>();
    rank = new HashMap<E,Integer>();
    count = 0;
    while ( iter.hasNext() ) {
      E e = iter.next();
      parent.put(e,e);
      rank.put(e,0);
      count++;
    }
  }

  public HashMap<E,E> parent() { return parent; }

  public HashMap<E,Integer> rank() { return rank; }

  public E find(E e) {
    E pe = parent.get(e);
    while ( ! pe.equals(e) ) {
      parent.put(e,parent.get(pe));
      e = parent.get(e);
      pe = parent.get(e);
    }
    return e;
  }

  public boolean connected(E e1, E e2) {
    return find(e1).equals(find(e2));
  }

  public void union(E e1, E e2) {
    E root1 = find(e1), root2 = find(e2);
    if ( root1.equals(root2) )
      return;
    // make root of smaller rank point to other root
    if ( rank.get(root1) < rank.get(root2) )
      parent.put(root1,root2);
    else if ( rank.get(root1) > rank.get(root2) )
      parent.put(root2,root1);
    else {
      parent.put(root2,root1);
      rank.put(root1,1+rank.get(root1));
    }
    count--;
  }

  public int numSubsets() { return count; }

  public static void main(String[] args) {
    String[] labels = { "a", "b", "c", "d", "e", "f", "g", "h" };
    UnionFind<String> uf = new UnionFind<String>(Arrays.asList(labels));
    System.out.println("uf.parent() = "+uf.parent());
    System.out.println("uf.rank() = "+uf.rank());
    uf.union("b","e");
    uf.union("c","d");
    System.out.println("After joining b & e, c & d:");
    System.out.println("uf.parent() = "+uf.parent());
    System.out.println("uf.rank() = "+uf.rank());
    System.out.println("count = "+uf.numSubsets());
    uf.union("e","h");
    uf.union("a","g");
    uf.union("h","c");
    System.out.println("Now joining e & h, a & g, h & c:");
    System.out.println("uf.parent() = "+uf.parent());
    System.out.println("uf.rank() = "+uf.rank());
    System.out.println("count = "+uf.numSubsets());
    for ( String s : labels )
      System.out.println("Rep for "+s+" is "+uf.find(s));
    uf.union("f","g");
    System.out.println("Now joining f & g:");
    System.out.println("uf.parent() = "+uf.parent());
    System.out.println("uf.rank() = "+uf.rank());
    System.out.println("count = "+uf.numSubsets());
    // uf.union("a","z"); // throws null pointer exception
    for ( String s : labels )
      System.out.println("Rep for "+s+" is "+uf.find(s));
    System.out.println("uf.parent() = "+uf.parent());
    System.out.println("uf.rank() = "+uf.rank());
    System.out.println("count = "+uf.numSubsets());
  }
}
