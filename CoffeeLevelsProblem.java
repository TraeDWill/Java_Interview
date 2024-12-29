import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;

public class CoffeeLevelsProblem {
    public static void main(String[] args) {
        // these are the hour of day and the predicted number of coffees that will be drank in that hour
        List<HourOfConsumptionPrediction> predictedCoffeeConsumption = Arrays.asList(
                new HourOfConsumptionPrediction(0, 1),
                new HourOfConsumptionPrediction(1, 0),
                new HourOfConsumptionPrediction(2, 0),
                new HourOfConsumptionPrediction(3, 0),
                new HourOfConsumptionPrediction(4, 12),
                new HourOfConsumptionPrediction(5, 27),
                new HourOfConsumptionPrediction(6, 137),
                new HourOfConsumptionPrediction(7, 205),
                new HourOfConsumptionPrediction(8, 248),
                new HourOfConsumptionPrediction(9, 207),
                new HourOfConsumptionPrediction(10, 196),
                new HourOfConsumptionPrediction(11,177),
                new HourOfConsumptionPrediction(12,143),
                new HourOfConsumptionPrediction(13, 158),
                new HourOfConsumptionPrediction(14, 185),
                new HourOfConsumptionPrediction(15, 122),
                new HourOfConsumptionPrediction(16, 105),
                new HourOfConsumptionPrediction(17, 82),
                new HourOfConsumptionPrediction(18, 53),
                new HourOfConsumptionPrediction(19, 21),
                new HourOfConsumptionPrediction(20, 18),
                new HourOfConsumptionPrediction(21, 10),
                new HourOfConsumptionPrediction(22, 6),
                new HourOfConsumptionPrediction(23, 2)
        );

        menu(predictedCoffeeConsumption);

    }
    /**
     * This is the menu function that allows the user to choose which solution they would like to use
     * 
     * @param predictedCoffeeConsumption
     */
    static void menu(List<HourOfConsumptionPrediction> predictedCoffeeConsumption){
        
        System.out.println("Which solution would you like to use?");
        System.out.println("1. Normal Solution");
        System.out.println("2. Fun Solution");
        System.out.println("3. Logical Solution");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
  
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch(choice){
            case(1):
                System.out.println("This is the solution i'd use in a real world scenario\n");
                CoffeeLevelsPredictor predictor = new CoffeeLevelsPredictor(predictedCoffeeConsumption);  
                BiFunction<Integer, Integer, Integer> function = predictor::getHourCoffeeRunsOut;
                testCoffee(function);
                break;

            case(2):
                System.out.println("This is the fun solution\n");
                CoffeeLevelsPredictorFun predictorFun = new CoffeeLevelsPredictorFun(predictedCoffeeConsumption); 
                BiFunction<Integer, Integer, Integer> functionFun = predictorFun::getHourCoffeeRunsOut;
                testCoffee(functionFun);
                break;

            case(3):
                System.out.println("This is the most logically set up solution for when I visualized the problem\n");
                CoffeeLevelsPredictorLogical predictorLogical = new CoffeeLevelsPredictorLogical(predictedCoffeeConsumption);
                BiFunction<Integer, Integer, Integer> functionLogical = predictorLogical::getHourCoffeeRunsOut;
                testCoffee(functionLogical);
                break;

            case(4):
                System.out.println("Thank you for your time!");
                System.exit(0);
                break;
                
            default:
                System.out.println("Invalid choice");
                break;
        }
        sc.close();

    }
    /**
     * This is the test function that tests the function with the given test cases
     * 
     * @param function the function that will be tested
     */
    static void testCoffee(BiFunction<Integer, Integer, Integer> function){
        System.out.println("Test: Hour: 3 CoffeeLeft: 1600 Final Hour: " + function.apply(3, 1600)+ "\n");
        System.out.println("Test: Hour: 6 CoffeeLeft: 1200 Final Hour: " + function.apply(6, 1200) + "\n");
        System.out.println("Test: Hour: 9 CoffeeLeft: 800 Final Hour: " + function.apply(9, 800) + "\n");
        System.out.println("Test: Hour: 12 CoffeeLeft: 800 Final Hour: " + function.apply(12, 800) + "\n");
        System.out.println("Test: Hour: 15 CoffeeLeft: 1800 Final Hour: " + function.apply(15, 1800));
    }
}



/*
 * This is the solution I would use in production. It takes the array list and it unpacks it as well as
 * placing it into a basic int array.
*/
class CoffeeLevelsPredictor {

    int[] AllPredictions = new int[24];

    /**
     * This constructor ports the ArrayList into an array
     * @param predictedCoffeeConsumption
     */
    public CoffeeLevelsPredictor(List<HourOfConsumptionPrediction> predictedCoffeeConsumption) {
        for(HourOfConsumptionPrediction prediction : predictedCoffeeConsumption){
            AllPredictions[prediction.getHour().intValue()] = prediction.getCoffeeConsumedThisHour();
        }
    }

    /**
     * Some other service is responsible for keeping track of how many coffees are left
     * and what time of day it is. Your job is to calculate based on the time of day passed in
     * and the number of coffees left, return the time we are expected to run out of coffee!
     *
     * @param coffeeLeft how many coffees are left at this time
     * @param currentHourOfDay what the start hour is for the prediction.
     * @return the hour we will run out of coffee.
     */
    public Integer getHourCoffeeRunsOut(Integer currentHourOfDay, Integer coffeeLeft) {

        int whatsLeft = coffeeLeft.intValue();

        for(int hours = currentHourOfDay.intValue(); hours < 24; hours++){
            whatsLeft -= AllPredictions[hours];
            System.out.println("Hour:" + hours + " Coffees Sold:" + AllPredictions[hours] + " Coffees Left:" + whatsLeft);
            if(whatsLeft <= 0){
                Integer lastHour = hours;
                return lastHour;
            }
        }
        // If we reach this point, we have not run out of coffee, and it will display what we have left over
        System.out.println("The coffee will not run out on this day, what's remaining is: " + whatsLeft);
        return 23;
    }
}

class CoffeeLevelsPredictorLogical {

    HashMap<Integer, Integer> AllPredictions = new HashMap<Integer, Integer>();

    /*
     * This constructor ports the ArrayList into a HashMap
     */
    public CoffeeLevelsPredictorLogical(List<HourOfConsumptionPrediction> predictedCoffeeConsumption) {
        for(HourOfConsumptionPrediction prediction : predictedCoffeeConsumption){
            AllPredictions.put(prediction.getHour(), prediction.getCoffeeConsumedThisHour());
        }
    }

    /**
     * @param coffeeLeft how many coffees are left at this time
     * @param currentHourOfDay what the start hour is for the prediction.
     * @return the hour we will run out of coffee.
     */
    public Integer getHourCoffeeRunsOut(Integer currentHourOfDay, Integer coffeeLeft) {

        Integer whatsLeft = coffeeLeft;
        Integer hours = currentHourOfDay;
        
        while(whatsLeft > 0 && hours < 24){
            whatsLeft -= AllPredictions.get(hours);
            System.out.println("Hour:" + hours + " Coffee Sold:" + AllPredictions.get(hours) + " Coffees Left:" + whatsLeft);
            hours++;
         }
        // If we pass this condition, we have not run out of coffee, and it will display what we have left over
        if(hours == 24){
            System.out.println("The coffee will not run out on this day, what's remaining is: " + whatsLeft);
            return 23;
        }
        //The final increment in the loop needs to be decremented by 1 to get the final hour
         return hours - 1;
   }

}

/*
 * This is the fun solution to the problem. I created a Linkedlist as it was the first full data structure I learned
 * It also seemed the best option for this set of data in a fun answer.
 */

class CoffeeLevelsPredictorFun {

    listNode head = null;
    /*
     * This constructor ports the List into a LinkedList manually for a fun solution
     */
    public CoffeeLevelsPredictorFun(List<HourOfConsumptionPrediction> predictedCoffeeConsumption) {
        for(HourOfConsumptionPrediction prediction : predictedCoffeeConsumption){
            listNode current = new listNode(prediction.getHour(), prediction.getCoffeeConsumedThisHour());
            if(head == null){
                head = current;
            }else{
                listNode temp = head;
                while(temp.next != null){
                    temp = temp.next;
                }
                temp.next = current;
            }
        }
    }

    /**
     * @param coffeeLeft how many coffees are left at this time
     * @param currentHourOfDay what the start hour is for the prediction.
     * @return the hour we will run out of coffee.
     */
    public Integer getHourCoffeeRunsOut(Integer currentHourOfDay, Integer coffeeLeft) {

        Integer whatsLeft = coffeeLeft; 
        listNode current = head;

        //Finds the correct node with a matching hour, if not found, it will return -1
        while(current.getHour() != currentHourOfDay && current != null){
            current = current.next;
        }
        if(current == null){
            System.out.println("Invalid hour");
            return -1;
        }

        //Iterates through the linked list until the coffee runs out, if it does not run out, it will display what's left
        while(whatsLeft > 0 && current != null){
            whatsLeft -= current.getCoffeeConsumedThisHour();
            System.out.println("Hour:" + current.getHour() + " Coffee Sold: " + current.getCoffeeConsumedThisHour() + " Coffees Left:" + whatsLeft);
            current = current.next;
        }
        if(current == null){
            System.out.println("The coffee will not run out on this day, what's remaining is: " + whatsLeft);
            return 23;
        }
        else{
            return current.getHour() -1;
        }
   }

}

class HourOfConsumptionPrediction {
    private Integer hour;
    private Integer coffeesConsumedThisHour;

    public HourOfConsumptionPrediction(Integer hour, Integer coffeesConsumedThisHour) {
        this.hour = hour;
        this.coffeesConsumedThisHour = coffeesConsumedThisHour;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getCoffeeConsumedThisHour() {
        return coffeesConsumedThisHour;
    }
}

/*
 * This is the linked list node class for the fun solution
 */
class listNode{
    private Integer hour;
    private Integer coffeesConsumedThisHour;
    listNode next;
    listNode(Integer hours, Integer coffeeConsumedThisHour){
        this.hour = hours;
        this.coffeesConsumedThisHour = coffeeConsumedThisHour;
        this.next = null;
    }
    public Integer getHour() {
        return hour;
    }
    public Integer getCoffeeConsumedThisHour() {
        return coffeesConsumedThisHour;
    }
}