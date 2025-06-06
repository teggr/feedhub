///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS dev.jbang:jash:0.0.3

import static java.lang.System.*;
import static dev.jbang.jash.Jash.*;

public class deploy4j  {

    public static void main(String... args) {
        out.println("Deploying application");

        // TODO: use https://www.stringtemplate.org/download.html for the commands
        // TODO: command line builder

        // TODO: do i want to do the docker build too?
        $("java -version").stream().forEach(System.out::println);

        $("docker -v").stream().forEach(System.out::println);

        // docker build -t link-streams:0.0.1-SNAPSHOT . 
        // mvn verify

        $("docker run -p 8080:8080 --rm -e SPRING_PROFILES_INCLUDE=image -v .\\.data:/data link-streams:0.0.1-SNAPSHOT").stream().forEach(System.out::println);

        // TODO: distribute docker image to repository
        // TODO: ssh to machine
        // TODO: deploy my proxy
        // TODO: deploy my service (attach storage volume)
        
    }

}
