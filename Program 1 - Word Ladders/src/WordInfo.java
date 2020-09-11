public class WordInfo {
    public String word;
    public int moves;
    public String history;

    public WordInfo(String word, int moves, String history){
        this.word = word;
        this.moves = moves;
        this.history = history;
    }

    public String toString(){
        return "Word " + word    + " Moves " +moves  + " History ["+format();
    }
    //implement a getLast method, to return the last word in the concatenated string "history" by using the substring method
    public String getLast(){
        return history.substring(history.length() - word.length());
    }
    //implement a insertion method, since my list is a concatenation of strings, just concatenate onto the current history
    public void insert(String newWord){
        history = history + newWord;
    }
    //custom history format for dividing the concatenated string into equal parts, separated by a single space
    private String format(){
        String newHist = "";
        int j = 0;
        //iterate over the entire length of the concatenated history
        for(int i = 0; i < history.length(); i++){
            newHist = newHist + history.charAt(i);
            j += 1;
            //add a space for every length of a word
            if(j == word.length()){
                newHist = newHist + " ";
                j = 0;
            }
        }
        //return new string, that includes spaces
        return newHist;
    }
}

