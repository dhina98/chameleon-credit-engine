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

    @Value("${chameleon.knn.k}")
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
        // 2. Normalization: Find the maximums to level the playing field
        // This ensures the "Amount" (87500) doesn't bully the "Count" (38)
        double maxTotal = total.values().stream().max(Double::compare).orElse(1.0);
        double maxCount = counts.values().stream().max(Integer::compare).orElse(1).doubleValue();
        double maxAvg = maxTotal / (maxCount > 0 ? maxCount : 1);

        //Calculate avg per category with vector
        Map<String, double[]> vectors = new HashMap<>();
        total.forEach((category, totals) -> {
            int cnts = counts.get(category);
            vectors.put(category, new double[] {
                    totals / maxTotal,
                    cnts / maxCount,
                    (totals/cnts) / maxAvg
            }); // vectors == "Food" [1500.0, 10.0, 150.0] , "Tech" [5000.0, 1.0, 5000.0] ,"Fuel" [1000.0, 4.0, 250.0]
        });

        // Query vector with grand total
        double grandTotal = total.values().stream().mapToDouble(Double::doubleValue).sum();
        int grandTotalSize = last30Days.size();
        double[] query = {
                grandTotal / maxTotal,
                (double) grandTotalSize / maxCount,
                (grandTotal / grandTotalSize) / maxAvg
        };// [7500.0, 15.0, 500.0]

        //Eculieden distance calculation for each category
        List<Map.Entry<String, Double>> distances = vectors.entrySet().stream()
                .map(e -> Map.entry(e.getKey(), euclidean(query, e.getValue())))
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList()); // "Food": Distance 6016.8 (The closest/most "normal") ,"Fuel": Distance 6513.2 ,"Tech": Distance 25000.5 (An outlier)

        //voting for category
        int kactual = Math.min(k,distances.size());
        Map<String, Double> weightedVotes = new HashMap<>();
        for (int i = 0; i < kactual; i++) {
            double weight = 1.0 / (distances.get(i).getValue() + 0.0001);
            weightedVotes.merge(distances.get(i).getKey(), weight, Double::sum);
        }

        //return the winner category
        return weightedVotes.entrySet().stream().max((Map.Entry.comparingByValue())).map((Map.Entry::getKey)).orElse("GENERAL"); // With k=1, "Food" wins. With k=2, "Food" and "Fuel" tie, but "Food" wins due to being first. With k=3, "Food", "Fuel" and "Tech" tie, but "Food" wins due to being first.
    }

    private double euclidean(double [] a, double [] b ){
        double sum = 0;
        for(int i =0; i< a.length; i++){
            sum += Math.pow(a[i]-b[i],2);
        }
        return Math.sqrt(sum);
    }
    public double calculateConfidence(List<SpendingDataPoint> points , String winner){
        if (points == null || points.isEmpty()) return 0.0;
        long winCount = points.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(winner))
                .count();
        return ((double) winCount / points.size()) * 100;
    }
}
