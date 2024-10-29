/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // Count of total accesses
    private int totalAccesses;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String logFileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(logFileName);
        // Initialize total accesses to 0
        totalAccesses = 0;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
            totalAccesses++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Returns the total number of accesses recorded in the log.
     * @return The total access count.
     */
    public int numberOfAccesses()
    {
        return totalAccesses;
    }
    
    /**
     * Find the busiest hour.
     * @return The hour with the highest number of accesses.
     */
    public int busiestHour() {
        int busiestHour = 0;
        int maxCount = hourCounts[0];
        
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > maxCount) {
                maxCount = hourCounts[hour];
                busiestHour = hour;
            }
        }
        return busiestHour;
    }
    
    /**
     * Find the quietest hour.
     * @return The hour with the fewest accesses.
     */
    public int quietestHour() {
        int quietestHour = 0;
        int minCount = hourCounts[0];
        
        for (int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < minCount) {
                minCount = hourCounts[hour];
                quietestHour = hour;
            }
        }
        return quietestHour;
    }
    
    /**
     * Find the busiest two-hour period.
     * @return The starting hour of the two-hour period with the most accesses.
     */
    public int busiestTwoHour() {
        int busiestTwoHourStart = 0;
        int maxTwoHourCount = hourCounts[0] + hourCounts[1];
        
        for (int hour = 1; hour < hourCounts.length - 1; hour++) {
            int twoHourCount = hourCounts[hour] + hourCounts[hour + 1];
            if (twoHourCount > maxTwoHourCount) {
                maxTwoHourCount = twoHourCount;
                busiestTwoHourStart = hour;
            }
        }
        return busiestTwoHourStart;
    }
}
