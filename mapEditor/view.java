import java.util.Scanner;

public class view {
    public static void main(String... args)
    {
        
        Scanner sc=new Scanner(System.in);
        MapEditor map=new MapEditor();
        while(true){
            String l_userInput;
            l_userInput=sc.nextLine();
            String l_commands[]=l_userInput.split(" ");
            if(l_commands.length == 1 || l_commands.length == 2){
                switch(l_commands[0]){
                    case "showmap": 
                        map.showMap(); 
                        break;
                    case "savemap": 
                        map.write(l_commands[1]); 
                        break;
                    case "validatemap":
                        map.validateMap();
                        break;
                    case "exit":
                        sc.close();
                        return;
                default:
                    System.out.println("Invalid Input");
                }
            }
            else{
                switch(l_commands[0]){
                    case "editcontinent":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    map.addContinent(Integer.parseInt(l_commands[++l_i]), Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                case "-remove":
                                    map.removeContinent(Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                    break;
                            }
                        } 
                        break;
                    case "editcountry":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    map.addCountry(Integer.parseInt(l_commands[++l_i]), Integer.parseInt(l_commands[++l_i])); 
                                    l_i++;
                                    break;
                                case "-remove":
                                    map.removeCountry(Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                            }
                        }
                        break;
                    case "editneighbor":
                        for(int l_i=1;l_i<l_commands.length;)
                        {
                            switch(l_commands[l_i])
                            {
                                case "-add":
                                    map.addNeighbor(Integer.parseInt(l_commands[++l_i]),Integer.parseInt(l_commands[++l_i]));
                                    l_i++;
                                    break;
                                case "-remove":
                                    map.removeNeighbor(Integer.parseInt(l_commands[++l_i]), Integer.parseInt(l_commands[++l_i])); 
                                    l_i++;
                                    break;
                                default:
                                    System.out.println("Invalid Input");
                                break;
                            }
                        }
                        break;
                    case "showmap": 
                        map.showMap(); 
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            }
            
        }
        
    }
}