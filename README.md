# About

A small web project for CodeCool Personal Assessment. (Sloth Comics manga store).

## `DataSource`

Before deploying to a webserver create a `Resource` like in your webserver's config (e.g. for Apache Tomcat in `conf/context.xml`).

```
<Resource name="jdbc/Web_PA"
          type="javax.sql.DataSource"
          username="postgres"
          password="admin"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://localhost:5432/Web_PA"
          closeMethod="close"/>
```
