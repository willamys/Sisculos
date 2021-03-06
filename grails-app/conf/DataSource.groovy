dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    //cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
    flush.mode = 'manual' // OSIV session flush mode outside of transactional context
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "org.postgresql.Driver"
            dialect = "org.hibernate.dialect.PostgreSQLDialect"
        
            uri = new URI(System.env.DATABASE_URL?:"postgres://nxulkqxfimqaui:7IAhsl1mSbxooHxu7xq8d6Z3rt@ec2-54-235-147-211.compute-1.amazonaws.com:5432/d20n68kagf4mj3")

            url = "jdbc:postgresql://"+uri.host+uri.path
            username = uri.userInfo.split(":")[0]
            password = uri.userInfo.split(":")[1]
        }
    }
//	production {
//		dataSource {
//			dbCreate = "update"
//			driverClassName = "org.postgresql.Driver"
//			dialect = org.hibernate.dialect.PostgreSQLDialect
//		
//			uri = new URI(System.env.DATABASE_URL?:"postgres://test:test@localhost/test")
//
//			url = "jdbc:postgresql://"+uri.host+uri.path
//			username = uri.userInfo.split(":")[0]
//			password = uri.userInfo.split(":")[1]
//		}
//	}
	
}
