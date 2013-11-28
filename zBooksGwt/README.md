To make it works, you need to first install the library gwtrpc-spring situated in the lib folder with the following command :
mvn install:install-file -Dfile=gwtrpcspring-1.02.jar \
                             -DgroupId=org.gwtrpcspring \
                             -DartifactId=gwtrpcspring \
                             -Dversion=1.02 \
                             -Dpackaging=jar \
                             -DgeneratePom=true
