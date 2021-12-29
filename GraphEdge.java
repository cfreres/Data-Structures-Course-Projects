
public class GraphEdge<E> {
  private GraphNode<E> from, to;
  public GraphEdge(GraphNode<E> from, GraphNode<E> to)
  { this.from = from; this.to = to; }
  public GraphNode<E> from() { return from; }
  public GraphNode<E> to()   { return to; }
  public String toString() {
    return "Edge: " + from().element() + " -> " + to().element();
  }
  // equals() and hashCode() need to be overridden to do a "deep" comparison
  @Override
  public boolean equals(Object o) {
    if ( o instanceof GraphEdge ) {
      GraphEdge<E> e = (GraphEdge<E>)o;
      return from().equals(e.from()) && to().equals(e.to());
    }
    else
      return false;
  }
  @Override
  public int hashCode() {
    int p = 65497; // = 2^16 - 39, prime
    return from().hashCode() + (p*to().hashCode());
  }
}
