# Repair Agency 
Repair Agency is a Web Application developed to perform functionality of repair request management.
##Tools and Technologies:
* **Patterns**: MVC, Command, Factory, Singleton, Front-Controller, PRG
* **Back-end**: JSP, JSTL, Java Servlets, JDBC
* **Front-end**: HTML, CSS, Bootstrap4
* **Database**: MySQL
* **Libraries**: Reflections, Log4j, JUnit, Mockito
* **Server**: Apache Tomcat
##Features by Users role:
* **Common** 
  * Authorization and Authentication
  * Internationalization
* **Admin** 
  * Manage users
* **Customer** 
  * Create repair request
  * Manage account (top-up, view transactions history, pay for repair request)
  * Leave feedback to the master
  * View created repair request, customer and master details
  * View created repair requests, customers, masters and feedbacks lists with sorting, filtering and pagination
  * Edit own user data
* **Manager**
  * Manage repair requests
  * Manage account (top-up, view transactions history, transfer to customer account)
  * View repair request, customer and master details
  * View repair requests, customers, masters and feedbacks lists with sorting, filtering and pagination
  * Edit own user data
* **Master**
  * Manage repair requests
  * View repair request and customer details
  * View repair requests, customers and feedbacks lists with sorting, filtering and pagination
  * Edit own user data
##Building & Running the Project:
  1. Install a server Apache Tomcat v8.X
  2. Open MySQL in your workspace, initialize database from `mysql-sripts/init.sql` and start MySQL server
  3. Clone the project into IDE on your local machine
  4. Check database credentials in `src/main/webapp/META-INF/context.xml` and change username or password if needed
  5. Run the Web Application.