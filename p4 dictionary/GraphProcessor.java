import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GraphProcessor {
	
	 /**
     * Graph which stores the dictionary words and their associated connections
     */
    private Graph<String> graph;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
	public GraphProcessor() 
	{
		this.graph = new Graph<>();
	}
	        
	 /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     *
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if encountering other exceptions
     */
	public Integer populateGraph(String filepath)
	{
		
		graph = new Graph<String>();
		List<String> words;
		try {
			words = WordProcessor.getWordStream(filepath).collect(Collectors.toList());
		} catch(Exception e) {
			System.out.println(e);
			return -1; 
		}
		Integer count = 0;
		for(String w : words)
		{
			graph.addVertex(w);
			for(String s : words)
			{
				if(WordProcessor.isAdjacent(w,s))
					graph.addEdge(w,s);
			}
			count++;
		}
		
		shortestPathPrecomputation();
		return count;
	}
	
	 /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     *
     * Log any issues encountered (print the issue details)
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added; return -1 if file not found or if encountering other exceptions
     */
	public Integer populate(String filepath)
	{
		
		graph = new Graph<String>();
		List<String> words;
		try {
			words = WordProcessor.getWordStream(filepath).collect(Collectors.toList());
		} catch(Exception e) {
			System.out.println(e);
			return -1; 
		}
		Integer count = 0;
		for(String w : words)
		{
			graph.addVertex(w);
			for(String s : words)
			{
				if(WordProcessor.isAdjacent(w,s))
					graph.addEdge(w,s);
			}
			count++;
		}
		
		shortestPathPrecomputation();
		return count;
	}
    
	
    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
    	
    		//1. why GraphADT type? We can not create our own methods?
    		int src=0;
    		int dist = 0;
    		for(int i = 0; i < graph.vertices.length; i++) {
    			if(graph.vertices[i] == word1) src = i;
    			if(graph.vertices[i] == word2) dist = i;

    		}
    		int[][] m = new int[graph.vertexCount][graph.vertexCount];
    		for(int i =0; i<graph.vertexCount; i ++) {

    			for(int j =0; j<graph.vertexCount; j ++) {
    				if(i == j) m[i][j] = 0;
    				else if(graph.adjacencyMatrix[i][j] == true) {
    					m[i][j] = 1;
    				}
    				else m[i][j] = 99999;
    			}			
    		}
    		FloydWarshell(m,graph.vertexCount);
    		return P;
    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
    		int src=0;
    		int dist = 0;
    		for(int i = 0; i < graph.vertices.length; i++) {
    			if(graph.vertices[i] == word1) src = i;
    			if(graph.vertices[i] == word2) dist = i;

    		}
    		int[][] m = new int[graph.vertexCount][graph.vertexCount];
    		for(int i =0; i<graph.vertexCount; i ++) {

    			for(int j =0; j<graph.vertexCount; j ++) {
    				if(i == j) m[i][j] = 0;
    				else if(graph.adjacencyMatrix[i][j] == true) {
    					m[i][j] = 1;
    				}
    				else m[i][j] = 99999;
    			}			
    		}
        return FloydWarshell(m,graph.vertexCount).length;
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
    		ArrayList<Integer> a = new ArrayList<Integer>();
    		for( String src: graph.getAllVertices()) {
    			for(String dest: graph.getAllVertices()) {
    				a.add(getShortestDistance(src,dest));
    			}
    		}
    }
 
    
    /* The following part is used for finding shortest path by using Floyd's Algorithm*/
	ArrayList<String> P = new ArrayList<String>();

    private void addPath(int[][] path, int v, int u)
    {
        if (path[v][u] == v)
            return;

        addPath(path, v, path[v][u]);
        P.add(graph.vertices[path[v][u]]);
    }

    // Function to add the shortest cost with path
    // information between all pairs of vertices
    private void addSolution(int[][] cost, int[][] path, int N)
    {
        for (int v = 0; v < N; v++)
        {
            for (int u = 0; u < N; u++)
            {
                if (u != v && path[v][u] != -1)
                {
                		P.add(graph.vertices[v]);
                		addPath(path,v,u);
                		P.add(graph.vertices[u]);
//                    System.out.print("Shortest Path from vertex " + graph.vertices[v] +
//                            " to vertex " + graph.vertices[u] + " is (" + graph.vertices[v] + " ");
//                    printPath(path, v, u);
//                    System.out.println(graph.vertices[u] + ")");
                }
            }
        }
    }

    // Function to run Floyd-Warshell algorithm
    public int[][] FloydWarshell(int[][] adjMatrix, int N)
    {
        // cost[] and parent[] stores shortest-path
        // (shortest-cost/shortest route) information
        int[][] cost = new int[N][N];
        int[][] path = new int[N][N];

        // initialize cost[] and parent[]
        for (int v = 0; v < N; v++)
        {
            for (int u = 0; u < N; u++)
            {
                // initally cost would be same as weight
                // of the edge
                cost[v][u] = adjMatrix[v][u];

                if (v == u)
                    path[v][u] = 0;
                else if (cost[v][u] != Integer.MAX_VALUE)
                    path[v][u] = v;
                else
                    path[v][u] = -1;
            }
        }

        // run Floyd-Warshell
        for (int k = 0; k < N; k++)
        {
            for (int v = 0; v < N; v++)
            {
                for (int u = 0; u < N; u++)
                {
                    // If vertex k is on the shortest path from v to u,
                    // then update the value of cost[v][u], path[v][u]

                    if (cost[v][k] != Integer.MAX_VALUE
                            && cost[k][u] != Integer.MAX_VALUE
                            && (cost[v][k] + cost[k][u] < cost[v][u]))
                    {
                        cost[v][u] = cost[v][k] + cost[k][u];
                        path[v][u] = path[k][u];
                    }
                }

                // if diagonal elements become negative, the
                // graph contains a negative weight cycle
                if (cost[v][v] < 0)
                {
                    System.out.println("Negative Weight Cycle Found!!");
                    return null;
                }
            }
        }

        // Print the shortest path between all pairs of vertices
        addSolution(cost, path, N);
        return path;
    }
	
}
