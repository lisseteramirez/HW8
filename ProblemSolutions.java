/******************************************************************
 *
 *   Lissete Ramirez / SECTION 002
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     * <p>
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     * <p>
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     * <p>
     * You goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     * <p>
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     * <p>
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     * <p>
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     * <p>
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     * <p>
     * Example 1:
     * Input: numExams = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0. So, it is possible (no
     * cyclic or cycle graph of prereqs).
     * <p>
     * <p>
     * Example 2:
     * Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     * Output: false
     * Explanation: There are a total of 2 exams to take.
     * To take exam 1 you should have completed the
     * certification of exam 0, and to take exams 0 you
     * should also have completed the certification of exam
     * 1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams      - number of exams, which will produce a graph of n nodes
     * @param prerequisites - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */
    public boolean canFinish(int numExams, int[][] prerequisites) {
        int numNodes = numExams;
        ArrayList<Integer>[] adj = getAdjList(numExams, prerequisites);
        //counts how many incoming edges each exam has
        int[] inDegree = new int[numExams];
        //keeps track of exams that have no prerequisites
        Queue<Integer> queue = new LinkedList<>();
        //counts the number of prerequisites for each exam
        for (int i = 0; i < numExams; i++) {
            for (int neighbor : adj[i]) {
                inDegree[neighbor]++;
            }
        }
        //all exams with no prerequisites are added to the queue
        for (int i = 0; i < numExams; i++) {
            if (inDegree[i] == 0) {
                //exams without prerequisites can be taken right away
                queue.add(i);
            }
        }
        //keeps track of how many exams we can successfully take
        int visited = 0;
        //process exams in the queue
        while (!queue.isEmpty()) {
            int node = queue.poll(); //takes the next exam from the queue
            visited++; //exam is marked as taken
            //Update the prerequisites for all exams that depend on this one
            for (int neighbor : adj[node]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    // If an exam now has no prerequisites, add it to the queue
                    queue.add(neighbor);
                }
            }
        }
        //if all exams were taken return true, if not return false
        return visited == numExams;
    }


    /**
     * Method getAdjList
     * <p>
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges    - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */

    private ArrayList<Integer>[] getAdjList(
            int numNodes, int[][] edges) {

        ArrayList<Integer>[] adj
                = new ArrayList[numNodes]; // Create an array of ArrayList ADT

        for (int node = 0; node < numNodes; node++) {
            adj[node] = new ArrayList<Integer>(); // Allocate empty ArrayList per node
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]); // Add connected node edge [1] for node [0]
        }
        return adj;
    }


    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
         AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 aee connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form on large group.
     */

    public int numGroups(int[][] adjMatrix) {
        int numNodes = adjMatrix.length;
        Map<Integer, List<Integer>> graph = new HashMap();
        int i = 0, j = 0;
        /*
         * Converting the Graph Adjacency Matrix to
         * an Adjacency List representation. This
         * sample code illustrates a technique to do so.
         */
        for (i = 0; i < numNodes; i++) {
            for (j = 0; j < numNodes; j++) {
                if (adjMatrix[i][j] == 1 && i != j) {
                    // Add AdjList for node i if not there
                    graph.putIfAbsent(i, new ArrayList());
                    // Add AdjList for node j if not there
                    graph.putIfAbsent(j, new ArrayList());

                    // Update node i adjList to include node j
                    graph.get(i).add(j);
                    // Update node j adjList to include node i
                    graph.get(j).add(i);
                }
            }
        }
        //array keeps track of which nodes have already been visited
        boolean[] visited = new boolean[numNodes];
        //keeps traks of the number of groups
        int groups = 0;
        //goes through each node in the graph
        for (i = 0; i < numNodes; i++) {
            if (!visited[i]) {
                groups++; //if new group is found, increase the group count
                dfs(i, graph, visited); //explores all connected nodes in this group
            }
        }
        //return the total number of groups found
        return groups;
    }

    private void dfs(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
        //current node is marked as visited, so it's not checked again
        visited[node] = true;
        //checks if this node has connections in the graph
        if (graph.containsKey(node)) {
            //loops through all connected nodes
            for (int neighbor : graph.get(node)) {
                //if neighbor hasn't been visited yet, then it is visited
                if (!visited[neighbor]) {
                    dfs(neighbor, graph, visited);
                }
            }
        }
    }
}
