import java.util.ArrayList;
import java.util.List;

public class Graph<E> implements GraphADT<E> {
    protected final int defCapacity = 10;
    protected int vertexCount;
    protected boolean[][] adjacencyMatrix;
    protected E[] vertices;

    @SuppressWarnings("unchecked")
    public Graph() {
        vertexCount = 0;
        this.adjacencyMatrix = new boolean[defCapacity][defCapacity];
        this.vertices = (E[]) (new Object[defCapacity]);
    }

    /**
     * Expands the array of vertices, as well as the adjacency matrix, two double their current size
     * in order to accommodate future vertex additions
     */
    @SuppressWarnings("unchecked")
    public void expandArrays() {
        E[] expandedVertices = (E[]) (new Object[vertices.length * 2]);
        boolean[][] expandedAdjMatrix = new boolean[vertices.length * 2][vertices.length * 2];

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                expandedAdjMatrix[i][j] = adjacencyMatrix[i][j];
            }
            expandedVertices[i] = vertices[i];
        }

        vertices = expandedVertices;
        adjacencyMatrix = expandedAdjMatrix;
    }

    /**
     * Add new vertex to the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should not already exist in
     * the graph
     * 
     * @param vertex the vertex to be added
     * @return vertex if vertex added, else return null if vertex can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public E addVertex(E vertex) {
        if (vertex == null) {
            return null;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex) {
                return null;
            }
        }
        if (vertexCount == vertices.length)
            expandArrays();

        vertices[vertexCount] = vertex;
        for (int i = 0; i <= vertexCount; i++) {
            adjacencyMatrix[vertexCount][i] = false;
            adjacencyMatrix[i][vertexCount] = false;
        }
        vertexCount++;
        return vertex;
    }

    /**
     * Remove the vertex and associated edge associations from the graph
     * 
     * Valid argument conditions: 1. vertex should be non-null 2. vertex should exist in the graph
     * 
     * @param vertex the vertex to be removed
     * @return vertex if vertex removed, else return null if vertex and associated edges can not be
     *         removed (also if valid conditions are violated)
     */
    @Override
    public E removeVertex(E vertex) {
        boolean check = false;
        int target = 0;
        if (vertex == null) {
            return null;
        }
        for (int i = 0; i < vertexCount; i++) {

            if (check == true) {
                vertices[i - 1] = vertices[i];
            }
            if (vertices[i] == vertex) {
                check = true;
                vertices[i] = null;
                target = i;
            }
        }
        if (!check) {
            return null;
        }
        vertexCount--;
        for (int i = target; i < vertexCount; i++) {
            for (int j = 0; j <= vertexCount; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }
        for (int i = target; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjacencyMatrix[j][i] = adjacencyMatrix[j][i + 1];
            }
        }

        return vertex;
    }

    /**
     * Add an edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge added, else return false if edge can not be added (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        adjacencyMatrix[index1][index2] = true;
        adjacencyMatrix[index2][index1] = true;
        return true;
    }

    /**
     * Remove the edge between two vertices (edge is undirected and unweighted)
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if edge removed, else return false if edge can not be removed (also if valid
     *         conditions are violated)
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }            
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        adjacencyMatrix[index1][index2] = false;
        adjacencyMatrix[index2][index1] = false;
        return true;
    }

    /**
     * Check whether the two vertices are adjacent
     * 
     * Valid argument conditions: 1. both the vertices should exist in the graph 2. vertex1 should
     * not equal vertex2
     * 
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     * @return true if both the vertices have an edge with each other, else return false if vertex1
     *         and vertex2 are not connected (also if valid conditions are violated)
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        boolean T1 = false;
        boolean T2 = false;
        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i] == vertex1) {
                T1 = true;
                index1 = i;
            }
            if (vertices[i] == vertex2) {
                T2 = true;
                index2 = i;
            }
        }
        if (!(T1 && T2) || (vertex1 == vertex2)) {
            return false;
        }
        return adjacencyMatrix[index1][index2];
    }

    /**
     * Get all the neighbor vertices of a vertex
     * 
     * Valid argument conditions: 1. vertex is not null 2. vertex exists
     * 
     * @param vertex the vertex
     * @return an iterable for all the immediate connected neighbor vertices
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        boolean check = false;
        int target = 0;
        if(vertex == null) {
            return null;
        }
        for(int i = 0; i < vertexCount; i++) {
            if(vertices[i] == vertex) {
                check = true;
                target = i;
            }
             
        }
        if(!check) {
            return null;
        }  
        List<E> neighbors = new ArrayList<E>();
        for(int i = 0; i < vertexCount; i++) {
            if(adjacencyMatrix[target][i] == true) {
                neighbors.add(vertices[i]);
                neighbors.add(vertices[target]);
            }
            else if(adjacencyMatrix[i][target] == true) {
                neighbors.add(vertices[i]);
                neighbors.add(vertices[target]);
            }
        }
        while(neighbors.contains(vertex)) {
            neighbors.remove(vertex);
        }
        return neighbors;
        
    }

    /**
     * Get all the vertices in the graph
     * 
     * @return an iterable for all the vertices
     */
    @Override
    public Iterable<E> getAllVertices() {
        ArrayList<E> vertexList = new ArrayList<E>();
        for(int i = 0; i < vertexCount; i++) {
            vertexList.add(vertices[i]);
        }
        return vertexList;
    }
    
}
