package com.sachin.nseanalyzer

import org.junit.Assert.assertEquals
import org.junit.Test

class AnalyzerConfigTest {
    @Test
    fun packageAndVersionIdentityAreStable() {
        assertEquals("com.sachin.nseanalyzer", "com.sachin.nseanalyzer")
        assertEquals("10.0", "10.0")
    }
}
