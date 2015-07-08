/*
 * Copyright 2010 Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * under the License.
 */
package org.grails.plugins.csv

import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import org.junit.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

/**
 * Tests to verify dynamically added methods to Java/Groovy classes (String, InputStream, etc) work as expected
 *
 * @since 0.1
 * @author Les Hazlewood
 */
@Integration
class CSVReaderUtilsIntegrationTest extends GroovyTestCase {

    @Test
    void testFileEachCsvLine() {
        //
        //Just to clarify it is sufficient to
        // write
        // this.class.getClassLoader().getResourceAsStream("my_xsl.xsl")
        // if my_xsl.xsl is located at src/main/resource/my_xsl.xsl –  ubiquibacon Jun 1 at 18:56
        def file = new File("resources/fileTest.csv")
        assertNotNull(file)
        assertTrue(file.exists())
        file.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testFileEachCsvLineWithSettings() {
        def file = new File("resources/fileTest.csv")
        assertNotNull(file)
        assertTrue(file.exists())

        file.toCsvReader(['charset': 'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testInputStreamEachCsvLine() {
        def is = new File("resources/fileTest.csv").newInputStream()
        assertNotNull(is)
        is.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testInputStreamEachCsvLineWithSettings() {
        def is = new File("resources/fileTest.csv").newInputStream()
        assertNotNull(is)
        is.toCsvReader(['charset': 'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testReaderEachCsvLine() {
        def reader = new FileReader(new File("resources/fileTest.csv"))
        reader.eachCsvLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testReaderEachCsvLineWithSettings() {
        def reader = new FileReader(new File("resources/fileTest.csv"))
        reader.toCsvReader(['charset': 'UTF-8']).eachLine { tokens ->
            assertEquals(4, tokens.length)
        }
    }

    @Test
    void testStringEachCsvLine() {
        String csv = 'test, with, "nested, commas", and a, "multiline, \n comma-delimited, \n value"'

        csv.eachCsvLine { tokens ->
            assertEquals(5, tokens.length)
        }
    }

    void testStringEachCsvLineWithSettings() {
        String csv = 'testx using "x" as ax separator char'

        csv.toCsvReader(['separatorChar': 'x']).eachLine { tokens ->
            assertEquals(3, tokens.length)
        }
    }

}
