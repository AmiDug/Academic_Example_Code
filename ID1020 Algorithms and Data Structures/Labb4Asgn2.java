/*AL 
TN 
MO 
OK 
NM 
AZ */

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.SymbolGraph;

public class Labb4Asgn2 
{
	public static void main(String[] args) {
		 SymbolGraph symbolg = new SymbolGraph("C:\\Users\\Amiran\\Downloads\\contiguous-usa.dat.txt", " ");
	     Graph graph = symbolg.G();
	     String text1 = "AL";
	     String text2 = "AZ";
	     int t1 = symbolg.index(text1);
	     BreadthFirstPaths bfs = new BreadthFirstPaths(graph, t1);
	     int t2 = symbolg.index(text2);
	     if (bfs.hasPathTo(t2))
	         for (int v : bfs.pathTo(t2))
	             StdOut.print(symbolg.name(v) + " \n");
	     else StdOut.println("No path");
	     StdOut.println();
    }
}
