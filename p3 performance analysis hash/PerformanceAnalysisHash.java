/**
 * Class used to perform a performance analysis on
 * a hash table versus the performance of a tree map
 * Due: 3/19/2018
 * Bugs: none known
 *
 * @author       Chris Sullivan (csullivan9@wisc.edu)
 * @see also     HashTable.java, results.txt
 */

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class PerformanceAnalysisHash implements PerformanceAnalysis {
    //hash table used for performance analysis
    HashTable hash;
    //tree map used for performance analysis
    TreeMap tree;
    // The input data from each file is stored in this/ per file
    private ArrayList<String> inputData;
    //stores the list of text files for input
    private ArrayList<String> input = new ArrayList<String>();
    //used to determine if input file is list of strings or integers
    private boolean isInteger = true;
    
    /**
     * empty constructor not used
     */
    public PerformanceAnalysisHash(){ 
    }
    
    /**
     * constructor used for reading in files
     * @param details_filename
     */
    public PerformanceAnalysisHash(String details_filename){
        File file = new File(details_filename);
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while(sc.hasNextLine()) {
                input.add(sc.useDelimiter(",").next());
                sc.nextLine();
            }  
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * loads data and calls insertion, search, and deletion methods
     */
    @Override
    public void compareDataStructures() {
        for(int i = 0; i < input.size(); i++) {
            try {
                loadData("./data/" + input.get(i));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(isInteger) {
                hash = new HashTable<Integer, Integer>(100, 0.75);
                tree = new TreeMap<Integer, Integer>();
            } else {
                hash = new HashTable<String, String>(100, 0.75);
                tree = new TreeMap<String, String>();
            }
            System.out.println("FileName: " + input.get(i));
            compareInsertion();
            compareSearch();
            compareDeletion();
        }
    }
    
    /**
     * not used
     */
    @Override
    public void printReport() {
    }
    
    /**
     * compares insertion times and memory used of tree map and hash table
     * and prints out values
     */
    @Override
    public void compareInsertion() {
        //get and store memory and time usage of the hash table insertion
        Runtime runtime = Runtime.getRuntime();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                hash.put(Integer.parseInt(inputData.get(i)), 
                        Integer.parseInt(inputData.get(i)));
            } else {
                hash.put(inputData.get(i), 
                        inputData.get(i));
            }
        }
        long stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: PUT | Data Stucture: HASHTABLE | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
        //get and store memory and time usage of the tree map insertion
        runtime = Runtime.getRuntime();
        startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                tree.put(Integer.parseInt(inputData.get(i)), 
                        Integer.parseInt(inputData.get(i)));
            } else {
                tree.put(inputData.get(i), 
                        inputData.get(i));
            }
        }
        stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: PUT | Data Stucture: TREEMAP | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
    }

    /**
     * compares deletion times of hash table and tree map and prints 
     * out values
     */
    @Override
    public void compareDeletion() {
      //get and store memory and time usage of the hash table remove
        Runtime runtime = Runtime.getRuntime();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                hash.remove(Integer.parseInt(inputData.get(i)));
            } else {
                hash.remove(inputData.get(i));
            }
        }
        long stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: DELETE | Data Stucture: HASHTABLE | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
        
        //get and store memory and time usage of the tree map remove
        runtime = Runtime.getRuntime();
        startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                tree.remove(Integer.parseInt(inputData.get(i)));
            } else {
                tree.remove(inputData.get(i));
            }
        }
        stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: DELETE | Data Stucture: TREEMAP | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
    }
    
    /**
     * compares search times of hash map and tree map and prints out
     * values
     */
    @Override
    public void compareSearch() {
        //get and store memory and time usage of the hash table remove
        Runtime runtime = Runtime.getRuntime();
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                hash.get(Integer.parseInt(inputData.get(i)));
            } else {
                hash.get(inputData.get(i));
            }
            
        }
        long stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: SEARCH | Data Stucture: HASHTABLE | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
        
        //get and store memory and time usage of the tree map remove
        runtime = Runtime.getRuntime();
        startTime = System.currentTimeMillis();
        for(int i = 0; i < inputData.size(); i++) {
            if(isInteger) {
                tree.get(Integer.parseInt(inputData.get(i)));
            } else {
                tree.get(inputData.get(i));
            }
        }
        stopTime = System.currentTimeMillis();
        runtime.gc();
        System.out.print("Operation: SEARCH | Data Stucture: TREEMAP | Bytes Used: " + (runtime.totalMemory() -
                runtime.freeMemory()) + " | Time Taken(ms): " + (stopTime - startTime) + "\n");
    }

    /*
    An implementation of loading files into local data structure is provided to you
    Please feel free to make any changes if required as per your implementation.
    However, this function can be used as is.
     */
    @Override
    public void loadData(String filename) throws IOException {

        // Opens the given test file and stores the objects each line as a string
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new ArrayList<>();
        String line = br.readLine();
        try {
            Integer.parseInt(line);
        } catch (NumberFormatException e) {
            isInteger = false;
        }
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
}
