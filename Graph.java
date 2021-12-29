import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.lang.Cloneable;

public class Graph<E> extends Kruskal {
  private HashMap<E,GraphNode<E>> nodeMap;

  public Graph() {
    nodeMap = new HashMap<E,GraphNode<E>>();
  }

  public GraphNode<E> getNode(E elt) throws NoSuchElementException {
    if ( nodeMap.containsKey(elt) )
      return nodeMap.get(elt);
    else
      throw(new NoSuchElementException("getNode: "+elt));
  }

  public GraphNode<E> addNode(E elt) {
    if ( ! nodeMap.containsKey(elt) )
      return nodeMap.put(elt,new GraphNode<E>(elt));
    else
      return nodeMap.get(elt); // not actually adding a node
  }

  public GraphEdge<E> addEdge(E eltfrom, E eltto)
              throws NoSuchElementException {
    if ( nodeMap.containsKey(eltfrom) && nodeMap.containsKey(eltto) ) {
      GraphNode<E> nfrom = getNode(eltfrom);
      GraphNode<E> nto   = getNode(eltto);
      nfrom.addEdge(nto);
      return new GraphEdge<E>(nfrom,nto);
    }
    else
      return null;
  }

  public void removeEdge(E eltfrom, E eltto) {
    if ( nodeMap.containsKey(eltfrom) && nodeMap.containsKey(eltto) ) {
      GraphNode<E> nfrom = getNode(eltfrom);
      GraphNode<E> nto   = getNode(eltto);
      if ( nfrom.hasNeighbor(nto) ) {
        nfrom.removeEdge(nto);
      }
    }
  }

  public void makeUndirected() {
    Iterator<GraphEdge<E>> edgeIter = edges();
    while ( edgeIter.hasNext() ) {
      GraphEdge<E> e = edgeIter.next();
      GraphNode<E> from = e.from(), to = e.to();
      if ( ! to.hasNeighbor(from) )
        to.addEdge(from);
    }
  }


  public boolean isUndirected() {
    Iterator<GraphEdge<E>> edgeIter = edges();
    while ( edgeIter.hasNext() ) {
      GraphEdge<E> e = edgeIter.next();
      GraphNode<E> from = e.from(), to = e.to();
      if ( ! to.hasNeighbor(from) )
        return false;
    }
    return true;
  }
  public List<GraphNode<E>> dfs(E start) {
    HashSet<GraphNode<E>> visited = new HashSet<GraphNode<E>>();
    GraphNode<E> startNode = getNode(start);
    visited.add(startNode);
    return dfsRec(startNode,visited);
  }

  private List<GraphNode<E>> dfsRec(GraphNode<E> startNode,
                                HashSet<GraphNode<E>> visited) {
    Iterator<GraphNode<E>> nbrIter = startNode.neighbors();
    ArrayList<GraphNode<E>> list = new ArrayList<GraphNode<E>>();
    // put dfs recursive here (~ 6 lines of code)
    return list;
  }

  public HashMap<GraphNode<E>,GraphNode<E>> dfsPrev(E start) {
    GraphNode<E> startNode = getNode(start);
    Iterator<GraphNode<E>> nbrIter = startNode.neighbors();
    // At end: prev.get(n) is predecessor in DFS
    HashMap<GraphNode<E>,GraphNode<E>> prev =
              new HashMap<GraphNode<E>,GraphNode<E>>();
    prev.put(startNode,startNode); // avoids using null for value
    dfsPrevRec(startNode, prev);
    return prev;
  }

  private void dfsPrevRec(GraphNode<E> start,
            HashMap<GraphNode<E>,GraphNode<E>> prev) {
    Iterator<GraphNode<E>> nbrIter = start.neighbors();
    while ( nbrIter.hasNext() ) {
      GraphNode<E> n = nbrIter.next();
      if ( ! prev.containsKey(n) ) {
        prev.put(n,start); // start -> n
        dfsPrevRec(n,prev);
      }
    }
  }

  public List<GraphNode<E>> bfs(E start) {
    GraphNode<E> startNode = getNode(start);
    Queue<GraphNode<E>> q = new ArrayDeque<GraphNode<E>>();
    ArrayList<GraphNode<E>> list = new ArrayList<GraphNode<E>>();
    HashSet<GraphNode<E>> visited = new HashSet<GraphNode<E>>();
    list.add(startNode);
    visited.add(startNode);
    q.add(startNode);
    while ( ! q.isEmpty() ) {
      GraphNode<E> from = q.remove();
      Iterator<GraphNode<E>> nbrIter = from.neighbors();
      while (  nbrIter.hasNext() ) {
        GraphNode<E> to = nbrIter.next();
        if ( ! visited.contains(to) ) {
          list.add(to);
          visited.add(to);
          q.add(to);
        } // if ...
      } // while ...
    } // while ...
    return list;
  }

  public HashMap<GraphNode<E>,GraphNode<E>> bfsPrev(E start) {
    GraphNode<E> startNode = getNode(start);
    Queue<GraphNode<E>> q = new ArrayDeque<GraphNode<E>>();
    HashMap<GraphNode<E>,GraphNode<E>> prev =
                new HashMap<GraphNode<E>,GraphNode<E>>();
    prev.put(startNode,startNode); // root of BFS tree
    q.add(startNode);
    while ( ! q.isEmpty() ) {
      GraphNode<E> from = q.remove();
      Iterator<GraphNode<E>> nbrIter = from.neighbors();
      while (  nbrIter.hasNext() ) {
        GraphNode<E> to =nbrIter.next();
        if ( ! prev.containsKey(to) ) {
          prev.put(to,from);
          q.add(to);
        } // if ...
      } // while ...
    } // while ...
    return prev;
  }

  public Collection<GraphNode<E>> nodes() { return nodeMap.values(); }

  public Iterator<GraphEdge<E>> edges() {
     Iterator<GraphEdge<E>> it = new Iterator<GraphEdge<E>>() {
       private Iterator<GraphNode<E>> fromIter = null;
       private Iterator<GraphNode<E>> toIter = null;
       private GraphNode<E> from = null, to = null;
       private int state = 0; // start state, end state is 4
       private final int open = 1, closed = 2;
       private int lock = open;

       private void step() { // sets from and to fields
         boolean breakLoop = false;
         while ( ! breakLoop ) {
           switch ( state ) {
             case 0:
                fromIter = nodeMap.values().iterator();
                if ( fromIter.hasNext() ) {
                  from = fromIter.next();
                  toIter = from.neighbors();
                  state = 1;
                } else {
                  state = 4;
                  breakLoop = true;
                }
                break;
             case 1:
                if ( toIter.hasNext() ) {
                  to = toIter.next();
                  state = 2;
                  breakLoop = true;
                } else {
                  state = 3;
                }
                break;
             case 2:
                if ( toIter.hasNext() ) {
                  to = toIter.next();
                  state = 2;
                  breakLoop = true;
                } else {
                  state = 3;
                }
                break;
             case 3:
                if ( fromIter.hasNext() ) {
                  from = fromIter.next();
                  toIter = from.neighbors();
                  state = 1;
                } else {
                  state = 4;
                  breakLoop = true;
                }
                break;
             default: // include case 4: (== end state)
                breakLoop = true;
                break;
           }
         }
       }

       @Override
       public boolean hasNext() {
         if ( lock == open )
            step();
         lock = closed;
         return state != 4;
       }

       @Override
       public GraphEdge<E> next() {
         if ( lock == open )
            step();
         lock = open;
         return new GraphEdge<E>(from,to);
       }
     };
     return it;
   }

  public String toString() {
    String s = "Graph: \n";
    for ( GraphNode<E> n : nodes() )
      s += n.toString()+"\n";

    // Iterator<GraphEdge<E>> iter = edges();
    // while ( iter.hasNext() ) {
    //   GraphEdge<E> e = iter.next();
    //   s += e.toString()+"\n";
    // }
    s += "endGraph\n";
    return s;
  }

  public static void main(String[] args) {
    EdgeWeightedGraph<String, Integer> g = new EdgeWeightedGraph<String, Integer>(1);
    System.out.println("g = "+g);
    // main code goes here -- usually a basic test

    String[] nodeNames = { "a", "b", "c", "d", "e", "f", "g", "h" };
    for (String s : nodeNames )
      g.addNode(s);

    g.addEdge("a","d", 10);
    g.addEdge("c","e", 9);
    g.addEdge("a","c", 7);
    g.addEdge("g","h", 6);
    g.addEdge("b","e", 8);
    g.addEdge("e","d", 3);
    g.addEdge("a","b", 1);
    g.addEdge("c","d", 11);
    g.addEdge("b","c", 5);
    g.addEdge("g","f", 4);
    g.addEdge("h", "f", 2);

    System.out.println();
    System.out.println(g);
    System.out.println();
    System.out.println("Nodes:");
    for ( GraphNode<String> n : g.nodes() )
      System.out.println(n);
    System.out.println();
    System.out.println("Edges:");
    Iterator<GraphEdge<String>> iter = g.edges();
    while ( iter.hasNext() )
      System.out.println(iter.next());
    System.out.println();
    System.out.println("Is g undirected? " + g.isUndirected());
    System.out.println();
    g.makeUndirected();
    System.out.println(g);
    System.out.println();
    System.out.println("Is g undirected now? " + g.isUndirected());
    System.out.println();
    List<GraphEdge<String>> kMST = weightedMST(g);
    System.out.println("MST:");
    for (GraphEdge<String> n : kMST)
      System.out.println(n + " (Weight: " + g.weight(n) + ")");

  }
}
