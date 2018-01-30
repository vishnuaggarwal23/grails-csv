Summary
===================
The Grails CSV Plugin allows you to easily parse and consume CSV data from a number of input sources. It supports complex parsing scenarios such as nested commas inside quotes, escaped tokens, multi-line quoted values and allows configuration of parsing options (separator char, escape char, text encoding, etc). It is based on Glen Smith (et. al.)'s OpenCSV project (http://opencsv.sourceforge.net)
This plugin adds two dynamic methods 'eachCsvLine' and 'toCsvReader' to each of the following classes:

- java.lang.String

- java.io.File

- java.io.InputStream

- java.io.Reader

Using it is extremely simple. On any instance of the four data types, call the 'eachCvsLine' method with a closure accepting the tokens (a String array) for each parsed line:

"hello, world, how, are, you".eachCsvLine { tokens -> //only one line in this case and tokens.length == 5 }

new File("iso3166Countries.csv").eachCsvLine { tokens -> new Country(tokens0, //ISO 3166 country name tokens1 (+)).save() //ISO 3166 2 letter character code }

Configuration

If you need to specify how the parsing should occur, you can construct your own csv reader with a map of configuration options and call the 'eachLine' method on the constructed reader:

anInputStream.toCsvReader(['charset':'UTF-8' (+)).eachLine { tokens -> … }

The supported config options:

- 'separatorChar': the character to use as the delimiter to separate the tokens. Defaults to the comma: ','

- 'quoteChar': the character indicating a quoted string is about to follow. Internal separatorChars can be inside the quoted string and they will not be split into tokens. Defaults to the double quote char: '"'

- 'escapeChar': the character to escape an immediately following character, indicating to the parser not to treat it as a special char. Defaults to the backslash char: ''

- 'skipLines': the number of lines in the input source to skip before parsing begins. This is useful to skip any potential CSV header lines that are not part of the CSV data. Defaults to zero '0'

- 'strictQuotes': if characters outside of quotes should be ignored (implying each individual token is quoted. Defaults to false

- 'ignoreLeadingWhiteSpace': white space in front of a quoted token is ignored. Defaults to true

- 'charset': use the specified charset when parsing an InputStream. The value can be either the Charset name as a String, a java.nio.charset.Charset instance, or a java.nio.charset.CharsetDecoder instance. Defaults to the system default charset. *Note that this option is ONLY valid for InputStream instances. It is ignored otherwise.


Installation
===================

Add dependency to your build.gradle for Grails 3.x:

```
repositories {
  ...
  maven { url "http://dl.bintray.com/sachinverma/plugins" }
}

dependencies {
    compile 'org.grails.plugins:grails-csv:1.0.1'
}
```

Usage
=====

Using it is extremely simple. On any instance of the four data types, call the 'eachCsvLine' method with a closure accepting the tokens (a String array) for each parsed line:

```
"hello, world, how, are, you".eachCsvLine { tokens ->
           //only one line in this case and tokens.length == 5
       }
```


```
new File("iso3166Countries.csv").eachCsvLine { tokens ->
           new Country(tokens[0],        //ISO 3166 country name
                       tokens[1]).save() //ISO 3166 2 letter character code
       }
```

Configuration
=====

By default, you don't need to configure anything. However, if you need to specify exactly how the parsing should occur, you can construct your own csv reader with a map of configuration options and call the 'eachLine' method on the constructed reader:

```
anInputStream.toCsvReader(['charset':'UTF-8']).eachLine { tokens ->
    …
}
```

List of maps using the CSVMapReader
========
A CSVMapReader class is also added for getting a list of maps from a CSV file. It has 2 constructors and an empty one File, Reader and String also have the toCsvMapReader() added to them

1. CSVMapReader(Reader reader) uses the default settings from OpenCSV
2. CSVMapReader(Reader reader,Map settingsMap) you can pass in a map from above but don't use skipLines as it will miss the the first line

CSVMapReader is iterable so all the groovy collection goodness can be used on it (each, eachWithIndex,collect, find, etc...)

If you are using a file or stream don't forget to call close when you are done on the csvMapReader so it cleans up the resources.

code examples

```
import grails.plugins.csv.CSVMapReader
 …
  //assumes the first line of the file has the field names
  new CSVMapReader(reader).each { map ->
     new Country(map).save() //assumes the keys match the Country properties
  }
```

```
def sample = '''col1|col2|col3
"val1"|val2|"val3"
"10"|20|"30,3"
'''
def settings = [separatorChar:'|']
sample.toCsvMapReader(settings).eachWithIndex{ map,i ->
   if(0==i){
      assert [col1:'val1',col2:'val2',col3:'val3'] == map
   if(1==i){
      assert [col1:'10',col2:'20',col3:'30,3'] == map
}

//or setup your own fieldKeys
def csvMapReader = new CSVMapReader(new StringReader(sample),settings )
csvMapReader.fieldKeys = ['gogo','gadget','csv'] //if you set this then first row in CSV is considered real data
def listomaps = new csvMapReader.toList() //<- alternate for a list of maps
assert  [gogo:'val1',gadget:'val2',csv:'val3']  == listomaps[1]
```

Readinging into a batched list by setting batchSize
===

If this is >0 then on each iteration, instead of a map, will return a list of maps of this batchSize and returnAll or toList will return a list of lists(batches) of maps based on the batchSize For example: this is usefull if you have a million rows in the CSV and want each iteration to return a list(batch) of 50 rows(maps) at a time so you can process inserts into the db in batches of 50.

```
def reader = new File("resources/${csvFile?:name}.csv").toCsvMapReader([batchSize:50])
   reader.each{ batchList ->
      City.withTransaction{
         batchList.each{ map ->
            saverService.save(map)
         }
         cleanUpGorm()
      } //end transaction
   }
```

CVSWriter Builder
===
Writes CSV content to a given writer, using a definition DSL. This class is NOT threadsafe. Examples:
```
def sw = new StringWriter()
   def b = new CSVWriter(sw, {
      col1 { it.val1 }
      col2:"foo" { it.val2 }
   })
   b << [val1: 'a', val2: 'b']
   b << [val1: 'c', val2: 'd']
assert b.writer.toString() == '''"col1","foo"
"a","b"
"c","d"'''
```

Source: [https://grails.org/plugin/csv?skipRedirect=true]


