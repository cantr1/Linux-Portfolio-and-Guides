// Shell Emulator Java Project
// This code emulators a standard shell in a UNIX/Linux Distro

// Authentication: only one user for now --- three attempts
// Input Processing: several commands --- limited to simple commands

import java.util.Scanner;

public class JavaShell {
    // Authenticate user
    public static boolean authUser() {
        System.out.println("""
                     _                    ____  _          _ _\s
                    | | __ ___   ____ _  / ___|| |__   ___| | |
                 _  | |/ _` \\ \\ / / _` | \\___ \\| '_ \\ / _ \\ | |
                | |_| | (_| |\\ V / (_| |  ___) | | | |  __/ | |
                 \\___/ \\__,_| \\_/ \\__,_| |____/|_| |_|\\___|_|_|""");

        // Create Scanner Object for auth
        Scanner authScanner = new Scanner(System.in);

        // Receive user creds
        int attempts = 3;
        int authenticated = 0;
        while (authenticated == 0) {
            System.out.print("Username: ");
            String userName = authScanner.nextLine();
            if (userName.equals("kelz")) {
                while (attempts > 0) {
                    System.out.print("Password: ");
                    String userPass = authScanner.nextLine();
                    if (userPass.equals("pass123")) {
                        authenticated = 1;
                        return true;
                    } else {
                        System.out.println("Incorrect Password --- Attempts left = " + (attempts - 1));
                        attempts--;
                        if (attempts == 0){
                            System.out.println("Too many failed attempts. Exiting...");
                            System.exit(1); // Terminates the program
                        }
                    }
                }
            } else {
                System.out.println("Username not found...");
            }
        }
        return true;
    }


    // Process user input
    public static void processInput(String userInput){
        // Set some environment variables
        String currentDirectory = "home/kelz/";
        String userName = "kelz";

        switch(userInput){

            //ls -- lists contents of directory
            case "ls" -> {
                if (currentDirectory.equals("home/kelz/"))
                    System.out.println("Documents Screenshots JavaProjects Music GitRepos");
            }

            //hostname -- return hostname
            case "hostname" -> {
                System.out.println("javashell");
            }

            //arch -- return system architecture
            case "arch" -> {
                System.out.println("x86_64");
            }

            //free -- return memory info
            case "free" -> {
                System.out.println("""
                                       total        used        free      shared  buff/cache   available
                        Mem:        14201168      728216    13175756        3252      562576    13472952
                        Swap:        4194304           0     4194304""");
            }

            //date -- return date info
            case "date" -> {
                System.out.println("Mon Mar  3 19:20:09 CST 2025");
            }

            //empty -- return the cmd line
            case "" -> {
            }

            // clear -- clears screen
            case "clear" -> {
                for(int i=0;i<20;i++){
                    System.out.println();
                }
            }

            // pwd -- print current directory
            case "pwd" -> {
                System.out.println(currentDirectory);
            }

            // ping -- test network connections
            case "ping 8.8.8.8" -> {
                System.out.println("""
                        PING 8.8.8.8 (8.8.8.8) 56(84) bytes of data.
                        64 bytes from 8.8.8.8: icmp_seq=1 ttl=114 time=13.0 ms
                        64 bytes from 8.8.8.8: icmp_seq=2 ttl=114 time=12.5 ms
                        64 bytes from 8.8.8.8: icmp_seq=3 ttl=114 time=13.3 ms
                        64 bytes from 8.8.8.8: icmp_seq=4 ttl=114 time=11.3 ms""");
            }

            // whoami -- prints out username
            case "whoami" -> {
                System.out.println(userName);
            }

            // ps -- prints out processes
            case "ps" -> {
                System.out.println("""
                        PID TTY          TIME CMD
                          18879 pts/3    00:00:00 bash
                          21005 pts/3    00:00:00 ps""");
            }

            //lsmem -- list memory information
            case "lsmem" -> {
                System.out.println("""
                        RANGE                                  SIZE  STATE REMOVABLE  BLOCK
                        0x0000000000000000-0x00000000f7ffffff  3.9G online       yes   0-30
                        0x0000000100000000-0x0000000387ffffff 10.1G online       yes 32-112
                        
                        Memory block size:       128M
                        Total online memory:      14G
                        Total offline memory:      0B""");
            }

            // default to handle edge case
            default -> System.out.println(userInput + ": command not found");
        }
    }


    // Main program
    public static void main(String args[]){
        if (authUser());
        {
            boolean continueShell = true;

            while (continueShell) {
                // Create Scanner Object
                Scanner scanner = new Scanner(System.in);

                // Print shell line
                System.out.print("kelz@javashell:~$ ");

                // Receive Input
                String userInput = scanner.nextLine();

                // Process userInput
                if (userInput.equals("exit")) // exit -- exits the terminal and closes while loop
                    continueShell = false;
                else
                    processInput(userInput);
            }
        }
    }
}
