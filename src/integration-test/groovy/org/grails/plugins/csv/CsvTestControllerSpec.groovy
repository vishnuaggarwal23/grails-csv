package org.grails.plugins.csv

import grails.test.mixin.TestFor
import spock.lang.Specification


@TestFor(CsvTestController)
class CsvTestControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
    }

    void "test render as csv"() {

        when: "The writeCsv action is executed without ant params"
        controller.writeCsv()

        then: "The response is correct"
        response.contentType == 'text/csv'
        response.contentAsString == '"x","y"\n"1","2"\n"3","4"\n"5","6"'
        !response.getHeader('Content-Disposition')
    }


    void "test render as csv as attachments"() {

        when: "The writeCsv action is executed with params containing filename"
        params.filename = 'my.csv'
        controller.writeCsv()

        then: "The response is correct"
        response.getHeader('Content-Disposition') == 'attachment; filename="my.csv";'
    }

    void "test render as csv as inline"() {

        when: "The writeCsv action is executed with params containing filename and attachment as false"
        params.filename = 'my.csv'
        params.attachment = false
        controller.writeCsv()

        then: "The response is correct"
        response.getHeader('Content-Disposition') == 'inline; filename="my.csv";'
    }

}
