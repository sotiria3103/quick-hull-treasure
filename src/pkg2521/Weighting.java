﻿
package pkg2521;

import java.util.Random;
/**
 * @author Sotiria Antaranian
 * 
 * η πολιπλοκότητα είναι Ο (log_3(n))
 */
public class Weighting 
{
   
    private int zygos()
    {
        Random randomGenerator = new Random();
        int x = randomGenerator.nextInt(100);
        if (x<34)
            return 1; //κλίνει αριστερά
        else if (x < 67)
            return 0; //ίσο βάρος
        else
            return -1; //κλίνει δεξιά
    }
    
    /**
     * αναδρομική συνάρτηση που χωρίζει κάθε φορά το πλήθος των διαμαντιών σε τρία μέρη και ζυγίζει δυο μέρη από αυτά. 
     * το ζύγισμα γίνεται με τη βοήθεια της συνάρτησης zygos. 
     * ανάλυση πολυπλοκότητας:  
     * C(n)=C(n/3)+1,  n=3^k , k=log_3(n)  
     * C(3^k)=C(3^(k-1))+1, C(3^(k-1))=C(3^(k-2))+1, ..., C(3)=C(1)+1 (προσθέτω κατά μέλη)   
     * C(3^k)=k ~ C(n)=log_3(n)  
     * η πολυπλοκότητα είναι Ο (log_3(n)) (log με δείκτη 3) όπου n το πλήθος όλων των διαμαντιών
     * @param amount πλήθος διαμαντιών
     * @param num αριθμός ζυγισμάτων
     * @return τον αριθμό ζυγισμάτων
     */
    public int weightingDiamonds (int amount,int num)
    {
        switch (amount) {
            case 1:
                return num; //αν το πλήθος των διαμαντιών είναι 1 τότε είναι το μη πολύτιμο διαμάντι, οπότε δεν χρειάζεται άλλο ζύγισμα και επιστρέφεται το πλήθος των ζυγισμάτων
            case 2:
                return ++num;//αν είναι 2 τα διαμάντια, τότε ζυγίζονται μια τελευταία φορά για να βρεθεί το μη πολύτιμο και επιτρέφεται το πλήθος ζυγισμάτων
            default: // τα διαμάντια χωρίζονται σε 3 μέρη
                if(zygos()==-1 || zygos()==1) // ζυγίζονται τα 2 τρίτα 
                {
                    num=num+1;
                    return weightingDiamonds (amount/3,num);  // αν δεν είναι ίσα καλείται αναδρομικά η συνάρτηση για το ελαφρύτερο μέρος, όπου βρίσκεται το μη πολύτιμο διαμάντι
                }
                else
                {
                    num=num+1;
                    return weightingDiamonds (amount/3+amount%3,num);//αν είναι ίσα, τότε το μη πολύτιμο διαμάντι είναι στο ένα τρίτο που δεν ζυγίστηκε ή το πιθανό υπόλοιπο που μπορεί να προέκυψε κατά τον χωρισμό και για αυτά καλείται αναδρομικά η συνάρτηση
                }
        }
    }
}

