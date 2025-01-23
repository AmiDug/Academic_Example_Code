import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Stack;

public class Labb4Asgn5 
{
	public static class Digraph
	{
	 private final int V;
	 private int E;
	 private Bag<Integer>[] adj;
	 public Digraph(int V)
	 {
	 this.V = V;
	 this.E = 0;
	 adj = (Bag<Integer>[]) new Bag[V];
	 for (int v = 0; v < V; v++)
	 adj[v] = new Bag<Integer>();
	 }
	 public int V() { return V; }
	 public int E() { return E; }
	 public void addEdge(int v, int w)
	 {
	 adj[v].add(w);
	 E++;
	 }
	 public Iterable<Integer> adj(int v)
	 { return adj[v]; }
	 public Digraph reverse()
	 {
	 Digraph R = new Digraph(V);
	 for (int v = 0; v < V; v++)
	 for (int w : adj(v))
	 R.addEdge(w, v);
	 return R;
	 }
	 public String toString(SymbolGraph sg) {
	        StringBuilder s = new StringBuilder();
	        s.append(V + " vertices, " + E + " edges \n");
	        for (int v = 0; v < V; v++) {
	            s.append(String.format("%s: ", sg.name(v)));
	            for (int w : adj[v]) {
	                s.append(String.format("%s ", sg.name(w)));
	            }
	            s.append("\n");
	        }
	        return s.toString();
	    }
	}
	public static class SymbolGraph {
	    private ST<String, Integer> st; 
	    private String[] keys;          
	    private Digraph graph;          
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
	        graph = new Digraph(st.size());
	        in = new In(filename);
	        while (in.hasNextLine()) {
	            String[] a = in.readLine().split(delimiter);
	            int v = st.get(a[0]);
	            for (int i = 1; i < a.length; i++) {
	            		graph.addEdge(v, st.get(a[i]));
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
	    public Digraph G() {
	        return graph;
	    }
	    public Digraph graph() {
	        return graph;
	    }
	    private void validateVertex(int v) {
	        int V = graph.V();
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	}
	public static class DepthFirstDirectedPaths {
	    private boolean[] marked; 
	    private int[] edgeTo;     
	    private final int s;      

	    public DepthFirstDirectedPaths(Digraph G, int s) {
	        marked = new boolean[G.V()];
	        edgeTo = new int[G.V()];
	        this.s = s;
	        validateVertex(s);
	        dfs(G, s);
	    }

	    private void dfs(Digraph G, int v) { 
	        marked[v] = true;
	        for (int w : G.adj(v)) {
	            if (!marked[w]) {
	                edgeTo[w] = v;
	                dfs(G, w);
	            }
	        }
	    }
	    public boolean hasPathTo(int v) {
	        validateVertex(v);
	        return marked[v];
	    }
	    public Iterable<String> pathTo(SymbolGraph symbolg, int value) {
	        if (!hasPathTo(value)) return null;
	        Stack<String> path = new Stack<String>();
	        for (int x = value; x != s; x = edgeTo[x])
	            path.push(symbolg.name(x));
	        path.push(symbolg.name(s));
	        return path;
	    }
	    private void validateVertex(int v) {
	        int V = marked.length;
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }

	}
	public static void main(String[] args)
	 {
		 SymbolGraph symbolg = new SymbolGraph("C:\\Users\\Amiran\\Downloads\\contiguous-usa.dat.txt", " ");
	     Digraph graph = symbolg.G();
	     String text1 = "AL";
	     String text2 = "FL";
	     int t1 = symbolg.index(text1);
	     int t2 = symbolg.index(text2);
	     DepthFirstDirectedPaths dfp = new DepthFirstDirectedPaths(graph, t1);
	     StdOut.println(dfp.hasPathTo(t2));
	     StdOut.println(graph.toString(symbolg));
	 }
}
