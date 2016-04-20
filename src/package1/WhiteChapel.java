package package1;

import java.util.*;

public class WhiteChapel {
     private List<Integer>[]graph;
     private Clue[] hideoutDistances;

    @SuppressWarnings("unchecked")
    public WhiteChapel(int numVertices){
        graph = new List[numVertices];
    }

    public void addEdges(int vertex1, int vertex2){

        if(graph[vertex1]==null)
            graph[vertex1]=new LinkedList<>();
        //Adds a vertex in the list of adjecent vertices
        graph[vertex1].add(vertex2);
    }

    public void initHideout(int numClues){
        hideoutDistances=new Clue[numClues];
    }

    public void addHideoutclue(int i , int crimeLoc , int hideoutDistance){
        hideoutDistances[i] = new Clue(crimeLoc,hideoutDistance);
    }

    public List<Integer> problem(){

        boolean [][] intersection = new boolean[hideoutDistances.length][graph.length];
        List<Integer> list=new LinkedList<>();
        for(int i = 0;i<hideoutDistances.length;i++){
            list=bfsExplore(intersection,hideoutDistances[i],i);
            if(list.size()==0){
                return null;
            }
        }

        return list;
    }

    private List<Integer> bfsExplore( boolean[][] intersection, Clue root,int matrixLine) {
        Queue<Integer> waiting = new LinkedList<>();
        Queue<Integer> adjacentNodes = new LinkedList<>();
        List<Integer> list=new LinkedList<>();
        boolean [] found = new boolean[graph.length];

        waiting.add(root.getCrimeLoc());
        found[root.getCrimeLoc()]=true;


            for (int i = 0; i <= root.getRoadClue(); i++) {


                while (!waiting.isEmpty()) {

                    for (int node : graph[waiting.remove()]) {

                        if (!found[node]) {
                            if (i == root.getRoadClue()) {
                                if (matrixLine > 0) {
                                    if (intersection[matrixLine - 1][node]) {
                                        intersection[matrixLine][node] = intersection[matrixLine - 1][node];
                                        list.add(node);
                                    }

                                } else {intersection[matrixLine][node] = true;
                                    list.add(node);
                                }
                            }
                            found[node] = true;
                            adjacentNodes.add(node);
                        }

                    }
                }
                waiting.addAll(adjacentNodes);
                adjacentNodes.clear();
            }
            return list;
    }
}