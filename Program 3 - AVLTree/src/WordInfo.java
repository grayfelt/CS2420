public class WordInfo implements Comparable<WordInfo> {
    public String word;
    public int moves;
    public String history;
    public int priority;
    private String lastAdded;

    public WordInfo(String word, int moves, String history){
        this.word = word;
        this.moves = moves;
        this.history = history;
    }

    public String toString(){
        return "Word " + word    + " Moves " +moves  + " History [" + history + " ]";
    }
    //implement a getLast method, to return the last word in the concatenated string "history" by using the substring method
    public String getLast(){
        return history.substring(history.length() - word.length());
    }
    //implement a insertion method, since my list is a concatenation of strings, just concatenate onto the current history
    public void insert(String newWord){
        history = history + newWord;
    }
    //function to determine priority of given word
    public void getPriority(String word){
        //for words that are equal
        if(this.getLast().equals(word)){
            priority = 0;
        }
        //initial priority will be equal to moves
        priority = moves;
        //length of last word (and current word)
        int thisLen = this.getLast().length();
        //iterate through the word, checking for similar characters, just like the "isOneLetterOff" method
        for(int i = 0; i < thisLen; i++){
            if(!(word.charAt(i)==this.getLast().charAt(i))){
                //lower priority for words that are different
                priority+=1;
            }
        }
    }
    public int compareTo(WordInfo T){
        if(this.priority > T.priority) return 1;
        else if (this.priority < T.priority) return -1;
        return 0;
    }
}

