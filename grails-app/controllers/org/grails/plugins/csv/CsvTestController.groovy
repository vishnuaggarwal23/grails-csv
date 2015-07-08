package org.grails.plugins.csv

class CsvTestController {

    static scope = "request"

    def index() {
        println "---------------------"
        println grailsApplication.mainContext['org.grails.plugins.csv.CsvTestController']
     render ("Welcome to Grails CSV"+ grailsApplication.config.spring.groovy.template)
    }

    def writeCsv = {
        def rows = []
        rows << [1,2]
        rows << [3,4]
        rows << [5,6]

        renderCsv(rows: rows, filename: params.filename, attachment: params.attachment) {
            x { it[0] }
            y { it[1] }
        }
    }
}
