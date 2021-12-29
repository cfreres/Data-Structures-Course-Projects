import java.util.HashMap;
import java.util.Iterator;
import java.lang.Number;
import java.util.ArrayList;

public class EdgeWeightedGraph<E,W extends Number> extends Graph<E> {
  private HashMap<GraphEdge<E>,W> weights = null;
  private W defaultWeight;
  public EdgeWeightedGraph(W defWt) {
    super();
    weights = new HashMap<GraphEdge<E>,W>();
    defaultWeight = defWt;
  }

  @Override
  public void makeUndirected() {
    Iterator<GraphEdge<E>> edgeIter = edges();
    ArrayList<GraphEdge<E>> edgeList = new ArrayList<GraphEdge<E>>();
    while ( edgeIter.hasNext() )
      edgeList.add(edgeIter.next());
    for ( GraphEdge<E> e : edgeList ) {
      GraphNode<E> from = e.from(), to = e.to();
      if ( ! to.hasNeighbor(from) )
        addEdge(to.element(),from.element(),weight(e));
    }
  }


  @Override
  public GraphEdge<E> addEdge(E efrom,E eto) {
    GraphEdge<E> edge = super.addEdge(efrom,eto);
    // System.out.println("Edge is "+edge);
    weights.put(edge,defaultWeight);
    return edge;
  }
  public GraphEdge<E> addEdge(E efrom, E eto, W wt) {
    GraphEdge<E> edge = super.addEdge(efrom,eto);
    // System.out.println("Edge is "+edge);
    weights.put(edge,wt);
    // System.out.println("Edge weight = "+wt);
    // System.out.println(weights);
    return edge;
  }

  @Override
  public void removeEdge(E efrom, E eto) {
    super.removeEdge(efrom,eto);
    GraphNode<E> from = getNode(efrom), to = getNode(eto);
    weights.remove(new GraphEdge<E>(from,to));
  }

  public W weight(GraphEdge<E> e) {
    return weights.get(e);
  }

  @Override
  public String toString() {
    String s = "EdgeWeightedGraph: \n";
    for ( GraphNode<E> n : nodes() )
      s += n.toString()+"\n";
    // s += weights + "\n"; // for debugging
    Iterator<GraphEdge<E>> edges = edges();
    while ( edges.hasNext() ) {
      GraphEdge<E> e = edges.next();
      s += e.toString()+" (Weight: "+weight(e)+")\n";
    }
    s += "endEdgeWeightedGraph";
    return s;
  }

  public static void main(String[] args) {
    EdgeWeightedGraph<String,Integer> g = new EdgeWeightedGraph<>(1);
    String[] nodes = { "apple", "berry", "cranberry", "orange", "kumkwat",
                    "date", "lemon" };
    for ( String s : nodes )
      g.addNode(s);
    g.addEdge("apple","kumkwat",7);
    System.out.println("apple node = "+g.getNode("apple"));
    System.out.println("kumkwat node = "+g.getNode("kumkwat"));
    System.out.println("weight = "+
            g.weight(new GraphEdge<String>(g.getNode("apple"),g.getNode("kumkwat"))));
    g.addEdge("berry","orange");
    g.addEdge("orange","lemon",3);
    g.addEdge("berry","cranberry",4);
    g.addEdge("orange","date",5);
    g.addEdge("lemon","apple",2);
    g.addEdge("cranberry","date");
    System.out.println(g);

  }
}
