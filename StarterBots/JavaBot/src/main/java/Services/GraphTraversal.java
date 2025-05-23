package Services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import utilities.Point;

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
    public static LinkedList<Point> findPathToNearestPellet(int[][] graph, Point startPoint) {
    	LinkedList<Point> path = new LinkedList<>();

        // Check if the start point exists in the graph and is not a wall.
        // If the start point is not in the graph, it's an invalid starting position.
        if (!isValidCoordinate(graph.length,startPoint) || graph[startPoint.x][startPoint.y] == 1) {
            System.out.println("Start point '" + startPoint.toString() + "' is invalid (not in graph or is a wall).");
            return path;
        }

        // If the start point itself is a pellet, the path is just the start point.
        if (graph[startPoint.x][startPoint.y] == 2) {
        	path.add(startPoint);
            return path;
        }

        // Queue for BFS traversal. Stores points to visit.
        Queue<Point> queue = new LinkedList<>();
        // Set to keep track of visited points to prevent cycles and redundant processing.
        Set<Point> visited = new HashSet<>();
        // Map to reconstruct the path: stores Child -> Parent relationship.
        Map<Point, Point> parentMap = new HashMap<>();

        // Initialize BFS: Add the start point to the queue and mark it as visited.
        queue.add(startPoint);
        visited.add(startPoint);

        // Define possible movements: (dx, dy) for right, left, down, up.
        int[] dx = {1, -1, 0, 0}; // Change in x-coordinate
        int[] dy = {0, 0, 1, -1}; // Change in y-coordinate

        Point foundPellet = null; // Stores the first pellet found (which will be the nearest)

        // Perform BFS until the queue is empty or a pellet is found.
        while (!queue.isEmpty()) {
            Point current = queue.poll(); // Get the current point from the front of the queue.

            // Explore all four possible neighbors (up, down, left, right).
            for (int i = 0; i < 4; i++) {
                Point neighbor = new Point(current.x + dx[i], current.y + dy[i]);
                
                // Check if the neighbor is within the graph bounds and has not been visited yet.
                if (isValidCoordinate(graph.length, neighbor) && !visited.contains(neighbor)) {
                    int neighborValue = graph[neighbor.x][neighbor.y];

                    // If the neighbor is a wall (value 1), it cannot be traversed, so skip it.
                    if (neighborValue == 1) {
                        continue;
                    }

                    // Mark the neighbor as visited and record its parent (the current point).
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);

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
            path.addFirst(currentPathPoint); // Add the current point to the beginning of the path
            currentPathPoint = parentMap.get(currentPathPoint); // Move to its parent
            // The loop naturally stops when currentPathPoint becomes null (after adding the startPoint)
        }
        
        return path;
    }
    
    
    private static boolean isValidCoordinate(int dimention, Point start) {
    	
    	return (start.x < dimention && start.x >= 0) && (start.x < dimention && start.x >= 0);	
    	
    }

}
