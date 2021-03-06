package org.benetech.servicenet.matching;

import org.benetech.servicenet.ServiceNetApp;
import org.benetech.servicenet.matching.counter.UrlSimilarityCounter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceNetApp.class)
public class UrlSimilarityCounterIntTest {

    private static final float PRECISION = 0.001f;

    @Autowired
    private UrlSimilarityCounter urlSimilarityCounter;

    @Test
    public void shouldReturnMinRatioForDifferentNormalizedAndUpperCased() {
        assertEquals(0, urlSimilarityCounter.countSimilarityRatio("one.com", "two.com", null), PRECISION);
    }

    @Test
    public void shouldReturnProperRatioForSameNormalizedAndUpperCased() {
        assertEquals(0.95, urlSimilarityCounter.countSimilarityRatio("one.com", "http://ONE.com", null), PRECISION);
    }

    @Test
    public void shouldReturnMaxRatioForSameNormalized() {
        assertEquals(1.0, urlSimilarityCounter.countSimilarityRatio("www.one.com", "https://one.com", null), PRECISION);
    }
}
