/**
 * @author Aaron G
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class DH4 extends Locomotive
{
    private final static double MASS=77.5;//tons
    private final static double MASS_EMPTY=74.2;//tons (not used)
    private final static int LENGTH_MM=12940;//milimeters (not used)
    private final static double LENGTH=12.94;//meters
    private final static int FLAT=2000;//0% gradient (tons)
    private final static int UPHILL=600;//2% gradient (tons)
    private final static int UPHILL_RAIN=500;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class DH4
     */
    public DH4()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
