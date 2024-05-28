package ste;

import org.apache.bookkeeper.client.DistributionSchedule;
import org.apache.bookkeeper.client.RoundRobinDistributionSchedule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RoundRobinDistributionScheduleTest {
    private final int ensembleSize;
    private final int ackQuorumSize;
    private final int writeQuorumSize;

    private final RoundRobinDistributionSchedule rrSched;

    public RoundRobinDistributionScheduleTest(
            int writeQuorumSize,
            int ackQuorumSize,
            int ensembleSize) {

        this.ensembleSize = ensembleSize;
        this.writeQuorumSize = writeQuorumSize;
        this.ackQuorumSize = ackQuorumSize;

        this.rrSched = new RoundRobinDistributionSchedule(
                writeQuorumSize,
                ackQuorumSize,
                ensembleSize);
    }

    @Parameterized.Parameters
    public static Collection configParams() {
        return Arrays.asList(new Object[][] {
            {1,2,3},
        });
    }

    @Test
    public void testRr() {
        DistributionSchedule.WriteSet ensembleSet = rrSched.getEnsembleSet(2);
        ensembleSet.moveAndShift(0, 1);
        System.out.println(ensembleSet.get(0));
        System.out.println(ensembleSet.get(1));
        System.out.println(ensembleSet.get(2));

        DistributionSchedule.WriteSet writeSet = rrSched.getWriteSet(0);
        System.out.println(writeSet.get(0));

        DistributionSchedule.AckSet ackSet = rrSched.getAckSet();
        System.out.println(ackSet.completeBookieAndCheck(0));
        System.out.println(ackSet.completeBookieAndCheck(1));
    }
}
