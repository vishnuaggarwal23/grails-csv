grails-csv
===================

The csv plugin provides utility methods and also support for reading/writing to csv files. It uses `opencsv` library.

Installation
===================

Add dependency to your build.gradle for Grails 3.x:

```
repositories {
  ...
  //maven { url "http://dl.bintray.com/sachinverma/plugins" } As Bintray services down so update with below repo url change.
  maven { url "https://softclinic.jfrog.io/artifactory/grails-plugins-release" }
}

dependencies {
    compile 'org.grails.plugins:csv:1.0.1'
}
```

For further info: [https://github.com/vsachinv/grails-csv/wiki/Grails-CSV]
