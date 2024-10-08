import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Demonstrates the calculation of shortest paths in the US Highway
 * network, showing the functionality of GraphProcessor and using
 * Visualize.
 */
public class GraphDemo {
    public static void main(String[] args) {
        try {
            GraphProcessor demoGraph = new GraphProcessor();

            // Adjust file paths as per your local structure
            FileInputStream file = new FileInputStream("data/usa.graph");
            FileInputStream citiesFile = new FileInputStream("data/uscities.csv");
            demoGraph.initialize(file);

            Scanner reader = new Scanner(System.in);

            System.out.print("Enter the latitude of your starting location: ");
            double[] cityACoordinates = new double[2];
            cityACoordinates[0] = reader.nextDouble();
            System.out.print("Enter the longitude of your starting location: ");
            cityACoordinates[1] = reader.nextDouble();

            System.out.print("Enter the latitude of your ending location: ");
            double[] cityBCoordinates = new double[2];
            cityBCoordinates[0] = reader.nextDouble();
            System.out.print("Enter the longitude of your ending location: ");
            cityBCoordinates[1] = reader.nextDouble();

            Point cityA = new Point(cityACoordinates[0], cityACoordinates[1]);
            Point cityB = new Point(cityBCoordinates[0], cityBCoordinates[1]);

            double beforeClosestPoints = System.nanoTime();
            Point nearCityA = demoGraph.nearestPoint(cityA);
            Point nearCityB = demoGraph.nearestPoint(cityB);
            double afterClosestPoints = System.nanoTime();
            double timeClosestPoints = (afterClosestPoints - beforeClosestPoints) / 1e6; // convert nano to ms

            double beforePath = System.nanoTime();
            List<Point> route = demoGraph.route(nearCityA, nearCityB);
            double afterPath = System.nanoTime();
            double timePath = (afterPath - beforePath) / 1e6;

            double beforeDist = System.nanoTime();
            double distance = demoGraph.routeDistance(route);
            double afterDist = System.nanoTime();
            double timeDist = (afterDist - beforeDist) / 1e6;

            // Adjust visualization file paths as per your environment
            String visFile = "data/usa.vis";
            String background = "images/usa.png";
            Visualize test = new Visualize(visFile, background);
            test.drawRoute(route);

            System.out.println();
            System.out.println("Nearest point to (" + cityACoordinates[0] + ", " + cityACoordinates[1]
                    + ") is " + nearCityA.toString() + ".");
            System.out.println("Nearest point to (" + cityBCoordinates[0] + ", " + cityBCoordinates[1]
                    + ") is " + nearCityB.toString() + ".");

            System.out.println("The route distance between " + nearCityA.toString()
                    + " and " + nearCityB.toString() + " is " + distance + " miles");

            double totalTime = timeClosestPoints + timePath + timeDist;
            System.out.println("Total Time = " + totalTime + " ms");

            // Close the Scanner properly
            reader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
