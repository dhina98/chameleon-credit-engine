package com.chameleon.categorization.service;

import com.chameleon.categorization.model.SpendingDataPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KNNClassifierService {

    @Value("${chemeleon.knn.k}")
    private int k;

    public String classify(List<SpendingDataPoint> last30Days){
        if (last30Days ==null || (last30Days.isEmpty())) {
            return "GENERAL";
        }

        // Aggeregate total and count per category
        Map<String, Double> total = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        for(SpendingDataPoint dp : last30Days) {
            total.merge(dp.getCategory(), dp.getAmount().doubleValue(), Double::sum); //Output (totals Map): { "Food": 1500.0, "Tech": 5000.0, "Fuel": 1000.0 }
            counts.merge(dp.getCategory(), 1, Integer::sum); //    Output (counts Map): { "Food": 10, "Tech": 1, "Fuel": 4 }
        }

        //Calculate avg per category with vector
        Map<String, double[]> vectors = new HashMap<>();
        total.forEach((category, totals) -> {
            int cnts = counts.get(category);
            vectors.put(category, new double[] {totals, cnts, totals/cnts}); // vectors == "Food" [1500.0, 10.0, 150.0] , "Tech" [5000.0, 1.0, 5000.0] ,"Fuel" [1000.0, 4.0, 250.0]
        });

        // Query vector with grand total
        double grandTotal = total.values().stream().mapToDouble(Double::doubleValue).sum();
        int grandTotalSize = last30Days.size();
        double [] query = {grandTotal, grandTotalSize, grandTotal/grandTotalSize }; // [7500.0, 15.0, 500.0]

        //Eculieden distance calculation for each category
        List<Map.Entry<String, Double>> distances = vectors.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), euclidean(query, e.getValue())))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList()); // "Food": Distance 6016.8 (The closest/most "normal") ,"Fuel": Distance 6513.2 ,"Tech": Distance 25000.5 (An outlier)

        //voting for category
        int kactual = Math.min(k,distances.size());
        Map<String, Long> votes = distances.subList(0,kactual).stream().collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting())); // With k=1, "Food" gets 1 vote. With k=2, "Food" gets 1 vote and "Fuel" gets 1 vote. With k=3, "Food" gets 1 vote, "Fuel" gets 1 vote and "Tech" gets 1 vote.

        //return the winner category
        return votes.entrySet().stream().max((Map.Entry.comparingByValue())).map((Map.Entry::getKey)).orElse("GENERAL"); // With k=1, "Food" wins. With k=2, "Food" and "Fuel" tie, but "Food" wins due to being first. With k=3, "Food", "Fuel" and "Tech" tie, but "Food" wins due to being first.
    }

    private double euclidean(double [] a, double [] b ){
        double sum = 0;
        for(int i =0; i< a.length; i++){
            sum += Math.pow(a[i]-b[i],2);
        }
        return Math.sqrt(sum);
    }
    public double calculateConfidence(List<SpendingDataPoint> points , String winner){
       Long winCount = points.stream().filter(p -> p.getCategory().equals(winner)).count();
       double confidencePercentage = winCount/points.size() *100;
       return confidencePercentage;
    }
}
