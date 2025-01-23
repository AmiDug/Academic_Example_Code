/*AL-FL 1,00000
AL-GA 2,00000
AL-MS 3,00000
AL-TN 4,00000
AR-MS 7,00000
AR-LA 5,00000
AR-MO 6,00000
AR-OK 8,00000
AR-TX 10,00000
AZ-NM 12,00000
AZ-CA 11,00000
CO-NM 19,00000
AZ-NV 13,00000
AZ-UT 14,00000
CA-OR 16,00000
CO-OK 20,00000
CO-KS 17,00000
CO-NE 18,00000
CO-WY 22,00000
CT-NY 24,00000
CT-MA 23,00000
NJ-NY 91,00000
CT-RI 25,00000
DC-VA 27,00000
DC-MD 26,00000
KY-VA 60,00000
DE-MD 28,00000
DE-NJ 29,00000
DE-PA 30,00000
GA-NC 32,00000
GA-SC 33,00000
IA-MO 37,00000
IA-IL 35,00000
IA-MN 36,00000
IA-SD 39,00000
IA-WI 40,00000
ID-NV 42,00000
ID-MT 41,00000
ID-WA 45,00000
IL-IN 47,00000
IL-KY 48,00000
IN-MI 52,00000
IN-OH 53,00000
KY-WV 61,00000
MA-NH 64,00000
MA-VT 67,00000
ME-NH 71,00000
MN-ND 74,00000
1488.0*/

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.UF;

public class Labb4Asgn4
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
	public static class PrimMST {
	    private static final double FLOATING_POINT_EPSILON = 1E-12;

	    private Edge[] edgeTo;      
	    private double[] distTo;     
	    private boolean[] marked;    
	    private IndexMinPQ<Double> pq;
	    public PrimMST(EdgeWeightedGraph G) {
	        edgeTo = new Edge[G.V()];
	        distTo = new double[G.V()];
	        marked = new boolean[G.V()];
	        pq = new IndexMinPQ<Double>(G.V());
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = Double.POSITIVE_INFINITY;

	        for (int v = 0; v < G.V(); v++)    
	            if (!marked[v]) prim(G, v);     
	        assert check(G);
	    }

	    private void prim(EdgeWeightedGraph G, int s) {
	        distTo[s] = 0.0;
	        pq.insert(s, distTo[s]);
	        while (!pq.isEmpty()) {
	            int v = pq.delMin();
	            scan(G, v);
	        }
	    }
	    private void scan(EdgeWeightedGraph G, int v) {
	        marked[v] = true;
	        for (Edge e : G.adj(v)) {
	            int w = e.other(v);
	            if (marked[w]) continue;      
	            if (e.weight() < distTo[w]) {
	                distTo[w] = e.weight();
	                edgeTo[w] = e;
	                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
	                else                pq.insert(w, distTo[w]);
	            }
	        }
	    }
	    public Iterable<Edge> edges() {
	        Queue<Edge> mst = new Queue<Edge>();
	        for (int v = 0; v < edgeTo.length; v++) {
	            Edge e = edgeTo[v];
	            if (e != null) {
	                mst.enqueue(e);
	            }
	        }
	        return mst;
	    }
	    public double weight() {
	        double weight = 0.0;
	        for (Edge e : edges())
	            weight += e.weight();
	        return weight;
	    }
	    private boolean check(EdgeWeightedGraph G) {
	        double totalWeight = 0.0;
	        for (Edge e : edges()) {
	            totalWeight += e.weight();
	        }
	        if (Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON) {
	            System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
	            return false;
	        }
	        UF uf = new UF(G.V());
	        for (Edge e : edges()) {
	            int v = e.either(), w = e.other(v);
	            if (uf.connected(v, w)) {
	                System.err.println("Not a forest");
	                return false;
	            }
	            uf.union(v, w);
	        }
	        for (Edge e : G.edges()) {
	            int v = e.either(), w = e.other(v);
	            if (!uf.connected(v, w)) {
	                System.err.println("Not a spanning forest");
	                return false;
	            }
	        }
	        for (Edge e : edges()) {
	            uf = new UF(G.V());
	            for (Edge f : edges()) {
	                int x = f.either(), y = f.other(x);
	                if (f != e) uf.union(x, y);
	            }
	            for (Edge f : G.edges()) {
	                int x = f.either(), y = f.other(x);
	                if (!uf.connected(x, y)) {
	                    if (f.weight() < e.weight()) {
	                        System.err.println("Edge " + f + " violates cut optimality conditions");
	                        return false;
	                    }
	                }
	            }

	        }

	        return true;
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
     PrimMST pri = new PrimMST(graph);
     double value = 0;
     for(Edge edge : pri.edges()) {
         StdOut.println(edge.toString(symbolg));
         value = value + edge.weight();
     }
     StdOut.println(value);
 }
}