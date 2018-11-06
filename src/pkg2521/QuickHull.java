
package pkg2521;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @author Sotiria Antaranian
 * 
 * η πολυπλοκότητα σύμφωνα με την πηγή είναι O(n*log n) στη μέση περίπτωση
 */

public class QuickHull {

    /**
     * συνάρτηση που προσδιορίζει τη θέση ενός σημείου p σε σχέση με την ευθεία που σχηματίζουν τα σημεία a και b. 
     * η εξίσωση της ευθείας είναι : (x_b-x_a)*(y-y_a)-(y_b-y_a)*(x-x_a)=0 . 
     * αν για x,y βάλουμε τις συντεταγμενες του σημείου p και το αποτέλεσμα είναι θετικό, τότε το σημείο βρίσκεται πάνω από την ευθεία. 
     * αν είναι αρνητικό, βρίσκεται κάτω από την ευθεία και αν είναι 0, τότε βρίσκεται πάνω στην ευθεία. 
     * πηγή : http://www.sanfoundry.com/java-program-implement-quick-hull-algorithm-find-convex-hull/
     * @param a 
     * @param b τα σημεία που σχηματίζουν την ευθεία
     * @param p το σημείο του οποίου αναζητάται η θέση σε σχέση με την ευθεία
     * @return επιστρέφει 1 αν βρίσκεται πάνω από την ευθεία, 0 αν είναι πάνω στην ευθεία και -1 αν είναι κάτω από την ευθεία
     */
    public int isAboveTheLine (Point a,Point b,Point p)
    {
    int cp1=(int) ((b.getX()-a.getX())*(p.getY()-a.getY())-(b.getY()-a.getY())*(p.getX()-a.getX())); // ευθεία των a,b με τις συντεταγμένες του p για x,y
    if (cp1>0)
        return 1;// πάνω από την ευθεία
    else if (cp1==0)
        return 0; // πάνω στην ευθεία
    else
        return -1; // κάτω από την ευθεία
    }

    /**
     *  συνάρτηση που εκτελεί τον αλγόριθμο quick hull (ο κώδικας περιέχει πιο αναλυτικά σχόλια)
     * @param inputPoints τα σημεία της εισόδου
     * @param start το πιο αριστερό σημείο
     * @param finish το πιο δεξί σημείο
     * @return λίστα με τα σημεία με την συντομότερη διαδρομή από το start στο finish
     */
    public List<Point> executeQuickHull(List<Point> inputPoints,Point start,Point finish)
    {
        double distance1;
        double distance2;
        if(inputPoints.isEmpty())
        {
            throw new IllegalArgumentException("Cannot compute convex hull of zero points.");
        }

        List<Point> leftOfLine = new LinkedList<>();
        List<Point> rightOfLine = new LinkedList<>();
        for(Point point : inputPoints)
        {// χωρισμός των σημείων με βάση το αν βρίσκονται πάνω από την ευθεία που σχηματίζουν η εκκίνηση και το τέρμα
            if(isAboveTheLine(start,finish, point)==-1)
                leftOfLine.add(point);
            else
                rightOfLine.add(point);
        }
        // εύρεση διαδρομή για τα πάνω σημεία και το μήκος της
        List<Point> hullUp=divide(leftOfLine, finish, start);
        distance1=distance(hullUp,start,finish);
        // εύρεση διαδρομής για τα κάτω σημεία και το μήκος της
        List<Point> hullDown=divide(rightOfLine, start, finish);
        distance2=distance(hullDown,start,finish);

        // εύρεση συντομότερης διαδρομής η οποία είναι και η λύση
        if(distance1<distance2)
            return hullUp;
        else
            return hullDown;
    }

    /**
     * συνάρτηση η οποία υπολογίζει το συνολικό μήκος της διαδρομής που σχηματίζουν τα σημεία της λίστας h
     * @param h λίστα με τα σημεία
     * @param start
     * @param finish
     * @return το μήκος
     */
    public double distance (List<Point> h,Point start,Point finish)
    {
        double a,b,dis=0.0;
        for(int i=0;i<h.size();i++)
        {
            if(i==0) //το πρώτο σημείο της λίστας για το οποίο πρέπει να βρεθεί η ευκλείδεια απόστασή του από το start
            {
                a=Math.pow(start.getX()-h.get(i).getX(),2);
                b=Math.pow(start.getY()-h.get(i).getY(),2);
                dis=dis+Math.sqrt(a+b);
            }
            if(i==h.size()-1) // το τελευταίο σημείο της λίστας για το οποίο πρέπει να βρεθεί η απόστασή του από το finish
            {
               a=Math.pow(finish.getX()-h.get(i).getX(),2);
               b=Math.pow(finish.getY()-h.get(i).getY(),2);
               dis=dis+Math.sqrt(a+b);
            }
            else // οι μεταξύ τους αποστάσεις για όλα τα σημεία εκτός από το τελευταίο που δεν έχει επόμενο (οπότε το i+1 δεν έχει νόημα)
            {
                a=Math.pow(h.get(i+1).getX()-h.get(i).getX(),2);
                b=Math.pow(h.get(i+1).getY()-h.get(i).getY(),2);
                dis=dis+Math.sqrt(a+b); 
            }
        }
        return dis;
    }

    /**
     * πηγή : https://github.com/Thurion/algolab/blob/master/src/info/hska/convexhull/QuickHull.java#L22
     * (ο κώδικας περιέχει πιο αναλυτικά σχόλια)
     * ανδρομική συνάρτηση που βρίσκει το πιο μακρινό σημείο της λίστας points από την ευθεία που σχηματίζουν τα p1,p2 και το προσθέτει στη λίστα την οποία τελικά επιστρέφει
     * @param points λίστα με τα σημεία
     * @param p1 
     * @param p2 
     * @return επιστρέφει λίστα με τα πιο ακριανά σημεία
     */
    public List<Point> divide(List<Point> points, Point p1, Point p2)
    {
        List<Point> hull = new ArrayList<>();
        if(points.isEmpty()) 
            return hull;
        else if(points.size()==1)
        {
            hull.add(points.get(0));
            return hull;
        }
        Point maxDistancePoint=points.get(0);
        List<Point> l1=new LinkedList<>();
        List<Point> l2=new LinkedList<>();
        double distance=0.0;
        for (Point point : points)
        {// εύρεση σημείου με την μεγαλύτερη απόσταση από την ευθεία που σχηματίζουν τα σημεία p1,p2
            if (distanceToLine(p1,p2,point) > distance)
            {
                distance=distanceToLine(p1,p2,point);
                maxDistancePoint=point;
            }
        }

        points.remove(maxDistancePoint);
        // τα σημεία που βρίσκονται πάνω από την ευθεία που σχηματιζουν τα p1 και το σημείο με τη μέγιστη απόσταση μπαίνουν σε λίστα
        // τα σημεία που βρίσκονται πάνω από την ευθεία που σχηματίζουν τα p2 και το σημείο με τη μέγιστη απόσταση μπαίνουν σε λίστα
        for (Point point : points)
        {
            if (isAboveTheLine(p1,maxDistancePoint,point)==1)
                l1.add(point);
            if (isAboveTheLine(maxDistancePoint,p2,point)==1)
                l2.add(point);
        }

        points.clear();
        // καλείται αναδρομικά η συνάρτηση και για τις δυο λίστες
        List <Point> hullPart=divide(l1,p1,maxDistancePoint);
        hull.addAll(hullPart);
        hull.add(maxDistancePoint);
        hullPart=divide(l2,maxDistancePoint,p2);
        hull.addAll(hullPart);

        return hull;
    }



    /**
     * εκτελεί τον κλασσικό τύπο για την εύρεση απόστασης σημείου από ευθεία 
     * @param p1 
     * @param p2 τα δυο σημεία που σχηματίζουν την ευθεία
     * @param p το σημείο για το οποίο ζητάται η απόστασή του από την ευθεία
     * @return επιστρέφει την απόσταση
     */
    public double distanceToLine (Point p1,Point p2,Point p)
    {
        double dis;
        double p12x=p2.getX()-p1.getX();
        double p12y=p2.getY()-p1.getY();

        dis=(p12x*(p1.getY()- p.getY())-p12y*(p1.getX()-p.getX()))/Math.sqrt(Math.pow(p12x,2)+Math.pow(p12y,2));

        if (dis<0)
            dis=-dis;
        return dis;
    }

}
