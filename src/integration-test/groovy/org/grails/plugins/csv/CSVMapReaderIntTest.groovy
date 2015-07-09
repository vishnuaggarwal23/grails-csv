package org.grails.plugins.csv

import grails.test.mixin.integration.Integration
import org.junit.Test

@Integration
class CSVMapReaderIntTest extends GroovyTestCase {

    @Test
    void testFileToCsvMapReader() {
        def recs = new File(this.class.classLoader.getResource('mapTest.csv').path).toCsvMapReader().toList()
        assert recs.size() == 2
        assertEquals([col1: 'val1', col2: 'val2', col3: 'val3'], recs[0])
    }
}
