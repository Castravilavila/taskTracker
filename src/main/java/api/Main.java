package api;

import dto.TaskDTO;
import service.TaskTrackerService;
import service.impl.TaskTrackerServiceImpl;
import storage.TaskTrackerStorage;
import validation.TaskTrackerValidator;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskTrackerStorage taskTrackerStorage = new TaskTrackerStorage();
        TaskTrackerValidator taskTrackerValidator = new TaskTrackerValidator(taskTrackerStorage);
        TaskTrackerService taskTrackerService = new TaskTrackerServiceImpl(taskTrackerStorage,taskTrackerValidator);
        TaskTrackerAPI api = new TaskTrackerAPI(taskTrackerStorage,taskTrackerValidator,taskTrackerService);

        //When you run main class you will have the possibility to use the api
        //from cli , just write the commands as presented with whatever parameters you like

        Scanner scanner = new Scanner(System.in);
        boolean stopProgram = false;
        while (!stopProgram){
            System.out.println("--------------------------------------------");
            System.out.println("%%%Please write one of the next commands: " +
                    "\n createTask('personName','taskNamee')" +
                    "\n startTask('taskName')" +
                    "\n stopTask('taskName')" +
                    "\n getAllTasksByPerson('personName')" +
                    "\n stopProgram");
            String command = scanner.nextLine();

            if (command!=null && command.length()>=11){
                if(extractCreateTaskApi(command).equalsIgnoreCase("createTask")){
                    if (countCharInStr(command,'\'')==4){
                        String[] parameters = getParametersFromTwoParametersApi(command);
                        api.createTask(parameters[0],parameters[1]);
                    }
                } else if(extractStartTaskApi(command).equalsIgnoreCase("startTask")){
                    if (countCharInStr(command,'\'')==2){
                        String parameter = getParameterFromOneParameterApi(command);
                        api.startTask(parameter);
                    }
                } else if(extractStopTaskApi(command).equalsIgnoreCase("stopTask")){
                    if (countCharInStr(command,'\'')==2){
                        String parameter = getParameterFromOneParameterApi(command);
                        api.stopTask(parameter);
                    }
                }else if(extractGetAllTasksByPersonApi(command).equalsIgnoreCase("getAllTasksByPerson")){
                    if (countCharInStr(command,'\'')==2){
                        String parameter = getParameterFromOneParameterApi(command);

                        ArrayList<TaskDTO>tasks = api.getAllTasksByPerson(parameter);
                    }
                }else if(extractStopProgramApi(command).equalsIgnoreCase("stopProgram")){
                    System.out.println("stopping...");
                        System.exit(0);
                }
            }
            System.out.println("would you like to execute another command?(y/n)");
            String secondCommand = scanner.nextLine();
            if (secondCommand!=null){
                if (secondCommand.equalsIgnoreCase("n")){
                    System.exit(0);
                }
            }
        }
    }

    public static String getParameterFromOneParameterApi(String str) {
        boolean flag = false;
        String strToReturn;
        String strToEdit = str;

        int startPos = strToEdit.indexOf('\'');
        strToEdit = strToEdit.substring(startPos + 1);
        int finPos = strToEdit.indexOf('\'');
        strToReturn = strToEdit.substring(0, finPos);
        strToEdit = strToEdit.substring(finPos + 1);

        return strToReturn;
    }

    public static String[] getParametersFromTwoParametersApi(String str){
        boolean flag = false;
        String[] arrayOfParameters= new String[2];
        int i=0;
        String strToEdit = str;
        while (!flag){
            int startPos = strToEdit.indexOf('\'');
            strToEdit = strToEdit.substring(startPos+1);
            int finPos = strToEdit.indexOf('\'');
            arrayOfParameters[i] = strToEdit.substring(0,finPos);
            strToEdit = strToEdit.substring(finPos+1);
            i++;

            if (arrayOfParameters[1]!=null){
                flag=true;
            }
        }
        return arrayOfParameters;
    }

    public static int countCharInStr(String str,char ch){
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }
    public static String extractParameterFromParanthesis(String str){
        return str.substring(str.indexOf("(")+1, str.indexOf(")"));
    }
    public static String extractCreateTaskApi(String str){
        return str.substring(0,10);
    }
    public static String extractStartTaskApi(String str){
        return str.substring(0,9);
    }
    public static String extractStopTaskApi(String str){
        return str.substring(0,8);
    }
    public static String extractGetAllTasksByPersonApi(String str){
        return str.substring(0,19);
    }
    public static String extractStopProgramApi(String str){
        return str.substring(0,11);
    }

}
