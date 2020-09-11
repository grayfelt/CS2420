import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
public class WritePoetry {
    public String WritePoem(String file, String startWord, int len, boolean print){
        //initialize scanner with file
        File poemFile = new File("src\\" + file);
        Scanner sc = null;
        try {
            sc = new Scanner(poemFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //grab first and following word, deleting all special characters and converting to lowercase
        String first = sc.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String veryFirst = first;
        String second = sc.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        //initialize HashTable, and add first wordFreqInfo object to the HashTable
        HashTable table = new HashTable(100);
        WordFreqInfo word = new WordFreqInfo(first, 1);
        word.updateFollows(second);
        table.insert(first, word);

        //reading the whole file, taking the next word and its follower and adding to the HashTable
        while(sc.hasNext()) {
            first = second;
            second = sc.next().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            //word already exists in HashTable, just update followers and occurCt
            if (table.contains(first)) {
                word = (WordFreqInfo) table.find(first);
                word.occurCt++;
                word.updateFollows(second);
            }
            //word is new to the HashTable
            else {
                word = new WordFreqInfo(first, 1);
                table.insert(first, word);
                word.updateFollows(second);

            }
        }
        String poem = "";
        Random rand = new Random();
        poem += startWord;
        poem += " ";
        //find first word to add to computer created poem
        WordFreqInfo nextWord = (WordFreqInfo) table.find(startWord);
        //stop at user-determined length
        for(int a = 0; a < len - 1; a++){
            //case for final word, loop to first word
            if(nextWord == null){
                nextWord = (WordFreqInfo) table.find(veryFirst);
            }
            //use probability example outlined in assignment write-up
            int occurCount = nextWord.occurCt;
            int randInt = rand.nextInt(occurCount + 1);
            int sumFreq = 0;
            //for each follower, sum their frequency and once that reaches the random number between 0-nextWord.occurCt,
            //use that index for the next word to use
            for(int i = 0; i < nextWord.followList.size(); i++){
                sumFreq += nextWord.followList.get(i).followCt;
                if(sumFreq >= randInt){
                    poem += nextWord.followList.get(i).follow;
                    poem += " ";
                    nextWord = (WordFreqInfo) table.find(nextWord.followList.get(i).follow);
                    break;
                }
            }
        }
        if(print){
            System.out.println("--------------------------------------");
            System.out.println("--------------HASH TABLE--------------");
            System.out.println(table.toString(400));
            System.out.println("--------------------------------------");
            System.out.println("-----------------POEM----------------");
        }
        else{
            System.out.println("--------------------------------------");
            System.out.println("-----------------POEM-----------------");
        }
        return poem;
    }
}
