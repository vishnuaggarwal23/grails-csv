package org.grails.plugins.csv

import grails.test.mixin.integration.Integration
import grails.transaction.*

import spock.lang.*
import geb.spock.*

@Integration
@Rollback
class CsvTestControllerSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "canary test"() {
        when:"The home page is visited"
            go '/'

        then:"The title is correct"
        	$('title').text() == "Welcome to Grails"
    }
}
