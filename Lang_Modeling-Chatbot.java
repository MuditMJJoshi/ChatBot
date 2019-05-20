//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Chatbot
//
// Author:          Mudit Joshi
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////



import java.util.*;
import java.io.*;
 
public class Chatbot{
	
    private static String filename = "./corpus.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    
    /**
     * In this question you will implement a chatbot by generating random
     *  sentences from your HW1 corpus using
     * n-gram language models with Laplace smoothing.
     * 
     * vocabulary file vocabulary.txt for you to interpret the data, though 
     * you do not need it
	 * for programming. The vocabulary is created by tokenizing the corpus, 
	 * converting everything to lower case,
	 * and keeping word types that appears three times or more
	 * 
     * @author Mudit Joshi
     *
     */
    private static class CorpusWords {
    	public final int word;
    	public final double left;
    	public final double right;
    	
    	public CorpusWords(int word, double left, double right) {
    		this.word = word;
    		this.left = left;
    		this.right = right;
    	}
    	
    } 
    
    /**
     * 
     * * vocabulary file vocabulary.txt for you to interpret the data, though
     * 
     *  you do not need it
	 * for programming. The vocabulary is created by tokenizing the corpus,
	 *  converting everything to lower case,
	 * and keeping word types that appears three times or more
     * 
     * * The words on the right are provided from the original essays for 
     * readability, they are not in the corpus. The
	 * word “undermined” is not in the vocabulary, therefore it is mapped to
	 *  OOV and have an index 0. OOV
	 * represents a set of out-of-vocabulary words such as “undermined, 
	 * extra-developed, metro, customizable,
	 * optimizable” etc. But for this homework you can treat OOV as a 
	 * single special word type. Therefore, the
	 * vocabulary has v = 4700 word types. The corpus has 228548 tokens
	 * 
     * @param Probablity
     * @return Corpus Word Generated
     * 
     */
    static private CorpusWords[] genProbs(HashMap<Integer, Double> Probablity) {
    	
    	int wordCounter = 0;  
     	
    	CorpusWords[] CorpusWordGen = new CorpusWords[Probablity.size()];
    	
    	for(int i = 0; i < 4700; i++) {
    		if (Probablity.containsKey(i)) {
    			double prob = Probablity.get(i);
    			
    			if (i == 0) {
    				CorpusWordGen[wordCounter] = new CorpusWords(wordCounter, 0.0, prob);
    			}
    			else {
    				CorpusWordGen[wordCounter] = new CorpusWords(wordCounter, CorpusWordGen[wordCounter-1].right, CorpusWordGen[wordCounter-1].right + prob);
    			}
    			wordCounter++;
    		}
    		
    	}
    	return CorpusWordGen;
    }
    
    
    private static void twohundrencounter(ArrayList<Integer> corpus,
HashMap<Integer, Integer> counterstore,double rem) {
		// TODO Auto-generated method stub
    	
    	for (int wordcount : corpus) {
    		counterstore.put(wordcount, counterstore.getOrDefault(wordcount, 0) + 1);
    	}  
    	
    	HashMap<Integer, Double> probability = new HashMap<>();
    	for (int i : counterstore.keySet()) {
    		probability.put(i, (double) (counterstore.get(i) + 1) / (corpus.size() + 4700));
    	} 
    	
        CorpusWords[] store = genProbs(probability);
         
        for (CorpusWords wrds : store) {
        	if (rem > wrds.left && rem <= wrds.right) {
        		System.out.println(wrds.word);
        		System.out.printf("%.7f\n", wrds.left);
        		System.out.printf("%.7f\n", wrds.right);
        		break;
        	}
        }
		
	}
    
    
    private static void fivehundredcounter(int value1,int value2,int num,
int counter,ArrayList<Integer> corpus,ArrayList<Integer> words) {
		// TODO Auto-generated method stub
    	
    	for (int i = 0; i < corpus.size() - 1; i++) {
        	if (corpus.get(i).equals(value1) && corpus.get(i+1).equals(value2)) {
        			words.add(corpus.get(i+2));
        			if (corpus.get(i+2).equals(num)) 
        				counter++;
        		}
        	}
        
        System.out.println(counter);
        System.out.println(words.size());
        System.out.println(String.format("%.7f", (counter + 1) / (double) (words.size() + 4700)));
		
	}
    
    
    private static void sixhundredcounter(int number1,int number2,int value1 ,
int value2, double rand,int words, ArrayList<Integer> 
    corpus,HashMap<Integer, Integer> counters) {
		// TODO Auto-generated method stub
    	
    	for (int i = 0; i < corpus.size() - 1; i++) {
    		if (corpus.get(i).equals(value1) && corpus.get(i+1).equals(value2)) {
    			words++;
    			int word = corpus.get(i+2);
    			counters.put(word, counters.getOrDefault(word, 0) + 1);
    		}
    		
    	}
    	
    	HashMap<Integer, Double> probability = new HashMap<>();
    	for (int i = 0; i < corpus.size() - 2; i++) {
    		if (counters.containsKey(i)) {
    			probability.put(i, (double) (counters.get(i) + 1) / (words + 4700));
    		}
    		else {
    			probability.put(i, (double) 1 / (words + 4700));
    		}
    	}
    	
        CorpusWords[] stores = genProbs(probability);
        
        for (CorpusWords wrds : stores) {
        	if (rand == 0) {
        		System.out.println(stores[0].word);
        		System.out.println(String.format("%.7f", wrds.left));
        		System.out.println(String.format("%.7f", wrds.right));
        		break;
        	}
        	if (rand > wrds.left && rand <= wrds.right) {
        		System.out.println(wrds.word);
        		System.out.println(String.format("%.7f", wrds.left));
        		System.out.println(String.format("%.7f", wrds.right));
        		break;
        	}
        }
		
	}
    
    
    private static void fourhundredcounter(int number1,int number2,int number3,
double rand, int words,ArrayList<Integer> corpus,
HashMap<Integer, Integer> counters) {
		// TODO Auto-generated method stub
    	
    	for (int i = 0; i < corpus.size() - 1; i++) {
    		if (corpus.get(i).equals(number3)) {
    			words++;
    			int word = corpus.get(i+1);
    			counters.put(word, counters.getOrDefault(word, 0) + 1);
    		}
    	} 
    	
    	HashMap<Integer, Double> probability = new HashMap<>();
    	for (int i = 0; i < corpus.size() -1; i++) {
    		if (counters.containsKey(i)) {
    			probability.put(i, (double) (counters.get(i) + 1) / (words + 4700));
    		}
    		else {
    			probability.put(i, (double) 1 / (words + 4700));
    		}
    	} 
    	 
        CorpusWords[] stores = genProbs(probability);
        
        for (CorpusWords wrds : stores) {
        	if (rand == 0) {
        		System.out.println(stores[0].word);
        		System.out.println(String.format("%.7f", wrds.left));
        		System.out.println(String.format("%.7f", wrds.right));
        		break;
        	}
        	if (rand > wrds.left && rand <= wrds.right) {
        		System.out.println(wrds.word);
        		System.out.println(String.format("%.7f", wrds.left));
        		System.out.println(String.format("%.7f", wrds.right));
        		break;
        	}
        }
		
	}

    
    /**
     * 
     * 
     * The words on the right are provided from the original essays for 
     * readability, they are not in the corpus. The
     * 
	 * word “undermined” is not in the vocabulary, therefore it is mapped 
	 * to OOV and have an index 0. OOV
	 * represents a set of out-of-vocabulary words such as “undermined,
	 *  extra-developed, metro, customizable,
	 *  
	 * optimizable” etc. But for this homework you can treat OOV as a
	 *  single special word type. Therefore, the
	 * vocabulary has v = 4700 word types. The corpus has 228548 tokens
     * 
     * @param args
     * 
     */
    static public void main(String[] args){
    	
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
		 
         
        if(flag == 100){
			int wrd = Integer.valueOf(args[1]);
            int counter = 0;
            for (int word : corpus) {
            	if (wrd == word)
            		counter++;
            }
            
            System.out.println(counter);
            System.out.println(String.format("%.7f" , (counter + 1) / (double) (corpus.size() + 4700)));
            
        }   
         
        else if(flag == 200){ 
            int number1 = Integer.valueOf(args[1]);
            int number2 = Integer.valueOf(args[2]);
            double rem = (double) number1 / number2;
            
        	HashMap<Integer, Integer> counterstore = new HashMap<>();
        	
        	twohundrencounter(corpus,counterstore,rem);
        	
        }
           
        
        else if(flag == 300){
            int value1 = Integer.valueOf(args[1]);
            int value2 = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words = new ArrayList<Integer>();
            
            for (int i = 0; i < corpus.size() - 1; i++) {
            	if (corpus.get(i).equals(value1)) {
            		words.add(corpus.get(i+1));
            		if (corpus.get(i+1).equals(value2)) 
            				count++;
            	}
            }
            
            System.out.println(count);
            System.out.println(words.size());
            System.out.println(String.format("%.7f", (count + 1) / (double) (words.size() + 4700)));
  
        } 
         
        else if(flag == 400){
            int number1 = Integer.valueOf(args[1]);
            int number2 = Integer.valueOf(args[2]);
            int number3 = Integer.valueOf(args[3]);
            double rand = (double) number1 / number2;
            int words = 0;  
            
            HashMap<Integer, Integer> counters = new HashMap<>();
            
            fourhundredcounter(number1,number2,number3,rand,words,corpus,counters);
           
             
        }
        
        else if(flag == 500){
            int value1 = Integer.valueOf(args[1]);
            int value2 = Integer.valueOf(args[2]);
            int num = Integer.valueOf(args[3]);
            int counter = 0;
            ArrayList<Integer> words = new ArrayList<Integer>();
            
            fivehundredcounter(value1,value2,num,counter,corpus,words);
            
            
			
        }
        
        else if(flag == 600){
        	int number1 = Integer.valueOf(args[1]);
            int number2 = Integer.valueOf(args[2]);
            int value1 = Integer.valueOf(args[3]);
            int value2 = Integer.valueOf(args[4]);
            double rand = (double) number1 / number2;
            int words = 0;
            
            HashMap<Integer, Integer> counters = new HashMap<>();
        	
        	
        	sixhundredcounter(number1,number2, value1,value2,rand,words,corpus,counters);
        	
        	
            
        }
        
        else if(flag == 700){
        	
            int seed = Integer.valueOf(args[1]);
            int number = Integer.valueOf(args[2]);
            
            int value1=0;
            int value2=0;
            HashMap<Integer, Integer> counter;
            HashMap<Integer, Double> probability;
            CorpusWords[] stores;
            
            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(number == 0){
                double random = rng.nextDouble();
                
                //Unigram Model
                counter = new HashMap<>();
                for (int i : corpus) {
                	counter.put(i, counter.getOrDefault(i, 0)+1);
                }
                
                probability = new HashMap<>();
                for (int i : counter.keySet()) {
                	probability.put(i, (double) (counter.get(i)+1) / (corpus.size()+4700));
                }
                
                stores = genProbs(probability);
                
                for (CorpusWords wrds : stores) {
                	if (random > wrds.left && random <= wrds.right) {
                		value1 = wrds.word;
                	}
                }
                
                System.out.println(value1);
                if(value1 == 9 || value1 == 10 || value1 == 12){
                    return;
                }

                // Generate second word using r
                random = rng.nextDouble();
                int words = 0;
                
                // Bigram Model
                counter = new HashMap<>();
            	for (int i = 0; i < corpus.size() - 1; i++) {
            		if (corpus.get(i).equals(value1)) {
            			words++;
            			int word = corpus.get(i+1);
            			counter.put(word, counter.getOrDefault(word, 0) + 1);
            		}
            	}
            	
            	probability = new HashMap<>();
            	for (int i = 0; i < corpus.size() -1; i++) {
            		if (counter.containsKey(i)) {
            			probability.put(i, (double) (counter.get(i) + 1) / (words + 4700));
            		}
            		else {
            			probability.put(i, (double) 1 / (words + 4700));
            		}
            	}
            	
                stores = genProbs(probability);
                
                for (CorpusWords d : stores) {
                	if (random == 0) {
                		value2 = d.word;
                		break;
                	}
                	if (random > d.left && random <= d.right) {
                		value2 = d.word;
                		break;
                	}
                }
                
                System.out.println(value2);
            }
            
            else if(number == 1){
            	
                value1 = Integer.valueOf(args[3]);
                double random = rng.nextDouble();
                int words = 0;
                
                // Bigram Model again
                counter = new HashMap<>();
            	for (int i = 0; i < corpus.size() - 1; i++) {
            		if (corpus.get(i).equals(value1)) {
            			words++;
            			int word = corpus.get(i+1);
            			counter.put(word, counter.getOrDefault(word, 0) + 1);
            		}
            	}
            	
            	probability = new HashMap<>();
            	for (int i = 0; i < corpus.size() -1; i++) {
            		if (counter.containsKey(i)) {
            			probability.put(i, (double) (counter.get(i) + 1) / (words + 4700));
            		}
            		else {
            			probability.put(i, (double) 1 / (words + 4700));
            		}
            	}
            	
                stores = genProbs(probability);
                
                for (CorpusWords d : stores) {
                	if (random == 0) {
                		value2 = d.word;
                		break;
                	}
                	if (random > d.left && random <= d.right) {
                		value2 = d.word;
                		break;
                	}
                }
                
                System.out.println(value2);
            }
            else if(number == 2){
                value1 = Integer.valueOf(args[3]);
                value2 = Integer.valueOf(args[4]);
            }

            while(value2 != 9 && value2 != 10 && value2 != 12){
                double random = rng.nextDouble();
                int wordcount  = 0;
                
                int words = 0;
                
                counter = new HashMap<>();
            	for (int i = 0; i < corpus.size() - 1; i++) {
            		if (corpus.get(i).equals(value1) && corpus.get(i+1).equals(value2)) {
            			words++;
            			int word = corpus.get(i+2);
            			counter.put(word, counter.getOrDefault(word, 0) + 1);
            		}
            		
            	}
            	
            	probability = new HashMap<>();
            	for (int i = 0; i < corpus.size() - 2; i++) {
            		if (counter.containsKey(i)) {
            			probability.put(i, (double) (counter.get(i) + 1) / (words + 4700));
            		}
            		else {
            			probability.put(i, (double) 1 / (words + 4700));
            		}
            	}
            	
            	stores = genProbs(probability);
                
                for (CorpusWords d : stores) {
                	if (random == 0) {
                		wordcount = d.word;
                		break;
                	}
                	if (random > d.left && random <= d.right) {
                		wordcount = d.word;
                		break;
                	}
                }
                
                System.out.println(wordcount);
                value1 = value2;
                value2 = wordcount;
            }
        }

        return;
    }


	

	

	
}
