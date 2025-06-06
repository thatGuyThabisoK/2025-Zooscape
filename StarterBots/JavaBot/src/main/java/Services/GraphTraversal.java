package Services;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Queue;


import Models.Dtos.CellDto;
import utilities.Point;
import utilities.UtilFunctions;

public class GraphTraversal{
    
    /**
     * Finds the shortest path to the nearest pellet (node with value 2) in a graph using Breadth-First Search (BFS).
     * Movement is restricted to up, down, left, and right. Walls (nodes with value 1) cannot be traversed.
     *
     * @param graph The graph represented as a HashMap where keys are "x,y" coordinate strings
     * and values are integers (0: empty node, 1: wall, 2: pellet).
     * @param startCoord The starting coordinate string "x,y".
     * @return A String array representing the path from the start to the nearest pellet (inclusive),
     * or an empty array if no pellet is reachable, the start point is invalid, or the start point is a wall.
     */
    public ArrayList<Point> findPathToNearestPellet(List<CellDto> graph, Point startPoint, Point zooKeeper) {
    	ArrayList<Point> path = new ArrayList<>();
    	int dimention = (int) Math.sqrt(graph.size());
        // Check if the start point exists in the graph and is not a wall.
        // If the start point is not in the graph, it's an invalid starting position.
        if (!isValidCoordinate(dimention,startPoint) || graph.get(UtilFunctions.getElementIndex(dimention,startPoint.x,startPoint.y)).getContent() == 1) {
            System.out.println("Start point '" + startPoint.toString() + "' is invalid (not in graph or is a wall).");
            return path;
        }

        // Queue for BFS traversal. Stores points to visit.
        Queue<Point> queue = new LinkedList<>();
      
        // Initialize BFS: Add the start point to the queue and mark it as visited.
        queue.add(startPoint);
        startPoint.setIsVisited(true);

        // Define possible movements: (dx, dy) for right, left, down, up.
        int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		int[] move = {3,4,1,2};
		
        Point foundPellet = null; // Stores the first pellet found (which will be the nearest)

        // Perform BFS until the queue is empty or a pellet is found.
        while (!queue.isEmpty()) {
            Point current = queue.poll(); // Get the current point from the front of the queue.

            // Explore all four possible neighbors (up, down, left, right).
            for (int i = 0; i < 4; i++) {
                Point neighbor = new Point(current.x + dx[i], current.y + dy[i]);
                neighbor.setDirection(move[i]);
                // Check if the neighbor is within the graph bounds and has not been visited yet.
                if (isValidCoordinate(dimention, neighbor) && !neighbor.isVisited()) {
                    int neighborValue = graph.get(UtilFunctions.getElementIndex(dimention,neighbor.x,neighbor.y)).getContent();

                    // If the neighbor is a wall (value 1), it cannot be traversed, so skip it.
                    if (neighborValue == 1 || neighborValue == 4 || neighborValue == 3 || startPoint.equals(zooKeeper)) {
                        continue;
                    }

                    // Mark the neighbor as visited and record its parent (the current point).
                    neighbor.setIsVisited(true);
                    neighbor.setParent(current);

                    // If the neighbor is a pellet (value 2), we have found the nearest one.
                    // BFS guarantees that the first time a target is found, it's via the shortest path.
                    if (neighborValue == 2) {
                        foundPellet = neighbor;
                        break; // Exit the inner loop (no need to check other neighbors from current point)
                    }
                    // If not a pellet, add the neighbor to the queue for further exploration.
                    queue.add(neighbor);
                }
            }
            // If a pellet was found in the inner loop, break out of the outer BFS loop as well.
            if (foundPellet != null) {
                break;
            }
        }

        // --- Path Reconstruction ---
        // If no pellet was found after traversing the entire reachable graph.
        if (foundPellet == null) {
            System.out.println("No pellet found reachable from the start point '" + startPoint.toString() + "'.");
            return path;
        }

        // Reconstruct the path by backtracking from the found pellet to the start point
        // using the parentMap. A LinkedList is used for efficient adding to the front.
        
        Point currentPathPoint = foundPellet;
        while (!currentPathPoint.equals(startPoint)) {
            path.add(currentPathPoint); // Add the current point to the beginning of the path
            currentPathPoint = currentPathPoint.getParent(); // Move to its parent
        }
        
        return path;
    }
    
    
    private boolean isValidCoordinate(int dimention, Point start) {
    	
    	return (start.x < dimention && start.x >= 0) && (start.y < dimention && start.y >= 0);	
    	
    }

}
