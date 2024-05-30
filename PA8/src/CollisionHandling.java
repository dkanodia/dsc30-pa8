/**
 * PA8 Part 1 worksheet (style not required)
 */
public class CollisionHandling {

    /**
     * method that stores the result of Q1 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] linearProbingResult(){
        int[][] output = {{0,56,0,42,0,28,0,14}, {70,56,112,42,98,28,84,14}}; //change
        return output;
    }

    /**
     * method that stores the result of Q2 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] quadraticProbingResult(){
        int[][] output = {{0,6,0,16,1,0,11,0}, {21,6,27,16,1,9,11,3}}; //change
        return output;
    }


    /**
     * method that stores the result of Q3 in PA8 worksheet
     * 4 insertions and then all of them
     * @return hashtable representation after insertions
     */
    public static int[][] doubleHashingResult(){
        int[][] output = {{0,0,10,17,0,23,2,0}, {51,31,10,17,38,23,2,44}}; //change
        return output;
    }

}