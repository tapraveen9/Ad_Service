Setting up the demo<br>
1) Pre-requirements<br>
a)  Operating system , Windows xp and above<br>
b) Java 1.8 run time environment(make sure jre is setup in the system path as java home)<br>
c) Web server (preferably Apache Tomcat 9)


2) SET Up<br>
a) Download AdService.zip<br>
b) Unzip it in a directory of your choice<br>
c) AdServiceServer-0.0.1-SNAPSHOT.war is the project war file , please deploy it appropriately on a web server. Below is the reference to a guide to install tomcat 9 and deploy an web application<br>
(https://tomcat.apache.org/tomcat-9.0-doc/deployer-howto.html) <br>
d) Alternatively AdServiceServer folder is an eclipse project. You can import the project in the eclipse as maven dynamic web project , include tomcat as server and run it from eclipse. Depending on convenience either C) or D) should be done , not both<br>
e) In the unzipped directory , please open config.properties file and change the host name to the host name of the deployed application on your web server (default entry hostname=default in the properties file , takes http://localhost:8080/AdServiceServer as the value)<br>

f) In the directory , you will additionally find three files POST.txt , GET.txt , GETALL.txt . Please rename them POST.bat , GET.bat , GETALL.bat respectively<br>
g) Open cmd.exe and CD to the unzipped directory<br>

To create an ad please use command POST.BAT as shown below<br>




To retrieve ad based on partnerId please use GET.BAT as shown below<br>


To retrieve all existing ads , please use GETALL as shown below<br>




Discuss the advantages and disadvantages of your persistence mechanism.
            The current implementation uses Map as data store for persistence
                    Advantages:<br>
a) Easy to implement<br>
b) Faster access there by faster response time to the requests<br>
c) No complex underlying calls that cause overhead unlike a relational database<br>
Disadvantages:<br>
a) Data lost when the server restarts.so the actual notion of persistence is not met<br>
b) As the map is in memory , data storage is very limited<br>
c) Transaction mechanism is complex to implement as the data model grows and number of the requests grow<br>
d) Big hindrance to the scalability of the application<br>

Add a URL to return a list of all campaigns as JSON.
      GETALL specified above covers this scenario
Add support for multiple ad campaigns per partner
      	Can be implemented with minimal effort. Couldn’t implement it due to time constraint



Please feel free to reach out to me if you have any questions. Thank you
