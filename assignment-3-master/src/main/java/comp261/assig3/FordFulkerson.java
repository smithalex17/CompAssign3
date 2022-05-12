package comp261.assig3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.util.Pair;

// helper class that does not need local memory

public class FordFulkerson {
    // class members
    // Augmentation paths and the corresponding bottleneck flows
    private static ArrayList<Pair<ArrayList<Node>, Double>> augmentationPaths;
    // residual graph
    private static double[][] residualGraph;

    // constructor
    public FordFulkerson() {
        augmentationPaths = null;
        residualGraph = null;
    }

    // find maximum flow value
    public static double calcMaxflows(Graph graph, Node source, Node sink) {
        int rows = graph.getAdjacencyMatrix().length;
        int cols = graph.getAdjacencyMatrix().length;
        residualGraph = new double[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows; j++){
                residualGraph[i][j] = graph.getAdjacencyMatrix()[i][j];
            }
        }

        Node parent[] = new Node[graph.getNodeList().size()];

        double flow = 0;

        while(newBfs(source, sink, parent) != 0){            
            double flow_capacity = Double.MAX_VALUE;

            int current = sink.getId();

            while(current != source.getId()){
                int prev = parent[current].getId();
                flow_capacity = Math.min(flow_capacity, residualGraph[source.getId()][sink.getId()]);
                current = prev; 
            }

            current = sink.getId();
            while(current != source.getId()){
                int prev = parent[current].getId();
                residualGraph[prev][current] -= flow_capacity;
                residualGraph[current][prev] += flow_capacity;
                current = prev;
            }

            flow += flow_capacity;
        }



        return flow;
    }

    // TODO:Use BFS to find an augmentation path from s to t
    // add the augmentation path found to the arraylist of augmentation paths
    // return bottleneck flow
    public static double bfs(Node s, Node t, Node[] parent) {
        Arrays.fill(parent, null);

        Queue<Pair<Node, Double>> q = new LinkedList<Pair<Node, Double>>();

        q.add(new Pair<Node, Double>(s, Double.MAX_VALUE));

        while(!q.isEmpty()){
            Pair<Node, Double> fPair = q.remove();
            Node cur = fPair.getKey();
            double flow = fPair.getValue();
            ArrayList<Node> adjacentNode = new ArrayList<>();
            adjacentNode = cur.getNeighbours();
            for(Node n : adjacentNode){
                if(parent[n.getId()] == null && residualGraph[cur.getId()][n.getId()] != 0){                 
                    parent[n.getId()] = cur;                 
                    double new_flow = Math.min(flow, residualGraph[cur.getId()][n.getId()]);                   
                    if(n == t){       
                        flowPath(s, t, parent, new_flow);                                        
                        return new_flow;                    
                    }
                    q.add(new Pair<Node,Double>(n, new_flow));
                }               
            }
        }
        return 0;
    }


    public static double newBfs(Node s, Node t, Node[] parent) {
        Arrays.fill(parent, null);

        Queue<Pair<Node, Double>> q = new LinkedList<Pair<Node, Double>>();

        q.add(new Pair<Node, Double>(s, Double.MAX_VALUE));

        while(!q.isEmpty()){

            Pair<Node, Double> fPair = q.remove();
            Node cur = fPair.getKey();
            double flow = fPair.getValue();

            ArrayList<Node> adjacentNode = new ArrayList<>();
            adjacentNode = cur.getNeighbours();

            for(Node n : adjacentNode){
                if(parent[n.getId()] == null && residualGraph[cur.getId()][n.getId()] != 0){

                    double new_flow = Math.min(flow, residualGraph[cur.getId()][n.getId()]);
                    q.add(new Pair<Node,Double>(n, new_flow));
                    parent[n.getId()] = cur;
                    if(n == t){ 
                        flowPath(s, t, parent, new_flow);  
                        return new_flow;                   
                    }                    
                }                
            }
        }
        return 0;
    }








    // TODO: For each flow identified by bfs() build the path from source to sink
    // Add this path to the Array list of augmentation paths: augmentationPaths
    // along with the corresponding flow
    public static void flowPath(Node s, Node t, Node[] parent, double new_flow) {

        ArrayList<Node> augmentationPath = new ArrayList<Node>();

        // TODO: find the augmentation path identified by the graph traversal algorithm
        // and add it to the list of augmentation paths

        if(parent[t.getId()] == null){
            return;
        }

        Node cur = t;
        while(parent[t.getId()] != null){
            augmentationPath.add(cur);
            cur = parent[cur.getId()];
        }
        //augmentationPath.add(s);       
        Collections.reverse(augmentationPath);
        Pair p = new Pair<ArrayList<Node>, Double>(augmentationPath, new_flow);
        //augmentationPaths.add(p);
    }



    // getter for augmentation paths
    public static ArrayList<Pair<ArrayList<Node>, Double>> getAugmentationPaths() {
        return augmentationPaths;
    }

    // TODO: find min-cut - as a set of sets and the corresponding cut-capacity
    public static Pair<Pair<HashSet<Node>, HashSet<Node>>, Double> minCut_s_t(Graph graph, Node source, Node sink) {
        
        calcMaxflows(graph, source, sink);
        int rows = graph.getAdjacencyMatrix().length;
        int cols = graph.getAdjacencyMatrix()[0].length;
        Pair<Pair<HashSet<Node>, HashSet<Node>>, Double> minCutwithSets = null;
        HashSet setOfS = new HashSet<Node>();
        HashSet setOfT = new HashSet<Node>();

        setOfS.add(source);
        setOfT.add(sink);

        double minCut = 0;

        dfs(graph, source);

        for(int i = 0; i < rows; i++){
            if(graph.findNode(i).isVisited()){
                setOfS.add(graph.findNode(i));
            }
        }
        for(int i = 0; i < graph.getAdjacencyMatrix().length; i++){
            if(!graph.findNode(i).isVisited()){
                setOfT.add(graph.findNode(i));
            }
        }

        for(int i = 0 ; i < rows; i++){
            for(int j = 0 ; j < rows; j++){
                if(graph.getAdjacencyMatrix()[i][j] > 0 
                && graph.findNode(i).isVisited()
                && !graph.findNode(j).isVisited()){
                    minCut +=graph.getAdjacencyMatrix()[i][j];
                }
            } 
        }
        Pair p = new Pair<HashSet<Node>, HashSet<Node>>(setOfS, setOfT);
        minCutwithSets = new Pair<Pair<HashSet<Node>, HashSet<Node>>, Double>(p, minCut);
        return minCutwithSets;
    }

    // TODO: Use dfs to find set of nodes connected to s
    private static void dfs(Graph graph, Node s) {
        s.setVisited(true);
        System.out.print(s.getName() + " ");

        LinkedList<Node> allNeighbors = new LinkedList<>();
        if (allNeighbors == null){
            return;
        }  
        for (Node neighbor : allNeighbors) {
            if (!neighbor.isVisited()){
                dfs(graph, neighbor);
            } 
        }

    }
}
