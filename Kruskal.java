import java.util.*;

public class Kruskal {
  public static <E> List<GraphEdge<E>> unweightedMST(Graph<E> g) {
    // assumes g is undirected
    ArrayList<GraphEdge<E>> mst = new ArrayList<GraphEdge<E>>();
    Iterator<GraphEdge<E>> edges = g.edges();
    UnionFind<GraphNode<E>> uf =
        new UnionFind<GraphNode<E>>(g.nodes().iterator());
    while ( edges.hasNext() ) {
      GraphEdge<E> edge = edges.next();
      GraphNode<E> from = edge.from(), to = edge.to();
      if ( ! uf.find(from).equals(uf.find(to)) ) {
        mst.add(edge);
        uf.union(from,to);
      }
    }
    return mst;
  }


  public static <E> List<GraphEdge<E>> weightedMST(EdgeWeightedGraph<E,Integer>  g ){
    Comparator<GraphEdge<E>> c = new Comparator<GraphEdge<E>>(){
      public int compare(GraphEdge<E> e1, GraphEdge<E> e2) {
        Integer w1 = g.weight(e1), w2 = g.weight(e2);
        return w1.compareTo(w2);
      }
    };
    ArrayList<GraphEdge<E>> mst = new ArrayList<GraphEdge<E>>();
    Iterator<GraphEdge<E>> edgeIter = g.edges();
    ArrayList<GraphEdge<E>> edgeList = new ArrayList<>();
    while ( edgeIter.hasNext() )
      edgeList.add(edgeIter.next());
    Collections.sort(edgeList,c);

    UnionFind<GraphNode<E>> uf =
            new UnionFind<GraphNode<E>>(g.nodes().iterator());

    for(GraphEdge<E> e : edgeList){
      GraphEdge<E> edge = e;
      GraphNode<E> from = edge.from(), to = edge.to();

      if ( ! uf.find(from).equals(uf.find(to)) ) {
        mst.add(edge);
        uf.union(from,to);
      }
    }
    return mst;
  }

  public static void main(String[] args) {
    Graph<String> g = new Graph<String>();
    EdgeWeightedGraph<String, Integer> wG = new EdgeWeightedGraph<>(0);
    // main code goes here -- usually a basic test
    String[] nodeNames = { "a", "b", "c", "d", "e", "f", "g", "h" };
    for (String s : nodeNames )
      g.addNode(s);
    g.addEdge("a","d");
    g.addEdge("c","e");
    g.addEdge("a","c");
    g.addEdge("g","d");
    g.addEdge("b","g");
    g.addEdge("e","f");
    g.addEdge("e","b");
    g.addEdge("c","d");
    g.addEdge("b","a");
    g.addEdge("g","h");
    System.out.println(g);
    List<GraphEdge<String>> mst = unweightedMST(g);
    List<GraphEdge<String>> wMST = weightedMST(wG);
    System.out.println("minimal spanning tree = "+mst);
    g.makeUndirected();
    System.out.println(g);
    mst = unweightedMST(g);
    System.out.println("minimal spanning tree = "+mst);
  }
}
