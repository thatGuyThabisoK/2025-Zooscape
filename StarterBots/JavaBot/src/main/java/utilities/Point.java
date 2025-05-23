package utilities;

import java.util.Objects;

public class Point {
	
        public int x;
        public int y;

        /**
         * Constructs a new Point object.
         * @param x The x-coordinate.
         * @param y The y-coordinate.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Converts the Point object back to its "x,y" string representation.
         * @return The coordinate string.
         */
        @Override
        public String toString() {
            return x + "," + y;
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
