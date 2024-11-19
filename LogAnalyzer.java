/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2016.02.29
 */
public class LogAnalyzer {
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Store daily access counts.
    private int[] dayCounts;
    // Store monthly access counts.
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    // Count of total accesses.
    private int totalAccesses;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String logFileName) {
        // Create the array object to hold the hourly access counts.
        hourCounts = new int[24];
        dayCounts = new int[31];  // Assuming maximum 31 days in a month
        monthCounts = new int[12]; // 12 months
        // Create the reader to obtain the data.
        reader = new LogfileReader(logFileName);
        // Initialize total accesses to 0.
        totalAccesses = 0;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData() {
        while (reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            // Assuming day is extracted from the log entry (index starts at 1 for day)
            int day = entry.getDay() - 1; // Zero-based index for day
            int month = entry.getMonth() - 1; // Zero-based index for month
            hourCounts[hour]++;
            if (day >= 0 && day < 31) dayCounts[day]++;  // Safeguard for days (assume 31 max days)
            if (month >= 0 && month < 12) monthCounts[month]++; // Safeguard for months
            totalAccesses++;
        }
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior call to analyzeHourlyData.
     */
    public void printHourlyCounts() {
        System.out.println("Hr: Count");
        for (int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader.
     */
    public void printData() {
        reader.printData();
    }

    /**
     * Returns the total number of accesses recorded in the log.
     * @return The total access count.
     */
    public int numberOfAccesses() {
        return totalAccesses;
    }

    // New Methods

    /**
     * Find the quietest day.
     * @return The quietest day (1-based index representing the day of the month).
     */
    public int quietestDay() {
        int quietestDay = 0;
        int minCount = dayCounts[0];
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] < minCount) {
                minCount = dayCounts[day];
                quietestDay = day;
            }
        }
        return quietestDay + 1; // Return 1-based index
    }

    /**
     * Find the busiest day.
     * @return The busiest day (1-based index representing the day of the month).
     */
    public int busiestDay() {
        int busiestDay = 0;
        int maxCount = dayCounts[0];
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] > maxCount) {
                maxCount = dayCounts[day];
                busiestDay = day;
            }
        }
        return busiestDay + 1; // Return 1-based index
    }

    /**
     * Calculate the total accesses per month.
     * @return A string listing the total accesses for each month.
     */
    public String totalAccessesPerMonth() {
        StringBuilder result = new StringBuilder("Accesses per month:\n");
        for (int month = 0; month < monthCounts.length; month++) {
            result.append(String.format("Month %d: %d\n", month + 1, monthCounts[month]));
        }
        return result.toString();
    }

    /**
     * Find the quietest month.
     * @return The quietest month (1-based index representing the month of the year).
     */
    public int quietestMonth() {
        int quietestMonth = 0;
        int minCount = monthCounts[0];
        for (int month = 1; month < monthCounts.length; month++) {
            if (monthCounts[month] < minCount) {
                minCount = monthCounts[month];
                quietestMonth = month;
            }
        }
        return quietestMonth + 1; // Return 1-based index
    }

    /**
     * Find the busiest month.
     * @return The busiest month (1-based index representing the month of the year).
     */
    public int busiestMonth() {
        int busiestMonth = 0;
        int maxCount = monthCounts[0];
        for (int month = 1; month < monthCounts.length; month++) {
            if (monthCounts[month] > maxCount) {
                maxCount = monthCounts[month];
                busiestMonth = month;
            }
        }
        return busiestMonth + 1; // Return 1-based index
    }

    /**
     * Calculate the average accesses per month.
     * @return The average number of accesses per month.
     */
    public double averageAccessesPerMonth() {
        int total = 0;
        for (int count : monthCounts) {
            total += count;
        }
        return total / 12.0;
    }
}
