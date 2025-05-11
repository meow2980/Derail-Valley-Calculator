import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
/**
 * provides the main and job weight
 * This is the primary class that does almost all of the work, and holds almost all of the information
 *
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class Train
{
    private static final boolean INCLUDE_LOCO_WEIGHT=true;//if to include the locos weight in calculations
    private static final DecimalFormat FMT2=new DecimalFormat("#,###,###.##");//lower than 1 billion will be formatted with commas, 2 decimal points
    private double mass;
    private double length;
    private static double offset;//if weight needs to be offset for some reason (+ or -)
    private String destination;
    private String track;//track at destination (for example b6i)
    private static ArrayList<Train> stages;
    private ArrayList<Locomotive> locos;
    private ArrayList<WorkTrains> workTrains;//should be one of each (5 max,total)
    private static int stage;
    /**
     * Constructor for objects of class Train
     */
    public Train()
    {
        mass=0;
        length=0;
        //offset=0;   (may uncomment if offset becomes static)
        destination="";
        track="";
        locos=new ArrayList<Locomotive>();
        workTrains=new ArrayList<WorkTrains>();
    }

    /**
     * Constructor for objects of class Train
     */
    public Train(double mass,double length)
    {
        this.mass=mass;
        this.length=length;
        //offset=0;   (may uncomment if offset becomes static)
        destination="";
        track="";
        locos=new ArrayList<Locomotive>();
        workTrains=new ArrayList<WorkTrains>();
    }

    /**
     * Adds the weight (tons) given to mass
     *
     * @param w, weight in tons to add
     * @return weight, the new weight of mass
     */
    public double addMass(double w)
    {
        mass+=w;
        return mass;
    }

    /**
     * sets the mass to the weight (tons) given
     *
     * @param w, weight in tons to set mass to
     * @return weight, the new weight of mass
     */
    public double setMass(double w)
    {
        mass=w;
        return mass;
    }

    /**
     * Adds the length (meters) given to length
     *
     * @param l, length in meters to add
     * @return length, the new lengh
     */
    public double addLength(double l)
    {
        length+=l;
        return length;
    }

    /**
     * Sets the length (meters) given to length
     *
     * @param l, length in meters to set
     * @return length, the new lengh
     */
    public double setLength(double l)
    {
        length+=l;
        return length;
    }

    private double getLocoMass()
    {
        //get locomotives mass of all locomotives in locos array
        double mass=0;//tons
        for(Locomotive l:locos)
            mass+=l.addMass(0);//works as a get method, (could also do l.mass instead)
        return mass;
    }

    private double getWorkTrainMass()
    {
        //get work train mass of all locomotives in workTrains array
        double mass=0;//tons
        for(WorkTrains w:workTrains)
            mass+=w.addMass(0);//works as a get method, (could also do w.mass instead)
        return mass;
    }

    private double getTotalStageLength()
    {//gets length of the stage(/train object)
        double length=0;//meters
        length+=addLength(0);//works as a get method, (could also do d.length instead)
        for(Locomotive l:locos)
            length+=l.addLength(0);//works as a get method, (could also do l.length instead)
        for(WorkTrains w:workTrains)
            length+=w.addLength(0);//works as a get method, (could also do w.length instead)
        return length;
    }

    private static String getTrainInformation()
    {
        String str="\n\tCurrent stage: "+stage+"\tCurrent offset: "+FMT2.format(offset)+"t";
        Train currentStage=stages.get(stage-1);
        if(currentStage.destination.length()>0||currentStage.track.length()>0)
            str+="\nCurrent destination: "+currentStage.destination.toUpperCase()+" "+currentStage.track.toUpperCase();
        double length=0;
        for(int i=0;i<stage;i++)
            length+=stages.get(i).getTotalStageLength();
        str+="\nCurrent total length: "+FMT2.format(length)+"m";
        double jobs=0,//train objects mass
        loco=0,//locos is the arraylist of locomotives
        wt=0;//workTrains is the arraylist of WorkTrains
        for(int i=0;i<stage;i++){
            jobs+=stages.get(i).addMass(0);//works as a get method, (could also do .mass instead)
            loco+=stages.get(i).getLocoMass();
            wt+=stages.get(i).getWorkTrainMass();
        }
        double totalMass=jobs+wt+offset;
        if(INCLUDE_LOCO_WEIGHT)//true by default
            totalMass+=loco;//wheither to include loco mass in calculations or not
        str+="\nCurrent total mass: "+FMT2.format(totalMass)+"t\t"+"Total job mass->"+FMT2.format(jobs)+"t\t"+"Total locos mass->"+FMT2.format(loco)+"t\t"+"Total Work Trains mass->"+FMT2.format(wt)+"t";
        double[] locoTonnage=new double[3];//index 0 holds flat, then uphill, then rain
        double[] workTrainTonnage=new double[3];//index 0 holds flat, then uphill, then rain
        for(int i=0;i<stage;i++){
            for(Locomotive d:stages.get(i).locos){
                locoTonnage[0]+=d.getFlat();
                locoTonnage[1]+=d.getUphill();
                locoTonnage[2]+=d.getRain();
            }
            for(WorkTrains d:stages.get(i).workTrains){
                workTrainTonnage[0]+=d.getFlat();
                workTrainTonnage[1]+=d.getUphill();
                workTrainTonnage[2]+=d.getRain();
            }
        }
        String[] grade=new String[3];
        grade[0]="flat (0%)";
        grade[1]="uphill (2%)";
        grade[2]="rain (2%)";
        str+="\n\t(following don't include work trains tonnage unless specified)";
        for(int i=0;i<3;i++){//goes through the tonnage ratings on each grade
            str+="\nCurrent "+grade[i]+" tonnage: "+FMT2.format(locoTonnage[i])+"t\t";
            if(totalMass!=0)//leaving possibility for negative, shouldn't be tho
                str+="ptw->"+FMT2.format(locoTonnage[i]/totalMass)+"\t";//avoiding error (otherwise ignoring)
            if(locoTonnage[i]<totalMass)
                str+="Over tonnage by->"+FMT2.format(totalMass-locoTonnage[i])+"t";
            else str+="Under tonnage by->"+FMT2.format(locoTonnage[i]-totalMass)+"t";
            str+="\tWork train tonnage->"+FMT2.format(workTrainTonnage[i])+"t";
        }
        return str;
    }

    public String toString()//displays current job/stage(/train object) info
    {
        String str="\n\tCurrent stage: "+stage+"\t(following don't include offset)";
        str+="\nCurrent destination: ";
        if(destination.length()>0||track.length()>0)
            str+=destination.toUpperCase()+" "+track.toUpperCase();
        else str+="N/A";//none entered
        str+="\nCurrent stage length: "+FMT2.format(getTotalStageLength())+"m";
        double jobs=mass,//train objects mass
        loco=getLocoMass(),//locos is the arraylist of locomotives
        wt=getWorkTrainMass();//workTrains is the arraylist of QorkTrains
        double totalMass=jobs+wt;
        if(INCLUDE_LOCO_WEIGHT)//true by default
            totalMass+=loco;//wheither to include loco mass in calculations or not
        str+="\nCurrent stage mass: "+FMT2.format(totalMass)+"t\t"+"Total job mass->"+FMT2.format(jobs)+"t\t"+"Total locos mass->"+FMT2.format(loco)+"t\t"+"Total work trains mass->"+FMT2.format(wt)+"t";
        double[] locoTonnage=new double[3];//index 0 holds flat, then uphill, then rain
        double[] workTrainTonnage=new double[3];//index 0 holds flat, then uphill, then rain
        for(Locomotive d:locos){
            locoTonnage[0]+=d.getFlat();
            locoTonnage[1]+=d.getUphill();
            locoTonnage[2]+=d.getRain();
        }
        for(WorkTrains d:workTrains){
            workTrainTonnage[0]+=d.getFlat();
            workTrainTonnage[1]+=d.getUphill();
            workTrainTonnage[2]+=d.getRain();
        }
        String[] grade=new String[3];
        grade[0]="flat (0%)";
        grade[1]="uphill (2%)";
        grade[2]="rain (2%)";
        str+="\n\t(following don't include work trains tonnage unless specified)";
        for(int i=0;i<3;i++){//goes through the tonnage ratings on each grade
            str+="\nCurrent "+grade[i]+" tonnage: "+FMT2.format(locoTonnage[i])+"t\t";
            if(totalMass!=0)//leaving possibility for negative, shouldn't be tho
                str+="ptw->"+FMT2.format(locoTonnage[i]/totalMass)+"\t";//avoiding error (otherwise ignoring)
            if(locoTonnage[i]<totalMass)
                str+="Over tonnage by->"+FMT2.format(totalMass-locoTonnage[i])+"t";
            else str+="Under tonnage by->"+FMT2.format(locoTonnage[i]-totalMass)+"t";
            str+="\tWork train tonnage->"+FMT2.format(workTrainTonnage[i])+"t";
        }
        return str;
    }

    public static void main(String[] args){main();}//purpose is just to have a normal main

    public static void main()//run()
    {
        stages=new ArrayList<Train>();
        stages.add(new Train());//to start with one stage
        stage=1;//first stage
        offset=0;//initializing offset to 0 (no offset)
        Scanner scan=new Scanner(System.in);
        System.out.println("\n\nTo get infomation on how to enter in information enter \"help\"");
        String response=scan.nextLine().trim().toLowerCase();
        while(!response.equals("end")&&!response.equals("quit"))
        {
            boolean added=true;//default sets to false
            int numToAdd=1;//only applies to locomotives
            if(response.contains(" ")&&response.charAt(0)!='-'&&isInt(getFirstWord(response))){//if first word is digit (adds that many locos)
                numToAdd=Integer.parseInt(response.substring(0,(response+" ").indexOf(" ")));
                response=next(response);
            }
            String previous=null;//used in a couple of the switches to catch errors
            switch(response.substring(0,(response+" ").indexOf(" ")))
            {
                //finds locos and work trains
                case "de2":
                    addLoco(new DE2(),numToAdd);
                    break;
                case "de6":
                    addLoco(new DE6(),numToAdd);
                    break;
                case "dm3":
                    addLoco(new DM3(),numToAdd);
                    break;
                case "dh4":
                    addLoco(new DH4(),numToAdd);
                    break;
                case "s282":
                    addLoco(new S282(),numToAdd);
                    break;
                case "s060":case "so6o":case "s06o":case "so60"://*idiot* proofing
                    addLoco(new S060(),numToAdd);
                    break;
                    //work trains
                case "be2":
                case "microshunter":
                    if(!addWorkTrain(new BE2()))
                        System.out.println("You already have a BE2, cannot have multiple");
                    break;
                case "dm1u":
                    previous="DM1U";
                case "urv":
                    if(previous==null)
                        previous="URV";
                    if(!addWorkTrain(new DM1U()))
                        System.out.println("You already have a "+previous+", cannot have multiple");
                    break;
                case "de6-860s":
                case "slug":
                    if(!addWorkTrain(new Slug()))
                        System.out.println("You already have a Slug, cannot have multiple");
                    break;
                case "caboose":
                    if(!addWorkTrain(new Caboose()))
                        System.out.println("You already have a Caboose, cannot have multiple");
                    break;
                case "flatcar":
                    if(!addWorkTrain(new Flatcar()))
                        System.out.println("You already have a Flatcar, cannot have multiple");
                    break;
                    //word cases
                    //if add (stage) (idea)
                case "add":
                    stage++;
                    while(stage<=stages.size())//removes previous stages
                        stages.remove(stage-1);
                    stages.add(new Train());
                    break;
                case "next":
                    stage--;//to hold previous
                    //if all stages used
                    if(stage==0){//may change this up later, has problems with mistakes (acts as total reset would)
                        if(response.length()>4)//if the response has multiple words
                            System.out.println("All the rest of this input after \"next\" will be removed, and required to be re-entered");
                        System.out.println("Reseting everthing due to no stages being left, no returning to previous");
                        main();
                        response="end";
                    }else System.out.println(getTrainInformation()+"\n\n");
                    break;
                case "previous":
                    if(stage<stages.size())//make sure it doesn't go past how many stages their are
                        stage++;//to get previous
                    else System.out.println("Cannot go to a previous stage, their are no previous stages");
                    break;
                case "offset":
                    previous="offset ";//incase there becomes an error, it can print out the removed messages
                    double previousOffset=0;//will be initialized to the correct value in the only case it could be reset
                    response=next(response);
                    if(getFirstWord(response).equals("add")){
                        previous+="add ";
                        response=next(response);
                    }
                    else{//will set offset to 0 before it would get added
                        if(getFirstWord(response).equals("set")){
                            previous+="set ";
                            response=next(response);
                        }
                        previousOffset=offset;//is the same as a getOffset() method
                        offset=0;
                    }
                    if(response.length()!=0&&response.charAt((response+" ").indexOf(" ")-1)=='t'&&isDouble(response.substring(0,(response+" ").indexOf(" ")-1)))
                        offset+=Double.parseDouble(response.substring(0,response.indexOf("t")));
                    else{//if offset was reset, will replace it to the origional, if there was an error
                        System.out.println("Error, invalid String: "+previous+response.substring(0,(response+" ").indexOf(" "))+"\n\tPlease re-enter in the correct format.\n\tYou can enter help to get help on how to enter in information.");
                        offset=previousOffset;//if was changed, return to origional state
                    }
                    break;
                case "help":
                    System.out.println("\nHelp menu:");
                    System.out.println("Enter job weight by entering a number and at the end an t, Example: 123.45t");
                    System.out.println("Enter job length by entering a number and at the end an m, Example: 123.45m");
                    System.out.println("Enter an offset to the weight by entering \"offset\" or \"offset set\" then the weight, Example: offset 123.45t (or offset set 123.45t)");
                    System.out.println("\tAdd to the offset by entering \"offset add\" then the weight, Example: offset add 123.45t");
                    System.out.println("Enter a location for delivery by entering in the location, Example: Machine Factory & Town");
                    System.out.println("\tEnter a track for delivery, Example: b6o or c4i   (optionaly can enter just b6 or c4 without the track type)");
                    System.out.println("Enter \"reset\" to reset the current stage/job to its innitial form");
                    System.out.println("\tEnter \"reset destination\" to reset the current stage/job's destination, this is done because otherwise trying to change the destination will only add to it");//could be changed
                    System.out.println("\t\tTo change the track destination just enter a new track");
                    System.out.println("\tEnter \"total reset\" to reset everything back to how it was at the start");
                    System.out.println("Enter \"information\" to get the information on the current job, like current tonnage, destination, etc (alternatively enter \"i\" or \"info\")");
                    System.out.println("Enter \"add\" to add a stage/job");
                    System.out.println("Enter \"next\" to go to the next stage/job");
                    System.out.println("\tEnter \"previous\" to go to the previous stage/job (if you do an add all previous stages will be removed)");
                    System.out.println("Enter the locomotive names to add it, Example DE2, s060");
                    System.out.println("\tTo enter multiple locomotives you can type the number, Example 5 DE6 (for 5 DE6 locomotives)");
                    System.out.println("\tEnter the work train names to add it, Example caboose, microshunter/be2,urv/dm1u,slug,flatcar. (note-slug does not affect tonnage)");
                    System.out.println("Enter \"current stage\" or \"current stage info\" to get the info attached to the current job/stage (not including other stages)");
                    System.out.println("\tEnter \"stage #\" or \"stage # info\" to get the info attached to the job/stage mentioned (job works instead of stage) (\"i\" or \"information\" work instead of \"info\")");
                    System.out.println("\n\tNote: Capitalisation doesn't matter, and will all be changed to lower case in outputs\n");
                    break;
                case "total":
                    if(response.indexOf("reset")==6){//totals length +1
                        if(response.length()>11)//if the response has multiple words (could change it to >0 with 2 response=next(response)'s before it)
                            System.out.println("All the rest of this input after \"reset\" will be removed, and required to be re-entered");
                        main();
                        response="end";
                    }else System.out.println("Error, total without a reset, re-enter \"total reset\" if you wish to reset the program");
                    break;
                case "reset":
                    if(getFirstWord(response).equals("track")){
                        stages.get(stage-1).track="";
                        break;}
                    response=next(response);
                    stages.get(stage-1).destination="";
                    //may want to remove this if and just have it to where only when the word after reset is destination it will reset the destination (to avoid accidental complete resets of stages)
                    if(!(getFirstWord(response).equals("destination")||getFirstWord(response).equals("destinations"))){//will reset everything if not specified
                        stages.set(stage-1,new Train());
                        response="temp "+response;
                    }
                    break;
                case "information":
                case "info":
                case "i"://so i can be lazy pretty much
                    System.out.println(getTrainInformation());
                    break;
                case "current":
                    response=next(response);
                    if(getFirstWord(response).equals("stage")||getFirstWord(response).equals("job")){
                        response=next(response);
                        switch(getFirstWord(response)){//made sense to have a switch statement for simplicity (otherwise i would have to have many equals methods)
                            case "i"://may want to remove
                            case "info":
                            case "information"://may want to remove
                                response=next(response);
                        }
                        System.out.println(stages.get(stage-1));
                    }else//error, message entered invalid
                        System.out.println("Error, don't understand \"current\" without \"stage\" or  \"job\" after, please re-enter");
                    response="temp "+response;//temp will be removed later
                    break;
                case "job":
                    previous="job";//incase there becomes an error, it can print out the removed messages
                case "stage":
                    response=next(response);
                    if(previous==null)//if not "job" (previous!="job")
                        previous="stage";//incase there becomes an error, it can print out the removed messages
                    if(response.length()>0&&response.charAt(0)!='-'&&isInt(getFirstWord(response))){//if second word is digit (char at the first letter of the second word)
                        int selectedStage=Integer.parseInt(response.substring(0,(response+" ").indexOf(" ")));
                        response=next(response);
                        previous+=selectedStage;
                        switch(getFirstWord(response)){//made sense to have a switch statement for simplicity (otherwise i would have to have many equals methods)
                            case "i"://may want to remove
                            case "info":
                            case "information"://may want to remove
                                response=next(response);
                        }
                        if(selectedStage>0&&selectedStage<=stage)
                            System.out.println(stages.get(selectedStage-1));
                        else System.out.println("Error, Invalid stage, you do not have that many stages, ");
                    }else System.out.println("Error, don't understand \""+previous+"\" without a number after, please re-enter");
                    response="temp "+response;//temp will be removed later
                    break;
                default:
                    added=false;
            }
            if(!added)
                if(response.charAt((response+" ").indexOf(" ")-1)=='m'&&isDouble(response.substring(0,(response+" ").indexOf(" ")-1)))
                    stages.get(stage-1).addLength(Double.parseDouble(response.substring(0,response.indexOf("m"))));
                else if(response.charAt((response+" ").indexOf(" ")-1)=='t'&&isDouble(response.substring(0,(response+" ").indexOf(" ")-1)))
                    stages.get(stage-1).addMass(Double.parseDouble(response.substring(0,response.indexOf("t"))));
                else if(isMessage(response.substring(0,(response+" ").indexOf(" ")))){
                    if(stages.get(stage-1).destination.length()!=0)
                        stages.get(stage-1).destination+=" ";//adds a space if previous words
                    stages.get(stage-1).destination+=response.substring(0,(response+" ").indexOf(" "));
                }else if((response+" ").indexOf(" ")==3&&Character.isDigit(response.charAt(1))&&isAlphabetic(response.charAt(0)+""+response.charAt(2))
                    ||(response+" ").indexOf(" ")==2&&Character.isDigit(response.charAt(1))&&isAlphabetic(response.charAt(0)+""))//If they dont include the type of track so instead of c4i c4 can be entered
                    stages.get(stage-1).track=response.substring(0,(response+" ").indexOf(" "));
                else
                    System.out.println("The word: \""+response.substring(0,(response+" ").indexOf(" "))+"\" is an invalid input (enter help for help on entering in information)");;//string was problematic
            if(!response.equals("end"))//if ended the program in switch case
                response=next(response);
            while(response.length()==0)//to make sure response never has 0 length
                response+=scan.nextLine().trim().toLowerCase();
        }
    }

    private static String next(String str)
    {
        if(!str.contains(" "))
            return "";
        else
            return str.substring(str.indexOf(" ")).trim();
    }

    private static boolean isAlphabetic(String str)
    {
        if(str.length()==0)
            return false;
        for(int i=0;i<str.length();i++)
            if(!Character.isAlphabetic(str.charAt(i)))
                return false;
        return true;
    }

    private static boolean isMessage(String str)//word(s), up to one & (should be isLocation, not sure why I named it this, but im not going to change it now)
    {//current use will not use while statement, could in future
        while(str.contains(" "))//removes spaces
            str=str.substring(0,str.indexOf(" "))+str.substring(str.indexOf(" ")+1);
        if(str.contains("&")){
            str=str.substring(0,str.indexOf("&"))+str.substring(str.indexOf("&")+1);
            if(str.length()==0)//if & was the only thing in the string
                return true;
        }
        return isAlphabetic(str);
    }

    private static boolean isInt(String str)//don't think I used fpr anything but as a helper method for isDouble
    {
        if(str.length()==0)
            return false;
        else if(str.charAt(0)=='-')//allowing positive and negative
            str=str.substring(1);
        for(int i=0;i<str.length();i++)
            if(!Character.isDigit(str.charAt(i)))
                return false;
        return true;
    }

    private static boolean isDouble(String str)
    {
        if(str.contains("."))//removes the decimal point and uses is Int
            str=str.substring(0,str.indexOf("."))+str.substring(str.indexOf(".")+1);
        return isInt(str);
    }

    private static void addLoco(Locomotive locoToAdd,int numToAdd)//used in switch statement
    {
        while(numToAdd-->0)
            stages.get(stage-1).locos.add(locoToAdd);
    }
    private static boolean addWorkTrain(WorkTrains workTrainToAdd)//returns true if added, returns false if not added because workTrain already exists
    {
        boolean added=true;
        for(Train t:stages)
            for(WorkTrains wt:t.workTrains)
                if(wt.getClass()==workTrainToAdd.getClass())
                    added=false;
        if(added)
            stages.get(stage-1).workTrains.add(workTrainToAdd);
        return added;
    }
    
    private static String getFirstWord(String str)//returns first word in the given string
    {//pre condition-str is trimmed & str.length()>0
        return str.substring(0,(str+" ").indexOf(" "));
    }
}
