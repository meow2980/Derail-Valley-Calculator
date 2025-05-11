/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public abstract class WorkTrains extends Locomotive//to get Train and Locomotive information
{
    //uses extend Locomotive to get its instance variables and methods
    /**
     * Constructor for objects of class Locomotive
     */
    public WorkTrains(double mass,double length)
    {
        super(mass,length,0,0,0);
    }
    /**
     * Constructor for objects of class Locomotive
     */
    //used for work trains BE2-microshunter and DM1U-URV
    public WorkTrains(double mass,double length,int flat,int uphill,int rain)
    {
        super(mass,length,flat,uphill,rain);
    }
}
