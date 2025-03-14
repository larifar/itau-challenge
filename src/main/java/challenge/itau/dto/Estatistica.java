package challenge.itau.dto;

import java.util.DoubleSummaryStatistics;

public record Estatistica(
        long count, double sum, double avg, double min, double max
) {

    public Estatistica (DoubleSummaryStatistics stats){
        this(stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax());
    }
}
