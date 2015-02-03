import java.util.Random;

public class PercolationStats {
    Random rand = new Random();
    Percolation percolation;
    public int runCount;
    public int randomRow;
    public int randomCol;
    public int gridSize;
    public int openSquares;
    public int sumOfThresholds;
    public int[] thresholdArray;
    double sumOfDifferenceSquared;
        
    public PercolationStats(int N, int T) {
        percolation = new Percolation(N);
        gridSize = N*N;
        thresholdArray = new int[T];
        for(int i=0; i<T; i++){
            openSquares = 0;
            while(!percolation.percolates()) {
                randomRow = rand.nextInt(N);
                randomCol = rand.nextInt(N);
                if(!percolation.isOpen(randomRow,randomCol)){
                    percolation.open(randomRow,randomCol);
                    openSquares++;
                }
            }
            thresholdArray[i] = openSquares/gridSize;
            sumOfThresholds += openSquares/gridSize;
            runCount++; 
        }
    };
    
    public int runCount() {
         return runCount;
    }
    
    public double mean() {
        return sumOfThresholds/runCount;    
    }
    
    public double stddev() {
        for(int i= 0; i< runCount; i++) {
           double difference = thresholdArray[i] - mean(); 
           sumOfDifferenceSquared += difference*difference;    
        }
        double variance = sumOfDifferenceSquared/runCount;
        double stdDev = Math.sqrt(variance);
        return stdDev;
    }
    
    public double confidenceLo() {
        return mean() - ((1.96*stddev())/Math.sqrt(runCount()));    
    }             

    public double confidenceHi() {
        return mean() + ((1.96*stddev())/Math.sqrt(runCount()));
    }             
   
}