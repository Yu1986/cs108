Some very basic examples showing how Servlets and JSPs work.  

In order to get these files to run you will need to 
1) Use Java Enterprise Edition
2) Install Apache Tomcat 8
3) Modify the Java Build Path to refer to where your Tomcat servlet-api.jar 
   files are instead of where mine are

index.html 
  -- this file includes links to all the webpages (html, jsps, and servlets)
     associated with this download.
   
BasicExampleServlet.java
  - this example is in SimpleExamples/src/examples
    this shows a simple Servlet which places the current time on the webpage

FormServlet.java
  - this example shows reading information submitted from a form and using it
    when generating a webpage
  - this servlet is designed to be run from the form_examples.html webpage
   
jsp_example.jsp
  - this example is in SimpleExamples/WebContent
    this shows a simple JSP file which places the current time on the webpage

jsp_fancier.jsp
  - here we've added in a lot more code so which switch to a scriplet instead of
    just using a JSP expression

jsp_fancier_helper.jsp
  - this shows how to define a method in your JSP using a JSP declaration

jsp_form_example.jsp
  - this is an example of a JSP which reads information from a form and uses it
    in generating the HTML. 
  - this JSP is designed to be run from the form_examples.html webpage
  
Notice both examples do essentially the same thing, however the syntax used to
create the webpage is entirely different.  You may use Servlets or JSPs to
carry out the same task.

-----

starter-html5.html
  - HTML5 starter file
    Note that the starter-html.html file will display a validation error on 
    the closing BODY element until you add elements between the <body> start and </body> end tag.