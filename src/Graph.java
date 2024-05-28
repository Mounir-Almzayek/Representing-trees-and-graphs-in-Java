// Java program to find the transpose of a graph
import java.util.*;
import java.lang.*;

class Graph {
    // Total number of vertices
    private int vertices;
    private ArrayList<Edge>[] adj ;
    private ArrayList<Edge>[] tr ;
    private List<Edge> edges;
    private int matrix[][];

    public Graph(int vertices) {
        this.vertices = vertices;
        adj = new ArrayList[vertices];
        tr = new ArrayList[vertices];
        for (int i = 0; i < vertices; ++i) {
            adj[i] = new ArrayList<>();
            tr[i] = new ArrayList<>();
        }
        edges = new ArrayList<>();
        matrix = new int[vertices][vertices];
    }

    public Graph(int vertices, int[][] matrix) {
        this.vertices = vertices;
        this.matrix = matrix;
    }

    // Function to add an edge from source vertex u to destination vertex v, if choice is false the edge is added to adj otherwise the edge is added to tr
    public void addedge(int u, int v, boolean choice) {
        adj[u].add(new Edge(u,v));
        if(choice)
            adj[v].add(new Edge(v,u));
    }

    public void addedge(int u, int v, int weight, boolean choice) {
        adj[u].add(new Edge(u,v,weight));
        if (choice)
            tr[v].add(new Edge(v,u,weight));

        edges.add(new Edge(u, v, weight));

    }

    // Function to convert adjacency lists (adj and tr) to adjacency matrix
    public int[][] adjacencyMatrixFromLists(ArrayList<Edge>[] adj, ArrayList<Edge>[] tr) {
        int[][] matrix = new int[vertices][vertices];

        // Initialize the matrix with zeros
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                matrix[i][j] = 0;
            }
        }

        // Fill the matrix using the adj list
        for (int i = 0; i < vertices; i++) {
            for (Edge edge : adj[i]) {
                matrix[i][edge.dest] = edge.weight;
            }
        }

        // Update the matrix using the tr list
        for (int i = 0; i < vertices; i++) {
            for (Edge edge : tr[i]) {
                matrix[edge.dest][i] = edge.weight;
            }
        }

        return matrix;
    }



    // Function to convert the graph representation from adjacency matrix to two adjacency lists (adj and tr)
    public void adjacencyListsFromMatrix(int[][] matrix, ArrayList<Edge>[] adj, ArrayList<Edge>[] tr) {
        // Initialize adjacency lists
        for (int i = 0; i < vertices; i++) {
            adj[i] = new ArrayList<>();
            tr[i] = new ArrayList<>();
        }

        // Traverse the adjacency matrix and add edges to the adjacency lists
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (matrix[i][j] != 0) {
                    adj[i].add(new Edge(i, j, matrix[i][j])); // Add edge to the original graph
                    tr[j].add(new Edge(j, i, matrix[i][j])); // Add edge to the transpose graph
                }
            }
        }
    }

    // The function to do BFS traversal.
    void BFS(int startNode) {
        // Create a queue for BFS
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[vertices];

        // Mark the current node as visited and enqueue it
        visited[startNode] = true;
        queue.add(startNode);

        System.out.print("Breadth First Traversal starting from vertex " + startNode + ": ");

        // Iterate over the queue
        while (!queue.isEmpty()) {
            // Dequeue a vertex from queue and print it
            int currentNode = queue.poll();
            System.out.print(currentNode + " ");

            // Get all adjacent vertices of the dequeued vertex currentNode If an adjacent has not been visited, then mark it visited and enqueue it
            for (Edge neighbor : adj[currentNode]) {
                if (!visited[neighbor.dest]) {
                    visited[neighbor.dest] = true;
                    queue.add(neighbor.dest);
                }
            }
        }
        System.out.println();
    }

    // A function used by DFS
    void DFSUtil(int v, boolean visited[]) {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v + " ");

        // Recur for all the vertices adjacent to this vertex
        Iterator<Edge> i = adj[v].listIterator();
        while (i.hasNext()) {
            Edge n = i.next();
            if (!visited[n.dest])
                DFSUtil(n.dest, visited);
        }
    }

    // The function to do DFS traversal.
    // It uses recursive DFSUtil()
    void DFS(int v) {
        // Mark all the vertices as not visited(set as false by default in java)
        boolean visited[] = new boolean[vertices];

        System.out.print("Depth First Traversal starting from vertex " + v + ": ");

        // Call the recursive helper
        // function to print DFS
        // traversal
        DFSUtil(v, visited);
        System.out.println();
    }

    // Function to print the graph representation
    public void printGraph() {
        for(int i = 0; i < vertices; i++) {
            System.out.print(i + "--> ");
            for(int j = 0; j < adj[i].size(); j++)
                System.out.print(adj[i].get(j).dest + " ");
            System.out.println();
        }
    }

    // Function to print the transpose of the graph represented as adj and store it in tr
    public void getTranspose() {
        // Traverse the graph and for each edge u, v in graph add the edge v, u in transpose
        for(int i = 0; i < vertices; i++)
            for(int j = 0; j < adj[i].size(); j++)
                addedge(adj[i].get(j).dest, i, true);
    }

    // Function to perform DFS and topological sorting
    void topologicalSortUtil(int v, ArrayList<Edge>[] adj, boolean[] visited, Stack<Integer> stack) {
        // Mark the current node as visited
        visited[v] = true;

        // Recur for all adjacent vertices
        for (Edge i : adj[v]) {
            if (!visited[i.dest])
                topologicalSortUtil(i.dest, adj, visited, stack);
        }

        // Push current vertex to stack which stores the
        // result
        stack.push(v);
    }

    // Function to perform Topological Sort
    void topologicalSort(ArrayList<Edge>[] adj, int V) {
        // Stack to store the result
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        // Call the recursive helper function to store Topological Sort starting from all vertices one by one
        for (int i = 0; i < V; i++) {
            if (!visited[i])
                topologicalSortUtil(i, adj, visited, stack);
        }

        // Print contents of stack
        System.out.print( "Topological sorting of the graph: ");
        while (!stack.empty()) {
            System.out.print(stack.pop() + " ");
        }
        System.out.println();
    }

    // Kruskal's Minimum Spanning Tree algorithm
    public void kruskalsMST() {
        // Sort edges in ascending order of weight
        edges.sort(Comparator.comparingInt(e -> e.weight));

        // Initialize parent array for Union-Find
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++)
            parent[i] = i;

        List<Edge> mst = new ArrayList<>();
        int mstWeight = 0;

        // Iterate through sorted edges
        for (Edge edge : edges) {
            int parentSrc = find(parent, edge.src);
            int parentDest = find(parent, edge.dest);

            // If including this edge doesn't form a cycle, add it to MST
            if (parentSrc != parentDest) {
                mst.add(edge);
                mstWeight += edge.weight;
                // Union the sets of src and dest
                union(parent, parentSrc, parentDest);
            }
        }

        // Print MST edges and total weight
        System.out.println("Kruskal's MST:");
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
        }
        System.out.println("Total weight: " + mstWeight);
    }

    // Find operation in Union-Find
    private int find(int[] parent, int vertex) {
        if (parent[vertex] != vertex)
            parent[vertex] = find(parent, parent[vertex]);
        return parent[vertex];
    }

    // Union operation in Union-Find
    private void union(int[] parent, int x, int y) {
        int xParent = find(parent, x);
        int yParent = find(parent, y);
        parent[yParent] = xParent;
    }

    // Function to find the Minimum Spanning Tree using Prim's algorithm
    void primMST() {
        int[] parent = new int[vertices]; // Array to store the parent node of each vertex in the MST
        int[] key = new int[vertices]; // Array to store the minimum key value for each vertex
        boolean[] inMST = new boolean[vertices]; // Array to track if the vertex is in the MST or not

        // Initialize key values as INFINITE and inMST values as false
        for (int i = 0; i < vertices; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }

        // Start with the first vertex
        key[0] = 0;
        parent[0] = -1;

        // Priority queue to store vertices with their key values
        PriorityQueue<Edge> minHeap = new PriorityQueue<>((a, b) -> a.weight - b.weight);

        // Add the first vertex to the queue
        minHeap.add(new Edge(0, 0, key[0]));

        while (!minHeap.isEmpty()) {
            // Extract the vertex with the minimum key value
            Edge u = minHeap.poll();
            int uVertex = u.dest;

            // Include uVertex in MST
            inMST[uVertex] = true;

            // Update key values and parent for adjacent vertices of uVertex
            for (Edge v : adj[uVertex]) {
                int vVertex = v.dest;
                int weight = v.weight;

                // If v is not in MST and weight of u-v is less than the current key value of v
                if (!inMST[vVertex] && weight < key[vVertex]) {
                    parent[vVertex] = uVertex;
                    key[vVertex] = weight;

                    // Update the key value of v in the priority queue
                    minHeap.add(new Edge(uVertex, vVertex, key[vVertex]));
                }
            }
            for (Edge v : tr[uVertex]) {
                int vVertex = v.dest;
                int weight = v.weight;

                // If v is not in MST and weight of u-v is less than the current key value of v
                if (!inMST[vVertex] && weight < key[vVertex]) {
                    parent[vVertex] = uVertex;
                    key[vVertex] = weight;

                    // Update the key value of v in the priority queue
                    minHeap.add(new Edge(uVertex, vVertex, key[vVertex]));
                }
            }
        }

        // Print MST edges and total weight
        System.out.println("Edges of Minimum Spanning Tree:");
        int mstWeight = 0;
        for (int i = 1; i < vertices; i++) {
            if (parent[i] != -1) { // Exclude edges with parent as -1 (root to itself)
                System.out.println(parent[i] + " - " + i + " : " + key[i]);
                mstWeight += key[i];
            }
        }
        System.out.println("Total weight: " + mstWeight);
    }

    // A utility function to find the vertex with minimum
    // distance value, from the set of vertices not yet
    // included in shortest path tree
    int minDistance(int dist[], Boolean sptSet[]) {
        // Initialize min value
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < vertices; v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    // A utility function to print the constructed distance
    // array
    void printSolution(int dist[]) {
        System.out.println(
                "Vertex \t\t Distance from Source");
        for (int i = 0; i < vertices; i++)
            System.out.println(i + " \t\t " + dist[i]);
    }

    // Function that implements Dijkstra's single source
    // shortest path algorithm for a graph represented using
    // adjacency matrix representation
    void dijkstra(int graph[][], int src) {
        int dist[] = new int[vertices]; // The output array.
        // dist[i] will hold
        // the shortest distance from src to i

        // sptSet[i] will true if vertex i is included in
        // shortest path tree or shortest distance from src
        // to i is finalized
        Boolean sptSet[] = new Boolean[vertices];

        // Initialize all distances as INFINITE and stpSet[]
        // as false
        for (int i = 0; i < vertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is always 0
        dist[src] = 0;

        // Find shortest path for all vertices
        for (int count = 0; count < vertices - 1; count++) {
            // Pick the minimum distance vertex from the set
            // of vertices not yet processed. u is always
            // equal to src in first iteration.
            int u = minDistance(dist, sptSet);

            // Mark the picked vertex as processed
            sptSet[u] = true;

            // Update dist value of the adjacent vertices of
            // the picked vertex.
            for (int v = 0; v < vertices; v++)

                // Update dist[v] only if is not in sptSet,
                // there is an edge from u to v, and total
                // weight of path from src to v through u is
                // smaller than current value of dist[v]
                if (!sptSet[v] && graph[u][v] != 0
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        // print the constructed distance array
        printSolution(dist);
    }

    void floydWarshall(int dist[][]) {

        int i, j, k;

        /* Add all vertices one by one
           to the set of intermediate
           vertices.
          ---> Before start of an iteration,
               we have shortest
               distances between all pairs
               of vertices such that
               the shortest distances consider
               only the vertices in
               set {0, 1, 2, .. k-1} as
               intermediate vertices.
          ----> After the end of an iteration,
                vertex no. k is added
                to the set of intermediate
                vertices and the set
                becomes {0, 1, 2, .. k} */
        for (k = 0; k < vertices; k++) {
            // Pick all vertices as source one by one
            for (i = 0; i < vertices; i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < vertices; j++) {
                    // If vertex k is on the shortest path
                    // from i to j, then update the value of
                    // dist[i][j]
                    if (dist[i][k] + dist[k][j]
                            < dist[i][j])
                        dist[i][j]
                                = dist[i][k] + dist[k][j];
                }
            }
        }

        // Print the shortest distance matrix
        printSolution(dist);
    }

    void printSolution(int dist[][]) {
        System.out.println(
                "The following matrix shows the shortest "
                        + "distances between every pair of vertices");
        for (int i = 0; i < vertices; ++i) {
            for (int j = 0; j < vertices; ++j) {
                if (dist[i][j] == Integer.MAX_VALUE)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static void main (String[] args){
        Graph graph = new Graph(9);

         graph.addedge(0, 1, 4,true);
         graph.addedge(0, 7, 8,true);
         graph.addedge(1, 2, 8,true);
         graph.addedge(1, 7, 1 ,true);
         graph.addedge(2, 3, 7,true);
         graph.addedge(2, 8, 2,true);
         graph.addedge(2, 5, 4,true);
         graph.addedge(3, 4, 9,true);
         graph.addedge(3, 5, 1 ,true);
         graph.addedge(4, 5, 1 ,true);
         graph.addedge(5, 6, 2,true);
         graph.addedge(6, 7, 1,true);
         graph.addedge(6, 8, 6,true);
         graph.addedge(7, 8, 7,true);


        // Printing the graph representation
        graph.printGraph();

        graph.topologicalSort(graph.adj , graph.vertices);

        graph.BFS(0);

        graph.DFS(0);

        // Find and print Kruskal's MST
        graph.kruskalsMST();

        graph.primMST();

        graph.dijkstra(graph.adjacencyMatrixFromLists(graph.adj, graph.tr),0 );

        int graph1[][] = new int[][] {
                { 0, 4 , 0, 0 , 0 , 0 , 0, 8 , 0 },
                { 4, 0 , 8, 0 , 0 , 0 , 0, 11, 0 },
                { 0, 8 , 0, 7 , 0 , 4 , 0, 0 , 2 },
                { 0, 0 , 7, 0 , 9 , 14, 0, 0 , 0 },
                { 0, 0 , 0, 9 , 0 , 10, 0, 0 , 0 },
                { 0, 0 , 4, 14, 10, 0 , 2, 0 , 0 },
                { 0, 0 , 0, 0 , 0 , 2 , 0, 1 , 6 },
                { 8, 11, 0, 0 , 0 , 0 , 1, 0 , 7 },
                { 0, 0 , 2, 0 , 0 , 0 , 6, 7 , 0 }
        };

    }
}