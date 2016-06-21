In order to get these files to run you will need to 
1) Use Java Enterprise Edition
2) Install Apache Tomcat 7
3) Modify the Java Build Path to refer to where your Tomcat servlet-api.jar 
   files are instead of where mine are
   
Servlet Examples
  - Servlet examples are in the WebExamples project,
    .java files are in WebExamples/src/web
	.html files are in WebExamples/WebContent
	
voting.html
Voting.java
  - This pair of files show simple access of information entered into a form
  
voting-forwarding.html
VoteForward.java
canvote.html
noteligible.html
  - These files shows use of a servlet to forward to HTML files, the same
    technique can be used to forward to a JSP
  - Start with the voting-forwarding.html, when you submit the VoteForward
    servlet runs and either forwars to canvote.html or noteligible.html
	
enter-name.html
NameEntry.java
RetrieveName.java
  - Here we store the user's name on the session.
  - Later we can access the session to retrieve the name.
  
enter-city.html
CityEntry.java
CityListener.java
  - This example is similar to the previous name example.  However, one problem
    with the name example is that the data is not initialized.  
  - Here we use a Listener to initialize the city attribute to Palo Alto.
   
