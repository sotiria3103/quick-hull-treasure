package pkg2521;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * @author Sotiria Antaranian
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException 
    {
        // TODO code application logic here
    List<Point> points=new ArrayList<>();    
    Scanner fileScanner = new Scanner(new File("file.txt"));
    Point start=new Point (); //σημείο εκκίνησης
    Point finish=new Point(); //σημείο θυσαυρού
    int x=fileScanner.nextInt();
    int y=fileScanner.nextInt();
    start.setLocation(x,y);
    x=fileScanner.nextInt();
    y=fileScanner.nextInt();
    finish.setLocation(x,y);
    int amountOfDiamonds=fileScanner.nextInt();
    while (fileScanner.hasNextInt()) //διάβασμα όλων των σημείων με τις νάρκες
    {
        Point p=new Point();
        x=fileScanner.nextInt();
        y=fileScanner.nextInt();
        p.setLocation(x,y);
        points.add(p);
    }
    QuickHull qHull;
    qHull = new QuickHull();
    List<Point> hull=new ArrayList<>(); //λίστα με τα σημεία της πιο σύντομης διαδρομής
    hull=qHull.executeQuickHull(points, start, finish);
    double distance;
    distance=qHull.distance(hull, start, finish); //μήκος διαδρομής
    System.out.println("The shortest distance is "+distance);
    System.out.print("The shortest path is:("+start.getX()+","+start.getY()+")");
    for(Point point : hull)
    {
        System.out.print("-->("+point.getX()+","+point.getY()+")");
    }
    System.out.println("-->("+finish.getX()+","+finish.getY()+")");
    
    Weighting weight=new Weighting();
    int numberOfWeightings=0;
    numberOfWeightings=weight.weightingDiamonds(amountOfDiamonds,numberOfWeightings);
    System.out.println("Number of weightings:"+numberOfWeightings);
    }
    
}
