package grails.plugins.csv

class CsvTestController {

    def writeCsv = {
        List<List<Integer>> rows = [[1, 2],
                                    [3, 4],
                                    [5, 6]]
        renderCsv(rows: rows, filename: params.filename, attachment: params.boolean('attachment')) {
            x { it[0] }
            y { it[1] }
        }
    }
}
