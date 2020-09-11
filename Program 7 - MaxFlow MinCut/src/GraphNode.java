import java.util.*;

public class GraphNode {

    private int nodeID;
    LinkedList<EdgeInfo> succ;
    LinkedList<Integer> path;
    GraphNode(int nodeID) {
        this.nodeID = nodeID;
        this.succ = new LinkedList<>();
        this.path = new LinkedList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nodeID).append(": ");
        for (EdgeInfo edgeInfo : succ) {
            sb.append(edgeInfo.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    void addEdge(int v1, int v2, int capacity) {
//        System.out.println("GraphNode.addEdge " + v1 + "->" + v2 + "(" + capacity + ")");
        succ.addFirst(new EdgeInfo(v1, v2, capacity));
    }

    public static class EdgeInfo {
        int from;        // source of edge
        int to;          // destination of edge
        int capacity;    // capacity of edge
        int fill;       // amount filled
        EdgeInfo(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.fill = 0;
        }

        public String toString() {
            return "Edge " + from + "->" + to + " Cap (" + capacity + ") Fill (" + fill +") Remain (" + getRemain() + ")";
        }
        int getRemain(){
            return capacity - fill;
        }
    }

}
