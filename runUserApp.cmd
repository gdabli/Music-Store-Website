rem for Windows, execute UserApp
rem be sure to start and load hsqldb, also use ant config-hsqldb  before using this
java -javaagent:lib/eclipselink.jar -cp lib/eclipselink.jar;lib/javax.persistence_2.1.0.v201304241213.jar;lib/hsqldb.jar;bin cs636.music.presentation.UserApp
