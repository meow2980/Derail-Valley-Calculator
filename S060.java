/**
 * @author Aaron G
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class S060 extends Locomotive
{
    private final static double MASS=50.7;//tons
    private final static int MASS_EMPTY=41;//tons (not used)
    private final static int LENGTH_MM=9320;//milimeters (not used)
    private final static double LENGTH=9.32;//meters
    private final static int FLAT=1500;//0% gradient (tons)
    private final static int UPHILL=400;//2% gradient (tons)
    private final static int UPHILL_RAIN=300;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class S060
     */
    public S060()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
