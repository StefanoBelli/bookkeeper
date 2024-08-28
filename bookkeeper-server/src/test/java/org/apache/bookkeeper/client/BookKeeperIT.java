package org.apache.bookkeeper.client;

import org.apache.bookkeeper.conf.ClientConfiguration;
import org.apache.bookkeeper.client.itutils.BookKeeperClusterTestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Arrays;

@RunWith(Parameterized.class)
public final class BookKeeperIT extends BookKeeperClusterTestCase {

    @Parameterized.Parameters
    public static Iterable<Object[]> params() {
        return Arrays.asList(new Object[][]{
                {1}
        });
    }

    public BookKeeperIT(int numBookies) {
        super(numBookies);
    }

    @Test
    public void test() throws BKException, IOException, InterruptedException {

    }
}
