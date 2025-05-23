package Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;


public class KMeansClustering{

    /**
     * Performs K-means clustering on a 2D integer array.
     *
     * @param data          The 2D integer array of ones and zeros.
     * @param k             The number of clusters.
     * @param iterations    The number of iterations to perform.
     * @return              A list of lists, where each inner list contains the 2D coordinates of the centroid for each cluster.
     * Returns an empty list if the input data is invalid or empty, or if k is invalid.
     */
    public static List<int[]> kmeans(int[][] data, int k, int iterations) {
        // Input validation
        if (data == null || data.length == 0 || data[0].length == 0 || k <= 0 || iterations <= 0) {
            return new ArrayList<>(); // Return an empty list for invalid input
        }

        int cols = data[0].length;

        // 1. Initialize centroids randomly
        List<int[]> centroids = initializeRandomCentroids(data, k);

        // 2. Iterate for the specified number of iterations
        for (int iter = 0; iter < iterations; iter++) {
            // 3. Assign each data point to the nearest cluster
            List<List<int[]>> clusters = assignDataToClusters(data, centroids);

            // 4. Update centroids based on the mean of the points in each cluster
            List<int[]> newCentroids = updateCentroids(clusters, cols);
             if (centroidsChanged(centroids, newCentroids)) {
                centroids = newCentroids;
            } else {
                 break; // Stop iterating if centroids no longer change
            }
        }

        // 5. Return the final centroids
        
        return centroids;
    }

    /**
     * Initializes the centroids randomly by selecting k data points from the input data.
     *
     * @param data The 2D integer array.
     * @param k    The number of clusters.
     * @return A list of integer arrays, where each array represents the coordinates of a centroid.
     */
    private static List<int[]> initializeRandomCentroids(int[][] data, int k) {
        List<int[]> centroids = new ArrayList<>();
        Random random = new Random();
        int rows = data.length;
        int cols = data[0].length;
        boolean[] chosen = new boolean[rows]; // Keep track of which rows have been chosen as centroids

        for (int i = 0; i < k; i++) {
            int rowIndex;
            do {
                rowIndex = random.nextInt(rows); // Generate random row index
            } while (chosen[rowIndex]);       // Ensure we don't pick the same row twice

            chosen[rowIndex] = true;            // Mark the row as chosen
            //Find the first '1' in the row.
            int colIndex = -1;
            for(int j = 0; j < cols; j++){
                if(data[rowIndex][j] == 2){
                    colIndex = j;
                    break;
                }
            }
            if(colIndex != -1){
                 centroids.add(new int[]{rowIndex, colIndex});
            }
            else{
                 centroids.add(new int[]{rowIndex, 0});
            }

        }
        return centroids;
    }

    /**
    * Assigns each data point to the nearest cluster based on the Euclidean distance
    * between the data point and the cluster's centroid.
    *
    * @param data      The 2D integer array.
    * @param centroids The list of centroids.
    * @return A list of lists, where each inner list contains the coordinates of the
    * data points belonging to that cluster.
    */
    private static List<List<int[]>> assignDataToClusters(int[][] data, List<int[]> centroids) {
        List<List<int[]>> clusters = new ArrayList<>();
        for (int i = 0; i < centroids.size(); i++) {
            clusters.add(new ArrayList<>()); // Initialize empty lists for each cluster
        }

        int rows = data.length;
        int cols = data[0].length;

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++){
                if(data[i][j] == 2){ // Only consider '1' values as data points.
                    double minDistance = Double.MAX_VALUE;
                    int closestCluster = -1;

                    for (int k = 0; k < centroids.size(); k++) {
                        double distance = calculateEuclideanDistance(i, j, centroids.get(k)[0], centroids.get(k)[1]);
                        if (distance < minDistance) {
                            minDistance = distance;
                            closestCluster = k;
                        }
                    }
                    clusters.get(closestCluster).add(new int[]{i, j}); // Add the point to the closest cluster
                }
            }

        }
        return clusters;
    }

     /**
     * Calculates the Euclidean distance between two points in 2D space.
     *
     * @param x1 The x-coordinate of the first point.
     * @param y1 The y-coordinate of the first point.
     * @param x2 The x-coordinate of the second point.
     * @param y2 The y-coordinate of the second point.
     * @return The Euclidean distance between the two points.
     */
    private static double calculateEuclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Updates the centroids based on the mean of the data points in each cluster.
     *
     * @param clusters The list of clusters.
     * @param cols The number of columns in the data.
     * @return A list of new centroids.
     */
    private static List<int[]> updateCentroids(List<List<int[]>> clusters, int cols) {
        List<int[]> newCentroids = new ArrayList<>();
        for (List<int[]> cluster : clusters) {
            if (cluster.isEmpty()) {
                // Handle empty cluster case: keep the old centroid or remove this cluster.
                // Here, we'll just add a default centroid at (0,0) to avoid errors.  A better strategy might be to re-initialize.
                newCentroids.add(new int[]{0, 0});
            } else {
                int sumX = 0;
                int sumY = 0;
                for (int[] point : cluster) {
                    sumX += point[0];
                    sumY += point[1];
                }
                int newCentroidX = sumX / cluster.size();
                int newCentroidY = sumY / cluster.size();
                newCentroids.add(new int[]{newCentroidX, newCentroidY});
            }
        }
        return newCentroids;
    }

    private static boolean centroidsChanged(List<int[]> oldCentroids, List<int[]> newCentroids) {
        if (oldCentroids.size() != newCentroids.size()) {
            return true; // Sizes differ, centroids have changed
        }
        for (int i = 0; i < oldCentroids.size(); i++) {
            if (oldCentroids.get(i)[0] != newCentroids.get(i)[0] || oldCentroids.get(i)[1] != newCentroids.get(i)[1]) {
                return true; // Coordinates differ, centroids have changed
            }
        }
        return false; // All centroids are the same
    }
    
    public static void showCentroids(List<int[]> clusters, int i) {
    	if (clusters.isEmpty()) {
            System.out.println("Invalid input or no clusters found for cluster "+i);
        } else {
            System.out.println("K-means Clustering Results for "+i+" : ");
                
            for(int[] centroid : clusters){
                System.out.println(Arrays.toString(centroid));
            }
        }
    }
}


