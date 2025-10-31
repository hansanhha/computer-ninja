package hansanhha;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class ColumnRangePartitioner implements Partitioner {

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Map<String, ExecutionContext> partitions = new HashMap<>();

        int min = 1;
        int max = 500;
        int range  = (max - min) / gridSize;
        int start = min;
        int end = start + range;

        for (int i = 0; i < gridSize; i++) {
            ExecutionContext context = new ExecutionContext();
            context.putInt("start", start);
            context.putInt("end", i == gridSize - 1 ? max : end);
            partitions.put("partition" + i, context);
            start = end + 1;
            end = end + range;
        }

        return partitions;
    }
    
}
