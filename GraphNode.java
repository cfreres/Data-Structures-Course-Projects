import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.lang.Iterable;

/** GraphNode<E> is a node type for the Graph implementation of simple digraphs;
    E is the node element type
 */
public class GraphNode<E> {
  private List<GraphNode<E>> nbrs; // nbrs is list of neighbors of this node
  private E elt; // elt is element stored in this node
  public GraphNode(E e, List<GraphNode<E>> nb)
  { elt = e; nbrs = nb; }
  public GraphNode(E e)
  { elt = e; nbrs = new ArrayList<GraphNode<E>>(); }
  public E element() { return elt; }
  public Iterator<GraphNode<E>> neighbors()   { return nbrs.iterator(); }
  public boolean hasNeighbor(GraphNode<E> to) { return nbrs.contains(to); }
  public void    addEdge   (GraphNode<E> to)  { nbrs.add(to); }
  public void    removeEdge(GraphNode<E> to)  { nbrs.remove(to); }
  public String toString() {
    String s = "Node: " + elt + " [";
    Iterator<GraphNode<E>> nbrIter = nbrs.iterator();
    while ( nbrIter.hasNext() ) {
      s += nbrIter.next().element();
      if ( nbrIter.hasNext() )
        s += " ";
    }
    s += "]";
    return s;
  }
}
