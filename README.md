# About

A small web project for my CodeCool Personal Assessment. (Sloth Comics manga store).

To get admin privileges, use:  -email: user2@user2
                                -password: admin
                                
As an Admin, you'll have access to see the shop income, and orders.
Ability to upload books.

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
