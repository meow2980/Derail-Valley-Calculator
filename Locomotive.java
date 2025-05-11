/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public abstract class Locomotive extends Train
{
    private int flat;//how much tons that can be pulled on 0% grade
    private int uphill;//how much tons that can be pulled up a 2% grade
    private int rain;//how much tons that can be pulled up a 2% grade with rain
    /**
     * Constructor for objects of class Locomotive
     */
    public Locomotive(double mass,double length,int flat,int uphill,int rain)
    {
        super(mass,length);
        this.flat=flat;
        this.uphill=uphill;
        this.rain=rain;
    }

    /**
     * gets the amount of tonnage that the locomotive can pull on a flat 0% grade
     *
     * @return flat, number of tons
     */
    public int getFlat()
    {
        return flat;
    }
    /**
     * gets the amount of tonnage that the locomotive can pull on a flat 0% grade
     *
     * @return flat, number of tons
     */
    public int getUphill()
    {
        return uphill;
    }
    /**
     * gets the amount of tonnage that the locomotive can pull on a flat 0% grade
     *
     * @return flat, number of tons
     */
    public int getRain()
    {
        return rain;
    }
}
