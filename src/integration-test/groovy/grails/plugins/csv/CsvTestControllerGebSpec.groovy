package grails.plugins.csv

import geb.spock.GebSpec
import grails.testing.mixin.integration.Integration

@Integration
class CsvTestControllerGebSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }


    void "test writeCsv"() {
        when: "writeCsv action is visited"
        go '/csvTest/writeCsv'

        then:
        driver.pageSource.contains("\"x\",\"y\"")
        driver.pageSource.contains("\"1\",\"2\"")
        driver.pageSource.contains("\"3\",\"4\"")
        driver.pageSource.contains("\"5\",\"6\"")

    }
}
