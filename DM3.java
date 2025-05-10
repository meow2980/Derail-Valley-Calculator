/**
 * @author Aaron G
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class DM3 extends Locomotive
{
    private final static int MASS=52;//tons
    private final static double MASS_EMPTY=50.4;//tons (not used)
    private final static int LENGTH_MM=8600;//milimeters (not used)
    private final static double LENGTH=8.6;//meters
    private final static int FLAT=2000;//0% gradient (tons)
    private final static int UPHILL=500;//2% gradient (tons)
    private final static int UPHILL_RAIN=400;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class DM3
     */
    public DM3()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
