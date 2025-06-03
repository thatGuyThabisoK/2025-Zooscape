package utilities;

import java.util.Objects;

public class Point {
	
        public int x;
        public int y;
        private Point parent;
        private boolean isVisited;
        private int direction;

        /**
         * Constructs a new Point object.
         * @param x The x-coordinate.
         * @param y The y-coordinate.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public void setParent(Point parent) {
        	this.parent = parent;
        }
        
        public Point getParent() {
        	return parent;
        }
        
        public void setIsVisited(boolean visited) {
        	isVisited = visited;
        }
        
        public boolean isVisited() {
        	return isVisited;
        }
        
        public void setDirection(int direction) {
        	this.direction = direction;
        }
        
        public int getDirection() {return direction;}

        /**
         * Converts the Point object back to its "x,y" string representation.
         * @return The coordinate string.
         */
        @Override
        public String toString() {
            return x + "," + y+" direction: "+direction;
        }

        /**
         * Compares this Point object with another object for equality.
         * Two Point objects are considered equal if their x and y coordinates are the same.
         * @param o The object to compare with.
         * @return true if the objects are equal, false otherwise.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        /**
         * Generates a hash code for this Point object.
         * Essential for using Point objects as keys in HashMaps or elements in HashSets.
         * @return The hash code.
         */
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

}
