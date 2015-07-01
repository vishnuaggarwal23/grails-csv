package grails.csv

import au.com.bytecode.opencsv.CSVReader
import grails.plugins.*
import org.grails.plugins.csv.CSVMapReader
import org.grails.plugins.csv.CSVReaderUtils
import org.grails.plugins.csv.controller.RenderCsvMethod

class GrailsCsvGrailsPlugin extends Plugin {

    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "3.0.2 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    // TODO Fill in these fields
    def title = "Grails Csv" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/grails-csv"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    Closure doWithSpring() { {->
            // TODO Implement runtime spring config (optional)
        } 
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
        CSVReader.metaClass.eachLine = { closure ->
            CSVReaderUtils.eachLine((CSVReader) delegate, closure)
        }

        File.metaClass.eachCsvLine = {Closure closure ->
            CSVReaderUtils.eachLine((File) delegate, closure)
        }
        File.metaClass.toCsvReader = {Map settingsMap ->
            return CSVReaderUtils.toCsvReader((File)delegate, settingsMap)
        }
        File.metaClass.toCsvMapReader = {Map settingsMap ->
            return new CSVMapReader(new FileReader(delegate),settingsMap)
        }

        InputStream.metaClass.eachCsvLine = {Closure closure ->
            CSVReaderUtils.eachLine((InputStream) delegate, closure)
        }
        InputStream.metaClass.toCsvReader = {Map settingsMap ->
            return CSVReaderUtils.toCsvReader((InputStream)delegate, settingsMap)
        }

        Reader.metaClass.eachCsvLine = {Closure closure ->
            CSVReaderUtils.eachLine((Reader)delegate, closure)
        }
        Reader.metaClass.toCsvReader = {Map settingsMap ->
            return CSVReaderUtils.toCsvReader((Reader)delegate, settingsMap)
        }
        Reader.metaClass.toCsvMapReader = {Map settingsMap ->
            return new CSVMapReader(delegate,settingsMap)
        }

        String.metaClass.eachCsvLine = {Closure closure ->
            CSVReaderUtils.eachLine((String) delegate, closure)
        }
        String.metaClass.toCsvReader = {Map settingsMap ->
            return CSVReaderUtils.toCsvReader((String)delegate, settingsMap)
        }
        String.metaClass.toCsvMapReader = {Map settingsMap ->
            return new CSVMapReader(new StringReader(delegate),settingsMap)
        }

        grailsApplication.controllerClasses.each {
            it.clazz.metaClass.renderCsv = renderCsvMethod
        }
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
        if (grailsApplication.isControllerClass(event.source)) {
            event.source.metaClass.renderCsv = renderCsvMethod
        }
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }


    def renderCsvMethod = { Map args, Closure definition ->
        new RenderCsvMethod(delegate).call(args, definition)
        false
    }
}
