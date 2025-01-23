/*0 to 1 (3.00)  AL FL
1 to 2 (3.00)  FL GA
2 to 3 (3.00)  GA MS*/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;

public class Labb4Asgn3
{
	public static class SymbolGraph {
	    private ST<String, Integer> st; 
	    private String[] keys;          
	    private EdgeWeightedGraph graph;          
	    public SymbolGraph(String filename, String delimiter) {
	        st = new ST<String, Integer>();
	        In in = new In(filename);
	        while (!in.isEmpty()) {
	            String[] a = in.readLine().split(delimiter);
	            for (int i = 0; i < a.length; i++) {
	                if (!st.contains(a[i]))
	                    st.put(a[i], st.size());
	            }
	        }
	        keys = new String[st.size()];
	        for (String name : st.keys()) {
	            keys[st.get(name)] = name;
	        }
	        graph = new EdgeWeightedGraph(st.size());
	        in = new In(filename);
	        int weight = 1;
	        while (in.hasNextLine()) {
	            String[] a = in.readLine().split(delimiter);
	            int v = st.get(a[0]);
	            for (int i = 1; i < a.length; i++) {
	            	Edge edge = new Edge(v, st.get(a[i]), weight++);
	                graph.addEdge(edge);
	            }
	        }
	    }
	    public boolean contains(String s) {
	        return st.contains(s);
	    }
	    @Deprecated
	    public int index(String s) {
	        return st.get(s);
	    }
	    public int indexOf(String s) {
	        return st.get(s);
	    }
	    @Deprecated
	    public String name(int v) {
	        validateVertex(v);
	        return keys[v];
	    }
	    public String nameOf(int v) {
	        validateVertex(v);
	        return keys[v];
	    }
	    @Deprecated
	    public EdgeWeightedGraph G() {
	        return graph;
	    }
	    public EdgeWeightedGraph graph() {
	        return graph;
	    }
	    private void validateVertex(int v) {
	        int V = graph.V();
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	}
	public static class Edge implements Comparable<Edge> { 

	    private final int v;
	    private final int w;
	    private final double weight;
	    public Edge(int v, int w, double weight) {
	        if (v < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
	        if (w < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
	        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
	        this.v = v;
	        this.w = w;
	        this.weight = weight;
	    }
	    public double weight() {
	        return weight;
	    }
	    public int either() {
	        return v;
	    }
	    public int other(int vertex) {
	        if      (vertex == v) return w;
	        else if (vertex == w) return v;
	        else throw new IllegalArgumentException("Illegal endpoint");
	    }
	    @Override
	    public int compareTo(Edge that) {
	        return Double.compare(this.weight, that.weight);
	    }
	    public String toString(SymbolGraph symbolg) {
	        return String.format("%s-%s %.5f", symbolg.name(v), symbolg.name(w), weight);
	    }

	}
	public static class EdgeWeightedGraph {
	    private static final String NEWLINE = System.getProperty("line.separator");

	    private final int V;
	    private int E;
	    private Bag<Edge>[] adj;
	    public EdgeWeightedGraph(int V) {
	        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
	        this.V = V;
	        this.E = 0;
	        adj = (Bag<Edge>[]) new Bag[V];
	        for (int v = 0; v < V; v++) {
	            adj[v] = new Bag<Edge>();
	        }
	    }
	    public EdgeWeightedGraph(int V, int E) {
	        this(V);
	        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
	        for (int i = 0; i < E; i++) {
	            int v = StdRandom.uniform(V);
	            int w = StdRandom.uniform(V);
	            double weight = Math.round(100 * StdRandom.uniform()) / 100.0;
	            Edge e = new Edge(v, w, weight);
	            addEdge(e);
	        }
	    }
	    public EdgeWeightedGraph(In in) {
	        this(in.readInt());
	        int E = in.readInt();
	        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
	        for (int i = 0; i < E; i++) {
	            int v = in.readInt();
	            int w = in.readInt();
	            validateVertex(v);
	            validateVertex(w);
	            double weight = in.readDouble();
	            Edge e = new Edge(v, w, weight);
	            addEdge(e);
	        }
	    }
	    public EdgeWeightedGraph(EdgeWeightedGraph G) {
	        this(G.V());
	        this.E = G.E();
	        for (int v = 0; v < G.V(); v++) {
	            Stack<Edge> reverse = new Stack<Edge>();
	            for (Edge e : G.adj[v]) {
	                reverse.push(e);
	            }
	            for (Edge e : reverse) {
	                adj[v].add(e);
	            }
	        }
	    }
	    public int V() {
	        return V;
	    }
	    public int E() {
	        return E;
	    }
	    private void validateVertex(int v) {
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	    public void addEdge(Edge e) {
	        int v = e.either();
	        int w = e.other(v);
	        validateVertex(v);
	        validateVertex(w);
	        adj[v].add(e);
	        adj[w].add(e);
	        E++;
	    }
	    public Iterable<Edge> adj(int v) {
	        validateVertex(v);
	        return adj[v];
	    }
	    public int degree(int v) {
	        validateVertex(v);
	        return adj[v].size();
	    }
	    public Iterable<Edge> edges() {
	        Bag<Edge> list = new Bag<Edge>();
	        for (int v = 0; v < V; v++) {
	            int selfLoops = 0;
	            for (Edge e : adj(v)) {
	                if (e.other(v) > v) {
	                    list.add(e);
	                }
	                else if (e.other(v) == v) {
	                    if (selfLoops % 2 == 0) list.add(e);
	                    selfLoops++;
	                }
	            }
	        }
	        return list;
	    }
	    public String toString() {
	        StringBuilder s = new StringBuilder();
	        s.append(V + " " + E + NEWLINE);
	        for (int v = 0; v < V; v++) {
	            s.append(v + ": ");
	            for (Edge e : adj[v]) {
	                s.append(e + "  ");
	            }
	            s.append(NEWLINE);
	        }
	        return s.toString();
	    }
	}
	public static class DijkstraSP {
	    private double[] distTo;       
	    private Edge[] edgeTo;    
	    private IndexMinPQ<Double> pq;   
	    public DijkstraSP(EdgeWeightedGraph G, int s) {
	        for (Edge e : G.edges()) {
	            if (e.weight() < 0)
	                throw new IllegalArgumentException("edge " + e + " has negative weight");
	        }
	        distTo = new double[G.V()];
	        edgeTo = new Edge[G.V()];
	        validateVertex(s);
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = Double.POSITIVE_INFINITY;
	        distTo[s] = 0.0;
	        pq = new IndexMinPQ<Double>(G.V());
	        pq.insert(s, distTo[s]);
	        while (!pq.isEmpty()) {
	            int v = pq.delMin();
	            for (Edge e : G.adj(v))
	                relax(e, v);
	        }
	    }
	    private void relax(Edge e, int v) {
	        int w = e.other(v);
	        if (distTo[w] > distTo[v] + e.weight()) {
	            distTo[w] = distTo[v] + e.weight();
	            edgeTo[w] = e;
	            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
	            else                pq.insert(w, distTo[w]);
	        }
	    }
	    public double distTo(int v) {
	        validateVertex(v);
	        return distTo[v];
	    }
	    public boolean hasPathTo(int v) {
	        validateVertex(v);
	        return distTo[v] < Double.POSITIVE_INFINITY;
	    }
	    private void validateVertex(int v) {
	        int V = distTo.length;
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	    public Iterable<Edge> pathTo(int v) {
	        validateVertex(v);
	        if (!hasPathTo(v)) return null;
	        Stack<Edge> path = new Stack<Edge>();
	        int temp = v;
	        for (Edge e = edgeTo[v]; e != null; e = edgeTo[temp]) {
	            path.push(e);
	            temp = e.other(temp);
	        }
	        return path;
	    }
	}
 public static void main(String[] args)
 {
	 SymbolGraph symbolg = new SymbolGraph("C:\\Users\\Amiran\\Downloads\\contiguous-usa.dat.txt", " ");
     EdgeWeightedGraph graph = symbolg.G();
     String text1 = "AL";
     String text2 = "MS";
     int t1 = symbolg.index(text1);
     int t2 = symbolg.index(text2);
     DijkstraSP dijk = new DijkstraSP(graph, t1);
     int j = 0;
     for (int i = 1; i < t2+1; i++) {
         if (dijk.hasPathTo(i)) {
             StdOut.printf("%d to %d (%.2f)  ", j, i, dijk.distTo(t2));
             StdOut.printf(symbolg.nameOf(j) + " " + symbolg.nameOf(i));
             j = i;
             StdOut.println();
         }
         else {
             StdOut.printf("%d to %d         no path\n", t1, i);
         }
     }
     }
 }