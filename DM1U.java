/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class DM1U extends WorkTrains//URV   Utility transport
{
    private final static double MASS=10.4;//tons
    private final static int MASS_EMPTY=10;//tons (not used)
    private final static int LENGTH_MM=14470;//milimeters (not used)
    private final static double LENGTH=14.47;//meters
    private final static int FLAT=0;//0% gradient (tons)
    private final static int UPHILL=0;//2% gradient (tons)
    private final static int UPHILL_RAIN=0;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class DM1U
     */
    public DM1U()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
