//grails.project.class.dir = "target/classes"
//grails.project.test.class.dir = "target/test-classes"
//grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

	inherits("global") {
    
    }
    log "warn" 
    repositories {
        grailsCentral()
    }
    dependencies {
		
    }

    plugins {
        build(":release:2.2.0", ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
