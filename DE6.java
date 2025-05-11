/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class DE6 extends Locomotive
{
    private final static int MASS=125;//tons
    private final static double MASS_EMPTY=119.2;//tons (not used)
    private final static int LENGTH_MM=18640;//milimeters (not used)
    private final static double LENGTH=18.64;//meters
    private final static int FLAT=3000;//0% gradient (tons)
    private final static int UPHILL=1200;//2% gradient (tons)
    private final static int UPHILL_RAIN=1000;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class DE6
     */
    public DE6()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
