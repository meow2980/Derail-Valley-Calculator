/**
 * @discord meow2980
 * @version 0.1           (first one, I expect updates)
 * @date 5/10/25
 * @build 99.4            (latest build of DV since last update)
 */
public class DE2 extends Locomotive
{
    private final static int MASS=38;//tons
    private final static int MASS_EMPTY=37;//tons (not used)
    private final static int LENGTH_MM=7600;//milimeters (not used)
    private final static double LENGTH=7.6;//meters
    private final static int FLAT=1200;//0% gradient (tons)
    private final static int UPHILL=300;//2% gradient (tons)
    private final static int UPHILL_RAIN=250;//2% gradient with rain (tons)
    /**
     * Constructor for objects of class DE2
     */
    public DE2()
    {
        super(MASS,LENGTH,FLAT,UPHILL,UPHILL_RAIN);
    }
}
