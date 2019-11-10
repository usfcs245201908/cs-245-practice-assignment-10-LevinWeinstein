import java.util.List;
import java.util.ArrayList;

public class GraphImplementation implements Graph {

    int [][] adjacencyMatrix;

    // Constructor
    public GraphImplementation(int size){
        adjacencyMatrix = new int[size][size];
    }

    /**
     * Add Edge adds an edge to the graph.
     *
     * @param src src vertex
     * @param tar target vertex
     * @throws Exception if the src or target vertex number is too high
     */
    public void addEdge(int src, int tar) throws Exception{
        if (src >= adjacencyMatrix.length || tar >= adjacencyMatrix.length)
            throw new Exception("Node number too big.");
        adjacencyMatrix[tar][src] = 1;
    }


    /**
     * Topological Sort: does a topological sort of the graph.
     * @return sorted list of nodes in topological order, otherwise null if circular dependencies
     */
    public List<Integer> topologicalSort(){
        int[] remainingNodes = listByDependencies();
        List<Integer> output = new ArrayList<>();


        for (int i = 0; i < remainingNodes.length; i++){
            int nextIndex = firstZeroIndex(remainingNodes);
            List<Integer> currentNeighbors = null;

            if (nextIndex == -1)
                return null; // cannot topological sort because circular dependencies

            output.add(nextIndex);
            try {
                currentNeighbors = neighbors(nextIndex);
            } catch (Exception e){
                System.out.println("Index Out of Bounds. This should never happen here.");
            }


            for (int j = 0; j < remainingNodes.length; j++){
                if (currentNeighbors.contains(j))
                    remainingNodes[j]--;
            }
            remainingNodes[nextIndex]--;
        }

        return output;
    }

    // Neighbors counts the numbers of neighbors vertex #vertex has.
    public List<Integer> neighbors(int vertex) throws Exception {
        if (vertex >= adjacencyMatrix.length)
            throw new Exception("Node number too big");
        List<Integer> neighborCount = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++){
            if (adjacencyMatrix[i][vertex] > 0) {
                neighborCount.add(i);
            }
        }
        return neighborCount;
    }

    /**
     * Finds the first index in the array nodeList whose value is 0
     * @param nodeList
     * @return index of 0, or -1 if no values are 0
     */
    private int firstZeroIndex(int []nodeList){
        for (int i = 0; i < nodeList.length; i++){
            if (nodeList[i] == 0)
                return i;
        }
        return -1;
    }

    /**
     * Generate a count of the dependencies for each node in the graph, as an array.
     *
     * So for each vertex n, output[n] = {number of connections n has to other vertices}
     */
    private int[] listByDependencies(){
        int[] output = new int[adjacencyMatrix.length];

        for (int i = 0; i < adjacencyMatrix.length; i++ ){
            int dependencyCount = 0;
            for (int j = 0; j < adjacencyMatrix.length; j++){
                if (adjacencyMatrix[i][j] > 0)
                    dependencyCount++;
            }
            output[i] = dependencyCount;
        }
        return output;
    }
}