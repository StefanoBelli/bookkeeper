package org.apache.bookkeeper.client;

import org.apache.bookkeeper.client.RoundRobinDistributionSchedule;
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
            int ensembleSize,
            int writeQuorumSize,
            int ackQuorumSize) {

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
        /*
         * BK docs: https://bookkeeper.apache.org/archives/docs/r4.4.0/bookkeeperProtocol.html
         *
         * E  (int) : ensemble size
         * Qw (int) : write quorum size
         * Qa (int) : ack quorum size
         *
         * E => Qw >= Qa
         *
         * ensemble: when the ledger is created, E bookies are chosen for the entries of that ledger.
         *
         * write quorums: each entry in the log is written to Qw nodes. This is considered the write
         *                 quorum for that entry. The write quorum is the subsequence of the ensemble,
         *                 Qw in length, and starting at the bookie at index (entryid % E).
         *
         * ack quorum: The ack quorum for an entry is any subset of the write quorum of size Qa.
         *             If Qa bookies acknowledge an entry, it means it has been fully replicated.
         */
        return Arrays.asList(new Object[][]{
                // E   Qw  Qa
                {  0, 0, 0  },
                {  0, 0, 1  },
                {  0, 0, 2  },
                {  0, 0, 3  },
                {  0, 1, 0  },
                {  0, 1, 1  },
                {  0, 1, 2  },
                {  0, 1, 3  },
                {  0, 2, 0  },
                {  0, 2, 1  },
                {  0, 2, 2  },
                {  0, 2, 3  },
                {  0, 3, 0  },
                {  0, 3, 1  },
                {  0, 3, 2  },
                {  0, 3, 3  },
                {  1, 0, 0  },
                {  1, 0, 1  },
                {  1, 0, 2  },
                {  1, 0, 3  },
                {  1, 1, 0  },
                {  1, 1, 1  },
                {  1, 1, 2  },
                {  1, 1, 3  },
                {  1, 2, 0  },
                {  1, 2, 1  },
                {  1, 2, 2  },
                {  1, 2, 3  },
                {  1, 3, 0  },
                {  1, 3, 1  },
                {  1, 3, 2  },
                {  1, 3, 3  },
                {  2, 0, 0  },
                {  2, 0, 1  },
                {  2, 0, 2  },
                {  2, 0, 3  },
                {  2, 1, 0  },
                {  2, 1, 1  },
                {  2, 1, 2  },
                {  2, 1, 3  },
                {  2, 2, 0  },
                {  2, 2, 1  },
                {  2, 2, 2  },
                {  2, 2, 3  },
                {  2, 3, 0  },
                {  2, 3, 1  },
                {  2, 3, 2  },
                {  2, 3, 3  },
                {  3, 0, 0  },
                {  3, 0, 1  },
                {  3, 0, 2  },
                {  3, 0, 3  },
                {  3, 1, 0  },
                {  3, 1, 1  },
                {  3, 1, 2  },
                {  3, 1, 3  },
                {  3, 2, 0  },
                {  3, 2, 1  },
                {  3, 2, 2  },
                {  3, 2, 3  },
                {  3, 3, 0  },
                {  3, 3, 1  },
                {  3, 3, 2  },
                {  3, 3, 3  }
        });
    }
}
