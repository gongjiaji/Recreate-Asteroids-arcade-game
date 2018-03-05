package game2;

import java.io.*;
import java.util.*;

public class HighScore {
    // create a SortedSet to store all scores.
    // scores are automatically sorted and no duplicate elements
    static SortedSet<Integer> list = new TreeSet<>(Collections.reverseOrder());


    // write score to the file
    public void write_score() {
        try {
            File file = new File("src/highScores.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Game.score + " ");  // make the score to string, use space to separate
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("check your file!!!");
        }
    }


    // get first 10 element in the score set
    public static String top10() {
        String top10_result;
        String final_result = "";

        try {
            Scanner input = new Scanner(new File("src/highScores.txt"));
            while (input.hasNext()) {
                int a = Integer.parseInt(input.next()); // get the next score add to list
                list.add(a);
            }

            Iterator<Integer> iterator = list.iterator();
            int i = 1;
            while(iterator.hasNext() && i<=10){ // show 10 scores only
                top10_result = i+" :            "+iterator.next()+"\n";
                final_result += top10_result;  // append score
                i++; // ranking
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("check your file !!!");
        }

        return final_result; // this will display on dialog
    }

}
