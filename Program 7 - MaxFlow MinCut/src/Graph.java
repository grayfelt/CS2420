import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private int vertexCt;  // Number of vertices in the graph.
    private GraphNode[] G;  // Adjacency list for graph.
    private String graphName;  //The file from which the graph was created.
    private ArrayList<LinkedList<Integer>> paths; //list of paths to print later

    private Graph() {
        this.vertexCt = 0;
        this.graphName = "";
        paths = new ArrayList<>();
    }

    public static void main(String[] args) {
        //loop for each file
        for(int i = 1; i < 6; i++){
            System.out.println("------------------------------------------");
            Graph graph1 = new Graph();
            String file = "demands" + i + ".txt";
            graph1.makeGraph("src\\" + file);
            graph1.graphName = file;

            //list of cuts to make
            ArrayList<GraphNode.EdgeInfo> cuts;

            System.out.println(file);
            //keep running a breadth first search until a path isn't possible
            while(graph1.bfs()){
                graph1.bfs();
            }

            //print flows
            StringBuilder produced = new StringBuilder();
            System.out.print("MAX FLOW: ");
            int producedSum = 0;
            for (LinkedList<Integer> currPath:graph1.paths) {
                int min = currPath.get(currPath.size() - 1);
                System.out.print("\n Found flow " + min + ": ");
                for(int a = 0; a < currPath.size() - 2; a++){
                    System.out.print(currPath.get(a) + " ");
                }
                System.out.print(graph1.vertexCt - 1);
                producedSum += min;
            }

            //print edges
            System.out.print("\nProduced " + producedSum + ":");
            for (GraphNode node:graph1.G) {
                for (GraphNode.EdgeInfo edge:node.succ) {
                    if(edge.fill!=0){
                        produced.append("\n Edge (");
                        produced.append(edge.from);
                        produced.append(",");
                        produced.append(edge.to);
                        produced.append(") transports ");
                        produced.append(edge.fill);
                        produced.append(" cases");
                    }
                }
            }
            System.out.println(produced);

            //print cuts
            cuts = graph1.minCut(producedSum);
            System.out.println("MIN CUT: ");
            for (GraphNode.EdgeInfo edge:cuts) {
                System.out.print(" Edge (" + edge.from + "," + edge.to + ") transports " + edge.fill + " cases\n");

            }
        }
    }

    private boolean addEdge(int source, int destination, int cap) {
//        System.out.println("addEdge " + source + "->" + destination + "(" + cap + ")");
        //source or destination cannot be out of bounds
        if (source < 0 || source >= vertexCt) return false;
        if (destination < 0 || destination >= vertexCt) return false;
        //add edge
        G[source].addEdge(source, destination, cap);
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The Graph ").append(graphName).append(" \n");
        for (int i = 0; i < vertexCt; i++) {
            sb.append(G[i].toString());
            for (GraphNode.EdgeInfo edge:G[i].succ) {
                if(edge.getRemain() != edge.capacity){
                    System.out.println("Edge (" + edge.from + "," + edge.to + ") transports " + edge.fill + " cases.");
                }
            }
        }
        return sb.toString();
    }

    private void makeGraph(String filename) {
        try {
            graphName = filename;
            Scanner reader = new Scanner(new File(filename));
            vertexCt = reader.nextInt();
            //create empty list of graphNodes
            G = new GraphNode[vertexCt];
            //fill graph node list
            for (int i = 0; i < vertexCt; i++) {
                G[i] = new GraphNode(i);
            }
            while (reader.hasNextInt()) {
                //source
                int v1 = reader.nextInt();
                //destination
                int v2 = reader.nextInt();
                //capacity
                int cap = reader.nextInt();
                if (!addEdge(v1, v2, cap))
                    throw new Exception();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean bfs(){
        //keep track of all visited nodes
        LinkedList<GraphNode> visited = new LinkedList<>();
        //implement a queue for the BFS
        LinkedList<GraphNode> queue = new LinkedList<>();
        //add the first node to the queue
        queue.add(G[0]);
        //for all nodes that aren't visited
        while(!queue.isEmpty() && !visited.contains(G[vertexCt - 1])){
            GraphNode node = queue.getFirst();
            queue.removeFirst();
            //for all of the edges in this node
            for(int i = 0; i < node.succ.size() && !visited.contains(G[vertexCt - 1]); i++){
                GraphNode.EdgeInfo currEdge = node.succ.get(i);
                //if not full and not visited
                if(currEdge.getRemain() != 0 && !visited.contains(G[currEdge.to])){
                    visited.add(G[currEdge.to]);
                    //add to next node's path
                    G[currEdge.to].path = (LinkedList<Integer>) node.path.clone();
                    G[currEdge.to].path.addLast(currEdge.to);
                    //continue down the line
                    queue.addLast(G[currEdge.to]);
                }
            }
        }
        //finished
        if(queue.isEmpty()){
            return false;
        }
        //sink
        GraphNode t = queue.getLast();
        //path to sink
        LinkedList<Integer> currPath = t.path;
        //add source
        currPath.addFirst(0);
        int min = 999999999;
        //add edges to a list for printing
        ArrayList<GraphNode.EdgeInfo> edges = new ArrayList<>();
        //finding minimum remaining capacity of all edges in path
        for(int i = 0; i < currPath.size() - 1; i++){
            GraphNode.EdgeInfo edge = findPath(currPath.get(i), currPath.get(i+1));
            edges.add(edge);
            assert edge != null;
            if(edge.getRemain() < min){
                min = edge.getRemain();
            }
        }
        paths.add(currPath);
        paths.get(paths.size() - 1).add(min);
        //fill path with minimum remaining capacity
        for (GraphNode.EdgeInfo a:edges) {
            a.fill += min;
        }
        //not finished
        return true;
    }

    private ArrayList<GraphNode.EdgeInfo> minCut(int sum){
        //essentially do a BFS until I find an edge that has no remaining capacity
        int runningSum = 0;
        LinkedList<GraphNode> visited = new LinkedList<>();
        LinkedList<GraphNode> queue = new LinkedList<>();
        ArrayList<GraphNode.EdgeInfo> cuts = new ArrayList<>();
        queue.add(G[0]);
        while(!queue.isEmpty() && !(runningSum == sum)){
            GraphNode node = queue.getFirst();
            queue.removeFirst();
            for(int i = 0; i < node.succ.size(); i++){
                GraphNode.EdgeInfo currEdge = node.succ.get(i);
                if(currEdge.getRemain() == 0 && !cuts.contains(currEdge)){
                    visited.add(G[currEdge.to]);
                    cuts.add(currEdge);
                    runningSum += currEdge.fill;
                }
                //if the edge isn't full
                if(currEdge.getRemain() != 0 && !cuts.contains(currEdge)){
                    visited.add(G[currEdge.to]);
                    queue.addLast(G[currEdge.to]);
                }
            }
        }
        return cuts;
    }
    //finds a path between two integer nodes
    private GraphNode.EdgeInfo findPath(int s, int t){
        GraphNode source = G[s];
        //search all paths from this node
        for (GraphNode.EdgeInfo edge:source.succ) {
            if(edge.to == t){
                return edge;
            }
        }
        System.out.println("Couldn't find path.");
        return null;
    }
}